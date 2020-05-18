package com.aptenobytes.bob.feature.profile.presentation.profileedit

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.profile.R
import com.aptenobytes.bob.library.base.extensions.ui.dpToPx
import com.aptenobytes.bob.library.base.presentation.bottomsheetdialogfragment.BaseContainerBottomSheetDialogFragment
import com.aptenobytes.bob.library.base.presentation.form.bindings.classes.marginAll
import com.aptenobytes.bob.library.base.presentation.form.builder.*
import com.aptenobytes.bob.library.base.presentation.form.elements.DateTimeElement
import com.aptenobytes.bob.library.base.presentation.form.elements.EditTextElement
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.iconics.utils.sizeDp
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import timber.log.Timber
import java.util.*


class ProfileEditFragment(
    val onSubmitProfileListener: (() -> Unit)? = null
) : BaseContainerBottomSheetDialogFragment(), ProfileEditView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: ProfileEditViewModel by instance<ProfileEditViewModel>()

    override val layoutResourceId = R.layout.fragment_profile_edit

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val stateObserver = Observer<ProfileEditViewState> { render(it) }

    private var form: Form? = null

    private enum class ProfileEditTags(val key: String) {
        FIRST_NAME("First Name"),
        LAST_NAME("Last Name"),
        EMAIL("Email"),
        BIRTHDAY("Birthday"),
        PHONE("Phone"),
        SUBMIT("Submit")
    }

    companion object{
        val TAG = "PROFILE_EDIT"
        fun newInstance(
            onSubmitProfileListener: (() -> Unit)? = null
        ): ProfileEditFragment{
            return ProfileEditFragment(
                onSubmitProfileListener = onSubmitProfileListener
            )
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(DialogFragment.STYLE_NO_FRAME,0);
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //super.onCreate(savedInstanceState)
        requireContext()
        setupForm()
        bind()
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // setup full height
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog: DialogInterface ->
            val dialogc = dialog as BottomSheetDialog
            val bottomSheet =
                dialogc.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
                bottomSheetBehavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            }
            bottomSheet?.setBackgroundColor(Color.TRANSPARENT)
        }
        return bottomSheetDialog

    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupForm() {
        val context = requireContext()
        this.form = form(context, viewLifecycleOwner, formRecyclerView) {
            text<String>(tag = ProfileEditTags.FIRST_NAME.ordinal) {
                key.postValue(ProfileEditTags.FIRST_NAME.key)
                hint.postValue("First Name")
                margin.postValue(
                    marginAll(
                        8.dpToPx
                    )
                )
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_text_fields).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            text<String>(tag = ProfileEditTags.LAST_NAME.ordinal) {
                key.postValue(ProfileEditTags.LAST_NAME.key)
                hint.postValue("Last Name")
                margin.postValue(
                    marginAll(
                        8.dpToPx
                    )
                )
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_text_fields).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            text<String>(tag = ProfileEditTags.EMAIL.ordinal) {
                key.postValue(ProfileEditTags.EMAIL.key)
                hint.postValue("Email")
                margin.postValue(
                    marginAll(
                        8.dpToPx
                    )
                )
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_text_fields).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            phone(tag = ProfileEditTags.PHONE.ordinal) {
                key.postValue(ProfileEditTags.PHONE.key)
                hint.postValue("Phone")
                margin.postValue(
                    marginAll(
                        8.dpToPx
                    )
                )
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_phone).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            datetime(tag = ProfileEditTags.BIRTHDAY.ordinal) {
                key.postValue(ProfileEditTags.BIRTHDAY.key)
                hint.postValue("Birthday")
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_date_range).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            button(tag = ProfileEditTags.SUBMIT.ordinal) {
                text.postValue("Submit")
                onClickHandler = {
                    Timber.v("Is Form Valid: ${form?.valid}")
                    Timber.v("Form Value: ${form?.value}")
                    submitForm()
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bind() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            updateForm(user = it)
        })
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        intents()
            .onEach { viewModel.processIntent(it) }
            .launchIn(lifecycleScope)
    }

    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf(ProfileEditIntent.GetUserBySessionIntent)
    )

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun render(viewState: ProfileEditViewState) {

        if (viewState.submit) {
            this.onSubmitProfileListener?.invoke()
            this.dismiss()
            return
        }

        viewModel.user.postValue(viewState.user)

    }

    private fun updateForm(user: UserDomainModel?) {

        this.form?.let {
            val firstName: EditTextElement<String>? = form?.getFormElement(tag = ProfileEditTags.FIRST_NAME.ordinal)
            firstName?.let {
                firstName.value.postValue(user?.firstName)
            }

            val lastName: EditTextElement<String>? = form?.getFormElement(tag = ProfileEditTags.LAST_NAME.ordinal)
            lastName?.let {
                lastName.value.postValue(user?.lastName)
            }

            val email: EditTextElement<String>? = form?.getFormElement(tag = ProfileEditTags.EMAIL.ordinal)
            email?.let {
                email.value.postValue(user?.email)
            }

            val phone: EditTextElement<String>? = form?.getFormElement(tag = ProfileEditTags.PHONE.ordinal)
            phone?.let {
                phone.value.postValue(user?.phone)
            }

            val birthday: DateTimeElement? = form?.getFormElement(tag = ProfileEditTags.BIRTHDAY.ordinal)
            birthday?.let {
                birthday.value.postValue(user?.birthday)
            }
        }

    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @Suppress("UNCHECKED_CAST")
    private fun submitForm() {
        form?.let { form ->
            viewModel.user.value?.id?.let { id ->
                if (form.valid) {
                    val user: UserDomainModel = UserDomainModel(
                        id = id,
                        firstName = if (form.value.containsKey(ProfileEditTags.FIRST_NAME.key))
                            form.value[ProfileEditTags.FIRST_NAME.key] as String? else null,
                        lastName = if (form.value.containsKey(ProfileEditTags.LAST_NAME.key))
                            form.value[ProfileEditTags.LAST_NAME.key] as String? else null,
                        email = if (form.value.containsKey(ProfileEditTags.EMAIL.key))
                            form.value[ProfileEditTags.EMAIL.key] as String? else null,
                        phone = if (form.value.containsKey(ProfileEditTags.PHONE.key))
                            form.value[ProfileEditTags.PHONE.key] as String? else null,
                        birthday = if (form.value.containsKey(ProfileEditTags.BIRTHDAY.key))
                            form.value[ProfileEditTags.BIRTHDAY.key] as Date? else null
                    )

                    flowOf(ProfileEditIntent.SubmitUserBySessionIntent(user = user))
                        .onEach { viewModel.processIntent(it) }
                        .launchIn(lifecycleScope)

                }
            }
        }
    }

}
