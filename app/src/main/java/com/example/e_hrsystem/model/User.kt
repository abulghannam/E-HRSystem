package com.example.e_hrsystem.model

import java.util.*

class User constructor(
        var username: String = "",
        var email: String? = null,
        var workingID: String? = null,
        var password: String? = null,
        var isAdmin: Boolean = false,
        var gender:String?="",
        var history: ArrayList<TimeLog>? = null

)