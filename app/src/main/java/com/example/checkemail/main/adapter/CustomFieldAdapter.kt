package com.example.checkemail.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import java.util.*


class CustomFilterAdapter(
    context: Context,
    viewResourceId: Int,
    private val items: ArrayList<String>
) :
    ArrayAdapter<String?>(context, viewResourceId, items as List<String?>) {
    private val itemsAll: ArrayList<String> = items.clone() as ArrayList<String>
    private val suggestions: ArrayList<String> = ArrayList()
    private val viewResourceId: Int

    init {
        this.viewResourceId = viewResourceId
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = convertView
        if (v == null) {
            val vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = vi.inflate(viewResourceId, null)
        }
        val customer = items[position]
        val customerNameLabel = v as TextView?
        if (customerNameLabel != null) {
            customerNameLabel.text = customer
        }
        return v!!
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    private var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any?): String? {
            return resultValue as String?
        }

         override fun performFiltering(constraint: CharSequence?): FilterResults {
            return if (constraint != null) {
                val palabra = constraint.toString()
                if (palabra.indexOf("@") != -1) {
                    val palabra2 = palabra.substring(palabra.indexOf("@"))
                    val antesArroba: String = try {
                        palabra.substring(0, palabra.indexOf("@"))
                    } catch (ex: Exception) {
                        ""
                    }
                    suggestions.clear()
                    for (customer in itemsAll) {
                        if (customer.lowercase(Locale.getDefault()).startsWith(
                                palabra2.lowercase(
                                    Locale.getDefault()
                                )
                            )
                        ) {
                            suggestions.add(antesArroba + customer)
                        }
                    }
                    val filterResults = FilterResults()
                    filterResults.values = suggestions
                    filterResults.count = suggestions.size
                    filterResults
                } else {
                    FilterResults()
                }
            } else {
                FilterResults()
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values != null){
                val filteredList = results.values as ArrayList<String>
                if (results.count > 0) {
                    clear()
                    for (c in filteredList) {
                        add(c)
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }
}