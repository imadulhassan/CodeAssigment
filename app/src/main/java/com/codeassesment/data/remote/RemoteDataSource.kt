package com.codeassesment.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiEndPpoint: UserApis) {


   suspend fun loadUserList() = apiEndPpoint.getUserList("50")

}
