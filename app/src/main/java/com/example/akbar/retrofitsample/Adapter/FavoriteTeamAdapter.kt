package com.example.akbar.retrofitsample.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.akbar.retrofitsample.DetailTeamActivity
import com.example.akbar.retrofitsample.HomeActivity
import com.example.akbar.retrofitsample.Model.DetailTeam
import com.example.akbar.retrofitsample.Model.TeamsFavorite
import com.example.akbar.retrofitsample.R
import kotlinx.android.synthetic.main.teams_item.view.*

class FavoriteTeamAdapter(private val context: Context, private val teamFavorite : ArrayList<TeamsFavorite>, private val listener : (TeamsFavorite) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamAdapter.FavoriteTeamViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamAdapter.FavoriteTeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teams_item, parent, false)
        return FavoriteTeamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return teamFavorite.size
    }

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) {
        holder.bind(teamFavorite[position])
        holder.bind2(context, teamFavorite[position].id_team.toString())
    }
    class FavoriteTeamViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var tf : TeamsFavorite? = null
        fun bind(tf : TeamsFavorite){
            this.tf = tf
            with(itemView){
                with(tf){
                    Glide.with(context).load(team_badge).into(iv_teams)
                    tv_teams.text = str_team
                }
            }
        }
        fun bind2(con : Context, id : String){
            itemView.setOnClickListener {
                val intent = Intent(HomeActivity@con, DetailTeamActivity::class.java)
                intent.putExtra(DetailTeamActivity.string_id2, id)
                itemView.context.startActivity(intent)
            }
        }
    }
}