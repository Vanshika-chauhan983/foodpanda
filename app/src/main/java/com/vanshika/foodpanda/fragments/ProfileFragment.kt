package com.vanshika.foodpanda.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vanshika.foodpanda.R

class ProfileFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var textview1: TextView
    private lateinit var textview2: TextView
    private lateinit var textview3: TextView
    private lateinit var textview4: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_profile,container,false)
        textview1=view.findViewById(R.id.username)
        textview2=view.findViewById(R.id.mobilenumber)
        textview3=view.findViewById(R.id.emailAddress)
        textview4=view.findViewById(R.id.address)

        val uidresult=FirebaseAuth.getInstance().currentUser?.uid

        databaseReference= uidresult?.let {
            FirebaseDatabase.getInstance().getReference("User").child(uidresult)
        }!!

        fetchdata()

        return view
    }


    private fun fetchdata() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val name=snapshot.child("name").getValue(String::class.java)
                val number=snapshot.child("number").getValue(String::class.java)
                val email=snapshot.child("email").getValue(String::class.java)
                val address=snapshot.child("deliveryAddress").getValue(String::class.java)


                textview1.text=name
                textview2.text="+91-$number"
                textview3.text=email
                textview4.text=address

            }

            @SuppressLint("SetTextI18n")
            override fun onCancelled(error: DatabaseError) {
                textview1.text="Failed to load! ${error.message}"
            }

        })
    }
}