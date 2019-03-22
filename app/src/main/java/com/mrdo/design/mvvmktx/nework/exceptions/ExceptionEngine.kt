package com.mrdo.design.mvvmktx.nework.exceptions

import android.net.ParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

object ExceptionEngine {
    //未知错误
    private const val UN_KNOWN_ERROR = 1000
    //解析数据错误
    private const val ANALYTIC_SERVER_DATA_ERROR = 1001
    //网络连接错误
    private const val CONNECT_ERROR = 1002
    //网络连接超时
    private const val TIME_OUT_ERROR = 1003
    //服务器错误
    private const val SERVER_ERROR = 1004

    fun handleException(e: Throwable): ApiException {

        return when (e) {
            is HttpException -> {
                ApiException(e.code(), "网络错误")
            }

            is ServerException -> {
                ApiException(SERVER_ERROR, e.msg)
            }

            is JSONException, is JSONException, is ParseException -> {
                ApiException(ANALYTIC_SERVER_DATA_ERROR, "解析错误")
            }

            is ConnectException -> {
                ApiException(CONNECT_ERROR, "连接失败")
            }

            is SocketTimeoutException -> {
                ApiException(TIME_OUT_ERROR, "网络超时")
            }

            else -> {
                val erroMsg = e.message
                if (erroMsg.isNullOrEmpty())
                    ApiException(UN_KNOWN_ERROR, "未知错误")
                else
                    ApiException(UN_KNOWN_ERROR, erroMsg)
            }
        }

    }
}