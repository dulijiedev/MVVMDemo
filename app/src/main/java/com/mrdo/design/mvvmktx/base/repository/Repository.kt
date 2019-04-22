package com.mrdo.design.mvvmktx.base.repository

/**
 * Created by dulijie on 2019/4/22.
 */
//网络等资源获取
open class BaseRepository<T : IRemoteDataSource>(open val dataSource: T) : IRepository

//本地资源获取
open class BaseLocalRepository<T : ILocalDataSource>(open val dataSource: T) : IRepository