package com.example.akbar.retrofitsample.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.akbar.retrofitsample.DetailPlayerActivity
import com.example.akbar.retrofitsample.Model.Player
import com.example.akbar.retrofitsample.R
import kotlinx.android.synthetic.main.players_item.view.*

class PlayerAdapter(private val context: Context, private val listPlayer: ArrayList<Player>, private val listener : (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerAdapter.PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.players_item, parent, false)
        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPlayer.size
    }

    override fun onBindViewHolder(holder: PlayerAdapter.PlayerViewHolder, position: Int) {
        holder.bind(listPlayer[position])
        holder.bind2(context, listPlayer[position].id_player.toString())
    }

    class PlayerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var lp : Player? = null
        fun bind(lp : Player){
            this.lp = lp
            with(itemView){
                with(lp){
                    if(str_cutout != null){
                        Glide.with(context).load(str_cutout).into(iv_player)
                    }
                    else{
                        Glide.with(context).load(R.drawable.blank).into(iv_player)
                    }
                    tv_player_name.text = str_player
                    tv_player_position.text = str_position
                }
            }
        }
        fun bind2(con : Context, id : String){
            itemView.setOnClickListener {
                val intent = Intent(DetailTeamActivity@con, DetailPlayerActivity::class.java)
                intent.putExtra(DetailPlayerActivity.string_id3, id)
                itemView.context.startActivity(intent)
            }
        }
    }

}