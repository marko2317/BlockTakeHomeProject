package com.example.directorytakehomeproject.model.state

import com.example.directorytakehomeproject.domain.DirectoryDataModel

data class DirectoryState(
    val directory: DirectoryDataModel? = null
) {
    val isLoading: Boolean
        get() = directory == null

    val isEmpty: Boolean
        get() {
            val successDataModel = directory as? DirectoryDataModel.SuccessDataModel
            return successDataModel?.employeesList?.isEmpty() ?: true
        }

    val isError: Boolean
        get() = directory is DirectoryDataModel.ErrorDataModel
}
