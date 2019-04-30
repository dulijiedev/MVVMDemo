package com.mrdo.design.mvvmktx.utils.voice

import com.mrdo.design.mvvmktx.MyApp
import com.mrdo.design.mvvmktx.utils.voice.utils.VoiceType
import com.mrdo.design.mvvmktx.utils.voice.voice.VoicePlay

/**
 * Created by dulijie on 2019/4/28.
// * VoicePlay.with(context).play(VoiceType.NewOrder)
 */


/**
 * 上下线声音
 */
fun onOfflineVoice(online: Boolean) {
    VoicePlay.with(MyApp.instance.applicationContext).play(if (online) VoiceType.Online else VoiceType.Offline)
}