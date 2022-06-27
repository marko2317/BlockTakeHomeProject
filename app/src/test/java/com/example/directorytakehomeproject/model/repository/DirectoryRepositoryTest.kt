package com.example.directorytakehomeproject.model.repository

import com.example.directorytakehomeproject.network.ApiCallType
import com.example.directorytakehomeproject.network.DirectoryResponse
import com.example.directorytakehomeproject.network.NetworkResponseWrapper
import com.example.directorytakehomeproject.network.RetrofitService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@OptIn(ExperimentalCoroutinesApi::class)
internal class DirectoryRepositoryTest {

    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(MockInterceptor())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()

    private val repository = DirectoryRepository(retrofit)

    @Test
    fun `WHEN fetchData for LOAD_FULL_DIRECTORY THEN return Success response`() = runTest {
        val result = repository.fetchData(ApiCallType.LOAD_FULL_DIRECTORY)
        Assert.assertNotNull(result)
        Assert.assertTrue(result is NetworkResponseWrapper.Success<DirectoryResponse>)

        val successResponse = result as NetworkResponseWrapper.Success<DirectoryResponse>

        val employees = successResponse.response.employees
        Assert.assertTrue(employees.isNotEmpty())
        Assert.assertTrue(employees.size == 11)
    }

    @Test
    fun `WHEN fetchData for LOAD_EMPTY_DIRECTORY THEN return Success response`() = runTest {
        val result = repository.fetchData(ApiCallType.LOAD_EMPTY_DIRECTORY)
        Assert.assertNotNull(result)
        Assert.assertTrue(result is NetworkResponseWrapper.Success<DirectoryResponse>)

        val successResponse = result as NetworkResponseWrapper.Success<DirectoryResponse>

        val employees = successResponse.response.employees
        Assert.assertTrue(employees.isEmpty())
    }

    @Test
    fun `WHEN fetchData for LOAD_MALFORMED_DIRECTORY THEN return Success response`() = runTest {
        val result = repository.fetchData(ApiCallType.LOAD_MALFORMED_DIRECTORY)
        Assert.assertNotNull(result)
        Assert.assertTrue(result is NetworkResponseWrapper.Success<DirectoryResponse>)

        val successResponse = result as NetworkResponseWrapper.Success<DirectoryResponse>

        val employees = successResponse.response.employees
        Assert.assertTrue(employees.isNotEmpty())
        Assert.assertTrue(employees.size == 11)
    }

    @Test
    fun `WHEN fetchData CATCHES a Throwable THEN return Error response`() = runTest {
        val result = repository.fetchData(ApiCallType.ERROR)

        Assert.assertNotNull(result)
        Assert.assertTrue(result is NetworkResponseWrapper.Error)
    }

    class MockInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith(FULL_EMPLOYEE_LIST) ->
                    MockResponseFileReader(SUCCESS_RESPONSE).content
                uri.endsWith(MALFORMED_EMPLOYEE_LIST) ->
                    MockResponseFileReader(MALFORMED_RESPONSE).content
                uri.endsWith(EMPTY_EMPLOYEE_LIST) ->
                    MockResponseFileReader(EMPTY_RESPONSE).content
                else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(HttpURLConnection.HTTP_OK)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray().toResponseBody(MEDIA_TYPE.toMediaTypeOrNull())
                )
                .build()
        }


    }

    private companion object {
        const val FULL_EMPLOYEE_LIST = "employees.json"
        const val MALFORMED_EMPLOYEE_LIST = "employees_malformed.json"
        const val EMPTY_EMPLOYEE_LIST = "employees_empty.json"
        const val SUCCESS_RESPONSE = "success_response.json"
        const val MALFORMED_RESPONSE = "malformed_response.json"
        const val EMPTY_RESPONSE = "empty_response.json"
        const val MEDIA_TYPE = "application/json"
    }
}