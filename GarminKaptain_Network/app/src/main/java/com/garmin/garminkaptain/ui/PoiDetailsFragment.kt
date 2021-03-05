package com.garmin.garminkaptain.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.garmin.garminkaptain.R
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.viewModel.PoiViewModel

class PoiDetailsFragment : Fragment(R.layout.poi_details_fragment2) {

    private val args: PoiDetailsFragmentArgs by navArgs()

    private lateinit var progressBar: ProgressBar
    private lateinit var nameTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var numReviewsTextView: TextView
    private lateinit var reviewsButton: Button
    private lateinit var detailsGroup: Group
    private lateinit var ratingBar: RatingBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach() called with: context = $context")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(
            TAG,
            "onCreateView() called with: inflater = $inflater, container = $container, savedInstanceState = $savedInstanceState"
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState"
        )

        progressBar = view.findViewById(R.id.poi_progress_view)
        nameTextView = view.findViewById(R.id.poi_name_view)
        typeTextView = view.findViewById(R.id.poi_type_view)
        numReviewsTextView = view.findViewById(R.id.poi_num_reviews_view)
        reviewsButton = view.findViewById(R.id.poi_view_reviews_button)
        detailsGroup = view.findViewById(R.id.poi_details_group)
        ratingBar = view.findViewById(R.id.poi_rating_view)

        val model: PoiViewModel by activityViewModels()
        model.getPoi(args.poiId).observe(viewLifecycleOwner, Observer { onPoiReceived(it) })

        reviewsButton.setOnClickListener {
            findNavController().navigate(
                PoiDetailsFragmentDirections.actionPoiDetailsFragmentToPoiReviewFragment(
                    args.poiId
                )
            )
        }
    }

    private fun onPoiReceived(poi: PointOfInterest?) = poi?.let {
        detailsGroup.visibility = VISIBLE
        nameTextView.text = poi.name
        typeTextView.text = poi.poiType
        ratingBar.rating = poi.reviewSummary?.averageRating?.toFloat() ?: 0.0F

        val numberOfReviews = poi.reviewSummary?.numberOfReviews
        numReviewsTextView.text = getString(R.string.label_num_reviews, numberOfReviews?:0)
        reviewsButton.isEnabled = numberOfReviews != null && numberOfReviews > 0
    }

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach() called")
    }
}