package com.example.directorytakehomeproject.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.directorytakehomeproject.R
import com.example.directorytakehomeproject.databinding.ActivityMainBinding
import com.example.directorytakehomeproject.domain.DirectoryDataModel
import com.example.directorytakehomeproject.model.sideeffect.DirectorySideEffect
import com.example.directorytakehomeproject.model.state.DirectoryState
import com.example.directorytakehomeproject.network.ApiCallType
import com.example.directorytakehomeproject.viewmodel.DirectoryViewModel
import org.orbitmvi.orbit.viewmodel.observe

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DirectoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel.observe(state = ::render, sideEffect = ::handleSideEffect, lifecycleOwner = this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.fetchData(ApiCallType.LOAD_FULL_DIRECTORY)
                true
            }
            R.id.action_empty_list -> {
                viewModel.fetchData(ApiCallType.LOAD_EMPTY_DIRECTORY)
                true
            }
            R.id.action_malformed_list -> {
                viewModel.fetchData(ApiCallType.LOAD_MALFORMED_DIRECTORY)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun render(state: DirectoryState) {
        showLoading(state.isLoading)
        showDirectory(state)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingIndicator.visibility = if (isLoading) {
            binding.directoryMessage.visibility = View.GONE
            binding.directory.visibility = View.GONE
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showDirectory(state: DirectoryState) {
        if (!state.isLoading) {
            if (state.isError) {
                val errorDataModel = state.directory as? DirectoryDataModel.ErrorDataModel
                errorDataModel?.errorMessage?.let { errorMessage ->
                    showErrorMessage(errorMessage)
                }
            } else {
                if (!state.isEmpty) {
                    state.directory?.let { directory ->
                        showNonEmptyDirectory(directory)
                    }
                } else {
                    showEmptyDirectory()
                }
            }
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        with(binding.directoryMessage) {
            visibility = View.VISIBLE
            text = errorMessage
        }
    }

    private fun showNonEmptyDirectory(directory: DirectoryDataModel) {
        binding.directoryMessage.visibility = View.GONE
        with(binding.directory) {
            val linearLayoutManager = LinearLayoutManager(this@MainActivity)
            layoutManager = linearLayoutManager
            adapter =
                    DirectoryAdapter(directory as DirectoryDataModel.SuccessDataModel, viewModel)
            visibility = View.VISIBLE
        }
    }

    private fun showEmptyDirectory() {
        binding.directory.visibility = View.GONE
        with(binding.directoryMessage) {
            visibility = View.VISIBLE
            text = getString(R.string.empty_state_message)
        }
    }

    private fun handleSideEffect(sideEffect: DirectorySideEffect) {
        if (sideEffect is DirectorySideEffect.EmployeeSummary) {
            val intent = Intent(this, EmployeeSummaryActivity::class.java).apply {
                putExtra(EMPLOYEE_BUNDLE_KEY, sideEffect.employee)
            }

            startActivity(intent)
        }
    }
}