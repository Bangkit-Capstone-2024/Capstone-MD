package com.cpstn.momee.utils

import android.view.View
import androidx.core.view.isVisible


fun View.visible(isVisible: Boolean) {
    this.isVisible = isVisible
}