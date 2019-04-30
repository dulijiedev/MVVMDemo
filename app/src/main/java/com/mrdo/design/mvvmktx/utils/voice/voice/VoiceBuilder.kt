package com.mrdo.design.mvvmktx.utils.voice.voice

import com.mrdo.design.mvvmktx.utils.voice.utils.VoiceType

/**
 * Created by dulijie on 2019/4/28.
 * 声音提示 语音播报
 */
class VoiceBuilder(builder: Builder) {


    var voiceType: VoiceType

    init {
        this.voiceType = builder.voiceType!!
    }

    open class Builder {

        var voiceType: VoiceType? = null

        /**
         * 必须调用set方法否则会崩溃
         */
        fun setVoice(voiceType: VoiceType): Builder {
            this.voiceType = voiceType
            return this
        }

        fun build(): VoiceBuilder {
            if (voiceType == null) {
                throw Exception("应先调用 setVoice设置VoiceType")
            }
            return VoiceBuilder(this)
        }
    }
}