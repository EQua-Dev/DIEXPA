package com.androidstrike.diexpa.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androidstrike.diexpa.R
import com.androidstrike.diexpa.data.MealPlan
import com.androidstrike.diexpa.utils.Common
import com.androidstrike.diexpa.utils.toast
import com.androidstrike.diexpa.utils.visible
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
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

        pb_fragment_weekday.visible(true)

        val nutritionRef =
            Firebase.firestore.collection(weekDay.toString())

        CoroutineScope(Dispatchers.IO).launch {
            try {
                //fetch the diet plan for the selected day
                val querySnapshot =
                    nutritionRef.whereEqualTo("nutrition", Common.userNutrition!!).get()
                        .await()
                for (query in querySnapshot) {
                    val mealPlan = query.toObject<MealPlan>()
                    withContext(Dispatchers.Main) {
                        day_breakfast.text = mealPlan.breakfast
                        day_lunch.text = mealPlan.lunch
                        day_dinner.text = mealPlan.dinner
                        day_brunch.text = mealPlan.brunch
                        Log.d("Equa", "onCreateView: ${mealPlan.brunch}")
                    }

                }
                withContext(Dispatchers.Main) {
                    pb_fragment_weekday.visible(false)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    activity?.toast(e.message.toString())
                    Log.d("Equa", "onActivityCreated: ${e.message.toString()}")
                }
            }
        }

        btn_back.setOnClickListener {

            val intent = Intent(requireContext(), Landing::class.java)
            startActivity(intent)

//            val frag_weekly = Weekly()
//            val manager = activity?.supportFragmentManager
//            val frag_tansaction  = manager?.beginTransaction()
//            frag_tansaction?.replace(R.id.fragmentContainerView, frag_weekly )?.addToBackStack(null) //.isAddToBackStackAllowed
//            frag_tansaction?.commit()
        }

    }

}






