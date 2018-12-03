package com.example.akbar.retrofitsample

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.akbar.retrofitsample.Model.*
import com.example.akbar.retrofitsample.R.id.add_to_favorite
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import kotlinx.android.synthetic.main.detail_fixture.*
import kotlinx.android.synthetic.main.fixture_item.*
import kotlinx.android.synthetic.main.fixture_item.view.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity(){
    private val TAG : String = DetailActivity::class.java.canonicalName
     var lastFixture: ArrayList<Fixture> = arrayListOf()
    var detailImage : ArrayList<DetailTeam> = arrayListOf()
    private var menuItem : Menu? = null
    private var isFavorite : Boolean = false
    lateinit var tvDate : TextView
    lateinit var tvTime : TextView
    companion object {
        var string_id = "string_id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_fixture)
        supportActionBar?.title = "Fixture Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //Log.e("blabla","bla bla")
        string_id = intent.getStringExtra(string_id)
        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<FixtureResponse> = apiInterface.getEventFixture(string_id)
        call.enqueue( object : Callback<FixtureResponse>{
            override fun onFailure(call: Call<FixtureResponse>?, t: Throwable?) {
                Toast.makeText(this@DetailActivity, "Gagal fetch last fixture", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Gagal fetch detail fixture")
            }

            override fun onResponse(call: Call<FixtureResponse>, response: Response<FixtureResponse>) {
                if (response.isSuccessful){
                    Log.d(TAG, response.body().toString())
                    lastFixture = response.body()!!.events
                    if (lastFixture[0].str_time!=null){
                        val timeConvert = gmtFormat(lastFixture[0].str_date,lastFixture[0].str_time)
                        val formatDate = SimpleDateFormat("E, dd MM yyyy", Locale(lastFixture[0].str_date))
                        val formatTime = SimpleDateFormat("HH:mm", Locale(lastFixture[0].str_time))
                        val date = formatDate.format(timeConvert)
                        val time = formatTime.format(timeConvert)
                        tv_detail_date.text = date
                        tv_detail_time.text = time
                    }else{
                        tv_detail_date.text = lastFixture[0].str_date
                    }
                    tv_scorer_a.text = lastFixture[0].str_home_goal?.replace(";","\n")
                    tv_scorer_b.text = lastFixture[0].str_awa_goal?.replace(";","\n")
                    tv_kiper_a.text = lastFixture[0].home_gk?.replace(";","")
                    tv_kiper_b.text = lastFixture[0].away_gk?.replace(";","")
                    tv_defender_a.text = lastFixture[0].home_def?.replace("; ","\n")
                    tv_defender_b.text = lastFixture[0].away_def?.replace("; ","\n")
                    tv_mid_a.text = lastFixture[0].home_mid?.replace("; ","\n")
                    tv_mid_b.text = lastFixture[0].away_mid?.replace("; ","\n")
                    tv_fw_a.text = lastFixture[0].home_fw?.replace("; ","\n")
                    tv_fw_b.text = lastFixture[0].away_fw?.replace("; ","\n")
                    tv_subs_a.text = lastFixture[0].home_subs?.replace("; ","\n")
                    tv_subs_b.text = lastFixture[0].away_subs?.replace("; ","\n")
                    if(lastFixture[0].int_home_shot != null || lastFixture[0].int_away_shot != null ){
                        tv_shot_a.text = lastFixture[0].int_home_shot
                        tv_shot_b.text = lastFixture[0].int_away_shot
                    }
                    else{
                        tv_shot_a.text = "-"
                        tv_shot_b.text = "-"
                    }
                    val call2 : Call<DetailTeamResponse> = apiInterface.getImageTeam(lastFixture[0].id_home_team)
                    call2.enqueue(object : Callback<DetailTeamResponse>{
                        override fun onFailure(call: Call<DetailTeamResponse>, t: Throwable) {
                            Toast.makeText(this@DetailActivity, "Gagal fetch last fixture", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "Gagal fetch detail fixture")
                        }

                        override fun onResponse(call: Call<DetailTeamResponse>, response: Response<DetailTeamResponse>) {
                            if(response.isSuccessful){
                                Log.d(TAG, response.body().toString())
                                detailImage = response.body()!!.teams
                                Glide.with(this@DetailActivity).load(detailImage[0].team_badge).into(iv_tim_a)
                            }
                        }
                    })
                    val call3 : Call<DetailTeamResponse> = apiInterface.getImageTeam(lastFixture[0].id_away_team)
                    call3.enqueue(object : Callback<DetailTeamResponse>{
                        override fun onFailure(call: Call<DetailTeamResponse>, t: Throwable) {
                            Toast.makeText(this@DetailActivity, "Gagal fetch last fixture", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "Gagal fetch detail fixture")
                        }

                        override fun onResponse(call: Call<DetailTeamResponse>, response: Response<DetailTeamResponse>) {
                            if(response.isSuccessful){
                                Log.d(TAG, response.body().toString())
                                detailImage = response.body()!!.teams
                                Glide.with(this@DetailActivity).load(detailImage[0].team_badge).into(iv_tim_b)
                            }
                        }
                    })
                    tv_dskor_a.text = lastFixture[0].int_HomeScore
                    tv_dskor_b.text = lastFixture[0].int_AwayScore
                    tv_teamname_a.text = lastFixture[0].str_HomeTeam
                    tv_teamname_b.text = lastFixture[0].str_AwayTeam
                }
                else{
                    Toast.makeText(this@DetailActivity, "Gagal bro gak metu tulisane", Toast.LENGTH_SHORT).show()
                }
            }
        })
        favoriteState()
    }
    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(ID_EVENT = {id})",
                    "id" to string_id)
            val favorite = result.parseList(classParser<Favorite>())
            if(!favorite.isEmpty()) isFavorite = true
        }
    }
    private fun gmtFormat(date: String?, time: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale(date,time))
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$date $time"
        return formatter.parse(dateTime)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite ->{
                if(isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                    Favorite.ID_EVENT to lastFixture[0].id_event,
                    Favorite.STR_DATE to lastFixture[0].str_date,
                    Favorite.STR_HOMETEAM to lastFixture[0].str_HomeTeam,
                    Favorite.STR_AWAYTEAM to lastFixture[0].str_AwayTeam,
                    Favorite.INT_HOMESCORE to lastFixture[0].int_HomeScore,
                    Favorite.INT_AWAYSCORE to lastFixture[0].int_AwayScore)
            }
            Toast.makeText(ctx, "Added to favorite", Toast.LENGTH_SHORT).show()
        }
        catch (e : SQLiteConstraintException){
            Toast.makeText(ctx, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(ID_EVENT = {id})",
                    "id" to string_id)
            }
            Toast.makeText(ctx, "Removed from favorite", Toast.LENGTH_SHORT).show()
        }
        catch (e : SQLiteConstraintException){
            Toast.makeText(ctx, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
    private fun setFavorite(){
        if(isFavorite){
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        }
        else{
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
        }
    }
}