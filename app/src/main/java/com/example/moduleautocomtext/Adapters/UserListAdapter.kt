package com.example.moduleautocomtext.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.example.moduleautocomtext.Model.UserModel

import java.util.*

class UserListAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val allEmps: List<UserModel>):
    ArrayAdapter<UserModel>(context, layoutResource, allEmps),
    Filterable {
    private var mEmps: List<UserModel> = allEmps

    override fun getCount(): Int {
        return mEmps.size
    }

    override fun getItem(p0: Int):UserModel? {
        return mEmps.get(p0)

    }

    /* override fun getItemId(p0: Int): Long {

         return mEmps.get(p0).id.toLong()
     }*/

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context)
            .inflate(layoutResource, parent, false) as TextView
        view.text = "${mEmps[position].login}  (${mEmps[position].id})"
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: Filter.FilterResults
            ) {
                mEmps = filterResults.values as List<UserModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    allEmps
                else
                    allEmps.filter {
                        it.login?.contains(queryString) == true ||
                                it.type?.contains(queryString) == true ||
                                it.id?.contains(queryString) == true

                    }

                return filterResults
            }

        }
    }

}