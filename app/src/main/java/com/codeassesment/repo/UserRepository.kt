package com.codeassesment.repo

import com.codeassesment.data.model.User
import com.codeassesment.data.remote.Resource

interface UserRepository {

    suspend fun getUserList(): Resource<List<User>>

    suspend fun getUserForcefullyUpdate(): Resource<List<User>>


}