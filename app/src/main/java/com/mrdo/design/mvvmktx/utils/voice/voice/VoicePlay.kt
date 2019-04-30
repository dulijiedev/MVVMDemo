package com.mrdo.design.mvvmktx.utils.voice.voice

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.util.Log
import com.mrdo.design.mvvmktx.utils.voice.constant.VOICE_FILE_PATH
import com.mrdo.design.mvvmktx.utils.voice.utils.VoiceType
import com.mrdo.design.mvvmktx.utils.voice.utils.getAssetFileDescription
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by dulijie on 2019/4/28.
 * 音频播放
 */
class VoicePlay(val context: Context) {

    private var mExecutorService: ExecutorService = Executors.newCachedThreadPool()

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        var mVoicePlay: VoicePlay? = null

        fun with(context: Context): VoicePlay {
            if (mVoicePlay == null) {
                synchronized(VoicePlay::class.java, block = {
                    if (mVoicePlay == null) {
                        mVoicePlay = VoicePlay(context)
                    }
                })
            }
            return mVoicePlay!!
        }
    }

    /**
     * 播放
     */
    fun play(voiceType: VoiceType) {
        val voiceBuilder = VoiceBuilder.Builder()
                .setVoice(voiceType)
                .build()
        executeStart(voiceBuilder)
    }

    /**
     * 开启线程执行
     */
    private fun executeStart(voiceBuilder: VoiceBuilder) {
        val voiceList = VoiceTemplate.getVoiceList(voiceBuilder)
                .filter { it != VoiceType.NONE }
                .toMutableList()
        if (voiceList.isNullOrEmpty()) {
            return
        }
        mExecutorService.execute { start(voiceList) }
    }

    /**
     * 开始播放
     */
    @Synchronized
    private fun start(voiceList: MutableList<VoiceType>) {
        synchronized(VoicePlay::javaClass, block = {
            val mediaPlayer = MediaPlayer()
            val countDownLatch = CountDownLatch(1)
            var assetFileDescriptor: AssetFileDescriptor? = null
            try {
                val counter = arrayOf(0)
                val path=String.format(VOICE_FILE_PATH, voiceList[counter[0]].value)
                Log.d("===voice====","$path")
                assetFileDescriptor = getAssetFileDescription(context, String.format(VOICE_FILE_PATH, voiceList[counter[0]].value))
                mediaPlayer.setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                )
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener { mediaPlayer.start() }
                mediaPlayer.setOnCompletionListener { mp ->
                    mp.reset()
                    counter[0]++
                    if (counter[0] < voiceList.size) {
                        try {
                            val assetFileDescriptor2 = getAssetFileDescription(context, String.format(VOICE_FILE_PATH, voiceList[counter[0]].value))
                            mp.setDataSource(
                                    assetFileDescriptor2.fileDescriptor,
                                    assetFileDescriptor2.startOffset,
                                    assetFileDescriptor2.length
                            )
                            mp.prepare()
                        } catch (ee: IOException) {
                            ee.printStackTrace()
                            countDownLatch.countDown()
                        }
                    } else {
                        mp.release()
                        countDownLatch.countDown()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                countDownLatch.countDown()
            } finally {
                assetFileDescriptor?.let {
                    it.close()
                }
            }

            try {
                countDownLatch.await()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        })
    }
}