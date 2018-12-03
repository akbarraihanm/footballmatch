package com.example.akbar.retrofitsample.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import com.example.akbar.retrofitsample.Adapter.ListTeamAdapter
import com.example.akbar.retrofitsample.Model.DetailTeam
import com.example.akbar.retrofitsample.Model.DetailTeamResponse
import com.example.akbar.retrofitsample.Model.FixtureResponse

import com.example.akbar.retrofitsample.R
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import com.example.akbar.retrofitsample.View.ListTeamView
import com.example.akbar.retrofitsample.presenter.LastFixturePresenter
import com.example.akbar.retrofitsample.presenter.ListTeamPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentListTeam : Fragment(), ListTeamView {
    lateinit var viewGais : View
    lateinit var rvListTeam : RecyclerView
    lateinit var listTeamPresenter: ListTeamPresenter
    lateinit var progressBar: ProgressBar
    lateinit var spinner: Spinner
    lateinit var view: ListTeamView
    lateinit var leagueName2 : String
    lateinit var svTeam : SearchView
    lateinit var listTeam : ArrayList<DetailTeam>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        viewGais = inflater.inflate(R.layout.fragment_fragment_list_team, container, false)
        rvListTeam = viewGais.findViewById(R.id.rv_team_list)
        spinner = viewGais.findViewById(R.id.sp_spinner_team)
        progressBar = viewGais.findViewById(R.id.pb_loading_teams)
        rvListTeam.layoutManager = LinearLayoutManager(context)

        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<DetailTeamResponse> = apiInterface.getLeagueItem()
        listTeamPresenter = ListTeamPresenter(call, this, context!!)
        listTeamPresenter.getListTeamItem()

        val spinnerItem = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItem)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName2 = spinner.selectedItem.toString()
                rvListTeam.adapter = null
                showLoading()
                val apiInterface2 :ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                val call : Call<DetailTeamResponse> = apiInterface2.getLeagueItemId(leagueName2)
                listTeamPresenter = ListTeamPresenter(call, this@FragmentListTeam, context!!)
                listTeamPresenter.getListTeamItem()
            }

        }

        return viewGais
    }
    override fun showListTeamItem(listTeam: ArrayList<DetailTeam>) {
        rvListTeam.adapter = ListTeamAdapter(context!!, listTeam){
            hideLoading()
        }
    }

    override fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = INVISIBLE
    }
    fun searching(query : String){
        spinner.visibility = INVISIBLE
        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<DetailTeamResponse> = apiInterface.getSearchTeamItem(query)
        call.enqueue(object : Callback<DetailTeamResponse>{
            override fun onFailure(call: Call<DetailTeamResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal mencari pertandingan", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DetailTeamResponse>, response: Response<DetailTeamResponse>) {
                listTeam = response.body()!!.teams
                rvListTeam.adapter = ListTeamAdapter(context!!, listTeam){

                }
            }

        })
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        svTeam = searchItem.actionView as SearchView

        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItem.OnActionExpandListener, MenuItemCompat.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                spinner.visibility = INVISIBLE
                val apiInterface :ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                val call : Call<DetailTeamResponse> = apiInterface.getLeagueItem()

                listTeamPresenter = ListTeamPresenter(call, this@FragmentListTeam, context!!)
                listTeamPresenter.getListTeamItem()

                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                return true
            }

        })

        svTeam.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("matsadam","GAGAL WOY")
                if(query!=""){
                    searching(query)
                }
                else{
                    spinner.visibility = VISIBLE
                    val apiInterface :ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                    val call : Call<DetailTeamResponse> = apiInterface.getLeagueItem()

                    listTeamPresenter = ListTeamPresenter(call, this@FragmentListTeam, context!!)
                    listTeamPresenter.getListTeamItem()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("matsadam2","GAGAL WOY")
                if(newText != ""){
                    searching(newText)
                }
                else{
                    spinner.visibility = VISIBLE
                    val apiInterface :ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                    val call : Call<DetailTeamResponse> = apiInterface.getLeagueItem()

                    listTeamPresenter = ListTeamPresenter(call, this@FragmentListTeam, context!!)
                    listTeamPresenter.getListTeamItem()
                }
                return true
            }
        })
    }
}
