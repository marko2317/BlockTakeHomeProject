package com.example.directorytakehomeproject.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.directorytakehomeproject.domain.DirectoryDataModel
import com.example.directorytakehomeproject.model.repository.DirectoryRepository
import com.example.directorytakehomeproject.model.sideeffect.DirectorySideEffect
import com.example.directorytakehomeproject.model.state.DirectoryState
import com.example.directorytakehomeproject.network.ApiCallType
import com.example.directorytakehomeproject.network.NetworkResponseWrapper
import com.example.directorytakehomeproject.network.RetrofitService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.io.*

class DirectoryViewModel : ContainerHost<DirectoryState, DirectorySideEffect>, ViewModel() {

    private val repository = DirectoryRepository(RetrofitService.getRetrofitInstance())
    private val gson = GsonBuilder().create()

    override val container: Container<DirectoryState, DirectorySideEffect> =
            container(DirectoryState())

    fun fetchData(apiType: ApiCallType, context: Context) = intent {
        reduce { state.copy(directory = null) }
        loadData(apiType, context)
    }

    private fun loadData(apiType: ApiCallType, context: Context) = intent {
        delay(1500)

        // if the file exists and have data
        // create the DM from input stream reader
        // else
        val result = repository.fetchData(apiType)
        if (result is NetworkResponseWrapper.Success) {
            val jsonString = gson.toJson(result.response)
            writeToDisk(jsonString, context)
        }
        reduce {
            state.copy(directory = DirectoryDataModel.fromNetworkResponse(result))
        }
    }

    private fun writeToDisk(data: String, context: Context) {
        try {
            val writter = OutputStreamWriter(context.openFileOutput("localDb.txt", Context.MODE_PRIVATE))
            writter.write(data)
            writter.close()
        } catch (e: IOException) {
            Log.e("Test", "File write failed! ${e.message}")
        }
    }

    private fun readFromDisk(context: Context): String? {
        return try {
            val inputStream = context.openFileInput("localDb.txt")
            inputStream?.let { stream ->
                val inputStreamReader = InputStreamReader(stream)
                val bufferReader = BufferedReader(inputStreamReader)
                var receivedString: String
                val stringBuilder = StringBuilder()

                while (bufferReader.readLine() != null) {
                    receivedString = bufferReader.readLine()
                    stringBuilder.append("/n").append(receivedString)
                }

                inputStream.close()
                stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            Log.e("Test", "File not founded")
            null
        } catch (e: IOException) {
            Log.e("Test", "Error reading file")
            null
        }
    }
}