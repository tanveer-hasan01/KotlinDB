package com.example.projectkt.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projectkt.ModelData.User
import com.example.projectkt.MySharedPreference
import com.example.projectkt.MySharedPreference_Factory
import com.example.projectkt.R
import com.example.projectkt.ViewModel.CreateViewModel
import com.example.projectkt.databinding.ActivityAddUserBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class AddUserActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var sharedPreferences: MySharedPreference

    private lateinit var binding: ActivityAddUserBinding
    lateinit var viewModel: CreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        title="Add User"
        initViewModel()
        createUserObservable()
        initSpinner()





        binding.btSave.setOnClickListener(View.OnClickListener {


            if (binding.etName.text.toString().isEmpty())
            {
                binding.etName.setError("invalid name")

            }else if (binding.etEmail.text.toString().isEmpty()){
                binding.etEmail.setError("invalid email")

            }

            else if (!binding.radioFemale.isChecked && !binding.radioMale.isChecked){

                Toast.makeText(this,"Please select gender!",Toast.LENGTH_LONG).show()
            }
            else{

                createUser()
            }
        })



    }

    fun initSpinner() {
        val languages = resources.getStringArray(R.array.status)

        // access the spinner

        if (binding.spinnerStatus != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            binding.spinnerStatus.adapter = adapter

            binding.spinnerStatus.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                   /* Toast.makeText(this@MainActivity,
                        binding.spinnerStatus.selectedItem.toString() + " " +
                                "" + languages[position], Toast.LENGTH_SHORT).show()*/
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    private fun createUser(){


        var userData= User("",binding.etName.text.toString(),binding.etEmail.text.toString()
            ,binding.spinnerStatus.selectedItem.toString(),binding.radioMale.text.toString())

        viewModel.createUser(userData)

    }

    private fun initViewModel(){

        viewModel=ViewModelProvider(this).get(CreateViewModel::class.java)


    }

    private fun createUserObservable()
    {
        viewModel.getCreateNewUserObservable().observe(this, androidx.lifecycle.Observer {

            if (it==null)
            {

                Toast.makeText(this, "Successful but dummy API insertion now unavailable !", Toast.LENGTH_LONG).show()
            }else{

                Toast.makeText(this, "Successfully created/updated user", Toast.LENGTH_LONG).show()
            }


        })

    }



}