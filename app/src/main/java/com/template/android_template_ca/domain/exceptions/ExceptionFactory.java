package com.template.android_template_ca.domain.exceptions;

import java.net.ConnectException;
import java.net.UnknownHostException;

/**
 * Created by Askar on 12/9/17.
 */

public class ExceptionFactory {

    public static Throwable getException(Throwable exception) {
        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            return new NoNetworkException(exception);
        } else if (exception instanceof retrofit2.HttpException) {
            return new HttpException(exception);
        }
        return exception;
    }

}
