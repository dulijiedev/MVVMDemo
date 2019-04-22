package com.mrdo.design.mvvmktx.datasource

import com.mrdo.design.mvvmktx.base.repository.BaseRepository
import com.mrdo.design.mvvmktx.base.repository.IRemoteDataSource

/**
 * Created by dulijie on 2019/4/22.
 */
interface IVerifyCodeDataSource : IRemoteDataSource {
    fun sendEmailCode(phoneNum: String)
}

class VerifyCodeDataSource : IVerifyCodeDataSource {
    override fun sendEmailCode(phoneNum: String) {
    }
}

class VerifyCodeRepository(datasource: IVerifyCodeDataSource) : BaseRepository<IVerifyCodeDataSource>(datasource) {
    fun sendEmail(phoneNum: String) = dataSource.sendEmailCode(phoneNum)
}