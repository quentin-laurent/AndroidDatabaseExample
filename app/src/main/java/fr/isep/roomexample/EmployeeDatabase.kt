package fr.isep.roomexample

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * A class representing the database connection. When annotating this class with @Database, all Room
 * entities must be provided in order to indicate to Room which data classea are actual entities.
 */
@Database(
    entities = [Employee::class, PhoneNumber::class],
    version = 1
)
abstract class EmployeeDatabase: RoomDatabase() {
    // Create an empty method that returns a DAO
    abstract fun employeeDao(): EmployeeDao
}
