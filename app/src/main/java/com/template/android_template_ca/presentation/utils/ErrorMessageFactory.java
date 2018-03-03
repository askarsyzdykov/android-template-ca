package com.template.android_template_ca.presentation.utils;

import com.template.android_template_ca.R;
import com.template.android_template_ca.domain.exceptions.HttpException;
import com.template.android_template_ca.domain.exceptions.NoNetworkException;
import com.template.android_template_ca.system.ResourceManager;

import java.net.HttpURLConnection;

public class ErrorMessageFactory {

    public static String create(ResourceManager resourceManager, Throwable exception) {
        if (exception instanceof NoNetworkException) {
            return resourceManager.getString(R.string.error_no_internet_connection);
        } else if (exception instanceof HttpException) {
            int code = ((HttpException) exception).getErrorCode();
            switch (code) {
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    return resourceManager.getString(R.string.error_http_401);
            }
        }
        return resourceManager.getString(R.string.error_default_exception);
    }

}
