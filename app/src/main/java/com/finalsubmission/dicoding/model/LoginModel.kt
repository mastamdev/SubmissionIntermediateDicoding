package com.dicoding.finalsubmission.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginModel (
    var name: String? = null,
    var userId: String? = null,
    var token: String? = null
): Parcelable