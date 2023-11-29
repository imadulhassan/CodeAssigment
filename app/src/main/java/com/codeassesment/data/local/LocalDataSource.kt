package com.codeassesment.data.local

import com.codeassesment.data.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val jsonLoader: Gson
) {


    fun loadUserListFromPref(): List<User>? {
        val serializedUserList = preferencesManager.loadUserList()
        val listType = object : TypeToken<List<User>>() {}.type
        return serializedUserList?.let { jsonLoader.fromJson(it, listType) }
    }


    fun saveUserListInPref(userList: ArrayList<User>?) {
        val listType = object : TypeToken<List<User>>() {}.type
        val serializedUserList = jsonLoader.toJson(userList, listType)
        preferencesManager.saveUserList(serializedUserList)
    }
}