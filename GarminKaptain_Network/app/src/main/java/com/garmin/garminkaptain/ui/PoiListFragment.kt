package com.garmin.garminkaptain.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.data.Resource
import com.garmin.garminkaptain.data.mockBoundingBox
import com.garmin.garminkaptain.viewModel.PoiViewModel

class PoiListFragment : Fragment(R.layout.poi_list_fragment), OnSharedPreferenceChangeListener {

    inner class PoiListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameView = itemView.findViewById<TextView>(R.id.poi_item_name_view)
        private val typeView = itemView.findViewById<TextView>(R.id.poi_item_type_view)

        fun bind(poi: PointOfInterest) {
            nameView.text = poi.name
            typeView.text = poi.poiType
            itemView.setOnClickListener {
                findNavController().navigate(
                    PoiListFragmentDirections.actionPoiListFragmentToPoiDetailsFragment(poi.id)
                )
            }
        }
    }

    inner class PoiListAdapter : RecyclerView.Adapter<PoiListItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiListItemViewHolder {
            return PoiListItemViewHolder(
                layoutInflater.inflate(R.layout.poi_list_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: PoiListItemViewHolder, position: Int) {
            pointsOfInterest.getOrNull(position)?.let {
                holder.bind(it)
            }
        }

        override fun getItemCount(): Int = pointsOfInterest.size
    }

    private var pointsOfInterest = listOf<PointOfInterest>()
    private var adapter = PoiListAdapter()
    private val viewModel: PoiViewModel by activityViewModels()
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.poi_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@PoiListFragment.adapter
        }

        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeRefreshLayout?.setOnRefreshListener { viewModel.refreshPoiList() }

        viewModel.getPoiListData().observe(viewLifecycleOwner, Observer { resouce ->
            if (resouce != null) {
                when (resouce) {
                    is Resource.Error -> {
                        hideProgressIndicator()
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> showProgressIndicator()
                    is Resource.Success -> {
                        hideProgressIndicator()
                        if (resouce.data != null) {
                            pointsOfInterest = resouce.data
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

        activity?.let {
            it.getPreferences(Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this)
        }
    }

    private fun showProgressIndicator() {
        swipeRefreshLayout?.isRefreshing = true
    }

    private fun hideProgressIndicator() {
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        Log.d(
            TAG, "onSharedPreferenceChanged " +
                    "key: $key value: ${sharedPreferences.getInt(key, 0)}"
        )
    }
}