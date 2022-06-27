package com.example.directorytakehomeproject.network

import retrofit2.http.GET

interface DirectoryService {

    @GET("/sq-mobile-interview/employees.json")
    suspend fun getEmployeeList(): DirectoryResponse

    @GET("/sq-mobile-interview/employees_malformed.json")
    suspend fun getMalformedEmployeeList(): DirectoryResponse

    @GET("/sq-mobile-interview/employees_empty.json")
    suspend fun getEmptyEmployeeList(): DirectoryResponse
}