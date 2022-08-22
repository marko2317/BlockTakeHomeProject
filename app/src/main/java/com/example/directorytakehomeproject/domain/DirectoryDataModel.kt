package com.example.directorytakehomeproject.domain

import android.os.Parcelable
import com.example.directorytakehomeproject.network.DirectoryResponse
import com.example.directorytakehomeproject.network.NetworkResponseWrapper
import kotlinx.parcelize.Parcelize

sealed class DirectoryDataModel {

    @Parcelize
    data class SuccessDataModel(val employeesList: List<EmployeeDataModel>) : DirectoryDataModel(), Parcelable {

        @Parcelize
        data class EmployeeDataModel(
                val fullName: String,
                val phoneNumber: String,
                val email: String,
                val team: String,
                val photoUrlSmall: String,
                val photoUrlLarge: String,
                val biography: String,
                val employeeType: DirectoryResponse.EmployeeType
        ) : Parcelable

    }

    data class ErrorDataModel(val errorMessage: String) : DirectoryDataModel()

    companion object {
        fun fromNetworkResponse(networkResponseWrapper: NetworkResponseWrapper<DirectoryResponse>): DirectoryDataModel =
                when (networkResponseWrapper) {
                    is NetworkResponseWrapper.Success -> SuccessDataModel(
                            employeesList = networkResponseWrapper.response.employees.map { employee ->
                                SuccessDataModel.EmployeeDataModel(
                                        fullName = employee.fullName ?: "",
                                        phoneNumber = employee.phoneNumber ?: "",
                                        email = employee.email ?: "",
                                        team = employee.team ?: "",
                                        photoUrlSmall = employee.photoUrlSmall ?: "",
                                        photoUrlLarge = employee.photoUrlLarge ?: "",
                                        biography = employee.biography ?: "",
                                        employeeType = employee.employeeType
                                                ?: DirectoryResponse.EmployeeType.FULL_TIME
                                )
                            })
                    is NetworkResponseWrapper.Error -> ErrorDataModel(errorMessage = networkResponseWrapper.message)
                }

    }
}
