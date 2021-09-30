package com.androidstrike.diexpa.ui.dailyFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidstrike.diexpa.R
import com.androidstrike.diexpa.data.MealPlan
import com.androidstrike.diexpa.utils.Common
import com.androidstrike.diexpa.utils.toast
import com.androidstrike.diexpa.utils.visible
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_next_day.*
import kotlinx.android.synthetic.main.fragment_tomorrow.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class NextDay : Fragment() {

    var nutritionRef = Firebase.firestore.collection(Common.dowNextDayGood)//${Common.userNutrition}")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_day, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pb_fragment_next_day.visible(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                nutritionRef.addSnapshotListener{value, error ->
                    error?.let {
                        activity?.toast(it.message.toString())
                        return@addSnapshotListener
                    }
                    value?.let {
                        for (document in it){
                            val mealPlan = document.toObject<MealPlan>()
                            next_day_breakfast.text = mealPlan.breakfast
                            next_day_lunch.text = mealPlan.lunch
                            next_day_dinner.text = mealPlan.dinner
                            next_day_brunch.text = mealPlan.brunch
                        }
                    }
                }
                withContext(Dispatchers.Main){
                    pb_fragment_next_day.visible(false)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    activity?.toast(e.message.toString())
                }
            }
        }

    }

}