package com.example.directorytakehomeproject.model.sideeffect

import com.example.directorytakehomeproject.domain.DirectoryDataModel

sealed class DirectorySideEffect {

    data class EmployeeSummary(val employee: DirectoryDataModel.SuccessDataModel.EmployeeDataModel) : DirectorySideEffect()
}
