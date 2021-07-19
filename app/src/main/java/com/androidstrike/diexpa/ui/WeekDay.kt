package com.androidstrike.diexpa.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.androidstrike.diexpa.R
import com.androidstrike.diexpa.data.MealPlan
import com.androidstrike.diexpa.utils.Common
import com.androidstrike.diexpa.utils.toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.fragment_today.today_breakfast
import kotlinx.android.synthetic.main.fragment_today.today_brunch
import kotlinx.android.synthetic.main.fragment_today.today_dinner
import kotlinx.android.synthetic.main.fragment_week_day.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class WeekDay : Fragment() {

    var weekDay: String? = null

    private val args by navArgs<WeekDayArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_day, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.show()


        if (arguments?.getString("weekDay") != null) {
            weekDay = arguments?.getString("weekDay")!!
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "$weekDay"

        val nutritionRef =
            Firebase.firestore.collection(weekDay.toString())

        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("Equa", "onCreateView: try1")
                val querySnapshot =
                    nutritionRef.whereEqualTo("nutrition", Common.userNutrition!!).get()
                        .await()
                Log.d("Equa", "onCreateView: $nutritionRef")
                Log.d("Equa", "onCreateView: ${Common.userNutrition}")
                Log.d("Equa", "onCreateView: ${querySnapshot.documents}")
                Log.d("Equa", "onCreateView: try 2")
                for (query in querySnapshot) {
                    Log.d("Equa", "onCreateView: try 3")
                    val mealPlan = query.toObject<MealPlan>()
                    withContext(Dispatchers.Main) {
                        day_breakfast.text = mealPlan.breakfast
                        day_lunch.text = mealPlan.lunch
                        day_dinner.text = mealPlan.dinner
                        day_brunch.text = mealPlan.brunch
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

    }

}






