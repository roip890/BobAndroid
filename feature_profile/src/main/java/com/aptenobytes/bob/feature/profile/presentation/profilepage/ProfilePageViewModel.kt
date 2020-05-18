package com.aptenobytes.bob.feature.profile.presentation.profilepage

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.profile.domain.usecase.GetUserBySessionUseCase
import com.aptenobytes.bob.library.base.presentation.navigation.NavManager
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class ProfilePageViewModel(
    private val navManager: NavManager,
    private val getUserBySessionUseCase: GetUserBySessionUseCase
) : BaseViewModel<ProfilePageViewState, ProfilePageAction>(ProfilePageViewState.initial()) {

    val userLiveData: MutableLiveData<UserDomainModel> = MutableLiveData<UserDomainModel>()

    val emailString = MutableLiveData<String>()
    val nameString = MutableLiveData<String>()
    val firstNameString = MutableLiveData<String>()
    val lastNameString = MutableLiveData<String>()
    val statusString = MutableLiveData<String>()
    val statusIcon = MutableLiveData<Drawable>()
    val statusColor = MutableLiveData<Int>()

    private val intentChannel = BroadcastChannel<ProfilePageIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: ProfilePageIntent) {
        intentChannel.send(intent)
    }

    init {
        val intentFlow = intentChannel.asFlow()
        merge(
            intentFlow
        )
            .toActionFlow()
            .processResult()
            .reduceToStateFlow()
            .launchIn(viewModelScope)
    }

    private fun <T: ProfilePageIntent> Flow<T>.toActionFlow(): Flow<ProfilePageAction> {
        return merge(
            filterIsInstance<ProfilePageIntent.GetUserBySessionIntent>()
                .flatMapConcat { flow {
                    logAction(ProfilePageAction.GetUserBySessionAction)
                    emit(ProfilePageAction.GetUserBySessionAction)
                } }
        )
    }

    private fun <T: ProfilePageAction> Flow<T>.processResult(): Flow<ProfilePageResult> {
        return merge(
            filterIsInstance<ProfilePageAction.GetUserBySessionAction>()
                .flatMapConcat {
                    processGetUserBySession()
                }
        )
    }

    private fun processGetUserBySession(): Flow<ProfilePageResult.GetUserBySessionResult> {
        return flow {
            emit(getUserBySessionUseCase.execute())
        }
            .map {
                ProfilePageResult.GetUserBySessionResult.Success(it) as ProfilePageResult.GetUserBySessionResult
            }
            .onStart {
                emit(ProfilePageResult.GetUserBySessionResult.Loading)
            }
            .catch {
                emit(ProfilePageResult.GetUserBySessionResult.Failure(it))
            }
    }

    private fun <T: ProfilePageResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                is ProfilePageResult.GetUserBySessionResult.Loading -> state.copy(
                    isLoading = true,
                    user = null,
                    error = null
                )
                is ProfilePageResult.GetUserBySessionResult.Success -> state.copy(
                    isLoading = false,
                    user = it.user,
                    error = null
                )
                is ProfilePageResult.GetUserBySessionResult.Failure -> state.copy(
                    isLoading = false,
                    user = null,
                    error = it.error
                )
                else -> state
            }
        }
    }

    // navigation
    fun navigateToProfilePicturePreview(imageUrl: String) {
        val navDirections = ProfilePageFragmentDirections.actionProfilePageToProfilePicturePreview(imageUrl)
        navManager.navigateDirection(navDirections)
    }

}
