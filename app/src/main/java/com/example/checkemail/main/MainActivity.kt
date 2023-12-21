package com.example.checkemail.main

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.checkemail.R
import com.example.checkemail.databinding.ActivityMainBinding
import com.example.checkemail.main.adapter.CustomFilterAdapter


class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        observe()
    }

    private fun observe() {
        viewModel.onCheckClickEvent.observe(this){
            if (isValidEmail(it)){
                Toast.makeText(this, "Все верно", Toast.LENGTH_SHORT).show()
            }else{
                viewBinding.tilEmail.error = getString(R.string.inter_valid_email)
            }
        }
        viewModel.onTextChangedEvent.observe(this){
            viewBinding.tilEmail.error = null
        }
        viewModel.onMailListEvent.observe(this){
            val adapter = CustomFilterAdapter(this, android.R.layout.simple_list_item_1, it)
            viewBinding.edEmail.setAdapter(adapter)
        }
    }

    private fun initViews() {
        viewBinding.btnCheck.setOnClickListener {
            viewModel.onCheckClick(viewBinding.edEmail.text.toString())
        }
        viewBinding.edEmail.addTextChangedListener{
            viewModel.onTextChanged()
        }
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}