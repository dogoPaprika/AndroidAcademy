package com.garmin.garminkaptain.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import com.garmin.garminkaptain.R

class PoiSortDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val preference = it.getPreferences(Context.MODE_PRIVATE)

            val builder = AlertDialog.Builder(it)
                .setTitle(R.string.label_poi_sort)
                .setSingleChoiceItems(
                    R.array.poi_sort_array,
                    preference.getInt(getString(R.string.key_poi_sort), 0)
                )
                { _, checkedItem ->
                    preference.edit {
                        putInt(getString(R.string.key_poi_sort), checkedItem)
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity should not be null!!")
    }
}