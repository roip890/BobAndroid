package com.aptenobytes.bob.feature.wish.presentation.wishsettings

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
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.filter.WishFilterDomainModel
import com.aptenobytes.bob.feature.wish.presentation.wishlist.WishListViewModel
import com.aptenobytes.bob.library.base.extensions.collections.toArrayList
import com.aptenobytes.bob.library.base.extensions.ui.dpToPx
import com.aptenobytes.bob.library.base.presentation.form.bindings.classes.marginAll
import com.aptenobytes.bob.library.base.presentation.form.builder.*
import com.aptenobytes.bob.library.base.presentation.form.elements.DateTimeElement
import com.aptenobytes.bob.library.base.presentation.form.elements.EditTextElement
import com.aptenobytes.bob.library.base.presentation.form.elements.MultiChoiceElement
import com.aptenobytes.bob.library.base.presentation.bottomsheetdialogfragment.BaseContainerBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.iconics.utils.sizeDp
import kotlinx.android.synthetic.main.fragment_wish_settings.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance
import timber.log.Timber
import java.util.*


class WishSettingsFragment(
    val onSubmitSettingListener: (() -> Unit)? = null
) : BaseContainerBottomSheetDialogFragment(), WishSettingsView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: WishSettingsViewModel by instance<WishSettingsViewModel>()

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val wishListViewModel: WishListViewModel by instance<WishListViewModel>()

    override val layoutResourceId = R.layout.fragment_wish_settings

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val stateObserver = Observer<WishSettingsViewState> { render(it) }

    private var form: Form? = null

    private enum class WishSettingsTags(val key: String) {
        SORT("Sort"),
        TITLE("Title"),
        MIN_DATE("Minimum Date"),
        MAX_DATE("Maximum Date"),
        STATUSES("Statuses"),
        DEPARTMENTS("Departments"),
        SUBMIT("Submit")
    }

    private enum class WishStatusView(val viewValue: String) {
        PENDING("Pending"),
        WAITING("Waiting"),
        IN_PROGRESS("In Progress"),
        DONE("Done")
    }

    private enum class WishSortView(val viewValue: String) {
        TIMESTAMP("Time"),
        STATUS("Status"),
        TYPE("Title")
    }

    companion object{
        val TAG = "WISHES_SETTINGS"
        fun newInstance(
            onSubmitSettingListener: (() -> Unit)? = null
        ): WishSettingsFragment{
            return WishSettingsFragment(
                onSubmitSettingListener = onSubmitSettingListener
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
            singleChoice<WishSortType>(
                tag = WishSettingsTags.SORT.ordinal,
                items = arrayListOf(
                    WishSortType.TIMESTAMP,
                    WishSortType.STATUS,
                    WishSortType.TYPE
                ),
                viewItemValue = {
                    when(it) {
                        WishSortType.TIMESTAMP -> WishSortView.TIMESTAMP.viewValue
                        WishSortType.STATUS -> WishSortView.STATUS.viewValue
                        WishSortType.TYPE -> WishSortView.TYPE.viewValue
                        else -> ""
                    }
                }
            ) {
                key.postValue(WishSettingsTags.SORT.key)
                hint.postValue("Sort")
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_sort).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            text<String>(tag = WishSettingsTags.TITLE.ordinal) {
                key.postValue(WishSettingsTags.TITLE.key)
                hint.postValue("Title")
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
            datetime(tag = WishSettingsTags.MIN_DATE.ordinal) {
                key.postValue(WishSettingsTags.MIN_DATE.key)
                hint.postValue("Minimum Date")
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_date_range).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            datetime(tag = WishSettingsTags.MAX_DATE.ordinal) {
                key.postValue(WishSettingsTags.MAX_DATE.key)
                hint.postValue("Maximum Date")
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_date_range).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            multiChoice<WishStatusType>(
                tag = WishSettingsTags.STATUSES.ordinal,
                items = arrayListOf(
                    WishStatusType.WAITING,
                    WishStatusType.PENDING,
                    WishStatusType.IN_PROGRESS,
                    WishStatusType.DONE
                ),
                viewItemValue = {
                    when(it) {
                        WishStatusType.WAITING -> WishStatusView.WAITING.viewValue
                        WishStatusType.PENDING -> WishStatusView.PENDING.viewValue
                        WishStatusType.IN_PROGRESS -> WishStatusView.IN_PROGRESS.viewValue
                        WishStatusType.DONE -> WishStatusView.DONE.viewValue
                        else -> ""
                    }
                }
            ) {
                key.postValue(WishSettingsTags.STATUSES.key)
                hint.postValue("Status")
                endDrawable.postValue(IconicsDrawable(requireContext(), FontAwesome.Icon.faw_check_square).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            multiChoice<DepartmentDomainModel>(
                tag = WishSettingsTags.DEPARTMENTS.ordinal,
                items = viewModel.departments.value?.toArrayList() ?: arrayListOf(),
                viewItemValue = {
                    it?.name
                }
            ) {
                key.postValue(WishSettingsTags.DEPARTMENTS.key)
                hint.postValue("Departments")
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_room_service).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            button(tag = WishSettingsTags.SUBMIT.ordinal) {
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
        viewModel.departments.observe(viewLifecycleOwner, Observer {
            this.updateDepartments(it)
        })
        viewModel.wishSettings.observe(viewLifecycleOwner, Observer {
            this.updateForm(it)
        })
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        intents()
            .onEach { viewModel.processIntent(it) }
            .launchIn(lifecycleScope)
    }

    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf(WishSettingsIntent.GetDepartmentsListIntent, WishSettingsIntent.GetWishSettingsIntent)
    )

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun render(viewState: WishSettingsViewState) {

        if (viewState.submit) {
            this.dismiss()
            this.onSubmitSettingListener?.invoke()
            return
        }

        viewModel.departments.postValue(viewState.departments)

        viewModel.wishSettings.postValue(viewState.wishSettings)

    }

    private fun updateDepartments(departments: List<DepartmentDomainModel>?) {
        this.form?.let {
            val departmentsElement: MultiChoiceElement<DepartmentDomainModel>? = form?.getFormElement(tag = WishSettingsTags.DEPARTMENTS.ordinal)
            departmentsElement?.let {
                departmentsElement.items = departments?.toArrayList() ?: arrayListOf()
            }
        }
    }

    private fun updateForm(wishSettings: WishSettingsDomainModel?) {

        this.form?.let {
            val sort: EditTextElement<WishSortType>? = form?.getFormElement(tag = WishSettingsTags.SORT.ordinal)
            sort?.let {
                sort.value.postValue(wishSettings?.sort)
            }

            val title: EditTextElement<String>? = form?.getFormElement(tag = WishSettingsTags.TITLE.ordinal)
            title?.let {
                title.value.postValue(wishSettings?.filter?.type)
            }

            val minDate: DateTimeElement? = form?.getFormElement(tag = WishSettingsTags.MIN_DATE.ordinal)
            minDate?.let {
                minDate.value.postValue(wishSettings?.filter?.minTimestamp)
            }

            val maxDate: DateTimeElement? = form?.getFormElement(tag = WishSettingsTags.MAX_DATE.ordinal)
            maxDate?.let {
                maxDate.value.postValue(wishSettings?.filter?.maxTimestamp)
            }

            val statuses: MultiChoiceElement<WishStatusType>? = form?.getFormElement(tag = WishSettingsTags.STATUSES.ordinal)
            statuses?.let {
                statuses.value.postValue(wishSettings?.filter?.statuses)
            }

            val departments: MultiChoiceElement<DepartmentDomainModel>? = form?.getFormElement(tag = WishSettingsTags.DEPARTMENTS.ordinal)
            departments?.let {
                departments.value.postValue(wishSettings?.filter?.departments)
            }
        }

    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @Suppress("UNCHECKED_CAST")
    private fun submitForm() {
        form?.let { form ->
            if (form.valid) {
                val filter: WishFilterDomainModel? = WishFilterDomainModel(
                    type = if (form.value.containsKey(WishSettingsTags.TITLE.key))
                        form.value[WishSettingsTags.TITLE.key] as String? else null,
                    minTimestamp = if (form.value.containsKey(WishSettingsTags.MIN_DATE.key))
                        form.value[WishSettingsTags.MIN_DATE.key] as Date? else null,
                    maxTimestamp = if (form.value.containsKey(WishSettingsTags.MAX_DATE.key))
                        form.value[WishSettingsTags.MAX_DATE.key] as Date? else null,
                    statuses = if (form.value.containsKey(WishSettingsTags.STATUSES.key))
                        form.value[WishSettingsTags.STATUSES.key] as ArrayList<WishStatusType>? else null,
                    departments = if (form.value.containsKey(WishSettingsTags.DEPARTMENTS.key))
                        form.value[WishSettingsTags.DEPARTMENTS.key] as ArrayList<DepartmentDomainModel>? else null
                )
                val sort: WishSortType? = if (form.value.containsKey(WishSettingsTags.SORT.key))
                    form.value[WishSettingsTags.SORT.key] as WishSortType? else null

                val wishSettings = WishSettingsDomainModel(filter = filter, sort = sort)

                flowOf(WishSettingsIntent.SubmitWishSettingsIntent(wishSettings = wishSettings))
                    .onEach { viewModel.processIntent(it) }
                    .launchIn(lifecycleScope)

            }
        }
    }

}
