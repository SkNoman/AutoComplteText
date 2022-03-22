package com.example.moduleautocomtext.View

import com.example.moduleautocomtext.Adapters.UserListAdapter
import com.example.moduleautocomtext.Model.UserModel
import com.example.moduleautocomtext.R
import com.example.moduleautocomtext.ViewModel.MainActivityViewModel


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import android.widget.*

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moduleautocomtext.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.samplelist.*

class MainActivity : AppCompatActivity() {
    lateinit var recyclerAdapter: UserListAdapter
    lateinit var autoTextView:AutoCompleteTextView
    lateinit var listView: ListView
    lateinit var deleteBtn:Button
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater) //viewBinding
        val view = binding.root
        setContentView(view)
//        autoTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView);
//        listView = findViewById<ListView>(R.id.listView_Emp)
//        deleteBtn = findViewById<Button>(R.id.btn_Delete)


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
                binding.autoCompleteTextView.setAdapter(adapter)
                binding.autoCompleteTextView.threshold = 1
                val newlist = mutableListOf<String>()


                autoCompleteTextView.setOnItemClickListener() { parent, _, position, id ->
                    val selectedEmp = parent.adapter.getItem(position) as UserModel?
                    autoCompleteTextView.setText(selectedEmp?.login)
                    selectedEmp?.login?.let { newlist.add(it) }
                    val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,newlist)
                    binding.listViewEmp.setAdapter(adapter)
                }






            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeAPICall()
    }
}





