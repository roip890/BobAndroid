package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.feature.profile.domain.usecase.RemoveProfilePictureBySessionUseCase
import com.aptenobytes.bob.feature.profile.domain.usecase.UploadProfilePictureBySessionUseCase
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class ProfilePictureMenuViewModel(
    private val uploadProfilePictureBySessionUseCase: UploadProfilePictureBySessionUseCase,
    private val removeProfilePictureBySessionUseCase: RemoveProfilePictureBySessionUseCase
) : BaseViewModel<ProfilePictureMenuViewState, ProfilePictureMenuAction>(ProfilePictureMenuViewState.initial()) {

    private val intentChannel = BroadcastChannel<ProfilePictureMenuIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: ProfilePictureMenuIntent) {
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

    private fun <T: ProfilePictureMenuIntent> Flow<T>.toActionFlow(): Flow<ProfilePictureMenuAction> {
        return merge(
            filterIsInstance<ProfilePictureMenuIntent.UploadProfilePictureIntent>()
                .flatMapConcat { flow {
                    logAction(ProfilePictureMenuAction.UploadProfilePictureAction(imagePath = it.imagePath))
                    emit(ProfilePictureMenuAction.UploadProfilePictureAction(imagePath = it.imagePath))
                } },
            filterIsInstance<ProfilePictureMenuIntent.RemoveProfilePictureIntent>()
                .flatMapConcat { flow {
                    logAction(ProfilePictureMenuAction.RemoveProfilePictureAction)
                    emit(ProfilePictureMenuAction.RemoveProfilePictureAction)
                } }
        )
    }

    private fun <T: ProfilePictureMenuAction> Flow<T>.processResult(): Flow<ProfilePictureMenuResult> {
        return merge(
            filterIsInstance<ProfilePictureMenuAction.UploadProfilePictureAction>()
                .flatMapConcat {
                    processUploadProfilePicture(imagePath = it.imagePath)
                },
            filterIsInstance<ProfilePictureMenuAction.RemoveProfilePictureAction>()
                .flatMapConcat {
                    processRemoveProfilePicture()
                }
        )
    }

    private fun processUploadProfilePicture(imagePath: String): Flow<ProfilePictureMenuResult.UploadProfilePictureResult> {
        return flow {
            emit(uploadProfilePictureBySessionUseCase.execute(imagePath = imagePath))
        }
            .map {
                ProfilePictureMenuResult.UploadProfilePictureResult.Success(imageUrl = it) as ProfilePictureMenuResult.UploadProfilePictureResult
            }
            .onStart {
                emit(ProfilePictureMenuResult.UploadProfilePictureResult.Loading)
            }
            .catch {
                emit(ProfilePictureMenuResult.UploadProfilePictureResult.Failure(it))
            }
    }

    private fun processRemoveProfilePicture(): Flow<ProfilePictureMenuResult.RemoveProfilePictureResult> {
        return flow {
            emit(removeProfilePictureBySessionUseCase.execute())
        }
            .map {
                ProfilePictureMenuResult.RemoveProfilePictureResult.Success(imageUrl = it) as ProfilePictureMenuResult.RemoveProfilePictureResult
            }
            .onStart {
                emit(ProfilePictureMenuResult.RemoveProfilePictureResult.Loading)
            }
            .catch {
                emit(ProfilePictureMenuResult.RemoveProfilePictureResult.Failure(it))
            }
    }

    private fun <T: ProfilePictureMenuResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                // upload
                is ProfilePictureMenuResult.UploadProfilePictureResult.Loading -> state.copy(
                    isProfilePictureChanged = false,
                    imageUrl = null,
                    isLoading = true,
                    error = null
                )
                is ProfilePictureMenuResult.UploadProfilePictureResult.Success -> state.copy(
                    isProfilePictureChanged = true,
                    imageUrl = it.imageUrl,
                    isLoading = false,
                    error = null
                )
                is ProfilePictureMenuResult.UploadProfilePictureResult.Failure -> state.copy(
                    isProfilePictureChanged = false,
                    imageUrl = null,
                    isLoading = false,
                    error = it.error
                )
                // remove
                is ProfilePictureMenuResult.RemoveProfilePictureResult.Loading -> state.copy(
                    isProfilePictureChanged = false,
                    imageUrl = null,
                    isLoading = true,
                    error = null
                )
                is ProfilePictureMenuResult.RemoveProfilePictureResult.Success -> state.copy(
                    isProfilePictureChanged = true,
                    imageUrl = it.imageUrl,
                    isLoading = false,
                    error = null
                )
                is ProfilePictureMenuResult.RemoveProfilePictureResult.Failure -> state.copy(
                    isProfilePictureChanged = false,
                    imageUrl = null,
                    isLoading = false,
                    error = it.error
                )
                else -> state
            }
        }
    }


}


