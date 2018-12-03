package com.example.akbar.retrofitsample.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.akbar.retrofitsample.DetailActivity
import com.example.akbar.retrofitsample.HomeActivity
import com.example.akbar.retrofitsample.Model.Favorite
import com.example.akbar.retrofitsample.Model.Fixture
import com.example.akbar.retrofitsample.R
import kotlinx.android.synthetic.main.fixture_item.view.*

class FavoriteFixtureAdapter (private val context: Context, private val lastFixture: ArrayList<Favorite>, private val listener : (Favorite)->Unit)
    : RecyclerView.Adapter<FavoriteFixtureAdapter.FavoriteFixtureViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFixtureAdapter.FavoriteFixtureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fixture_item, parent, false)
        return FavoriteFixtureViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lastFixture.size
    }

    override fun onBindViewHolder(holder: FavoriteFixtureViewHolder, position: Int) {
        holder.bindItem(lastFixture[position])
        holder.bindItem2(context, lastFixture[position].id_event.toString())
    }
    class FavoriteFixtureViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private var lf : Favorite? = null
        fun bindItem(lf : Favorite){
            this.lf = lf
            with(itemView){
                with(lf){
                    if(str_date == null){
                        tv_date.text = "Kosong coy"
                    }
                    else{
                        tv_date.text = str_date
                    }
                    tv_team_a.text = str_HomeTeam
                    tv_team_b.text = str_AwayTeam
                    tv_skor_a.text = int_HomeScore
                    tv_skor_b.text = int_AwayScore
                }
            }
        }
        fun bindItem2(context: Context, id : String){
            itemView.setOnClickListener {
                val intent = Intent(HomeActivity@context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.string_id, id)
                itemView.context.startActivity(intent)
            }
        }
    }
}