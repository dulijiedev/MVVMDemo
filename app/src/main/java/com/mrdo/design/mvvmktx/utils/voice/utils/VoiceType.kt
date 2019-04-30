package com.mrdo.design.mvvmktx.utils.voice.utils

/**
 * Created by dulijie on 2019/4/28.
 */

enum class VoiceType(val value: String) {
    NONE("no_voice"),
    //新订单
    NewOrder("order_operator"),
    //下线
    Offline("offline_operator"),
    //上线
    Online("online_operator"),
    //对方已付款确认
    PayedConfirm("payed_operator"),
    //未付款清及时处理
    UnpaidOperator("unpay_operator")

}