package com.aptenobytes.bob.feature.profile.presentation.profilepage.profilepicturepreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.aptenobytes.bob.feature.profile.R
import com.aptenobytes.bob.feature.profile.databinding.FragmentProfilePicturePreviewBinding
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.kodein.di.generic.instance
import timber.log.Timber

class ProfilePicturePreviewFragment: BaseContainerFragment() {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: ProfilePicturePreviewViewModel by instance<ProfilePicturePreviewViewModel>()

    override val layoutResourceId = R.layout.fragment_profile_picture_preview

    lateinit var binding: FragmentProfilePicturePreviewBinding

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return this.view?.let {
            this.view
        } ?: run {
            binding = FragmentProfilePicturePreviewBinding.inflate(inflater, null, false).also {
                Timber.v("onCreateView ${javaClass.simpleName}")
            }
            binding.root
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext()
        setupPhotoView()
        bind()
    }

    private fun setupPhotoView() {
        binding.photoView.minimumScale = 0.5F
        binding.photoView.maximumScale = 2.0F
        binding.photoView.setZoomable(true)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bind() {
        viewModel.imageUrlLiveData.observe(viewLifecycleOwner, Observer {
            binding.photoView.load(it) {
                crossfade(true)
                placeholder(com.aptenobytes.bob.R.drawable.ic_round_profile_avatar)
                error(com.aptenobytes.bob.R.drawable.ic_round_profile_avatar)
            }
        })
    }

}
