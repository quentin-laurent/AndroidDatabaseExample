package com.isep.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "sinj2.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "contacts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DATA1 = "firstname"
        private const val COLUMN_DATA2 = "lastname"
        private const val COLUMN_DATA3 = "phonenumbers"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_DATA1 TEXT, " +
                "$COLUMN_DATA2 TEXT, " +
                "$COLUMN_DATA3 TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(firstName: String, lastName: String, phoneNumbers: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_DATA1, firstName)
            put(COLUMN_DATA2, lastName)
            put(COLUMN_DATA3, phoneNumbers)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    fun selectAllData(): Array<Array<String>> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val data = mutableListOf<Array<String>>()

        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val data1 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA1))
            val data2 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA2))
            val data3 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA3))
            data.add(arrayOf(id, data1, data2, data3))
        }

        cursor.close()
        return data.toTypedArray()
    }

    fun deleteData(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}