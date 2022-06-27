package com.example.directorytakehomeproject.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.directorytakehomeproject.R
import com.example.directorytakehomeproject.databinding.EmployeeViewHolderBinding
import com.example.directorytakehomeproject.domain.DirectoryDataModel

class DirectoryAdapter(
    var dataModel: DirectoryDataModel.SuccessDataModel
) : RecyclerView.Adapter<DirectoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_view_holder, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindEmployee(dataModel.employeesList[position])
    }

    override fun getItemCount() = dataModel.employeesList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = DataBindingUtil.bind<EmployeeViewHolderBinding>(view)

        fun bindEmployee(employee: DirectoryDataModel.SuccessDataModel.EmployeeDataModel) {
            binding?.employeeFullName?.text = employee.fullName
            binding?.employeeTeam?.text = employee.team
            binding?.employeeEmail?.text = employee.email
            binding?.employeePhoneNumber?.text = employee.phoneNumber
            binding?.employeePhoto?.load(employee.photoUrlSmall) {
                crossfade(true)
                transformations(RoundedCornersTransformation())
                placeholder(android.R.drawable.ic_dialog_alert)
            }
        }
    }
}