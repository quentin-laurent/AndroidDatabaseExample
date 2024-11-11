package com.isep.sqlite

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity(), ContactAdapter.ContactAdapterListener {

    private val TAG: String = "MAIN"
    private val dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val firstName = findViewById<EditText>(R.id.inputFirstName)
        val lastName = findViewById<EditText>(R.id.inputLastName)
        val phonesNumber = findViewById<EditText>(R.id.inputPhones)
        val addButton = findViewById<Button>(R.id.buttonAdd)

        updateContactsList()

        addButton.setOnClickListener {

            val text1 = firstName.text.toString()
            val text2 = lastName.text.toString()
            val text3 = phonesNumber.text.toString()

            Log.d("MainActivity", "Input 1: $text1")
            Log.d("MainActivity", "Input 2: $text2")
            Log.d("MainActivity", "Input 3: $text3")
            val rowId = dbHelper.insertData(text1, text2, text3)
            // update row with ID
            updateContactsList()
        }
    }

    fun updateContactsList() {
        val contacts = dbHelper.selectAllData()
        val adapter = ContactAdapter(this, contacts, this)
        val listViewContacts = findViewById<ListView>(R.id.listViewContacts)
        listViewContacts.adapter = adapter
    }

    override fun onContactDeleted() {
        updateContactsList()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}