package com.example.directorytakehomeproject.domain

import com.example.directorytakehomeproject.network.DirectoryResponse
import com.example.directorytakehomeproject.network.NetworkResponseWrapper

sealed class DirectoryDataModel {

    data class SuccessDataModel(val employeesList: List<EmployeeDataModel>) : DirectoryDataModel() {

        data class EmployeeDataModel(
            val fullName: String,
            val phoneNumber: String,
            val email: String,
            val team: String,
            val photoUrlSmall: String
        )

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
                            photoUrlSmall = employee.photoUrlSmall ?: ""
                        )
                    })
                is NetworkResponseWrapper.Error -> ErrorDataModel(errorMessage = networkResponseWrapper.message)
            }

    }
}
