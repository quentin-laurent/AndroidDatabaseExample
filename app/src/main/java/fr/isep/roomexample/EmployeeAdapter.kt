package fr.isep.roomexample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

/**
 * This class has no link with Room, it is purely used to update the UI.
 */
class EmployeeAdapter(
    private val context: Context,
    private val employeesWithPhoneNumbers: List<EmployeeWithPhoneNumbers>,
    private val listener: EmployeeAdapterListener,
    private val employeeDao: EmployeeDao
): BaseAdapter() {

    interface EmployeeAdapterListener {
        fun onEmployeeDeleted()
    }

    override fun getCount(): Int {
        return employeesWithPhoneNumbers.size
    }

    override fun getItem(position: Int): Any {
        return employeesWithPhoneNumbers[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_employee, parent, false)

        // Views
        val employeeInfoTextView = view.findViewById<TextView>(R.id.textViewEmployeeInfo)
        val deleteButton = view.findViewById<Button>(R.id.buttonDelete)

        val employeeWithPhoneNumbers = employeesWithPhoneNumbers[position]
        var phoneNumbersString = ""
        for (phoneNumber in employeeWithPhoneNumbers.phoneNumbers)
            phoneNumbersString += "${phoneNumber.countryCode}${phoneNumber.number},"
        phoneNumbersString.dropLast(1)

        // Information displayed on each line of the listView
        employeeInfoTextView.text = "${employeeWithPhoneNumbers.employee.firstName} ${employeeWithPhoneNumbers.employee.lastName} - $phoneNumbersString"

        // Event listened on the "Delete" button of each Employee
        deleteButton.setOnClickListener {
            // Delete the Employee from the database
            employeeDao.deleteEmployee(employeeWithPhoneNumbers.employee)
            // Also delete all phone numbers associated with that Employee
            for (phoneNumber in employeeWithPhoneNumbers.phoneNumbers)
                employeeDao.deletePhoneNumber(phoneNumber)
            // Refresh the ListView
            listener.onEmployeeDeleted()
        }

        return view
    }
}