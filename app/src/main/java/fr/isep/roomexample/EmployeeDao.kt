package fr.isep.roomexample

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

/**
 * An interface containing all the user-defined methods that interact with the database.
 */
@Dao
interface EmployeeDao {

    @Insert
    fun insertEmployee(employee: Employee): Long

    @Insert
    fun insertPhoneNumber(phoneNumber: PhoneNumber): Long

    @Delete
    fun deleteEmployee(employee: Employee)

    @Delete
    fun deletePhoneNumber(phoneNumber: PhoneNumber)

    @Query("SELECT * FROM Employee ORDER BY firstName ASC")
    fun getAllEmployees(): List<Employee>

    @Transaction
    @Query("SELECT * FROM Employee ORDER BY firstName ASC")
    fun getAllEmployeesWithPhoneNumbers(): List<EmployeeWithPhoneNumbers>
}
