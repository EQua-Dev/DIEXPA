package com.androidstrike.diexpa.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidstrike.diexpa.R
import com.androidstrike.diexpa.adapters.Supplier
import com.androidstrike.diexpa.adapters.WeeklyAdapter
import com.androidstrike.diexpa.data.MealPlan
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_weekly.*

class Weekly : Fragment() {

//    var weeklyAdapter: FirestoreRecyclerAdapter<MealPlan, WeeklyAdapter>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        rv_weekly.layoutManager = layoutManager
        rv_weekly.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
        )

        val adapter = WeeklyAdapter(requireContext(), Supplier.days)

        rv_weekly.adapter = adapter


    }
}