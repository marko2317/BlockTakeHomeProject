package com.example.directorytakehomeproject.network

import com.google.gson.annotations.SerializedName

data class DirectoryResponse(
    @SerializedName("employees")
    val employees: List<Employee>
) {

    data class Employee(
        @SerializedName("uuid")
        val uuid: String? = null,
        @SerializedName("full_name")
        val fullName: String? = null,
        @SerializedName("phone_number")
        val phoneNumber: String? = null,
        @SerializedName("email_address")
        val email: String? = null,
        @SerializedName("biography")
        val biography: String? = null,
        @SerializedName("photo_url_small")
        val photoUrlSmall: String? = null,
        @SerializedName("photo_url_large")
        val photoUrlLarge: String? = null,
        @SerializedName("team")
        val team: String? = null,
        @SerializedName("employee_type")
        val employeeType: EmployeeType? = null
    )

    enum class EmployeeType(val type: String) {
        FULL_TIME("Full Time"), PART_TIME("Part Time"), CONTRACTOR("Contractor")
    }
}
