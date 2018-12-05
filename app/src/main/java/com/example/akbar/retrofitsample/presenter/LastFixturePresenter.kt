package com.example.akbar.retrofitsample.presenter

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.akbar.retrofitsample.Fragment.FragmentLastFixture
import com.example.akbar.retrofitsample.Model.Fixture
import com.example.akbar.retrofitsample.Model.FixtureResponse
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import com.example.akbar.retrofitsample.View.LastFixtureView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LastFixturePresenter(private val call : Call<FixtureResponse>,
                           private val view : LastFixtureView,
                           private val context: Context?){
    lateinit var objek : FixtureResponse

    fun callView(lastFixture: ArrayList<Fixture>){
        view.showLastFixtureItem(lastFixture)
    }
    fun cancelrequest(){
        call.cancel()
    }
    fun getLastFixtureItem(){
        var lastFixture : ArrayList<Fixture>

        call.enqueue(object : Callback<FixtureResponse> {
            override fun onFailure(call: Call<FixtureResponse>?, t: Throwable?) {
                Toast.makeText(context,"Gagal fetch last fixture", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Gagal fetch last fixture")
            }

            override fun onResponse(call: Call<FixtureResponse>?, response: Response<FixtureResponse>?) {
                lastFixture = response!!.body()!!.events
                Log.d(TAG, response.body().toString())
                Log.e("coba", "response")
                view.showLoading()
                try {
                    view.showLastFixtureItem(lastFixture)
                }catch (e: Exception){
                    Log.d(ContentValues.TAG, "Pindah Fragment")
                }

                view.hideLoading()
            }
        })
    }
    fun execEvent(){
        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getLastFixture()
        val response = call.execute()
        objek = response.body()!!
        val lastFixture = objek.events
        callView(lastFixture)
    }
}