package com.aptenobytes.bob.feature.profile.presentation.profileedit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.profile.domain.usecase.GetUserBySessionUseCase
import com.aptenobytes.bob.feature.profile.domain.usecase.UpdateUserBySessionUseCase
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class ProfileEditViewModel(
    private val getUserBySessionUseCase: GetUserBySessionUseCase,
    private val updateUserBySessionUseCase: UpdateUserBySessionUseCase
) : BaseViewModel<ProfileEditViewState, ProfileEditAction>(ProfileEditViewState.initial()) {

    var user: MutableLiveData<UserDomainModel> = MutableLiveData<UserDomainModel>()

    private val intentChannel = BroadcastChannel<ProfileEditIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: ProfileEditIntent) {
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

    private fun <T: ProfileEditIntent> Flow<T>.toActionFlow(): Flow<ProfileEditAction> {
        return merge(
            filterIsInstance<ProfileEditIntent.GetUserBySessionIntent>()
                .flatMapConcat { flow {
                    logAction(ProfileEditAction.GetUserBySessionAction)
                    emit(ProfileEditAction.GetUserBySessionAction)
                } },
            filterIsInstance<ProfileEditIntent.UpdateUserBySessionIntent>()
                .flatMapConcat { flow {
                    logAction(ProfileEditAction.UpdateUserBySessionAction(user = it.user))
                    emit(ProfileEditAction.UpdateUserBySessionAction(user = it.user))
                } },
            filterIsInstance<ProfileEditIntent.SubmitUserBySessionIntent>()
                .flatMapConcat { flow {
                    logAction(ProfileEditAction.SubmitUserBySessionAction(user = it.user))
                    emit(ProfileEditAction.SubmitUserBySessionAction(user = it.user))
                } }
        )
    }

    private fun <T: ProfileEditAction> Flow<T>.processResult(): Flow<ProfileEditResult> {
        return merge(
            filterIsInstance<ProfileEditAction.GetUserBySessionAction>()
                .flatMapConcat {
                    processGetUserBySession()
                },
            filterIsInstance<ProfileEditAction.UpdateUserBySessionAction>()
                .flatMapConcat {
                    processUpdateUserBySession(it.user)
                },
            filterIsInstance<ProfileEditAction.SubmitUserBySessionAction>()
                .flatMapConcat {
                    processSubmitUserBySession(it.user)
                }
        )
    }

    private fun processGetUserBySession(): Flow<ProfileEditResult.GetUserBySessionResult> {
        return flow {
            emit(getUserBySessionUseCase.execute())
        }
            .map {
                ProfileEditResult.GetUserBySessionResult.Success(it) as ProfileEditResult.GetUserBySessionResult
            }
            .onStart {
                emit(ProfileEditResult.GetUserBySessionResult.Loading)
            }
            .catch {
                emit(ProfileEditResult.GetUserBySessionResult.Failure(it))
            }
    }

    private fun processUpdateUserBySession(user: UserDomainModel): Flow<ProfileEditResult.UpdateUserBySessionResult> {
        return flow {
            emit(updateUserBySessionUseCase.execute(user = user))
        }
            .map {
                ProfileEditResult.UpdateUserBySessionResult.Success(it) as ProfileEditResult.UpdateUserBySessionResult
            }
            .onStart {
                emit(ProfileEditResult.UpdateUserBySessionResult.Loading)
            }
            .catch {
                emit(ProfileEditResult.UpdateUserBySessionResult.Failure(it))
            }
    }

    private fun processSubmitUserBySession(user: UserDomainModel): Flow<ProfileEditResult.SubmitUserBySessionResult> {
        return flow {
            emit(updateUserBySessionUseCase.execute(user = user))
        }
            .map {
                ProfileEditResult.SubmitUserBySessionResult.Success(it) as ProfileEditResult.SubmitUserBySessionResult
            }
            .onStart {
                emit(ProfileEditResult.SubmitUserBySessionResult.Loading)
            }
            .catch {
                emit(ProfileEditResult.SubmitUserBySessionResult.Failure(it))
            }
    }

    private fun <T: ProfileEditResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                // get user by session
                is ProfileEditResult.GetUserBySessionResult.Loading -> state.copy(
                    isLoading = true,
                    user = null,
                    error = null
                )
                is ProfileEditResult.GetUserBySessionResult.Success -> state.copy(
                    isLoading = false,
                    user = it.user,
                    error = null
                )
                is ProfileEditResult.GetUserBySessionResult.Failure -> state.copy(
                    isLoading = false,
                    user = null,
                    error = it.error
                )
                // update user by session
                is ProfileEditResult.UpdateUserBySessionResult.Loading -> state.copy(
                    isLoading = true,
                    user = null,
                    error = null
                )
                is ProfileEditResult.UpdateUserBySessionResult.Success -> state.copy(
                    isLoading = false,
                    user = it.user,
                    error = null
                )
                is ProfileEditResult.UpdateUserBySessionResult.Failure -> state.copy(
                    isLoading = false,
                    user = null,
                    error = it.error
                )
                // submit user by session
                is ProfileEditResult.SubmitUserBySessionResult.Loading -> state.copy(
                    isLoading = true,
                    user = null,
                    error = null
                )
                is ProfileEditResult.SubmitUserBySessionResult.Success -> state.copy(
                    isLoading = false,
                    user = it.user,
                    error = null,
                    submit = true
                )
                is ProfileEditResult.SubmitUserBySessionResult.Failure -> state.copy(
                    isLoading = false,
                    user = null,
                    error = it.error
                )
                else -> state
            }
        }
    }
}

