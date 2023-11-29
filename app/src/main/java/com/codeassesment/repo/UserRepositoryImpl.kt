package com.codeassesment.repo

import com.codeassesment.data.local.LocalDataSource
import com.codeassesment.data.model.User
import com.codeassesment.data.remote.RemoteDataSource
import com.codeassesment.data.remote.Resource
import com.codeassesment.data.remote.Status
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource
) : UserRepository {


    override suspend fun getUserList(): Resource<List<User>> {
        val response = localDataSource.loadUserListFromPref()
        return if (!response.isNullOrEmpty()) {
            Resource(Status.SUCCESS, response, "Success")
        } else {
            Resource(Status.ERROR, null, "Failed")
        }
    }


    override suspend fun getUserForcefullyUpdate(): Resource<List<User>> {
        val userReponsee = remoteDataSource.loadUserList()
        return if (userReponsee.isSuccessful) {
            val newUser = userReponsee.body()?.results
            localDataSource.saveUserListInPref(newUser)
            Resource(Status.SUCCESS, userReponsee.body()?.results, "Success")
        } else {
            Resource(Status.ERROR, null, "Failed")
        }
    }
}
