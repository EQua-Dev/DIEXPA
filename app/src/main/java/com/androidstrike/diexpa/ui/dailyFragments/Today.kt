package com.androidstrike.diexpa.ui.dailyFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidstrike.diexpa.R
import com.androidstrike.diexpa.data.MealPlan
import com.androidstrike.diexpa.data.User
import com.androidstrike.diexpa.utils.Common
import com.androidstrike.diexpa.utils.toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.StringBuilder
import kotlin.math.log

class Today : Fragment() {

    private val nutritionRef =
        Firebase.firestore.collection(Common.dowGood)//.document("${Common.userNutrition}")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_today, container, false)

        Log.d("Equa", "onCreateView: Today Reached")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("Equa", "onCreateView: try1")
                val querySnapshot =
                    nutritionRef.whereEqualTo("nutrition", Common.userNutrition!!).get()
                        .await()
                Log.d("Equa", "onCreateView: ${Common.dowGood}")
                Log.d("Equa", "onCreateView: $nutritionRef")
                Log.d("Equa", "onCreateView: ${Common.userNutrition}")
                Log.d("Equa", "onCreateView: ${querySnapshot.documents}")
                Log.d("Equa", "onCreateView: try 2")
                for (query in querySnapshot) {
                    Log.d("Equa", "onCreateView: try 3")
                    val mealPlan = query.toObject<MealPlan>()
                    withContext(Dispatchers.Main) {
                        today_breakfast.text = mealPlan.breakfast
                        today_lunch.text = mealPlan.lunch
                        today_dinner.text = mealPlan.dinner
                        today_brunch.text = mealPlan.brunch
                        Log.d("Equa", "onCreateView: ${mealPlan.brunch}")
                    }

//                                .addSnapshotListener { value, error ->
//                                    error?.let {
//                                        activity?.toast(it.message.toString())
//                                        return@addSnapshotListener
//                                    }
//                Log.d("Equa", "signIn: here")
//                Log.d("Equa", "signIn: ${querySnapshot.documents}")
//                                    value?.let {
//                Log.d("Equa", "signIn: Here2")
//                val sb = StringBuilder()
//                for (document in querySnapshot) {
//                    Log.d("Equa", "signIn: Here3")
//                    val mealPlan = document.toObject<MealPlan>()
//
//                    sb.append(mealPlan.breakfast)
//                    Common.userNutrition = sb.toString()
//                }
//                nutritionRef.addSnapshotListener{ value, error ->
//                    error?.let {
//                        activity?.toast(it.message.toString())
//                        Log.d("Equa", "onActivityCreated: ${it.message.toString()}")
//                        return@addSnapshotListener
//                    }
//                    value?.let {
//                        for (document in it){
//
//                        }
//                    }
//                }
//                nutritionRef.addSnapshotListener{value, error ->
//                    error?.let {
//                        activity?.toast(it.message.toString())
//                        return@addSnapshotListener
//                    }
//                    value?.let {
//                        for (document in it){
//
//                        }
////                        for (document in it){
////                            val mealPlan = document.toObject<MealPlan>()
////                            today_breakfast.text = mealPlan.breakfast
////                            today_lunch.text = mealPlan.lunch
////                            today_dinner.text = mealPlan.dinner
////                            today_brunch.text = mealPlan.brunch
////                        }
//                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    activity?.toast(e.message.toString())
                    Log.d("Equa", "onActivityCreated: ${e.message.toString()}")
                }
            }
        }
        return view
    }

}