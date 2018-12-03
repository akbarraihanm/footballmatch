package com.example.akbar.retrofitsample.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.akbar.retrofitsample.DetailTeamActivity
import com.example.akbar.retrofitsample.Fragment.FragmentOverviewTeam
import com.example.akbar.retrofitsample.HomeActivity
import com.example.akbar.retrofitsample.Model.DetailTeam
import com.example.akbar.retrofitsample.R
import kotlinx.android.synthetic.main.teams_item.view.*
import java.util.*

class ListTeamAdapter(private val context: Context, private val listTeam : ArrayList<DetailTeam>, private val listener: (DetailTeam) -> Unit)
    : RecyclerView.Adapter<ListTeamAdapter.ListTeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teams_item, parent, false)
        return ListTeamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTeam.size
    }

    override fun onBindViewHolder(holder: ListTeamViewHolder, position: Int) {
        holder.bind(listTeam[position])
        holder.bind2(context, listTeam[position].id_team.toString())
    }

    class ListTeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var lt : DetailTeam? = null
        fun bind(lt : DetailTeam){
            this.lt = lt
            with(itemView){
                with(lt){
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