package com.template.android_template_ca.presentation.common

import com.pixplicity.easyprefs.library.Prefs
import com.template.android_template_ca.system.TextUtils

class AuthUtils : AuthKeyValueStorage {

    override fun saveToken(token: String) {
        Prefs.putString(AuthKeyValueStorage.TOKEN_KEY, token)
    }

    override fun getToken(): String = Prefs.getString(AuthKeyValueStorage.TOKEN_KEY, "")

    override fun isAuthorized() = !TextUtils.isEmpty(getToken())

}
