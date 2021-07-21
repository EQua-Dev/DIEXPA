package com.androidstrike.diexpa.adapters

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.androidstrike.diexpa.R
import com.androidstrike.diexpa.adapters.Supplier.days
import com.androidstrike.diexpa.data.MealPlan
import com.androidstrike.diexpa.ui.Landing
import com.androidstrike.diexpa.ui.WeekDay
import com.androidstrike.diexpa.ui.WeekDayDirections
import com.androidstrike.diexpa.ui.WeeklyDirections
import com.androidstrike.diexpa.utils.Common
import com.androidstrike.diexpa.utils.IRecyclerItemClickListener
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.custom_weekly_layout.view.*

class WeeklyAdapter(val context: Context, val days: List<Days>) :
    RecyclerView.Adapter<WeeklyAdapter.MyViewHolder>(), View.OnClickListener {

    lateinit var iRecyclerItemClickListener: IRecyclerItemClickListener
    var adapterPosition: Int? = 0

    fun setClick(iRecyclerItemClickListener: IRecyclerItemClickListener) {
        this.iRecyclerItemClickListener = iRecyclerItemClickListener
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(day: Days?, position: Int) {
            itemView.txt_week_day.text = day!!.day
            Common.weeklyDay = day.day

            this.currentDay = day
            this.currentPosition = position
        }

        var currentDay: Days? = null
        var currentPosition: Int? = 0

        init {
            itemView.setOnClickListener{
                //get the week day of the selected day
                val currentDay = days[currentPosition!!]
                val activity = it.context as AppCompatActivity
                val weeklyItem = WeekDay()

                val bundle = Bundle()
                bundle.putString("weekDay", currentDay.day)
                weeklyItem.arguments = bundle

                val manager = activity.supportFragmentManager
                val frag_tansaction  = manager.beginTransaction()
                frag_tansaction.replace(R.id.fragmentContainerView, weeklyItem).addToBackStack(null) //.isAddToBackStackAllowed
                frag_tansaction.commit()

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.custom_weekly_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val day = days[position]
        holder.setData(day, position)
        adapterPosition = position
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onClick(v: View?) {
        iRecyclerItemClickListener.onItemClickListener(v!!, adapterPosition!!)
        

    }
}

private fun FragmentActivity.onBackPressed() {
    fragmentManager.popBackStackImmediate()
}

@Parcelize
data class Days(var day: String): Parcelable

object Supplier {
    val days = listOf<Days>(
        Days("Monday"),
        Days("Tuesday"),
        Days("Wednesday"),
        Days("Thursday"),
        Days("Friday"),
        Days("Saturday"),
        Days("Sunday"),
    )
}
