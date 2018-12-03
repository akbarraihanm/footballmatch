package com.example.akbar.retrofitsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.akbar.retrofitsample.Model.Player
import com.example.akbar.retrofitsample.Model.PlayerResponse
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import org.jetbrains.anko.find
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPlayerActivity : AppCompatActivity() {

    var detailPlayer : ArrayList<Player> = arrayListOf()
    lateinit var ivFanArt : ImageView
    lateinit var tvWeight : TextView
    lateinit var tvHeight : TextView
    lateinit var tvPositionDetail : TextView
    lateinit var tvPlayerDescription : TextView

    companion object {
        var string_id3 = "string_id3"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        string_id3 = intent.getStringExtra(string_id3)

        ivFanArt = findViewById(R.id.iv_fanart)
        tvWeight = findViewById(R.id.tv_weight)
        tvHeight = findViewById(R.id.tv_height)
        tvPositionDetail = findViewById(R.id.tv_position_detail)
        tvPlayerDescription = findViewById(R.id.tv_player_description)

        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<PlayerResponse> = apiInterface.getPlayerItemId(string_id3)

        call.enqueue(object : Callback<PlayerResponse>{
            override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Gagal fetch last fixture", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
                if (response.isSuccessful){
                    detailPlayer = response.body()!!.players
                    supportActionBar?.title = detailPlayer[0].str_player
                    if(detailPlayer[0].str_fanart3 != null){
                        Glide.with(applicationContext).load(detailPlayer[0].str_fanart3).into(ivFanArt)
                    }
                    else{
                        Glide.with(applicationContext).load(R.color.background_material_dark).into(ivFanArt)
                    }
                    tvWeight.text = detailPlayer[0].str_weight
                    tvHeight.text = detailPlayer[0].str_height
                    tvPositionDetail.text = detailPlayer[0].str_position
                    tvPlayerDescription.text = detailPlayer[0].str_description_en_p
                }
                else{
                    Toast.makeText(applicationContext, "Gagal bro gak metu tulisane", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
