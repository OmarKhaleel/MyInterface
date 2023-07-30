package com.example.myinterface

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomePage : ComponentActivity() {

    private lateinit var gridView: GridView
//    private val gridOptions = arrayOf("Expenses", "ToDo List")
    private val apiKey = "79bbf7055c809b6152b1376c43bcde83"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val gridOptions = ArrayList<GridViewItem>()
        //add data to the list
        gridOptions.add(GridViewItem("Expenses", R.drawable._289247))
        gridOptions.add(GridViewItem("ToDo List", R.drawable._080887))



        gridView = findViewById(R.id.gridView)
        val adapter = MyBaseAdapter(this, gridOptions)
        gridView.adapter = adapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val selectedOption = gridView.getItemAtPosition(position)

            if (selectedOption != null) {
                val item = selectedOption as GridViewItem
                when (item.name) {
                    "Expenses" -> startActivity(Intent(this, ExpensesActivity::class.java))
                    "ToDo List" -> startActivity(Intent(this, ToDoList::class.java))
                }
//                "Expenses" -> startActivity(Intent(this, ExpensesActivity::class.java))
//                "ToDo List" -> startActivity(Intent(this, ToDoList::class.java))
            }
        }

        getWeatherData()
    }

    private fun getWeatherData() {
        val city = "Amman"

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherApi::class.java)

        weatherApi.getWeatherDataByCity(city, apiKey).enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    weatherData?.let {
                        displayWeather(it)
                    }
                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun displayWeather(weatherData: WeatherData) {
        val cityTextView: TextView = findViewById(R.id.cityTextView)
        val temperatureTextView: TextView = findViewById(R.id.temperatureTextView)
        val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)

        val temperature = weatherData.main.temp - 273.15
        val description = weatherData.weather[0].description
        val cityName = "Amman"

        cityTextView.text = cityName
        temperatureTextView.text = String.format("%.1f °C", temperature)
        descriptionTextView.text = description
    }
}