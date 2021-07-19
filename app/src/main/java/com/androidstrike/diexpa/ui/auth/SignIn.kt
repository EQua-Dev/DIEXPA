package com.androidstrike.diexpa.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.androidstrike.diexpa.MainActivity
import com.androidstrike.diexpa.R
import com.androidstrike.diexpa.data.User
import com.androidstrike.diexpa.ui.Landing
import com.androidstrike.diexpa.utils.Common
import com.androidstrike.diexpa.utils.toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.StringBuilder

class SignIn : Fragment() {

    lateinit var password: String
    lateinit var email: String

    var userName: String = "User"
    private var firebaseUser: FirebaseUser? = null


//    private var firebaseUser: FirebaseUser? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //display user name if user has previously used app on device
        if (isFirstTime())
            log_in_welcome_message.text = "Welcome"
        else
            log_in_welcome_message.text = "Welcome Back $userName"

        log_in_btn_register.setOnClickListener {
            //first run validation on input
            email = log_in_email.text.toString()
            password = log_in_password.text.toString()

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                log_in_email.error = "Valid Email Required"
                log_in_email.requestFocus()
            }
            if (password.isEmpty()) {
                log_in_password.error = "Password Required"
                log_in_password.requestFocus()
            } else //perform sign in
                signIn(email, password)
        }

        login_forgot_password.setOnClickListener {
            //navigate to password reset screen
            findNavController().navigate(R.id.action_signIn_to_forgotPassword)
        }

        login_tv_signup.setOnClickListener {
            //navigate to sign up screen
            findNavController().navigate(R.id.action_signIn_to_signUp)
        }


    }

    private fun signIn(email: String, password: String) {
        //implement sign in method
        email.let { Common.mAuth.signInWithEmailAndPassword(it, password) }
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    //login success
                    Log.d("Equa", "signIn: ${Common.userId}")
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val querySnapshot =Common.userCollectionRef.whereEqualTo("uid", Common.userId!!).get().await()
//                                .addSnapshotListener { value, error ->
//                                    error?.let {
//                                        activity?.toast(it.message.toString())
//                                        return@addSnapshotListener
//                                    }
                                    Log.d("Equa", "signIn: here")
                                    Log.d("Equa", "signIn: ${querySnapshot.documents}")
//                                    value?.let {
                                        Log.d("Equa", "signIn: Here2")
                                        val sb = StringBuilder()
                                        for (document in querySnapshot){
                                            Log.d("Equa", "signIn: Here3")
                                            val user = document.toObject<User>()
                                            sb.append(user.nutrition)
                                            Common.userNutrition = sb.toString()
                                        }
                                        Log.d("Equa", "signIn: ${Common.userNutrition}")
//                                    }
//                                }
//                                Log.d("Equa", "signIn: 2")
//                                val query = Common.userCollectionRef.whereEqualTo("uid", Common.userId).get().await()
////                                val query = Common.userCollectionRef.whereEqualTo("uid", "BrE4yrw63Xh3IGXF6Uc6iRHKcEy1BrE4yrw63Xh3IGXF6Uc6iRHKcEy1").get().await()
//                                Log.d("Equa", "signIn: ${Common.userNutrition}")
//                                Log.d("Equa", "signIn: 1")
//                                for (document in query){
//                                    val user = document.toObject<User>()
//                                    Log.d("EQUA", "signIn: ${user.nutrition}")
//                                    val sb = StringBuilder(user.nutrition)
//                                    Common.userNutrition = sb.toString()
//                                    Log.d("Equa", "signIn: ${Common.userNutrition}")
//                                }

                            val landing = Landing()
                            val manager = fragmentManager
                            val frag_tansaction  = manager?.beginTransaction()
                            frag_tansaction?.replace(R.id.fragmentContainerView, landing)
                            frag_tansaction?.commit()

//                            val i = Intent(requireContext(), Landing::class.java)
//                            startActivity(i)
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                activity?.toast(e.message.toString())
                                Log.d("Equa", "signIn: ${e.message.toString()}")
                            }
                        }
                    }

//                    Common.currentUser = firebaseUser?.uid!!
                } else {
                    activity?.toast(it.exception?.message.toString())
                }
            }
    }

    //boolean shared pref to store whether user is using the app for the 1st time
    private fun isFirstTime(): Boolean {
        //get the from the shared preference: user name and return true if user has previously launched app on device
        val sharedPref =
            requireActivity().getSharedPreferences(Common.sharedPrefName, Context.MODE_PRIVATE)
        userName = sharedPref.getString(Common.userNamekey, "User").toString()
        return sharedPref.getBoolean(Common.firstTimeKey, true)

    }


}