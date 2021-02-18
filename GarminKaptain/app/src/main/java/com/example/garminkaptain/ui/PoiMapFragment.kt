package com.example.garminkaptain.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.garminkaptain.R
import com.example.garminkaptain.data.poiList
import com.example.garminkaptain.viewModel.PoiViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class PoiMapFragment : Fragment(R.layout.poi_map_fragment), GoogleMap.OnInfoWindowClickListener {
    private var pointsOfInterest = poiList
    private lateinit var mapFragment: SupportMapFragment

    private val viewModel: PoiViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        view.doOnLayout {
            refreshMap()
        }

        viewModel.getPoiList().observe(viewLifecycleOwner, Observer {
            it?.let {
                pointsOfInterest = it
            }
        })
    }

    override fun onInfoWindowClick(selectedMarker: Marker?) {
        selectedMarker?.let { marker ->
            viewModel.findPoiOnLatitudeLongitude(marker.position.latitude, marker.position.longitude)
                .observe(viewLifecycleOwner, Observer { poi ->
                poi.let {
                    findNavController().navigate(
                        PoiMapFragmentDirections.actionPoiMapFragmentToPoiDetailsFragment(poi.id)
                    )
                }
            })
        }
    }

    private fun refreshMap() {
        mapFragment.getMapAsync { map ->
            map.setOnInfoWindowClickListener(this)
            val latLngBoundsBuilder = LatLngBounds.builder()
            pointsOfInterest.forEach { poi ->
                LatLng(poi.mapLocation.latitude, poi.mapLocation.longitude).also {
                    latLngBoundsBuilder.include(it)
                    map.addMarker(MarkerOptions().position(it).title(poi.name))
                }
            }
            map.animateCamera(
                CameraUpdateFactory.newLatLngBounds(latLngBoundsBuilder.build(), PADDING)
            )
        }
    }

    companion object {
        private const val PADDING = 100
    }
}