package com.example.madia2

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var db: DBHelper
    private lateinit var listView: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var txtProgress: TextView
    private lateinit var packAdapter: PackAdapter

    private val items = ArrayList<PackItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(
            R.id.toolbar
        )

        setSupportActionBar(toolbar)
        supportActionBar?.title = "PackList"

        db = DBHelper(this)

        listView = findViewById(R.id.listView)
        progressBar = findViewById(R.id.progressBar)
        txtProgress = findViewById(R.id.txtProgress)

        packAdapter = PackAdapter(this, items) { item, checked ->

            db.updatePacked(
                item.id,
                if (checked) 1 else 0
            )

            // Refresh list and progress
            loadItems()
        }

        listView.adapter = packAdapter

        // Enable long press menu
        registerForContextMenu(listView)

        // Add Item button
        findViewById<Button>(R.id.btnAdd).setOnClickListener {

            startActivity(
                Intent(this, AddItemActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()

        if (::packAdapter.isInitialized) {
            loadItems()
        }
    }

    private fun loadItems() {
        packAdapter.updateData(db.getAllItems())
        updateProgress()
    }

    private fun updateProgress() {

        val total = items.size

        val packed = items.count {
            it.packed == 1
        }

        txtProgress.text = "Packed: $packed / $total"

        progressBar.progress =
            if (total == 0) 0
            else (packed * 100 / total)
    }

    // Options Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menuUncheck -> {

                db.uncheckAll()
                loadItems()

                Toast.makeText(
                    this,
                    "All items unchecked",
                    Toast.LENGTH_SHORT
                ).show()

                return true
            }

            R.id.menuDeletePacked -> {

                db.deletePackedItems()
                loadItems()

                Toast.makeText(
                    this,
                    "Packed items deleted",
                    Toast.LENGTH_SHORT
                ).show()

                return true
            }

            R.id.menuDefaults -> {

                val defaults = listOf(
                    "T-Shirts" to "Clothes",
                    "Jeans" to "Clothes",
                    "Shoes" to "Clothes",
                    "Passport" to "Documents",
                    "ID Card" to "Documents",
                    "Tickets" to "Documents",
                    "Mobile Phone" to "Gadgets",
                    "Charger" to "Gadgets",
                    "Headphones" to "Gadgets"
                )

                defaults.forEach {
                    db.addItem(it.first, it.second)
                }

                loadItems()

                Toast.makeText(
                    this,
                    "Default items added",
                    Toast.LENGTH_SHORT
                ).show()

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // Long Press Context Menu
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {

        super.onCreateContextMenu(menu, v, menuInfo)

        menu.setHeaderTitle("Manage Item")

        menu.add(0, 101, 0, "Edit")
        menu.add(0, 102, 1, "Delete")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        val info = item.menuInfo
                as? AdapterView.AdapterContextMenuInfo
            ?: return super.onContextItemSelected(item)

        val selected = packAdapter.getItem(info.position)

        when (item.itemId) {

            101 -> {

                val intent = Intent(
                    this,
                    AddItemActivity::class.java
                )

                intent.putExtra("item_id", selected.id)
                intent.putExtra("item_name", selected.name)
                intent.putExtra("item_category", selected.category)

                startActivity(intent)

                return true
            }

            102 -> {

                AlertDialog.Builder(this)
                    .setTitle("Delete Item")
                    .setMessage("Delete ${selected.name}?")
                    .setPositiveButton("Delete") { _, _ ->

                        db.deleteItem(selected.id)
                        loadItems()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()

                return true
            }
        }

        return super.onContextItemSelected(item)
    }
}