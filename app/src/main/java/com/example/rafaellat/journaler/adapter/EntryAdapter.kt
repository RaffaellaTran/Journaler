package com.example.rafaellat.journaler.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import androidx.fragment.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.rafaellat.journaler.Journaler.Companion.ctx
import com.example.rafaellat.journaler.R
import com.example.rafaellat.journaler.R.id.items
import com.example.rafaellat.journaler.activity.BaseActivity
import com.example.rafaellat.journaler.database.DbHelper
import com.example.rafaellat.journaler.model.Entry

class EntryAdapter(ctx: Context, crsr: Cursor): CursorAdapter(ctx, crsr) {

    //return the instance of the view to popuate with data
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(R.layout.adapter_entry, null)
    }

    //populate data from the cursor instance
    override fun bindView(view: View?, context: Context?, p1: Cursor?) {
        context?.let{
            val label = view?.findViewById<TextView>(R.id.title)
            label?.text = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_TITLE))
            label?.setOnClickListener {
               Toast.makeText(context, cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MESSAGE)), Toast.LENGTH_SHORT).show()

            }
        }
    }

//
//    //returns the instance of the populated view based on the current position in the container
//    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
//        p1?.let {
//            return p1
//        }
//        val inflater= LayoutInflater.from(ctx)
//        val view= inflater.inflate(R.layout.adapter_entry, null)
//        val label = view.findViewById<TextView>(R.id.title)
//        label.text= items[p0].title
//        return view
//    }
//
//    //return the instance of the item we use to create the view. In this case can be Note or Todo
//    override fun getItem(p0: Int): Entry= items[p0]
//
//    //Id of the current item instance
//    override fun getItemId(p0: Int): Long = items[p0].id
//
//    //return the total number of items
//    override fun getCount(): Int = items.size

}