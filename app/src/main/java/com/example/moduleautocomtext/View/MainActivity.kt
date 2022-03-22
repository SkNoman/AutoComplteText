package com.example.moduleautocomtext.View

import com.example.moduleautocomtext.Adapters.UserListAdapter
import com.example.moduleautocomtext.Model.UserModel
import com.example.moduleautocomtext.R
import com.example.moduleautocomtext.ViewModel.MainActivityViewModel


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.*

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var recyclerAdapter: UserListAdapter
    lateinit var autoTextView:AutoCompleteTextView
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        autoTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView);
        listView = findViewById<ListView>(R.id.listView_Emp)
        initRecyclerView()
        initViewModel()
    }
    private fun initRecyclerView() {

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        val viewModel: MainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer { userList ->
            if(userList != null) {

                val adapter = UserListAdapter(this, android.R.layout.simple_list_item_1, userList)
                autoCompleteTextView.setAdapter(adapter)
                autoCompleteTextView.threshold = 1
                val newlist = mutableListOf<String>()


                autoCompleteTextView.setOnItemClickListener() { parent, _, position, id ->
                    val selectedEmp = parent.adapter.getItem(position) as UserModel?
                    autoCompleteTextView.setText(selectedEmp?.login)
                    selectedEmp?.login?.let { newlist.add(it) }
                    val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,newlist)
                    listView.setAdapter(adapter)
                }

            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeAPICall()
    }
}





