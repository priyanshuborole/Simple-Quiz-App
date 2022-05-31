package com.app.simplequizapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.app.simplequizapp.R
import com.app.simplequizapp.databinding.ActivityQuestionBinding
import com.app.simplequizapp.ui.fragments.QuestionPageFragment
import com.app.simplequizapp.ui.viewmodel.QuizViewModel
import java.util.*
import java.util.concurrent.TimeUnit

private const val NUM_PAGES = 5
class QuestionActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuestionBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var  sharedViewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedViewModel =  ViewModelProvider(this)
            .get(QuizViewModel::class.java)
        viewPager = binding.pager
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        initListeners()
        countdown()
    }

    private fun initListeners() {
        binding.btnNext.setOnClickListener {
            if (viewPager.currentItem == NUM_PAGES-1) {
                results()
                Toast.makeText(this, "Quiz Finished", Toast.LENGTH_LONG).show();
            } else {
                viewPager.currentItem = viewPager.currentItem + 1
            }
        }

        binding.btnPrevious.setOnClickListener{
            if (viewPager.currentItem == 0) {
                Toast.makeText(this, "No More", Toast.LENGTH_LONG).show();
            } else {
                // Otherwise, select the previous step.
                viewPager.currentItem = viewPager.currentItem - 1
            }
        }
    }


    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private fun countdown(){
        val duration:Long= TimeUnit.SECONDS.toMillis(50)
        val countTime = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                var sDuration:String= String.format(
                    Locale.ENGLISH,
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))

                binding.tvTime.text = sDuration

            }
            override fun onFinish() {
               results()
            }
        }
        countTime.start()
    }

    private fun results() {
        val score = sharedViewModel.getScore()
        val intent = Intent(this,ResultActivity::class.java)
        intent.putExtra("score",score.value)
        startActivity(intent)
    }


    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment = QuestionPageFragment(position)
    }
}