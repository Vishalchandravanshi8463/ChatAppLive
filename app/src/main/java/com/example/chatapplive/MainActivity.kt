package com.example.chatapplive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapplive.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding?=null
    var databaseReference: DatabaseReference?=null

   var userAdapter:UserAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        userAdapter=UserAdapter(this)
        binding?.recycler?.adapter=userAdapter
        binding?.recycler?.layoutManager=LinearLayoutManager(this)

        databaseReference=FirebaseDatabase.getInstance().getReference("users")

        databaseReference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userAdapter!!.clear()

                for (dataSnapshot in snapshot.children)
                {
                    val uid=dataSnapshot.key
                    if(uid!=FirebaseAuth.getInstance().uid)
                    {
                        val userModel=dataSnapshot.getValue(UserModel::class.java)
                        println(userModel)
                        userModel?.let { userAdapter?.add(it) }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Error fetching data: ")

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.logout->{
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@MainActivity,SigninActivity::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}