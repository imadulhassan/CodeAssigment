package com.codeassesment.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeassesment.repo.UserRepositoryImpl
import com.codeassesment.data.model.User
import com.codeassesment.data.remote.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: UserRepositoryImpl
) : ViewModel() {

    private val uiStateMld = MutableLiveData(UiState())
    private val userListMld = MutableLiveData<List<User>>()
    internal val uiState: LiveData<UiState> get() = uiStateMld
    val userList: LiveData<List<User>> get() = userListMld

    init {
       getUserList()
    }


    fun getUserList() = viewModelScope.launch(Dispatchers.IO) {
        uiStateMld.postValue(UiState(isLoading = true))
        val response = mainRepository.getUserList()
        var message = ""
        when (response.status) {
            Status.SUCCESS -> userListMld.postValue(response.data.orEmpty())
            Status.ERROR -> {
                message = response.message.toString()
                refreshUserList()
            }
        }
        uiStateMld.postValue(UiState(isLoading = false, message))
    }


    fun refreshUserList() = viewModelScope.launch(Dispatchers.IO) {
        uiStateMld.postValue(UiState(isLoading = true))
        val response = mainRepository.getUserForcefullyUpdate()
        var message = ""
        when (response.status) {
            Status.SUCCESS -> userListMld.postValue(response.data.orEmpty())
            Status.ERROR -> {
                message = response.message.toString()
            }
        }
        uiStateMld.postValue(UiState(isLoading = false, message))
    }



}
