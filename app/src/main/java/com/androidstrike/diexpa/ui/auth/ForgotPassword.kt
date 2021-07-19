package com.androidstrike.diexpa.ui.auth

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.androidstrike.diexpa.R
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPassword : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_reset_password.setOnClickListener {
            val email = text_recover_email.text.toString().trim()

            //run validation on input
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                text_recover_email.error = "Valid Email Required"
                text_recover_email.requestFocus()
                return@setOnClickListener
            }

            //perform password reset using the firebase auth method
            // TODO: 18/06/2021 implement password reset

            findNavController().navigate(R.id.action_forgotPassword_to_signIn)
        }
    }

}