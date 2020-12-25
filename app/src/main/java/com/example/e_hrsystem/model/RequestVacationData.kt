package com.example.e_hrsystem.model

import java.text.SimpleDateFormat
import java.util.*

class RequestVacationData(

        var type: String? = "",
        var startDateVac: String? = null,
        var endDateVac: String? = null,
        var moreInfo: String? = null,
        var id: String? = "",
        var isApproved: String? = null,
        var username: String? = ""
) {
    fun isValidStartDate(startDateVac: String): Boolean {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val currentStartDate = simpleDateFormat.parse(this.startDateVac ?: "") ?: Date()
        val newStartDate = simpleDateFormat.parse(startDateVac) ?: Date()
        return newStartDate.after(currentStartDate)
    }
}