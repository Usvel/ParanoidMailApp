package com.example.paranoid.common.utils

import android.app.Application
import androidx.annotation.StringRes
import retrofit2.HttpException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object Utils {

    private var application: Application? = null

    fun init(application: Application) {
        Utils.application = application
    }

    fun Exception.isNetworkError(): Boolean {
        return this is SocketTimeoutException ||
                this is ConnectException ||
                this is NoRouteToHostException ||
                this is UnknownHostException ||
                this is HttpException && this.code() >= 500
    }

    fun getString(@StringRes id: Int, vararg parameters: Any): String {
        return application?.getString(id, *parameters)
            ?: throw IllegalStateException(
                "Application context in Utils not initialized.Please " +
                        "call method init in your Application instance"
            )
    }
}
