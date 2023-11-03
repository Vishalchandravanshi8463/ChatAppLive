package com.example.chatapplive

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val context:Context):RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private var userModelList:MutableList<UserModel> = ArrayList()
            init{
                userModelList=ArrayList()
            }

    fun add(userModel: UserModel)
    {
        userModelList.add(userModel)
        notifyDataSetChanged()
    }
    fun addAll(userModels: List<UserModel>)
    {
        userModelList.addAll(userModels)
        notifyDataSetChanged()
    }
    fun clear()
    {
        userModelList.clear()
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView:ImageView=itemView.findViewById(R.id.imageView)
        val userNameTextView:TextView=itemView.findViewById(R.id.userName)
        val userEmailTextView:TextView=itemView.findViewById(R.id.userEmail)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view:View=LayoutInflater.from(context).inflate(R.layout.user_row,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentUser =userModelList[position]

        holder.userNameTextView.text=currentUser.name
        holder.userEmailTextView.text=currentUser.email

        holder.itemView.setOnClickListener{
            val userId=currentUser.userId
            println(userId)

            if(userId!=null && userId.isNotEmpty())
            {
                val intent= Intent(context,ChatActivity::class.java)
                intent.putExtra("id",userId)
                context.startActivity(intent)


            }
        }
    }
}