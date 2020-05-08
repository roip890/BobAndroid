package com.aptenobytes.bob.feature.wish.presentation.wishessettings

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.library.base.extensions.ui.dpToPx
import com.aptenobytes.bob.library.base.extensions.ui.spToPx
import com.aptenobytes.bob.library.base.form.bindings.classes.horizontalGravity
import com.aptenobytes.bob.library.base.form.bindings.classes.marginAll
import com.aptenobytes.bob.library.base.form.builder.*
import com.aptenobytes.bob.library.base.presentation.extension.observe
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import kotlinx.android.synthetic.main.fragment_wish_settings.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance
import timber.log.Timber

class WishesSettingsFragment : BaseContainerFragment(), WishesSettingsView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: WishesSettingsViewModel by instance<WishesSettingsViewModel>()

    override val layoutResourceId = R.layout.fragment_wish_settings

    private val stateObserver = Observer<WishesSettingsViewState> { render(it) }

    private lateinit var form: Form

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //super.onCreate(savedInstanceState)
        requireContext()

        setupForm()

        bind()
    }

    private fun setupForm() {
        val context = requireContext()
        form = form(context, viewLifecycleOwner, formRecyclerView) {

            text<String> {
                key.postValue("Title")
                hint.postValue("Title")
                validate = {value: String? -> value?.let {if (it.length < 5) "Less than 5" else null} }
                width.postValue(250.dpToPx)
                height.postValue(60.dpToPx)
                horizontalGravity.postValue(
                    horizontalGravity(
                        center = true
                    )
                )
                textSize.postValue(12.spToPx)
                inputType.postValue(InputType.TYPE_CLASS_NUMBER)
                margin.postValue(
                    marginAll(
                        8.dpToPx
                    )
                )
//                startDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_person).apply {
//                    sizeDp = 28
//                    paddingDp = 4
//                })
            }
            datetime {
                key.postValue("Min Date")
                hint.postValue("Min Date")
            }
            datetime {
                key.postValue("Max Date")
                hint.postValue("Max Date")
            }
            multiChoice<String>(
                items = arrayListOf("A", "B", "C", "D")
            ) {
                key.postValue("Multi Choice")
                hint.postValue("Multi Choice")
            }
            singleChoice<String>(
                items = arrayListOf("A", "B", "C", "D")
            ) {
                key.postValue("Single Choice")
                hint.postValue("Single Choice")
            }
            list<String>(
                items = arrayListOf("A", "B", "C", "D")
            ) {
                key.postValue("List")
                hint.postValue("List")
            }
            button {
                text.postValue("button")
                onClickHandler = {
                    Timber.v("Is Form Valid: ${form.valid}")
                    Timber.v("Form Value: ${form.value}")
                }
                textColor.postValue(Color.BLUE)
                backgroundColor.postValue(Color.YELLOW)
            }
        }

    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bind() {
        observe(viewModel.stateLiveData, stateObserver)

        intents()
            .onEach { viewModel.processIntent(it) }
            .launchIn(lifecycleScope)
    }

    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf(WishesSettingsIntent.InitialIntent)
    )

    private fun render(viewState: WishesSettingsViewState) {

        renderForm(wishesSettings = viewState.wishesSettings)

    }

    private fun renderForm(wishesSettings: WishesSettingsDomainModel) {

//        wishesSettingsType.setText(wishesSettings.filter?.type)
//
//        wishesSettingsMinDate.setText(wishesSettings.filter?.minTimestamp)
//
//        wishesSettingsMaxDate.setText(wishesSettings.filter?.maxTimestamp)
//
//        wishesSettingsStatus.setText(wishesSettings.filter?.statuses?.joinToString(separator = ", "))
//
//        wishesSettingsDepartments.setText(wishesSettings.filter?.departments?.joinToString(separator = ", "))

    }


}
