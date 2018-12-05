package com.example.akbar.retrofitsample.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import com.example.akbar.retrofitsample.Adapter.LastFixtureAdapter
import com.example.akbar.retrofitsample.Model.Fixture
import com.example.akbar.retrofitsample.Model.FixtureResponse
import com.example.akbar.retrofitsample.R
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import com.example.akbar.retrofitsample.View.NextFixtureView
import com.example.akbar.retrofitsample.presenter.NextFixturePresenter
import retrofit2.Call
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.example.akbar.retrofitsample.presenter.LastFixturePresenter
import retrofit2.Callback
import retrofit2.Response

class FragmentNextFixture : Fragment(), NextFixtureView {

    lateinit var rvLastFixture : RecyclerView
    private val TAG : String = FragmentLastFixture::class.java.canonicalName
    lateinit var progressBar: ProgressBar
    lateinit var nextFixturePresenter: NextFixturePresenter
    lateinit var view: NextFixtureView
    lateinit var v : View
    lateinit var spinner: Spinner
    lateinit var leagueName : String
    lateinit var svFixtureNext : SearchView
    lateinit var listEvent : ArrayList<Fixture>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        v = inflater.inflate(R.layout.fragment_fragment_last_fixture, container, false)
        spinner = v.findViewById(R.id.sp_spinner)
        progressBar = v.findViewById(R.id.pb_loading)
        rvLastFixture = v.findViewById(R.id.rv_lastFixture)
        rvLastFixture.layoutManager = LinearLayoutManager(context)
        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<FixtureResponse> = apiInterface.getNextFixture()

        val spinnerId = resources.getStringArray(R.array.leagueId)
        val spinnerItem = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItem)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                showLoading()
                rvLastFixture.adapter = null
                val apiInterface2: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                val call: Call<FixtureResponse> = apiInterface2.getNextFixtureId(spinnerId[position].replace("f", ""))
                nextFixturePresenter = NextFixturePresenter(call, context!!, this@FragmentNextFixture)
                nextFixturePresenter.getNextFixtureItem()
            }
        }
        nextFixturePresenter = NextFixturePresenter(call, context!!, this)
        nextFixturePresenter.getNextFixtureItem()

        return v
    }

    override fun onPause() {
        super.onPause()
        nextFixturePresenter.cancelRequest()
    }

    override fun onStop() {
        super.onStop()
        nextFixturePresenter.cancelRequest()
    }

    override fun onDestroy() {
        super.onDestroy()
        nextFixturePresenter.cancelRequest()
    }

    override fun showNextFixtureItem(lastFixture: ArrayList<Fixture>) {
        rvLastFixture.adapter = LastFixtureAdapter(context!!, lastFixture){
            hideLoading()
        }
    }
    override fun showLoading(){
        progressBar.visibility = VISIBLE
    }
    override fun hideLoading(){
        progressBar.visibility = INVISIBLE
    }
    fun searching(query :String){
        spinner.visibility = INVISIBLE
        val apiInterface :ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<FixtureResponse> = apiInterface.getSearchEventItem(query)
        call.enqueue(object : Callback<FixtureResponse> {
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
        svFixtureNext = searchItem.actionView as SearchView

        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItem.OnActionExpandListener, MenuItemCompat.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                spinner.visibility = INVISIBLE
                val apiInterface :ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                val call : Call<FixtureResponse> = apiInterface.getNextFixture()

                nextFixturePresenter = NextFixturePresenter(call, context!!, this@FragmentNextFixture)
                nextFixturePresenter.getNextFixtureItem()

                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                return true
            }

        })

        svFixtureNext.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("matsadam","GAGAL WOY")
                if(query!=""){
                    searching(query)
                }
                else{
                    spinner.visibility = VISIBLE
                    val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                    val call : Call<FixtureResponse> = apiInterface.getNextFixture()
                    nextFixturePresenter = NextFixturePresenter(call, context!!, this@FragmentNextFixture)
                    nextFixturePresenter.getNextFixtureItem()
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
                    val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
                    val call : Call<FixtureResponse> = apiInterface.getNextFixture()
                    nextFixturePresenter = NextFixturePresenter(call, context!!, this@FragmentNextFixture)
                    nextFixturePresenter.getNextFixtureItem()
                }
                return true
            }
        })
    }
}
