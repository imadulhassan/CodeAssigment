package com.codeassesment.data.local

import android.content.Context
import com.codeassesment.ui.main.MainActivity

class PreferencesManager {

  fun saveUserList(user: String) {
    val sharedPreferences = MainActivity.sharedContext?.getSharedPreferences(
      "assesment-preferences",
      Context.MODE_PRIVATE
    )
    sharedPreferences?.edit()?.putString("saved-user-list", user)?.apply()
  }

  fun loadUserList(): String? {
    val sharedPreferences = MainActivity.sharedContext?.getSharedPreferences(
      "assesment-preferences",
      Context.MODE_PRIVATE
    )
    return sharedPreferences?.getString("saved-user-list", null)
  }
}
