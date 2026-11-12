package com.example.madia2

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {

    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        db = DBHelper(this)

        val title = findViewById<TextView>(R.id.txtTitle)
        val name = findViewById<EditText>(R.id.edtItemName)
        val spinner = findViewById<Spinner>(R.id.spinnerCategory)
        val save = findViewById<Button>(R.id.btnSave)

        val categories = arrayOf("Clothes", "Documents", "Gadgets")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinner.adapter = adapter

        val itemId = intent.getIntExtra("item_id", -1)

        if (itemId != -1) {
            title.text = "Edit Packing Item"
            save.text = "Update Item"

            name.setText(intent.getStringExtra("item_name"))

            val oldCategory =
                intent.getStringExtra("item_category")

            val position = categories.indexOf(oldCategory)

            if (position >= 0) {
                spinner.setSelection(position)
            }
        }

        save.setOnClickListener {

            val itemName = name.text.toString().trim()
            val category = spinner.selectedItem.toString()

            if (itemName.isEmpty()) {
                name.error = "Enter item name"
                return@setOnClickListener
            }

            if (itemId == -1) {
                db.addItem(itemName, category)
                Toast.makeText(
                    this,
                    "Item added successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                db.updateItem(itemId, itemName, category)
                Toast.makeText(
                    this,
                    "Item updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }

            finish()
        }
    }
}
