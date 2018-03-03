package com.template.android_template_ca.presentation.utils;

import com.template.android_template_ca.system.TextUtils;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.Companion.isEmpty(target) && EMAIL_ADDRESS.matcher(target).matches();
    }

}
