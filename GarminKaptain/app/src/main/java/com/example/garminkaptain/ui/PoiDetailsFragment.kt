package com.example.garminkaptain.ui

import android.os.Bundle
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
import com.example.garminkaptain.R
import com.example.garminkaptain.viewModel.PoiViewModel

class PoiDetailsFragment : Fragment() {

    private val args: PoiDetailsFragmentArgs by navArgs()

    private val viewModel: PoiViewModel by activityViewModels()

    private lateinit var progressBar: ProgressBar
    private lateinit var group: Group
    private lateinit var nameTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var numReviewsTextView: TextView
    private lateinit var reviewsButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.poi_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.apply {
            nameTextView = findViewById(R.id.poi_name_view)
            typeTextView = findViewById(R.id.poi_type_view)
            ratingBar = findViewById(R.id.poi_rating_view)
            numReviewsTextView = findViewById(R.id.poi_num_reviews_view)
            reviewsButton = findViewById(R.id.poi_view_reviews_button)
            progressBar = findViewById(R.id.poi_progress)
            group = findViewById(R.id.poi_details_group)
        }

        viewModel.getLoading().observe(
            viewLifecycleOwner,
            Observer {
                progressBar.visibility = if (it) VISIBLE else GONE
            })

        viewModel.getPoi(args.poiId).observe(viewLifecycleOwner, Observer { poi ->
            poi?.let {
                group.visibility = VISIBLE
                nameTextView.text = poi.name
                typeTextView.text = poi.poiType
                ratingBar.rating = poi.reviewSummary.averageRating.toFloat()
                numReviewsTextView.text = getString(R.string.label_num_reviews, poi.reviewSummary.numberOfReviews)

                reviewsButton.isEnabled = poi.reviewSummary.numberOfReviews > 0
                reviewsButton.setOnClickListener {
                    findNavController().navigate(
                        PoiDetailsFragmentDirections.actionPoiDetailsFragmentToReviewListFragment(
                            args.poiId
                        )
                    )
                }
            }
        })
    }
}