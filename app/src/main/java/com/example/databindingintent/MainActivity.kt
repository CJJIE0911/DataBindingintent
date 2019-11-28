package com.example.databindingintent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.databindingintent.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myContact = Contact(name = "See",phone = "012345678")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //setContentView(R.layout.activity_main)
        binding.contact = myContact

        binding.buttonSave.setOnClickListener{
            with(binding){
                contact?.name = editTextName.text.toString()
                contact?.phone = editTextPhone.text.toString()
                invalidateAll()//refresh UI
            }
        }

        binding.buttonSend.setOnClickListener{
            //Create an Explicit Intent
            val intent = Intent(this,
                SecondActivity::class.java)

            //Prepare Extra data
            intent.putExtra(EXTRA_NAME,binding.contact?.name)
            intent.putExtra(EXTRA_PHONE,binding.contact?.phone)
            //startActivity(intent)
            // Expect no return your parent
            startActivityForResult(intent, RESQUEST_REPLY) //Expect PTPTN expecrs result
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RESQUEST_REPLY){
            if(resultCode == Activity.RESULT_OK){
                val reply = data?.getStringExtra(EXTRA_REPLY)
                textViewReply.text = String.format("Reply : %s",reply)
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object{
        const val EXTRA_NAME = "com.example.databindingintent.NAME"
        const val EXTRA_PHONE = "com.example.databindingintent.PHONE"
        const val EXTRA_REPLY = "com.example.databindingintent.REPLY"
        const val RESQUEST_REPLY = 1
    }

}
