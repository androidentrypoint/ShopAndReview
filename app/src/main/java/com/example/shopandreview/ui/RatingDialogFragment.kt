package com.example.shopandreview.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shopandreview.R
import com.example.shopandreview.databinding.DialogRatingBinding
import com.example.shopandreview.model.Review
import com.example.shopandreview.util.viewbinding.dialogViewBinding
import java.util.*

class RatingDialogFragment : DialogFragment(R.layout.dialog_rating) {

    private val binding by dialogViewBinding(DialogRatingBinding::bind)

    private val args by navArgs<RatingDialogFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addReview.setOnClickListener {
            with(args.product) {
                setFragmentResult(
                    RATING_KEY,
                    bundleOf(
                        RATING_KEY to Review(
                            id,
                            Locale.getDefault().toString(),
                            binding.rating.rating.toInt(),
                            binding.comment.text.toString()
                        )
                    )
                )
            }
            findNavController().popBackStack()
        }
    }

    companion object {
        const val RATING_KEY = "REVIEW_RESULT"
    }
}