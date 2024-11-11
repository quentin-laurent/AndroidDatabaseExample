package fr.isep.roomexample

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A class representing a Phone Number. Room automatically creates a table called "PhoneNumber" (the name of the class)
 * that has the same attributes as this class.
 */
@Entity
data class PhoneNumber(val countryCode: String, val number: String, val employeeId: Long) {
    // We do not include the ID in the constructor because it is automatically managed by Room
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
