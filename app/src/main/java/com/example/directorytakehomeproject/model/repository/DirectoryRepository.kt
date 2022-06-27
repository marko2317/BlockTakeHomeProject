package com.example.directorytakehomeproject.model.repository

import com.example.directorytakehomeproject.network.ApiCallType
import com.example.directorytakehomeproject.network.DirectoryService
import com.example.directorytakehomeproject.network.NetworkResponseWrapper
import retrofit2.Retrofit

class DirectoryRepository(retrofit: Retrofit) {

    private val service = retrofit
        .create(DirectoryService::class.java)

    suspend fun fetchData(apiType: ApiCallType) = try {
        val result = when (apiType) {
            ApiCallType.LOAD_FULL_DIRECTORY -> service.getEmployeeList()
            ApiCallType.LOAD_EMPTY_DIRECTORY -> service.getEmptyEmployeeList()
            ApiCallType.LOAD_MALFORMED_DIRECTORY -> service.getMalformedEmployeeList()
            else -> throw Throwable(ERROR_MESSAGE)
        }
        NetworkResponseWrapper.Success(result)
    } catch (throwable: Throwable) {
        NetworkResponseWrapper.Error(message = ERROR_MESSAGE)
    }

    private companion object {
        const val ERROR_MESSAGE = "Something unexpected happen"
    }
}