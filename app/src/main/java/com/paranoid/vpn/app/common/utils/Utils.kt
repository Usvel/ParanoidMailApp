package com.paranoid.vpn.app.common.utils

import android.app.Application
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.StringRes
import com.paranoid.vpn.app.common.ui.base.MessageData
import retrofit2.HttpException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.regex.Pattern

private const val NETWORK_CODE_500 = 500

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
                this is HttpException &&
                this.code() >= NETWORK_CODE_500
    }

    fun getString(@StringRes id: Int, vararg parameters: Any): String {
        return application?.getString(id, parameters)
            ?: throw IllegalStateException(
                "Application context in Utils not initialized.Please " +
                        "call method init in your Application instance"
            )
    }

    fun makeToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }
}

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence?.isValidPassword(): Boolean {
    val passwordPattern: Pattern = Pattern.compile(
        "[a-zA-Z0-9]{8,24}"
    )

    return !isNullOrEmpty() && passwordPattern.matcher(this).matches()
}

fun CharSequence?.isValidName(): Boolean {
    val namePattern: Pattern = Pattern.compile(
        "[a-zA-Z0-9]{2,10}"
    )

    return isNullOrEmpty() || namePattern.matcher(this).matches()
}

fun CharSequence?.isValidUrl() = isNullOrEmpty() || Patterns.WEB_URL.matcher(this).matches()

enum class VPNState {
    CONNECTED, NOT_CONNECTED, ERROR
}

enum class UserLoggedState {
    USER_LOGGED_IN,
    USER_LOGGED_OUT
}

sealed class NetworkStatus<T>(
    val data: T? = null,
    val messageData: MessageData? = null
) {
    class Success<T>(data: T? = null) : NetworkStatus<T>(data)
    class Loading<T>(data: T? = null) : NetworkStatus<T>(data)
    class Error<T>(messageData: MessageData?, data: T? = null) : NetworkStatus<T>(data, messageData)
}

class Validators {
    companion object {
        private val PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
        )

        private fun validate(ip: String?): Boolean {
            return PATTERN.matcher(ip).matches()
        }

        fun validateIP(ips: List<String>): Boolean {
            for (ip in ips)
                if (!validate(ip))
                    return false
            return true
        }
    }
}

enum class ClickHandlers {
    GetConfiguration, SetConfiguration, QRCode, Edit, Share
}