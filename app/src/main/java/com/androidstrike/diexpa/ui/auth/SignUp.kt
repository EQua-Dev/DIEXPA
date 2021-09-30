package com.androidstrike.diexpa.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.androidstrike.diexpa.R
import com.androidstrike.diexpa.data.User
import com.androidstrike.diexpa.utils.Common
import com.androidstrike.diexpa.utils.Common.userCollectionRef
import com.androidstrike.diexpa.utils.Common.userEmail
import com.androidstrike.diexpa.utils.Common.userId
import com.androidstrike.diexpa.utils.setOnSingleClickListener
import com.androidstrike.diexpa.utils.toast
import com.androidstrike.diexpa.utils.visible
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignUp : Fragment(), AdapterView.OnItemSelectedListener {


    private var emailAddress: String? = null
    lateinit var uName: String
    lateinit var email: String
    lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var nutrition: String
    var userNutrition: String? = null


//    var nutritionArray = arrayOf(")
    val NEW_SPINNER_ID = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val nutritionArray = resources.getStringArray(R.array.nutrition)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.drop_down_item,nutritionArray)

        auto_complete_tv.setAdapter(arrayAdapter)

        nutrition = auto_complete_tv.text.toString()

//        val nutritionSpinner =
//            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, nutritionArray)
//
//        nutritionSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        with(spinner_nutrition) {
//            adapter = nutritionSpinner
//            setSelection(0, false)
//            onItemSelectedListener = this@SignUp
//            prompt = "Select Your Nutrition"
//            gravity = Gravity.CENTER
//        }
//
//        val spinner = Spinner(context)
//        spinner.id = NEW_SPINNER_ID
//
//        val ll = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        ll.setMargins(10, 40, 10, 10)
//        layout_sign_up.addView(spinner)

        signup_txt_login.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_signIn)
        }

        sign_up_btn_register.setOnSingleClickListener {
            uName = sign_up_user_name.text.toString().trim()
            email = sign_up_email.text.toString().trim()
            password = sign_up_password.text.toString().trim()
            confirmPassword = sign_up_confirm_password.text.toString().trim()

            validateInput()

        }

    }

    private fun validateInput() {

        //if user name field is empty
        if (uName.isEmpty()) {
            sign_up_user_name.error = "First Name Required"
            sign_up_user_name.requestFocus()
            return
        }
        // if the email and password fields are empty we display error messages
        if (email.isEmpty()) {
            sign_up_email.error = "Email Required"
            sign_up_email.requestFocus()
            return
        }

        //if the email pattern/format does not does match that as defined, we display error messages
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            sign_up_email.error = "Valid Email Required"
            sign_up_email.requestFocus()
            return
        }
        //if the password contains less than 6 characters we display error message
        if (password.isEmpty() || password.length < 6) {
            sign_up_password.error = "6 char password required"
            sign_up_password.requestFocus()
            return
        }

        if (confirmPassword != password) {
            sign_up_confirm_password.error = "Does Not Match Password"
            sign_up_confirm_password.requestFocus()
            return
        }
            if (nutrition.isEmpty()){
                txt_input_layout.error = "Select Your Diet Type"
                auto_complete_tv.requestFocus()
                return
            }
//        if (!Common.PASSWORD_PATTERN.matcher(password).matches()) {
//            sign_up_password.error =
//                "Password too weak. Must Contain at least one uppercase, one lowercase, one number and one character"
//            sign_up_password.requestFocus()
//            return
//        }
        else {
            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {

        pb_sign_up.visible(true)
        //  implement user sign up
        Common.mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val newUserId = Common.mAuth.uid
                    saveUser(email, newUserId)
//                    userId = Common.mAuth.currentUser?.uid
                    isFirstTime()
                    pb_sign_up.visible(false)
                    findNavController().navigate(R.id.action_signUp_to_signIn)
                } else {
                    it.exception?.message?.let {
                        pb_sign_up.visible(false)
                        activity?.toast(it)
                    }
                }
            }
    }

    private fun saveUser(email: String, newUserId: String?) {
        val user = getUser(email, newUserId)
        saveNewUser(user)
    }

    private fun saveNewUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        try {
            //create new user in firestore
            userCollectionRef.add(user).await()
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                activity?.toast(e.message.toString())
            }
        }
    }

    private fun getUser(email: String, newUserId: String?): User {
        val userName = sign_up_user_name.text.toString()
        val nutrition = userNutrition.toString()
        val uid = newUserId.toString()
        val newEmail = email

        return User(userName,nutrition,uid,newEmail)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        nutrition = parent?.getItemAtPosition(position).toString()
//        userNutrition = nutrition
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
//        userNutrition = nutritionArray[0]
    }

    //boolean shared pref to store whether user is using the app for the 1st time
    private fun isFirstTime() {
        val sharedPref =
            requireActivity().getSharedPreferences(Common.sharedPrefName, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(Common.firstTimeKey, false)
        editor.putString(Common.userNamekey, uName)
        editor.putString(Common.userNutritionKey, userNutrition)
        editor.apply()
    }


}

