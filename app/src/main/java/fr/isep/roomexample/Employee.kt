package fr.isep.roomexample

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A class representing an Employee. Room automatically creates a table called "Employee" (the name of the class)
 * that has the same attributes as this class.
 */
@Entity
data class Employee(val firstName: String, val lastName: String) {
    // We do not include the ID in the constructor because it is automatically managed by Room
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
