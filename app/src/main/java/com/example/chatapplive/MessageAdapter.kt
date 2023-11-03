package com.example.chatapplive

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val context:Context):RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    private var messageModelList:MutableList<MessageModel> = ArrayList()
    init{
        messageModelList=ArrayList()
    }

    fun add(messageModel: MessageModel)
    {
        messageModelList.add(messageModel)
        notifyDataSetChanged()
    }
    fun addAll(messageModel: List<MessageModel>)
    {
        messageModelList.addAll(messageModel)
        notifyDataSetChanged()
    }
    fun clear()
    {
        messageModelList.clear()
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val msg:TextView=itemView.findViewById(R.id.message)
        val main:LinearLayout=itemView.findViewById(R.id.mainMessageLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view:View=LayoutInflater.from(context).inflate(R.layout.message_row,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentMessage =messageModelList[position]

        holder.msg.text=currentMessage.message
        val isCurrentUser=currentMessage.senderId==FirebaseAuth.getInstance().uid

        val backgroundColorRes=if(isCurrentUser) R.color.white else R.color.theme1
        val textColorRes=if(isCurrentUser) R.color.black else R.color.white

        holder.main.setBackgroundColor(ContextCompat .getColor(context,backgroundColorRes))
        holder.msg.setTextColor(ContextCompat .getColor(context,textColorRes))

        holder .itemView.setOnClickListener {
            //DELETE FUN

        }

    }


}