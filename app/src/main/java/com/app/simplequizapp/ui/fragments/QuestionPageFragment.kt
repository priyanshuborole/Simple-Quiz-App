package com.app.simplequizapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.app.simplequizapp.data.Constants
import com.app.simplequizapp.data.Questions
import com.app.simplequizapp.databinding.FragmentQuestionPageBinding
import com.app.simplequizapp.ui.viewmodel.QuizViewModel


class QuestionPageFragment(val position : Int) : Fragment() {

    lateinit var binding: FragmentQuestionPageBinding
    private lateinit var question: Questions
    private lateinit var viewModel: QuizViewModel
    private val sharedViewModel: QuizViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionPageBinding.inflate(inflater,container,false)
        val view = binding.root


        //Toast.makeText(activity, "position is "+ position, Toast.LENGTH_LONG).show()
        question = Constants.questions[position]

        initViews(question,view);
        return view

    }

    private fun initViews(question: Questions, view: View) {
        binding.tvQuestion.text = question.question
        binding.opt1.text = question.optionOne
        binding.opt2.text = question.optionTwo
        binding.opt3.text = question.optionThree
        binding.opt4.text = question.optionFour

        // Get radio group selected item using on checked change listener
        binding.radioGrp.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = view.findViewById(checkedId)

            })
        // Get radio group selected status and text using button click event
        binding.save.setOnClickListener{
            // Get the checked radio button id from radio group
            var id: Int = binding.radioGrp.checkedRadioButtonId
            if (id!=-1){ // If any radio button checked from radio group
                Toast.makeText(requireContext(),"Saved",Toast.LENGTH_SHORT).show()
                // Get the instance of radio button using id
                val radio:RadioButton = view.findViewById(id)
               getResult(radio.text.toString())
            }else{
                // If no radio button checked in this radio group
                Toast.makeText(requireContext(),"On button click :" +
                        " nothing selected",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun getResult(selection: String) {
        if (selection == question.correctOption) {
            sharedViewModel.updateScore(1)
            Log.d("PRI", sharedViewModel.getScore().toString())
        } else {
            sharedViewModel.updateScore(0)
        }

    }

}