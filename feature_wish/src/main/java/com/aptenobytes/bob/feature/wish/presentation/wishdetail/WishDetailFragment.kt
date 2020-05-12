package com.aptenobytes.bob.feature.wish.presentation.wishdetail

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.databinding.FragmentWishDetailBinding
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.presentation.model.WishViewModel
import com.aptenobytes.bob.feature.wish.presentation.model.toDomainModel
import com.aptenobytes.bob.feature.wish.presentation.model.toViewModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.SetWishStatusFragment
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.google.android.material.appbar.AppBarLayout
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.iconics.utils.sizeDp
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_wish_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance
import timber.log.Timber
import kotlin.math.abs


class WishDetailFragment : BaseContainerFragment(), WishDetailView, AppBarLayout.OnOffsetChangedListener {

    companion object {
        const val PERCENTAGE_TO_SHOW_TITLE = 0.8f
        const val PERCENTAGE_TO_HIDE_SECOND_TITTLE = 0.3f
        const val ALPHA_ANIMATIONS_DURATION = 200
    }


    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: WishDetailViewModel by instance<WishDetailViewModel>()

    override val layoutResourceId = R.layout.fragment_wish_detail

    lateinit var binding: FragmentWishDetailBinding

    private val stateObserver = Observer<WishDetailViewState> { render(it) }

    private var isSmallWishInfoVisible = false
    private var isBigWishInfoVisible = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return this.view?.let {
            this.view
        } ?: run {
            binding = FragmentWishDetailBinding.inflate(inflater, null, false).also {
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
        setupAppBarCollapsingImage()
        setupFloatingActionButton()
        bind()
    }

    private fun setupAppBarCollapsingImage() {
        appBarLayout.addOnOffsetChangedListener(this)
        startAlphaAnimation(wishSmallInfoLayout, 0, View.INVISIBLE)
    }

    private fun setupFloatingActionButton() {
        this.floatingActionButton.setImageDrawable(
            IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_filter).apply {
                sizeDp = 28
                paddingDp = 4
            }
        )
        this.floatingActionButton.setOnClickListener {
            binding.viewModel?.let { wishViewModel ->
                val setWishStatusFragment = SetWishStatusFragment.newInstance(
                    wish = wishViewModel.toDomainModel(),
                    onChangeStatusListener = {wishAfterChange ->
                        wishViewModel.status.postValue(wishAfterChange?.status)
                    }
                )
                setWishStatusFragment.show(parentFragmentManager, SetWishStatusFragment.TAG)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bind() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        intents()
            .onEach { viewModel.processIntent(it) }
            .launchIn(lifecycleScope)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf(WishDetailIntent.GetWishDetailIntent(wishId = viewModel.getWishId()))
    )

    private fun render(viewState: WishDetailViewState) {

        viewState.wish?.let {
            bindWishViewModel(viewState.wish.toViewModel())
        }

        this.progressBar.visible = viewState.isLoading
        viewState.error?.let {
            messageLayout.visible = true
            messageImage.visible = false
            messageAnimation.visible = true
        } ?: run {
            messageLayout.visible = false
        }

    }

    private fun bindWishViewModel(wish: WishViewModel) {
        binding.viewModel = wish
        binding.viewModel?.iconUrl?.observe(viewLifecycleOwner, Observer<String?> { iconUrl ->
            updateIcon(iconUrl = iconUrl)
        })
        binding.viewModel?.status?.observe(viewLifecycleOwner, Observer {status ->
            updateStatus(status = status)
        })
        binding.viewModel?.departments?.observe(viewLifecycleOwner, Observer {departments ->
            updateDepartments(departments = departments)
        })
        updateStatus(status = binding.viewModel?.status?.value)
        updateDepartments(departments = binding.viewModel?.departments?.value)
    }

    private fun updateIcon(iconUrl: String?) {
        try {
            iconUrl?.let {
                binding.wishBigImage.load(iconUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_image)
                    error(R.drawable.ic_image)
                    transformations(RoundedCornersTransformation(10F))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateStatus(status: WishStatusType?) {
        binding.viewModel?.let { wishViewModel ->
            status?.let {
                wishViewModel.statusString.postValue(
                    wishStatusTypeToString(
                        status
                    )
                )
                wishViewModel.statusIcon.postValue(
                    wishStatusTypeToIcon(
                        status
                    )
                )
                wishViewModel.statusColor.postValue(
                    wishStatusTypeToColor(
                        status
                    )
                )
            }
        }
    }

    private fun updateDepartments(departments: List<DepartmentDomainModel>?) {
        binding.viewModel?.let { wishViewModel ->
            departments?.let {
                wishViewModel.departmentsString.postValue(
                    wishDepartmentsToString(
                        departments
                    )
                )
            }
        }
    }

    private fun wishStatusTypeToString(status: WishStatusType): String {
        return when(status) {
            WishStatusType.WAITING -> "Wating"
            WishStatusType.PENDING -> "Pending"
            WishStatusType.IN_PROGRESS -> "In Progress"
            WishStatusType.DONE -> "Done"
            else -> ""
        }
    }

    private fun wishStatusTypeToIcon(status: WishStatusType): Drawable {
        return GradientDrawable().apply {
            this.shape = GradientDrawable.OVAL
            this.color = ColorStateList.valueOf(wishStatusTypeToColor(status))
        }
    }

    private fun wishStatusTypeToColor(status: WishStatusType): Int {
        return when(status) {
            WishStatusType.WAITING -> ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.faded_red)
            WishStatusType.PENDING -> ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.faded_orange)
            WishStatusType.IN_PROGRESS -> ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.faded_yellow)
            WishStatusType.DONE -> ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.faded_green)
            else -> ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.faded_blue)
        }
    }

    private fun wishDepartmentsToString(departments: List<DepartmentDomainModel>): String {
        return departments.joinToString(separator = ", ") { it.name }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val maxScroll: Int = appBarLayout.totalScrollRange
        val percentage = abs(offset).toFloat() / maxScroll.toFloat()
        handleAlphaOnMainTitle(percentage)
        handleAlphaOnSecondTitle(percentage)
    }

    private fun handleAlphaOnSecondTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE) {
            if (!isSmallWishInfoVisible) {
                startAlphaAnimation(
                    wishSmallInfoLayout,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.VISIBLE
                )
                isSmallWishInfoVisible = true
            }
        } else {
            if (isSmallWishInfoVisible) {
                startAlphaAnimation(
                    wishSmallInfoLayout,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.INVISIBLE
                )
                isSmallWishInfoVisible = false
            }
        }
    }

    private fun handleAlphaOnMainTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_SECOND_TITTLE) {
            if (isBigWishInfoVisible) {
                startAlphaAnimation(
                    wishBigInfoLayout,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.INVISIBLE
                )
                isBigWishInfoVisible = false
            }
        } else {
            if (!isBigWishInfoVisible) {
                startAlphaAnimation(
                    wishBigInfoLayout,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.VISIBLE
                )
                isBigWishInfoVisible = true
            }
        }
    }

    private fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
        val alphaAnimation =
            if (visibility == View.VISIBLE) AlphaAnimation(0f, 1f) else AlphaAnimation(1f, 0f)
        alphaAnimation.duration = duration
        alphaAnimation.fillAfter = true
        v.startAnimation(alphaAnimation)
    }


}
