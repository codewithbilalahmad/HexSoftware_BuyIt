package com.muhammad.buyit.data

import android.content.Context
import androidx.core.content.edit
import com.muhammad.domain.model.UserDomainModel

private const val USER_SESSION = "user_session"

class UserSession(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(USER_SESSION, Context.MODE_PRIVATE)
    fun insertUser(user: UserDomainModel) {
        sharedPreferences.edit {
            putInt("id", user.id!!)
            putString("username", user.username)
            putString("email", user.email)
            putString("name", user.name)
            apply()
        }
    }

    fun getUser(): UserDomainModel? {
        val id = sharedPreferences.getInt("id", 0)
        val username = sharedPreferences.getString("username", null)
        val email = sharedPreferences.getString("email", null)
        val name = sharedPreferences.getString("name", null)
        return if (id != 0) {
            UserDomainModel(
                id = id,
                username = username.orEmpty(),
                email = email.orEmpty(),
                name = name.orEmpty()
            )
        } else {
            null
        }
    }
}