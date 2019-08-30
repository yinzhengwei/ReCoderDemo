package com.android.yzw.myapplication

import android.content.Context
import android.media.AudioManager

/**
 * Create by yinzhengwei on 2019-08-29
 * @Function
 */
fun open(context: Context) {
    val mAM = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    mAM.mode = AudioManager.MODE_FM
}