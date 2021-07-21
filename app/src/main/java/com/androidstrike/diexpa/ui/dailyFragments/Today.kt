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

        CoroutineScope(Dispatchers.IO).launch {
            try {
                //fetch and display the meal plan for 'today'
                val querySnapshot =
                    nutritionRef.whereEqualTo("nutrition", Common.userNutrition!!).get()
                        .await()

                for (query in querySnapshot) {
                    val mealPlan = query.toObject<MealPlan>()
                    withContext(Dispatchers.Main) {
                        today_breakfast.text = mealPlan.breakfast
                        today_lunch.text = mealPlan.lunch
                        today_dinner.text = mealPlan.dinner
                        today_brunch.text = mealPlan.brunch
                        Log.d("Equa", "onCreateView: ${mealPlan.brunch}")
                    }

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