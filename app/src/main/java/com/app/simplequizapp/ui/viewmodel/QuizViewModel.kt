package com.app.simplequizapp.ui.viewmodel

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

class QuizViewModel : ViewModel() {

    var scoreUp = MutableLiveData<Int>(0)
    fun updateScore(score : Int){
        scoreUp.value = scoreUp.value?.plus(score)
    }
    fun getScore() : MutableLiveData<Int> {
        return scoreUp
    }

}