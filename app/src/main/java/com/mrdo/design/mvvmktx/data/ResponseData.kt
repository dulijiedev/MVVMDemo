package com.mrdo.design.mvvmktx.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by dulijie on 2019/3/22.
 * 请求结果返回数据
 */
@Parcelize
data class HomeData(
    val coins: List<Coin>,
    val countrys: List<Country>,
    val goodsTypes: List<GoodsType>,
    val legaltenders: List<Legaltender>,
    val payMethods: List<String>,
    val paymentchannels: List<Paymentchannel>
) : Parcelable

@Parcelize
data class Paymentchannel(
    val code: String,
    val created: String,
    val description: String?,
    val enabled: Boolean,
    val id: Int,
    val isAlive: Boolean,
    val modified: String,
    val name: String
) : Parcelable

@Parcelize
data class Country(
    val countryCode: String,
    val countryName: String,
    val created: String,
    val id: Int,
    val legalTenderCode: String,
    val legalTenderDecimal: Int,
    val legalTenderName: String,
    val legalTenderSign: String,
    val modified: String,
    val saasId: String,
    val state: Int
) : Parcelable

@Parcelize
data class GoodsType(
    val code: Int,
    val name: String,
    val sort: Int,
    val state: Int
) : Parcelable

@Parcelize
data class Legaltender(
    val countryCode: String,
    val countryName: String,
    val created: String,
    val id: Int,
    val legalTenderCode: String,
    val legalTenderDecimal: Int,
    val legalTenderName: String,
    val legalTenderSign: String,
    val modified: String,
    val saasId: String,
    val state: Int
) : Parcelable

@Parcelize
data class Coin(
    val coinCode: String,
    val name: String
) : Parcelable