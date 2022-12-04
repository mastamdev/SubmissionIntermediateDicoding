package com.finalsubmission.dicoding.preferences

import android.content.Context
import com.dicoding.finalsubmission.model.LoginModel

class LoginPref(context: Context) {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()

    fun setUser(name: String, userId: String, token: String) {
        editor.putString(NAME, name)
        editor.putString(TOKEN, token)
        editor.putString(ID, userId)
        editor.apply()
    }
    fun getUser(): LoginModel {
        val model = LoginModel()
        model.name = pref.getString(NAME, "").toString()
        model.token = pref.getString(TOKEN, "").toString()
        model.userId = pref.getString(ID, "").toString()
        return model
    }
    fun delUser() {
        editor.clear()
        editor.apply()
    }

    companion object{
        const val PREF_NAME = "user_pref"
        const val NAME = "name"
        const val TOKEN = "token"
        const val ID = "id"
    }
}