package com.cpstn.momee.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cpstn.momee.R
import com.cpstn.momee.databinding.BottomSheetUploadBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UploadBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetUploadBinding? = null
    private val binding get() = _binding!!

    private var listener: Listener? = null

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            containerGallery.setOnClickListener {
                listener?.onGalleryClick()
            }
            containerCamera.setOnClickListener {
                listener?.onCameraClick()
            }
        }
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface Listener {
        fun onGalleryClick()
        fun onCameraClick()
    }
}