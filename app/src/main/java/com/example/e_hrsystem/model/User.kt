package com.example.e_hrsystem.model

import java.util.*

class User constructor(
        var username: String = "",
        var email: String? = null,
        var workingID: String? = null,
        var isAdmin: Boolean = false,
        var gender:String?="",
        var isDeleted: Boolean = false,
        var history: ArrayList<TimeLog>? = null

)