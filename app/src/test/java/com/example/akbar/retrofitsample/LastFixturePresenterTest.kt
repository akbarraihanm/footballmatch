package com.example.akbar.retrofitsample

import android.content.Context
import com.example.akbar.retrofitsample.Model.Fixture
import com.example.akbar.retrofitsample.Model.FixtureResponse
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import com.example.akbar.retrofitsample.View.LastFixtureView
import com.example.akbar.retrofitsample.presenter.LastFixturePresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Call

class LastFixturePresenterTest{
    @Mock
    private lateinit var view : LastFixtureView
    @Mock
    private lateinit var context : Context
    @Mock
    private lateinit var lastFixture: ArrayList<Fixture>
    lateinit var lastFixturePresenter: LastFixturePresenter
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetLastFixtureItem(){
        MockitoAnnotations.initMocks(this)
        val objek : FixtureResponse?
        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<FixtureResponse> = apiInterface.getLastFixture()
        val response = call.execute()
        objek = response.body()
        lastFixture = objek!!.events

        lastFixturePresenter = LastFixturePresenter(call, view, context)
        lastFixturePresenter.execEvent()

        verify(view).showLastFixtureItem(lastFixture)
    }
}


