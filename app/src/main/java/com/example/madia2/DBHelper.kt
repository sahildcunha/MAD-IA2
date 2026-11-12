package com.example.madia2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class PackItem(
    val id: Int,
    val name: String,
    val category: String,
    val packed: Int
)

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "PackList.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                category TEXT,
                packed INTEGER DEFAULT 0
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS items")
        onCreate(db)
    }

    fun addItem(name: String, category: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("category", category)
            put("packed", 0)
        }

        return db.insert("items", null, values) != -1L
    }

    fun getAllItems(): ArrayList<PackItem> {
        val items = ArrayList<PackItem>()
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM items ORDER BY id DESC", null
        )

        cursor.use {
            while (it.moveToNext()) {
                items.add(
                    PackItem(
                        it.getInt(it.getColumnIndexOrThrow("id")),
                        it.getString(it.getColumnIndexOrThrow("name")),
                        it.getString(it.getColumnIndexOrThrow("category")),
                        it.getInt(it.getColumnIndexOrThrow("packed"))
                    )
                )
            }
        }
        return items
    }

    fun updatePacked(id: Int, packed: Int) {
        val values = ContentValues().apply {
            put("packed", packed)
        }
        writableDatabase.update(
            "items", values, "id=?", arrayOf(id.toString())
        )
    }

    fun updateItem(id: Int, name: String, category: String) {
        val values = ContentValues().apply {
            put("name", name)
            put("category", category)
        }
        writableDatabase.update(
            "items", values, "id=?", arrayOf(id.toString())
        )
    }

    fun deleteItem(id: Int) {
        writableDatabase.delete(
            "items", "id=?", arrayOf(id.toString())
        )
    }

    fun deletePackedItems() {
        writableDatabase.delete("items", "packed=1", null)
    }

    fun uncheckAll() {
        val values = ContentValues().apply {
            put("packed", 0)
        }
        writableDatabase.update("items", values, null, null)
    }
}
