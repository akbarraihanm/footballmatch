package com.example.akbar.retrofitsample.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akbar.retrofitsample.DetailActivity
import com.example.akbar.retrofitsample.HomeActivity
import com.example.akbar.retrofitsample.Model.Fixture
import com.example.akbar.retrofitsample.R
import kotlinx.android.synthetic.main.fixture_item.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Suppress("UNUSED_EXPRESSION")
class LastFixtureAdapter(private val context : Context?, private val lastFixture : ArrayList<Fixture>, private val listener : (Fixture)->Unit)
    : RecyclerView.Adapter<LastFixtureAdapter.LastFixtureViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastFixtureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fixture_item, parent, false)
        return LastFixtureViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lastFixture.size
    }

    override fun onBindViewHolder(holder: LastFixtureViewHolder, position: Int) {
        holder.bind(lastFixture[position])
        holder.bind2(context!!, lastFixture[position].id_event.toString())
    }
    class LastFixtureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //private var view : View = itemView
        private var lf: Fixture? = null
        fun bind(lf: Fixture) {
            this.lf = lf
            with(itemView){
                with(lf){
                    tv_date.text = str_date.toString()
                    tv_team_a.text = str_HomeTeam
                    tv_team_b.text = str_AwayTeam
                    tv_skor_a.text = int_HomeScore
                    tv_skor_b.text = int_AwayScore
                    if (str_time!=null){
                        val timeConvert = gmtFormat(str_date,str_time)
                        val formatDate = SimpleDateFormat("E, dd MM yyyy", Locale(str_date))
                        val formatTime = SimpleDateFormat("HH:mm", Locale(str_time))
                        val date = formatDate.format(timeConvert)
                        val time = formatTime.format(timeConvert)
                        tv_date.text = date
                        tv_time.text = time
                    }else{
                        tv_date.text = str_date
                    }
                }
            }
        }
        private fun gmtFormat(date: String?, time: String?): Date? {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale(date,time))
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val dateTime = "$date $time"
            return formatter.parse(dateTime)
        }
        fun bind2(con: Context, id : String){
            itemView.setOnClickListener {
                val intent = Intent(HomeActivity@con, DetailActivity::class.java)
                intent.putExtra(DetailActivity.string_id, id)
                itemView.context.startActivity(intent)
            }
        }
    }
}