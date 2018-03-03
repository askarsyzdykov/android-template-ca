package com.template.android_template_ca.system

import android.content.Context
import javax.inject.Inject

class ResourceManager @Inject constructor(private val context: Context) {

    fun getString(id: Int) = context.getString(id)

    fun getAssets() = context.assets

    fun getFilesDir() = context.filesDir
}