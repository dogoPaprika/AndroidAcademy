package com.example.garminkaptain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
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
//        val poiId = args.poiId
//        val poi = poiList.find { it.id == poiId }

        view.apply {
            nameTextView = findViewById(R.id.poi_name_view)
            typeTextView = findViewById(R.id.poi_type_view)
            ratingBar = findViewById(R.id.poi_rating_view)
            numReviewsTextView = findViewById(R.id.poi_num_reviews_view)
            reviewsButton = findViewById(R.id.poi_view_reviews_button)
        }

        viewModel.getPoi(args.poiId).observe(viewLifecycleOwner, Observer { poi ->
            poi?.let {
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

//        poi?.let {
//            view.apply {
//                findViewById<TextView>(R.id.poi_name_view).text = poi.name
//                findViewById<TextView>(R.id.poi_type_view).text = poi.poiType
//                findViewById<RatingBar>(R.id.poi_rating_view).rating =
//                    poi.reviewSummary.averageRating.toFloat()
//                findViewById<TextView>(R.id.poi_num_reviews_view).text =
//                    getString(R.string.label_num_reviews, poi.reviewSummary.numberOfReviews)
//
//                val button = findViewById<Button>(R.id.poi_view_reviews_button)
//                button.isEnabled = poi.reviewSummary.numberOfReviews > 0
//                button.setOnClickListener {
//                    findNavController().navigate(
//                        PoiDetailsFragmentDirections.actionPoiDetailsFragmentToReviewListFragment(
//                            poiId
//                        )
//                    )
//                }
//            }
//        }
    }
}