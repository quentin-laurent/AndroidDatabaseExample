package fr.isep.roomexample

import androidx.room.Embedded
import androidx.room.Relation

/**
 * A class representing a SQL Relation between two Room entities. In this case, a One-To-Many relationship
 * between the Employee and PhoneNumber entities.
 */
data class EmployeeWithPhoneNumbers(
    @Embedded
    val employee: Employee,
    @Relation(
        parentColumn = "id",        // Primary key of the Employee class
        entityColumn = "employeeId" // Foreign key of the PhoneNumber class
    )
    val phoneNumbers: List<PhoneNumber>
)
