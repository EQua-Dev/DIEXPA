package com.androidstrike.diexpa.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.androidstrike.diexpa.R
import com.androidstrike.diexpa.utils.Common
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_daily.*
import kotlinx.android.synthetic.main.fragment_daily.view.*

class Daily : Fragment() {

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_daily, container, false)



//        navController = requireActivity().findNavController(R.id.fragmentContainerViewDaily)
            //Navigation.findNavController(requireContext() as Activity, R.id.fragmentContainerViewDaily)

//        view.bottomNavigationView.setupWithNavController(navController)

//        NavigationUI.setupActionBarWithNavController(requireActivity() as AppCompatActivity,navController)

//        val frgContainer = view.findViewById(R.id.fragmentContainerViewDaily) as View
//        val bottomNavigationView = view?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        val navHostFragment = //FragmentManager.findFragment(frgContainer) as NavHostFragment
//            activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerViewDaily) as NavHostFragment
//        val navController = navHostFragment.navController //as AppCompatActivity
//
//        val appBarConfiguration = AppBarConfiguration(setOf(R.id.menu_today, R.id.menu_tomorrow, R.id.menu_next_day)) //as AppCompatActivity
//
////        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        bottomNavigationView?.setupWithNavController(navController)
//
//        val sharedPref = requireActivity().getSharedPreferences(Common.sharedPrefName, Context.MODE_PRIVATE)
////        Common.userNutrition =  sharedPref.getString(Common.userNutritionKey, " ").toString()


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val btmNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
//        val frgDaily = findViewById(R.id.fragment_daily)

        val navController = requireActivity().findNavController(R.id.fragment_daily)
        bottom_navigation_view.setupWithNavController(navController!!)
    }


}