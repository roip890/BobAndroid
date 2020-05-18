package com.aptenobytes.bob.feature.wish.presentation.wishdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.databinding.FragmentWishDetailBinding
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.SetWishStatusFragment
import com.aptenobytes.bob.feature.wish.presentation.utils.*
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.aptenobytes.bob.library.base.presentation.utils.startAlphaAnimation
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_wish_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance
import timber.log.Timber
import java.util.*
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

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val stateObserver = Observer<WishDetailViewState> { render(it) }

    private var isWishSmallHeaderVisible = false
    private var isWishBigHeaderVisible = true

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return this.view?.let {
            this.view
        } ?: run {
            binding = FragmentWishDetailBinding.inflate(inflater, null, false).also {
                Timber.v("onCreateView ${javaClass.simpleName}")
            }
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
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
        startAlphaAnimation(wishSmallHeader, 0, View.INVISIBLE)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupFloatingActionButton() {
        this.floatingActionButton.setOnClickListener {
            binding.viewModel?.wishLiveData?.value?.let { wish ->
                val setWishStatusFragment = SetWishStatusFragment.newInstance(
                    wishId = wish.id,
                    onChangeStatusListener = {wishAfterChange ->
                        binding.viewModel?.wishLiveData?.postValue(wishAfterChange)
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

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun render(viewState: WishDetailViewState) {

        viewState.wish?.let {
            bindWishViewModel(viewState.wish)
        }

        this.progressBar.visibility = if (viewState.isLoading) VISIBLE else GONE
        viewState.error?.let {
            messageLayout.visibility = VISIBLE
            messageImage.visibility = GONE
            messageAnimation.visibility = VISIBLE
        } ?: run {
            messageLayout.visibility = GONE
        }

    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bindWishViewModel(wish: WishDomainModel) {

        binding.viewModel?.wishLiveData?.postValue(wish)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        binding.viewModel?.wishLiveData?.observe(viewLifecycleOwner, Observer<WishDomainModel?> { wishLiveData ->
            updateWish(wish = wishLiveData)
        })
        updateWish(wish = wish)

    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateWish(wish: WishDomainModel?) {
        updateType(type = wish?.type)
        updateIcon(iconUrl = wish?.iconUrl)
        updateStatus(status = wish?.status)
        updateDepartments(departments = wish?.departments)
        updateTimeStamp(timeStamp = wish?.timeStamp)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateType(type: String?) {
        type?.let {
            binding.viewModel?.typeString?.postValue(type)
        }
    }

    private fun updateIcon(iconUrl: String?) {
        try {
            iconUrl?.let {
                binding.wishBigImage.load(iconUrl) {
                    crossfade(true)
                    placeholder(com.aptenobytes.bob.R.drawable.ic_round_room_service_avatar)
                    error(com.aptenobytes.bob.R.drawable.ic_round_room_service_avatar)
                    transformations(RoundedCornersTransformation(10F))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateStatus(status: WishStatusType?) {
        status?.let {
            binding.viewModel?.statusString?.postValue(
                wishStatusTypeToString(
                    status
                )
            )
            binding.viewModel?.statusColor?.postValue(
                wishStatusTypeToColor(
                    status,
                    requireContext()
                )
            )
            binding.viewModel?.statusIcon?.postValue(
                wishStatusTypeToIcon(
                    status,
                    requireContext()
                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateDepartments(departments: List<DepartmentDomainModel>?) {
        departments?.let {
            binding.viewModel?.departmentsString?.postValue(
                wishDepartmentsToString(
                    departments
                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateTimeStamp(timeStamp: Date?) {
        timeStamp?.let {
            binding.viewModel?.timeStampString?.postValue(
                wishTimeStampToString(
                    timeStamp
                )
            )
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val maxScroll: Int = appBarLayout.totalScrollRange
        val percentage = abs(offset).toFloat() / maxScroll.toFloat()
        handleAlphaOnMainTitle(percentage)
        handleAlphaOnSecondTitle(percentage)
    }

    private fun handleAlphaOnSecondTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE) {
            if (!isWishSmallHeaderVisible) {
                startAlphaAnimation(
                    wishSmallHeader,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    VISIBLE
                )
                isWishSmallHeaderVisible = true
            }
        } else {
            if (isWishSmallHeaderVisible) {
                startAlphaAnimation(
                    wishSmallHeader,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.INVISIBLE
                )
                isWishSmallHeaderVisible = false
            }
        }
    }

    private fun handleAlphaOnMainTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_SECOND_TITTLE) {
            if (isWishBigHeaderVisible) {
                startAlphaAnimation(
                    wishBigHeader,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.INVISIBLE
                )
                isWishBigHeaderVisible = false
            }
        } else {
            if (!isWishBigHeaderVisible) {
                startAlphaAnimation(
                    wishBigHeader,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    VISIBLE
                )
                isWishBigHeaderVisible = true
            }
        }
    }

}
