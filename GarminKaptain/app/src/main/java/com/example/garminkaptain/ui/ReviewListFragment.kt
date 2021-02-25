package com.example.garminkaptain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.garminkaptain.R
import com.example.garminkaptain.data.Review
import com.example.garminkaptain.data.poiList
import com.example.garminkaptain.data.reviewList
import com.example.garminkaptain.viewModel.PoiViewModel
import java.util.ArrayList

class ReviewListFragment : Fragment() {
    private val args: ReviewListFragmentArgs by navArgs()

    inner class ReviewListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView = itemView.findViewById<TextView>(R.id.title_review_view)
        private val textView = itemView.findViewById<TextView>(R.id.text_review_view)
        private val ratingView = itemView.findViewById<RatingBar>(R.id.rating_review_view)
        private val dateView = itemView.findViewById<TextView>(R.id.date_review_view)

        fun bind(review: Review) {
            titleView.text = review.owner
            textView.text = review.text
            ratingView.rating = review.rating
            dateView.text = getString(R.string.label_date_review, review.date.year, review.date.month, review.date.day)
        }
    }

    inner class ReviewListAdapter : RecyclerView.Adapter<ReviewListItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListItemViewHolder {
            return ReviewListItemViewHolder(
                layoutInflater.inflate(R.layout.review_list_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ReviewListItemViewHolder, position: Int) {
            reviews.getOrNull(position)?.let {
                holder.bind(it)
            }
        }

        override fun getItemCount(): Int = reviews.size
    }

    private var reviews = reviewList
    private var adapter = ReviewListAdapter()
    private val viewModel: PoiViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.review_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@ReviewListFragment.adapter
        }

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeToRefreshReviews)
        swipeRefreshLayout.setOnRefreshListener { viewModel.loadReviewList(args.poiId) }

        viewModel.getLoading()
            .observe(viewLifecycleOwner, Observer { swipeRefreshLayout.isRefreshing = it })

        viewModel.getReviewList(args.poiId).observe(viewLifecycleOwner, Observer {
            it?.let {
                reviews = it as ArrayList<Review>
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.review_list_fragment, container, false)
    }
}