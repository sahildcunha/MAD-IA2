package com.example.madia2

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView

class PackAdapter(
    private val context: Context,
    private val items: ArrayList<PackItem>,
    private val onCheckedChange: (PackItem, Boolean) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): PackItem =
        items[position]

    override fun getItemId(position: Int): Long =
        items[position].id.toLong()

    fun updateData(newItems: ArrayList<PackItem>) {

        items.clear()
        items.addAll(newItems)

        notifyDataSetChanged()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_pack, parent, false)

        val item = items[position]

        val checkBox = view.findViewById<CheckBox>(
            R.id.checkBoxItem
        )

        val category = view.findViewById<TextView>(
            R.id.txtCategory
        )

        // Allow checkbox tapping but don't let it
        // intercept the ListView long-press menu.
        checkBox.isFocusable = false
        checkBox.isLongClickable = false

        category.isFocusable = false
        category.isLongClickable = false

        // Remove old listener before changing checkbox state
        checkBox.setOnCheckedChangeListener(null)

        checkBox.text = item.name
        checkBox.isChecked = item.packed == 1

        category.text = item.category

        // Strike-through packed items
        checkBox.paintFlags =
            if (item.packed == 1) {
                checkBox.paintFlags or
                        Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                checkBox.paintFlags and
                        Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

        // Save checkbox changes
        checkBox.setOnCheckedChangeListener { _, isChecked ->

            onCheckedChange(item, isChecked)
        }

        return view
    }
}