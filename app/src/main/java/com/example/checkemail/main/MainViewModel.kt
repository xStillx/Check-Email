package com.example.checkemail.main

import androidx.lifecycle.ViewModel
import com.example.checkemail.utils.SingleLiveEvent
import com.example.checkemail.utils.call

class MainViewModel(): ViewModel() {

    val onCheckClickEvent = SingleLiveEvent<String>()
    val onTextChangedEvent = SingleLiveEvent<Unit>()
    val onMailListEvent = SingleLiveEvent<ArrayList<String>>()

    init {
        onMailList()
    }

    fun onCheckClick(email: String){
        onCheckClickEvent.call(email)
    }

    fun onTextChanged(){
        onTextChangedEvent.call()
    }

    private fun onMailList(){
        val mailList = arrayListOf<String>()
        mailList.add("@gmail.com")
        mailList.add("@hotmail.com")
        mailList.add("@yahoo.com")
        mailList.add("@outlook.com")
        mailList.add("@mail.ru")
        mailList.add("@adinet.com.uy")
        onMailListEvent.call(mailList)
    }
}