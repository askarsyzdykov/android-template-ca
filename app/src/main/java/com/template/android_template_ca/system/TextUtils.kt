package com.template.android_template_ca.system

import android.support.annotation.Nullable

/**
* Created by Askar Syzdykov on 2/22/18.
*/
class TextUtils {

    companion object {
        /**
         * Returns true if the string is null or 0-length.
         * @param str the string to be examined
         * @return true if str is null or zero length
         */
        fun isEmpty(@Nullable str: CharSequence?): Boolean {
            return str == null || str.isEmpty()
        }
    }

}