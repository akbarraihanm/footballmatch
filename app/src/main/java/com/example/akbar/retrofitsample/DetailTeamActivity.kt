package com.example.akbar.retrofitsample

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.akbar.retrofitsample.Fragment.FragmentOverviewTeam
import com.example.akbar.retrofitsample.Fragment.FragmentPlayerTeam
import com.example.akbar.retrofitsample.Model.DetailTeam
import com.example.akbar.retrofitsample.Model.DetailTeamResponse
import com.example.akbar.retrofitsample.Model.Favorite
import com.example.akbar.retrofitsample.Model.TeamsFavorite
import com.example.akbar.retrofitsample.R.id.add_to_favorite
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import kotlinx.android.synthetic.main.cofragment_detail_team.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTeamActivity : AppCompatActivity() {
    var detailTeam : ArrayList<DetailTeam> = arrayListOf()

    lateinit var abl1 : AppBarLayout
    lateinit var ctl1 : CollapsingToolbarLayout
    lateinit var ivTimDetail : ImageView
    lateinit var tvTahunTim : TextView
    lateinit var tvStadiumTim : TextView
    lateinit var tb1 : android.support.v7.widget.Toolbar
    private var menuItem : Menu? = null
    private var isFavorite : Boolean = false

    companion object {
        var string_id2 = "string_id2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        string_id2 = intent.getStringExtra(string_id2)

        abl1 = findViewById(R.id.abl_1)
        ctl1 = findViewById(R.id.ctl_1)
        ivTimDetail = findViewById(R.id.iv_tim_detail)
        tvTahunTim = findViewById(R.id.tv_tahun_tim)
        tvStadiumTim = findViewById(R.id.tv_stadium_tim)
        tb1 = findViewById(R.id.tb_1)

        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<DetailTeamResponse> = apiInterface.getDetailTeamId(string_id2)

        call.enqueue(object : Callback<DetailTeamResponse>{
            override fun onFailure(call: Call<DetailTeamResponse>, t: Throwable) {
                Toast.makeText(this@DetailTeamActivity, "Gagal fetch last fixture", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DetailTeamResponse>, response: Response<DetailTeamResponse>) {
                if(response.isSuccessful){
                    detailTeam = response.body()!!.teams
                    supportActionBar?.title = detailTeam[0].str_team
                    Glide.with(applicationContext).load(detailTeam[0].team_badge).into(ivTimDetail)
                    tvStadiumTim.text = detailTeam[0].str_stadium
                    tvTahunTim.text = detailTeam[0].int_formed_year
                }
                else{
                    Toast.makeText(this@DetailTeamActivity, "Gagal bro gak metu tulisane", Toast.LENGTH_SHORT).show()
                }
            }
        })
        favoriteState()

        val adapter = DetailTeamViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentOverviewTeam(), "Overview")
        adapter.addFragment(FragmentPlayerTeam(), "Players")
        vp_cofragment.adapter = adapter
        tl_cofragment.setupWithViewPager(vp_cofragment)
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }
    private fun favoriteState(){
        favDatabase.use {
            val result = select(TeamsFavorite.TABLE_TEAMSFAV)
                .whereArgs("(ID_TEAM = {id})",
                    "id" to DetailTeamActivity.string_id2
                )
            val favorite = result.parseList(classParser<TeamsFavorite>())
            if(!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite(){
        try {
            favDatabase.use {
                insert(TeamsFavorite.TABLE_TEAMSFAV,
                    TeamsFavorite.ID_TEAM to detailTeam[0].id_team,
                    TeamsFavorite.STR_TEAM to detailTeam[0].str_team,
                    TeamsFavorite.TEAM_BADGE to detailTeam[0].team_badge
                    )
            }
            Toast.makeText(ctx, "Added to favorite", Toast.LENGTH_SHORT).show()
        }
        catch (e : SQLiteConstraintException){
            Toast.makeText(ctx, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            favDatabase.use {
                delete(TeamsFavorite.TABLE_TEAMSFAV, "(ID_TEAM = {id})",
                    "id" to string_id2)
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

    class DetailTeamViewPagerAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager){
        val fragmentList : MutableList<Fragment> = ArrayList()
        val titleList : MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title : String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }
}
