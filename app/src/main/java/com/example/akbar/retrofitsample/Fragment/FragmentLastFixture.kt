package com.example.akbar.retrofitsample.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import com.example.akbar.retrofitsample.Adapter.LastFixtureAdapter
import com.example.akbar.retrofitsample.Model.Fixture
import com.example.akbar.retrofitsample.Model.FixtureResponse
import com.example.akbar.retrofitsample.R
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import com.example.akbar.retrofitsample.View.LastFixtureView
import com.example.akbar.retrofitsample.presenter.LastFixturePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.widget.SearchView
import com.example.akbar.retrofitsample.HomeActivity
import kotlinx.android.synthetic.main.activity_home.*
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION



@Suppress("DEPRECATION")
class FragmentLastFixture : Fragment(), LastFixtureView {
    lateinit var rvLastFixture : RecyclerView
    private val TAG : String = FragmentLastFixture::class.java.canonicalName
    private lateinit var lastFixturePresenter: LastFixturePresenter
    lateinit var view : LastFixtureView
    lateinit var v : View
    private lateinit var progressBar: ProgressBar
    private lateinit var spinner : Spinner
    lateinit var leagueName : String
    lateinit var svFixture : SearchView
    lateinit var listEvent : ArrayList<Fixture>
    lateinit var apiInterface: ApiInterface
    lateinit var call: Call<FixtureResponse>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        v=inflater.inflate(R.layout.fragment_fragment_last_fixture, container, false)
        spinner = v.findViewById(R.id.sp_spinner)
        progressBar = v.findViewById(R.id.pb_loading)
        rvLastFixture = v.findViewById(R.id.rv_lastFixture)
        rvLastFixture.layoutManager = LinearLayoutManager(context)

        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        call  = apiInterface.getLastFixture()

        lastFixturePresenter = LastFixturePresenter(call, this, context!!)
        lastFixturePresenter.getLastFixtureItem()

        val spinnerId = resources.getStringArray(R.array.leagueId)
        val spinnerItem = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItem)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                rvLastFixture.adapter = null
                showLoading()
                val apiInterface2 :ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                val call : Call<FixtureResponse> = apiInterface2.getLastFixtureId(spinnerId[position].replace("f",""))
                lastFixturePresenter = LastFixturePresenter(call, this@FragmentLastFixture, context)
                lastFixturePresenter.getLastFixtureItem()
            }
        }

        return v
    }

    override fun onPause() {
        super.onPause()
        lastFixturePresenter.cancelrequest()
    }
    override fun onDestroy() {
        super.onDestroy()
        lastFixturePresenter.cancelrequest()
    }

    override fun onStop() {
        super.onStop()
        lastFixturePresenter.cancelrequest()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        lastFixturePresenter.cancelrequest()

    }

    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    }
    override fun showLastFixtureItem(lastFixture: ArrayList<Fixture>) {
        rvLastFixture.adapter = LastFixtureAdapter(context, lastFixture){
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
        val apiInterface :ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<FixtureResponse> = apiInterface.getSearchEventItem(query)
        call.enqueue(object : Callback<FixtureResponse>{
            override fun onFailure(call: Call<FixtureResponse>, t: Throwable) {
                Toast.makeText(context, "Gagal mencari pertandingan", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<FixtureResponse>, response: Response<FixtureResponse>) {
                listEvent = response.body()!!.event
                rvLastFixture.adapter = LastFixtureAdapter(context,listEvent){

                }
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        svFixture = searchItem.actionView as SearchView

        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItem.OnActionExpandListener, MenuItemCompat.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                spinner.visibility = INVISIBLE
                val apiInterface :ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                val call : Call<FixtureResponse> = apiInterface.getLastFixture()

                lastFixturePresenter = LastFixturePresenter(call, this@FragmentLastFixture, context!!)
                lastFixturePresenter.getLastFixtureItem()

                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                return true
            }

        })

        svFixture.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("matsadam","GAGAL WOY")
                if(query!=""){
                    searching(query)
                }
                else{
                    spinner.visibility = VISIBLE
                    val apiInterface :ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                    val call : Call<FixtureResponse> = apiInterface.getLastFixture()

                    lastFixturePresenter = LastFixturePresenter(call, this@FragmentLastFixture, context!!)
                    lastFixturePresenter.getLastFixtureItem()
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
                    val call : Call<FixtureResponse> = apiInterface.getLastFixture()

                    lastFixturePresenter = LastFixturePresenter(call, this@FragmentLastFixture, context!!)
                    lastFixturePresenter.getLastFixtureItem()
                }
                return true
            }
        })
    }
}
