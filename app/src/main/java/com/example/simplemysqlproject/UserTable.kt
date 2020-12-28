package com.example.simplemysqlproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class UserTable : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private var userAdapter:UserRVAdapter?=null
    private var userListData:MutableList<UserData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_user)
        initRecyclerView();
        addDataSet();
        userAdapter?.notifyDataSetChanged()
    }

    private fun addDataSet() {
        var stringRequest=StringRequest(Request.Method.POST, Constants.ShowAll_URL,
                Response.Listener<String> { response ->
                    try {
                        var jsonObject: JSONObject = JSONObject(response)
                        var jsonArray = jsonObject.getJSONArray("data")
                        for (a in 0 until jsonArray.length()) {
                            var b: JSONObject = jsonArray.getJSONObject(a)
                            userListData.add(UserData(b.getInt("id"), b.getString("Username"), b.getString("email")))
                            userAdapter?.submitList(userListData)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {

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