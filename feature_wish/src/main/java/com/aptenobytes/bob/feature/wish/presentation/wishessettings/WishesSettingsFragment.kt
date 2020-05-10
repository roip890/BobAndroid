package com.aptenobytes.bob.feature.wish.presentation.wishessettings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.domain.enums.wishsort.WishSortType
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.filter.WishFilterDomainModel
import com.aptenobytes.bob.library.base.extensions.collections.toArrayList
import com.aptenobytes.bob.library.base.extensions.ui.dpToPx
import com.aptenobytes.bob.library.base.form.bindings.classes.marginAll
import com.aptenobytes.bob.library.base.form.builder.*
import com.aptenobytes.bob.library.base.form.elements.DateTimeElement
import com.aptenobytes.bob.library.base.form.elements.EditTextElement
import com.aptenobytes.bob.library.base.form.elements.MultiChoiceElement
import com.aptenobytes.bob.library.base.presentation.extension.observe
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.paddingDp
import com.mikepenz.iconics.utils.sizeDp
import com.pawegio.kandroid.d
import kotlinx.android.synthetic.main.fragment_wish_settings.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.kodein.di.generic.instance
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class WishesSettingsFragment() : BaseContainerFragment(), WishesSettingsView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: WishesSettingsViewModel by instance<WishesSettingsViewModel>()

    override val layoutResourceId = R.layout.fragment_wish_settings

    private val stateObserver = Observer<WishesSettingsViewState> { render(it) }

    private var departments: List<DepartmentDomainModel> = listOf<DepartmentDomainModel>()

    private var wishesSettings: WishesSettingsDomainModel = WishesSettingsDomainModel()

    private var form: Form? = null

    private enum class WishesSettingsTags(val key: String) {
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

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //super.onCreate(savedInstanceState)
        requireContext()

        initForm()

        bind()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun initForm() {
        val context = requireContext()
        this.form = form(context, viewLifecycleOwner, formRecyclerView) {
            singleChoice<WishSortType>(
                tag = WishesSettingsTags.SORT.ordinal,
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
                key.postValue(WishesSettingsTags.SORT.key)
                hint.postValue("Sort")
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_sort).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            text<String>(tag = WishesSettingsTags.TITLE.ordinal) {
                key.postValue(WishesSettingsTags.TITLE.key)
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
            datetime(tag = WishesSettingsTags.MIN_DATE.ordinal) {
                key.postValue(WishesSettingsTags.MIN_DATE.key)
                hint.postValue("Minimum Date")
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_date_range).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            datetime(tag = WishesSettingsTags.MAX_DATE.ordinal) {
                key.postValue(WishesSettingsTags.MAX_DATE.key)
                hint.postValue("Maximum Date")
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_date_range).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            multiChoice<WishStatusType>(
                tag = WishesSettingsTags.STATUSES.ordinal,
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
                key.postValue(WishesSettingsTags.STATUSES.key)
                hint.postValue("Status")
                endDrawable.postValue(IconicsDrawable(requireContext(), FontAwesome.Icon.faw_check_square).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            multiChoice<DepartmentDomainModel>(
                tag = WishesSettingsTags.DEPARTMENTS.ordinal,
                items = departments.toArrayList(),
                viewItemValue = {
                    it?.name
                }
            ) {
                key.postValue(WishesSettingsTags.DEPARTMENTS.key)
                hint.postValue("Departments")
                endDrawable.postValue(IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_room_service).apply {
                    sizeDp = 28
                    paddingDp = 4
                })
            }
            button(tag = WishesSettingsTags.SUBMIT.ordinal) {
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
        observe(viewModel.stateLiveData, stateObserver)

        intents()
            .onEach { viewModel.processIntent(it) }
            .launchIn(lifecycleScope)
    }

    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf(WishesSettingsIntent.GetDepartmentsListIntent, WishesSettingsIntent.GetWishesSettingsIntent)
    )

    private fun saveDepartments(departments: List<DepartmentDomainModel>) {
        this.departments = departments
    }

    private fun saveWishesSettings(wishesSettings: WishesSettingsDomainModel) {
        this.wishesSettings = wishesSettings
    }

    private fun render(viewState: WishesSettingsViewState) {

        saveDepartments(viewState.departments)

        renderDepartments()

        saveWishesSettings(viewState.wishesSettings)

        renderForm(wishesSettings = viewState.wishesSettings)

    }

    private fun renderDepartments() {
        this.form?.let {
            val departments: MultiChoiceElement<DepartmentDomainModel>? = form?.getFormElement(tag = WishesSettingsTags.DEPARTMENTS.ordinal)
            departments?.let {
                departments.items = this.departments.toArrayList()
            }
        }
    }

    private fun renderForm(wishesSettings: WishesSettingsDomainModel) {

        this.form?.let {
            val title: EditTextElement<String>? = form?.getFormElement(tag = WishesSettingsTags.TITLE.ordinal)
            title?.let {
                title.value.postValue(wishesSettings.filter?.type)
            }

            val minDate: DateTimeElement? = form?.getFormElement(tag = WishesSettingsTags.MIN_DATE.ordinal)
            minDate?.let {
                minDate.value.postValue(wishesSettings.filter?.minTimestamp)
            }

            val maxDate: DateTimeElement? = form?.getFormElement(tag = WishesSettingsTags.MAX_DATE.ordinal)
            maxDate?.let {
                maxDate.value.postValue(wishesSettings.filter?.maxTimestamp)
            }

            val statuses: MultiChoiceElement<WishStatusType>? = form?.getFormElement(tag = WishesSettingsTags.STATUSES.ordinal)
            statuses?.let {
                statuses.value.postValue(wishesSettings.filter?.statuses)
            }

            val departments: MultiChoiceElement<DepartmentDomainModel>? = form?.getFormElement(tag = WishesSettingsTags.DEPARTMENTS.ordinal)
            departments?.let {
                departments.value.postValue(wishesSettings.filter?.departments)
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
                    type = if (form.value.containsKey(WishesSettingsTags.TITLE.key))
                        form.value[WishesSettingsTags.TITLE.key] as String? else null,
                    minTimestamp = if (form.value.containsKey(WishesSettingsTags.MIN_DATE.key))
                        form.value[WishesSettingsTags.MIN_DATE.key] as Date? else null,
                    maxTimestamp = if (form.value.containsKey(WishesSettingsTags.MAX_DATE.key))
                        form.value[WishesSettingsTags.MAX_DATE.key] as Date? else null,
                    statuses = if (form.value.containsKey(WishesSettingsTags.STATUSES.key))
                        form.value[WishesSettingsTags.STATUSES.key] as ArrayList<WishStatusType>? else null,
                    departments = if (form.value.containsKey(WishesSettingsTags.DEPARTMENTS.key))
                        form.value[WishesSettingsTags.DEPARTMENTS.key] as ArrayList<DepartmentDomainModel>? else null
                )
                val sort: WishSortType? = if (form.value.containsKey(WishesSettingsTags.SORT.key))
                    form.value[WishesSettingsTags.SORT.key] as WishSortType? else null

                val wishesSettings = WishesSettingsDomainModel(filter = filter, sort = sort)

                flowOf(WishesSettingsIntent.SetWishesSettingsIntent(wishesSettings = wishesSettings))
                    .onEach { viewModel.processIntent(it) }
                    .launchIn(lifecycleScope)

            }
        }
    }

}
