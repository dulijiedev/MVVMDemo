package com.mrdo.design.mvvmktx.nework.exceptions

import java.lang.RuntimeException

/**
 * Created by dulijie on 2019/3/22.
 */
class ServerException(val code: Int, val msg: String) : RuntimeException()