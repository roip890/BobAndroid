package com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptenobytes.bob.feature.profile.R
import com.aptenobytes.bob.feature.profile.presentation.profilepage.ProfilePageIntent
import com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu.recyclerview.ProfilePictureMenuItemViewHolder
import com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu.recyclerview.ProfilePictureMenuItemViewModel
import com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu.recyclerview.profilePictureMenuAdapter
import com.aptenobytes.bob.feature.profile.presentation.profilepicturemenu.recyclerview.toViewModel
import com.aptenobytes.bob.library.base.extensions.ui.action
import com.aptenobytes.bob.library.base.extensions.ui.snack
import com.aptenobytes.bob.library.base.presentation.bottomsheetdialogfragment.BaseContainerBottomSheetDialogFragment
import com.aptenobytes.bob.library.base.presentation.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.presentation.recyclerview.builder.recycleView
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.coroutines.flow
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp
import kotlinx.android.synthetic.main.fragment_set_user_status.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance
import timber.log.Timber
import java.io.File

class ProfilePictureMenuFragment(
    val onChangeProfilePictureListener: ((imageUrl: String?) -> Unit)? = null,
    val onShowProfilePictureListener: (() -> Unit)? = null
) : BaseContainerBottomSheetDialogFragment(), ProfilePictureMenuView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: ProfilePictureMenuViewModel by instance<ProfilePictureMenuViewModel>()

    override val layoutResourceId = R.layout.fragment_set_user_status

    private lateinit var adapter: RecyclerViewAdapter<ProfilePictureMenuItemViewModel, ProfilePictureMenuItemViewHolder>

    private val stateObserver = Observer<ProfilePictureMenuViewState> { render(it) }


    companion object{
        val TAG = "SET_USERS"
        fun newInstance(
            onChangeProfilePictureListener: ((imageUrl: String?) -> Unit)? = null,
            onShowProfilePictureListener: (() -> Unit)? = null
        ): ProfilePictureMenuFragment {
            return ProfilePictureMenuFragment(
                onChangeProfilePictureListener = onChangeProfilePictureListener,
                onShowProfilePictureListener = onShowProfilePictureListener
            )
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(DialogFragment.STYLE_NO_FRAME,0);
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext()
        setupUsersStatusList()
        progressBar.visibility = GONE
        bind()
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // setup full height
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog: DialogInterface ->
            val bottomSheet =
                (dialog as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.setBackgroundColor(Color.TRANSPARENT)
        }
        return bottomSheetDialog

    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupUsersStatusList() {
        val context = requireContext()
        adapter = profilePictureMenuAdapter(
            lifecycleOwner = viewLifecycleOwner,
            onDebouncedClickListener = {profilePictureMenuItem ->
                when (profilePictureMenuItem) {
                    is ProfilePictureMenuItem.ShowProfilePicture -> {
                        showProfilePicturePreview()
                    }
                    is ProfilePictureMenuItem.PickFromGallery -> {
                        pickFromGallery()
                    }
                    is ProfilePictureMenuItem.PickFromCamera -> {
                        pickFromCamera()
                    }
                    is ProfilePictureMenuItem.RemoveProfilePicture -> {
                        removeProfilePicture()
                    }
                }
            }
        )
        recycleView(
            recyclerView,
            LinearLayoutManager(context),
            adapter
        ).apply {
            setHasFixedSize(true)
        }
        adapter.items = createProfilePictureMenuItems()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun showProfilePicturePreview() {
        onShowProfilePictureListener?.invoke()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun pickFromGallery() {
        val request = permissionsBuilder(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).build()
        request.flow()
            .onEach {permissionStatusList ->
                if (permissionStatusList.allGranted()) {
                    ImagePicker.with(this)
                        .crop()
                        .galleryOnly()
                        .start { resultCode, data ->
                            if (resultCode == Activity.RESULT_OK) {
                                //Image Uri will not be null for RESULT_OK
                                ImagePicker.getFilePath(data)?.let {imagePath ->
                                    flowOf(ProfilePictureMenuIntent.UploadProfilePictureIntent(imagePath = imagePath))
                                        .onEach { viewModel.processIntent(it) }
                                        .launchIn(lifecycleScope)
                                }
                            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                                errorSnack("Pick from gallery error")
                                this.dismiss()
                            } else {
                                errorSnack("Pick from gallery cancelled")
                                this.dismiss()
                            }
                        }
                }
            }
            .launchIn(lifecycleScope)
        request.send()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun pickFromCamera() {
        val request = permissionsBuilder(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).build()
        request.flow()
            .onEach {permissionStatusList ->
                if (permissionStatusList.allGranted()) {
                    ImagePicker.with(this)
                        .crop()
                        .cameraOnly()
                        .start { resultCode, data ->
                            if (resultCode == Activity.RESULT_OK) {
                                //Image Uri will not be null for RESULT_OK
                                ImagePicker.getFilePath(data)?.let {imagePath ->
                                    flowOf(ProfilePictureMenuIntent.UploadProfilePictureIntent(imagePath = imagePath))
                                        .onEach { viewModel.processIntent(it) }
                                        .launchIn(lifecycleScope)
                                }
                            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                                errorSnack("Pick from gallery error")
                                this.dismiss()
                            } else {
                                errorSnack("Pick from gallery cancelled")
                                this.dismiss()
                            }
                        }
                }
            }
            .launchIn(lifecycleScope)
        request.send()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun removeProfilePicture() {
        flowOf(ProfilePictureMenuIntent.RemoveProfilePictureIntent)
            .onEach { viewModel.processIntent(it) }
            .launchIn(lifecycleScope)
    }

    private fun createProfilePictureMenuItems(): ArrayList<ProfilePictureMenuItemViewModel?> {
        return arrayListOf(
            ProfilePictureMenuItem.ShowProfilePicture(
                title = "Show Profile Picture",
                icon = IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_person).apply {
                    sizeDp = 24
                    colorInt = Color.WHITE
                }
            ).toViewModel(),
            ProfilePictureMenuItem.PickFromGallery(
                title = "Pick From Gallery",
                icon = IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_image).apply {
                    sizeDp = 24
                    colorInt = Color.WHITE
                }
            ).toViewModel(),
            ProfilePictureMenuItem.PickFromCamera(
                title = "Pick From Camera",
                icon = IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_camera).apply {
                    sizeDp = 24
                    colorInt = Color.WHITE
                }
            ).toViewModel(),
            ProfilePictureMenuItem.RemoveProfilePicture(
                title = "Remove Profile Picture",
                icon = IconicsDrawable(requireContext(), GoogleMaterial.Icon.gmd_delete).apply {
                    sizeDp = 24
                    colorInt = Color.WHITE
                }
            ).toViewModel()
        )
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bind() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
    }

    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf<ProfilePictureMenuIntent>()
    )

    private fun render(viewState: ProfilePictureMenuViewState) {
        viewState.isProfilePictureChanged?.let {
            if (viewState.isProfilePictureChanged) {
                onChangeProfilePictureListener?.invoke(viewState.imageUrl)
                successSnack("Profile picture changed!")
                this.dismiss()
            }
        }
        progressBar.visibility = if (viewState.isLoading) View.VISIBLE else GONE
        recyclerView.visibility = if (viewState.error == null && !viewState.isLoading) View.VISIBLE else GONE
        if (viewState.error != null) {
            errorSnack("Profile picture error")
            this.dismiss()
        }
    }

    private fun errorSnack(error: String) {
        val rootView: View? = activity?.window?.decorView?.findViewById(android.R.id.content)
        rootView?.snack(message = error, length = Snackbar.LENGTH_LONG) {
            this.view.setBackgroundColor(ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.faded_red))
            action("Close", ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.white)) {
                this.dismiss()
            }
        }
    }

    private fun successSnack(message: String) {
        val rootView: View? = activity?.window?.decorView?.findViewById(android.R.id.content)
        rootView?.snack(message = message, length = Snackbar.LENGTH_LONG) {
            this.view.setBackgroundColor(ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.faded_green))
            action("Close", ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.white)) {
                this.dismiss()
            }
        }
    }

}
