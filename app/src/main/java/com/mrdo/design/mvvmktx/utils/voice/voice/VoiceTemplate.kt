package com.mrdo.design.mvvmktx.utils.voice.voice

import com.mrdo.design.mvvmktx.utils.voice.utils.VoiceType


/**
 * Created by dulijie on 2019/4/28.
 * 音频组合 集合
 */
object VoiceTemplate {

    fun getVoiceList(voiceBuilder: VoiceBuilder): MutableList<VoiceType> {
        val result = mutableListOf<VoiceType>()
        result.add(voiceBuilder.voiceType)
        return result
    }
}