package com.app.simplequizapp.data

data class Questions(
    val id:Int,
    val question:String,
    val optionOne:String,
    val optionTwo :String,
    val optionThree:String,
    val optionFour:String,
    val correctOption:String
) {
}