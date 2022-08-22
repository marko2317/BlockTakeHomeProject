package com.example.directorytakehomeproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.directorytakehomeproject.domain.DirectoryDataModel
import com.example.directorytakehomeproject.model.repository.DirectoryRepository
import com.example.directorytakehomeproject.model.sideeffect.DirectorySideEffect
import com.example.directorytakehomeproject.model.state.DirectoryState
import com.example.directorytakehomeproject.network.ApiCallType
import com.example.directorytakehomeproject.network.RetrofitService
import com.example.directorytakehomeproject.view.DirectoryAdapter
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class DirectoryViewModel : ContainerHost<DirectoryState, DirectorySideEffect>, ViewModel(), DirectoryAdapter.Interactions {

    private val repository = DirectoryRepository(RetrofitService.getRetrofitInstance())

    override val container: Container<DirectoryState, DirectorySideEffect> =
            container(DirectoryState())

    init {
        loadData(ApiCallType.LOAD_FULL_DIRECTORY)
    }

    fun fetchData(apiType: ApiCallType) = intent {
        reduce { state.copy(directory = null) }
        loadData(apiType)
    }

    private fun loadData(apiType: ApiCallType) = intent {
        delay(1500)
        val result = repository.fetchData(apiType)
        reduce {
            state.copy(directory = DirectoryDataModel.fromNetworkResponse(result))
        }
    }

    override fun onEmployeeClicked(employee: DirectoryDataModel.SuccessDataModel.EmployeeDataModel) = intent {
        postSideEffect(DirectorySideEffect.EmployeeSummary(employee))
    }
}