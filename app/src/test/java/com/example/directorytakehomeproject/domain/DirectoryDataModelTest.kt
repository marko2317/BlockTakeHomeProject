package com.example.directorytakehomeproject.domain

import com.example.directorytakehomeproject.network.DirectoryResponse
import com.example.directorytakehomeproject.network.NetworkResponseWrapper
import org.junit.Assert
import org.junit.Test

internal class DirectoryDataModelTest {

    @Test
    fun `GIVEN a NetworkResponseWrapper Success THEN SuccessDataModel is parsed`() {
        val response = DirectoryResponse(
            employees = listOf(
                DirectoryResponse.Employee(
                    uuid = UUID,
                    fullName = FULL_NAME,
                    phoneNumber = PHONE_NUMBER,
                    email = EMAIL,
                    biography = BIOGRAPHY,
                    photoUrlSmall = PHOTO_URL_SMALL,
                    photoUrlLarge = PHOTO_URL_LARGE,
                    team = TEAM,
                    employeeType = EMPLOYEE_TYPE
                )
            )
        )
        val successResponse = NetworkResponseWrapper.Success(response)

        val dataModel = DirectoryDataModel.fromNetworkResponse(successResponse)

        Assert.assertTrue(dataModel is DirectoryDataModel.SuccessDataModel)

        val successDataModel = dataModel as DirectoryDataModel.SuccessDataModel
        val mockEmployee = response.employees[0]
        val parsedEmployee = successDataModel.employeesList[0]

        Assert.assertEquals(mockEmployee.fullName, parsedEmployee.fullName)
        Assert.assertEquals(mockEmployee.phoneNumber, parsedEmployee.phoneNumber)
        Assert.assertEquals(mockEmployee.email, parsedEmployee.email)
        Assert.assertEquals(mockEmployee.team, parsedEmployee.team)
        Assert.assertEquals(mockEmployee.photoUrlSmall, parsedEmployee.photoUrlSmall)
    }

    @Test
    fun `GIVEN a NetworkResponseWrapper Error THEN ErrorDataModel is parsed`() {
        val errorResponse = NetworkResponseWrapper.Error(ERROR_MESSAGE)

        val dataModel = DirectoryDataModel.fromNetworkResponse(errorResponse)

        Assert.assertTrue(dataModel is DirectoryDataModel.ErrorDataModel)

        val errorDataModel = dataModel as DirectoryDataModel.ErrorDataModel

        Assert.assertEquals(errorResponse.message, errorDataModel.errorMessage)
    }

    private companion object {
        const val UUID = "uuid"
        const val FULL_NAME = "John Smith"
        const val PHONE_NUMBER = "1234567890"
        const val EMAIL = "email@test.com"
        const val BIOGRAPHY = "biography"
        const val PHOTO_URL_SMALL = "photoUrlSmall"
        const val PHOTO_URL_LARGE = "photoUrlLarge"
        const val TEAM = "team"
        const val ERROR_MESSAGE = "error"
        val EMPLOYEE_TYPE = DirectoryResponse.EmployeeType.FULL_TIME
    }
}