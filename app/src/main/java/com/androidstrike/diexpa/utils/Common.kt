package com.androidstrike.diexpa.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.androidstrike.diexpa.ui.auth.SignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.regex.Pattern

object Common {

    var weeklyDay: String? = " "
    lateinit var currentUser: Any

    //values for shared preferences
    val sharedPrefName= "FirstUse"
    val firstTimeKey = "FirstTime"
    val userNamekey = "userName"
    val userNutritionKey = "userNutrition"

    var userNutrition: String? = ""

    //Day of the week (Today)
    @RequiresApi(Build.VERSION_CODES.O)
    var date: LocalDate = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var dow = date.dayOfWeek.toString().toLowerCase(Locale.ROOT)
    var dowGood = dow[0].toUpperCase()+dow.substring(1)


    //    var c: Date = Calendar.getInstance().time
//    var df: SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    @RequiresApi(Build.VERSION_CODES.O)
    val formattedToday = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))

    //Tomorrow
    @RequiresApi(Build.VERSION_CODES.O)
    var tomorrow = LocalDate.now().plusDays(1)
    @RequiresApi(Build.VERSION_CODES.O)
    var dowTom = tomorrow.dayOfWeek.toString().toLowerCase(Locale.ROOT)
    var dowTomGood = dowTom[0].toUpperCase()+ dowTom.substring(1)

    @RequiresApi(Build.VERSION_CODES.O)
    val formattedTomorrow = tomorrow.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))

    //Day After Tomorrow
    @RequiresApi(Build.VERSION_CODES.O)
    var nextDay = LocalDate.now().plusDays(2)
    @RequiresApi(Build.VERSION_CODES.O)
    var dowNextDay = nextDay.dayOfWeek.toString().toLowerCase(Locale.ROOT)
    var dowNextDayGood = dowNextDay[0].toUpperCase()+ dowNextDay.substring(1)
    @RequiresApi(Build.VERSION_CODES.O)
    var formattedNextDay = nextDay.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))

    var userName: String? = SignIn().userName
    var mAuth = FirebaseAuth.getInstance()
    var userId = mAuth.currentUser?.uid
    var userEmail = mAuth.currentUser?.email
    val userCollectionRef = Firebase.firestore.collection("Users")//.document("${Common.userId}")




    //password regex validator
    val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +  //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&+!=])" +    //at least 1 special character
                "(?=\\S+$)" +  //no white spaces
                ".{4,}" +  //at least 4 characters
                "$"
    )

}