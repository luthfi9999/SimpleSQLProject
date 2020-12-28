package com.example.simplemysqlproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

class UserRVAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val TAG: String = "AppDebug"

    private var items: List<UserData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.user_data, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is BlogViewHolder -> {
                holder.bind(items.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(userList: List<UserData>){
        items = userList
    }

    class BlogViewHolder
    constructor(
            itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val username:TextView = itemView.findViewById(R.id.tvUsername)
        val email:TextView = itemView.findViewById(R.id.tvEmail)

        fun bind(userdata: UserData){
            /*
            val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)

            */
            /*
            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(blogPost.image)
                    .into(blog_image)
                    */
            username.setText(userdata.username)
            email.setText(userdata.email)
        }

    }

}
