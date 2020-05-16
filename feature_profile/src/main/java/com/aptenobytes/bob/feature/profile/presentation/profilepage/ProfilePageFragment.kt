package com.aptenobytes.bob.feature.profile.presentation.profilepage

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
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.profile.R
import com.aptenobytes.bob.feature.profile.databinding.FragmentProfilePageBinding
import com.aptenobytes.bob.feature.profile.presentation.utils.userStatusTypeToColor
import com.aptenobytes.bob.feature.profile.presentation.utils.userStatusTypeToIcon
import com.aptenobytes.bob.feature.profile.presentation.utils.userStatusTypeToString
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.aptenobytes.bob.library.base.presentation.utils.startAlphaAnimation
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_profile_page.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance
import timber.log.Timber
import kotlin.math.abs


class ProfilePageFragment : BaseContainerFragment(), ProfilePageView, AppBarLayout.OnOffsetChangedListener {

    companion object {
        const val PERCENTAGE_TO_SHOW_TITLE = 0.8f
        const val PERCENTAGE_TO_HIDE_SECOND_TITTLE = 0.3f
        const val ALPHA_ANIMATIONS_DURATION = 200
    }


    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: ProfilePageViewModel by instance<ProfilePageViewModel>()

    override val layoutResourceId = R.layout.fragment_profile_page

    lateinit var binding: FragmentProfilePageBinding

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val stateObserver = Observer<ProfilePageViewState> { render(it) }

    private var isUserSmallHeaderVisible = false
    private var isUserBigHeaderVisible = true

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return this.view?.let {
            this.view
        } ?: run {
            binding = FragmentProfilePageBinding.inflate(inflater, null, false).also {
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
        startAlphaAnimation(userSmallHeader, 0, View.INVISIBLE)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupFloatingActionButton() {
        this.floatingActionButton.setOnClickListener {

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
        flowOf(ProfilePageIntent.GetUserBySessionIntent)
    )

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun render(viewState: ProfilePageViewState) {

        viewState.user?.let {
            bindWishViewModel(viewState.user)
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
    private fun bindWishViewModel(user: UserDomainModel) {

        binding.viewModel?.userLiveData?.postValue(user)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        binding.viewModel?.userLiveData?.observe(viewLifecycleOwner, Observer<UserDomainModel?> { userLiveData ->
            updateWish(user = userLiveData)
        })
        updateWish(user = user)

    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateWish(user: UserDomainModel?) {
        updateEmail(email = user?.email)
        updateName(firstName = user?.firstName, lastName = user?.lastName)
        updateFirstName(firstName = user?.firstName)
        updateLastName(lastName = user?.lastName)
        updateImage(imageUrl = user?.imageUrl)
        updateStatus(status = user?.status)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateEmail(email: String?) {
        email?.let {
            binding.viewModel?.emailString?.postValue(email)
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateName(firstName: String?, lastName: String?) {
        firstName?.let {
            lastName?.let {
                binding.viewModel?.nameString?.postValue("$firstName $lastName")
            } ?: run {
                binding.viewModel?.nameString?.postValue(firstName)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateFirstName(firstName: String?) {
        firstName?.let {
            binding.viewModel?.firstNameString?.postValue(firstName)
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateLastName(lastName: String?) {
        lastName?.let {
            binding.viewModel?.lastNameString?.postValue(lastName)
        }
    }

    private fun updateImage(imageUrl: String?) {
        try {
            imageUrl?.let {
                binding.userBigImage.load(imageUrl) {
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

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun updateStatus(status: UserStatusType?) {
        status?.let {
            binding.viewModel?.statusString?.postValue(
                userStatusTypeToString(
                    status
                )
            )
            binding.viewModel?.statusIcon?.postValue(
                userStatusTypeToIcon(
                    status,
                    requireContext()
                )
            )
            binding.viewModel?.statusColor?.postValue(
                userStatusTypeToColor(
                    status,
                    requireContext()
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
            if (!isUserSmallHeaderVisible) {
                startAlphaAnimation(
                    userSmallHeader,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    VISIBLE
                )
                isUserSmallHeaderVisible = true
            }
        } else {
            if (isUserSmallHeaderVisible) {
                startAlphaAnimation(
                    userSmallHeader,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.INVISIBLE
                )
                isUserSmallHeaderVisible = false
            }
        }
    }

    private fun handleAlphaOnMainTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_SECOND_TITTLE) {
            if (isUserBigHeaderVisible) {
                startAlphaAnimation(
                    userBigHeader,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    View.INVISIBLE
                )
                isUserBigHeaderVisible = false
            }
        } else {
            if (!isUserBigHeaderVisible) {
                startAlphaAnimation(
                    userBigHeader,
                    ALPHA_ANIMATIONS_DURATION.toLong(),
                    VISIBLE
                )
                isUserBigHeaderVisible = true
            }
        }
    }

}
