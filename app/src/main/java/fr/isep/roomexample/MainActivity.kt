package fr.isep.roomexample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room

class MainActivity : AppCompatActivity(), EmployeeAdapter.EmployeeAdapterListener {

    private lateinit var db: EmployeeDatabase
    private lateinit var employeeDao: EmployeeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Create an instance of the database
        // NOTE: Here, we allow queries to be run on the main thread, which is not recommended as it can lock the UI for a long period of time
        db = Room.databaseBuilder(applicationContext, EmployeeDatabase::class.java, "employees-db").allowMainThreadQueries().build()
        // Create an instance of the Employee DAO that allows access to the different methods
        employeeDao = db.employeeDao()

        // Views
        val firstName = findViewById<EditText>(R.id.inputFirstName)
        val lastName = findViewById<EditText>(R.id.inputLastName)
        val phonesNumber = findViewById<EditText>(R.id.inputPhones)
        val addButton = findViewById<Button>(R.id.buttonAdd)

        // Load the initial data present in the database
        updateEmployeesListUI()

        // Event listened on the "Validate" button
        addButton.setOnClickListener {
            // Create a new Employee using the fields from the different views and insert it into the database
            val employee = Employee(firstName.text.toString(), lastName.text.toString())
            val employeeId = employeeDao.insertEmployee(employee)

            // Associate all phone numbers in the database and set their Foreign Key to the Primary key of the created Employee
            val phoneNumbersList = phonesNumber.text.toString().split(",")
            for (phoneNumberString in phoneNumbersList) {
                // NOTE: We assume the country code is 2 digits (e.g. +33)
                val phoneNumber = PhoneNumber(phoneNumberString.substring(0, 3), phoneNumberString.substring(3, 11), employeeId)
                employeeDao.insertPhoneNumber(phoneNumber)
            }

            // Update the UI
            updateEmployeesListUI()
        }
    }

    /**
     * Updates the listView UI element in order to display all the Employees from the database
     */
    private fun updateEmployeesListUI() {
        val employeesWithPhoneNumbers = employeeDao.getAllEmployeesWithPhoneNumbers()
        val adapter = EmployeeAdapter(this, employeesWithPhoneNumbers, this, employeeDao)
        val listViewEmployees = findViewById<ListView>(R.id.listViewEmployees)
        listViewEmployees.adapter = adapter
    }

    override fun onEmployeeDeleted() {
        updateEmployeesListUI()
    }
}
