package com.template.android_template_ca.data.network.models

class ResponseWrapper<out T> constructor(val result: T, val errors: List<String>)