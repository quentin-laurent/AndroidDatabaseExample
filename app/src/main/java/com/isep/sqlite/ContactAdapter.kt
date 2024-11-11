package com.isep.sqlite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView



class ContactAdapter(private val context: Context,
                     private val contacts: Array<Array<String>>,
                     private val listener: ContactAdapterListener) : BaseAdapter() {

    interface ContactAdapterListener {
        fun onContactDeleted()
    }

    override fun getCount(): Int {
        return contacts.size
    }

    override fun getItem(position: Int): Any {
        return contacts[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_contact, parent, false)

        val contactInfoTextView = view.findViewById<TextView>(R.id.textViewContactInfo)
        val deleteButton = view.findViewById<Button>(R.id.buttonDelete)

        val contact = contacts[position]
        val id = contact[0]
        val firstName = contact[1]
        val lastName = contact[2]
        val phoneNumbers = contact[3]

        contactInfoTextView.text = "$firstName - $lastName - $phoneNumbers"

        deleteButton.setOnClickListener {
            // Delete the entry from the database using the ID
            val dbHelper = DatabaseHelper(context)
            dbHelper.deleteData(id.toInt())
            // Refresh the ListView
            listener.onContactDeleted()
        }

        return view
    }
}