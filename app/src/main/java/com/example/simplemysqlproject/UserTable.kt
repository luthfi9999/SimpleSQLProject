package com.example.simplemysqlproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonArray
import org.json.JSONException
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class UserTable : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private var userAdapter:UserRVAdapter?=null
    private var userListData:MutableList<UserData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_user)
        initRecyclerView();
        showRetro();
        userAdapter?.notifyDataSetChanged()
    }
    private fun showRetro(){
        var jsonObject:Callback<JsonObject>?=null
        NetworkConfig().getService()
                .getUsers()
                .enqueue(object :Callback<JsonObject>{
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        var jsonArray=response.body()?.getAsJsonArray("data")
                        var b:Int=0
                        for(i in 0..(jsonArray?.size()?.minus(1)!!)){
                            userListData.add(UserData(jsonArray?.get(i)?.asJsonObject?.get("id")?.
                                asInt,jsonArray?.get(i)?.asJsonObject?.get("Username")?.asString,
                                    jsonArray?.get(i)?.asJsonObject?.get("email")?.asString))
                        }
                        Toast.makeText(this@UserTable, response.body()?.get("message").toString().trim(), Toast.LENGTH_LONG).show()
                        userAdapter?.submitList(userListData)
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Toast.makeText(this@UserTable, t.localizedMessage, Toast.LENGTH_LONG).show()
                    }

                })
    }
    private fun addDataSet() {
        var stringRequest=StringRequest(Request.Method.POST, Constants.ShowAll_URL,
                com.android.volley.Response.Listener<String> { response ->
                    try {
                        var jsonObject: org.json.JSONObject = org.json.JSONObject(response)
                        var jsonArray = jsonObject.getJSONArray("data")
                        for (a in 0 until jsonArray.length()) {
                            var b: org.json.JSONObject = jsonArray.getJSONObject(a)
                            userListData.add(UserData(b.getInt("id"), b.getString("Username"), b.getString("email")))
                            userAdapter?.submitList(userListData)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                com.android.volley.Response.ErrorListener {

                }

        )


        var reqQueue=Volley.newRequestQueue(this@UserTable)
        reqQueue.add(stringRequest)
    }

    private fun initRecyclerView() {
        rv=findViewById<RecyclerView>(R.id.recycler_view)
        rv.apply {
            layoutManager=LinearLayoutManager(this@UserTable)
            userAdapter=UserRVAdapter()
            adapter=userAdapter
        }
    }
}