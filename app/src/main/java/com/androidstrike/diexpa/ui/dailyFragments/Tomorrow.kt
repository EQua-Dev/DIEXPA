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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.fragment_tomorrow.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class Tomorrow : Fragment() {

    var nutritionRef = Firebase.firestore.collection(Common.dowTomGood)//${Common.userNutrition}")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tomorrow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
                            tomorrow_breakfast.text = mealPlan.breakfast
                            tomorrow_lunch.text = mealPlan.lunch
                            tomorrow_dinner.text = mealPlan.dinner
                            tomorrow_brunch.text = mealPlan.brunch
                        }
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    activity?.toast(e.message.toString())
                }
            }
        }
    }

}