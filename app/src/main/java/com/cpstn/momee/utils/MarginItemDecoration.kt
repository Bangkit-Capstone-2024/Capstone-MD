package com.cpstn.momee.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            left = when {
                parent.getChildAdapterPosition(view) == 0 -> spaceSize
                else -> spaceSize / 2
            }
            right = spaceSize / 2
        }
    }
}