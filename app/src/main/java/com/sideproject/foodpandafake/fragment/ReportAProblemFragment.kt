package com.sideproject.foodpandafake.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sideproject.foodpandafake.MainViewModel
import com.sideproject.foodpandafake.R
import com.sideproject.foodpandafake.databinding.FragmentReportBinding
import com.sideproject.foodpandafake.util.FragmentUtil
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class ReportAProblemFragment : Fragment(R.layout.fragment_report) {

    val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentReportBinding
    private lateinit var galleryResultLauncher: ActivityResultLauncher<String>
    private lateinit var takePicResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var photoUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        FragmentUtil.setBottomNavHideOrVisible(mainViewModel)
        binding = FragmentReportBinding.bind(view)

        setTakeAPhoto()

        setGalleryToGetPhoto()
    }

    private fun setGalleryToGetPhoto() {
        binding.btnGallery.setOnClickListener {
            if (checkPermission()) {
                galleryResultLauncher.launch("image/*")
            }
        }
    }

    private fun setTakeAPhoto() {
        binding.btnTakeAPhoto.setOnClickListener {

            if (checkPermission()) {
                val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                photoUri = FileProvider.getUriForFile(
                    requireContext(), "com.sideproject.foodpandafake.provider", createImageFile()
                )
                i.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                takePicResultLauncher.launch(i)
            }

        }
    }

    private fun createImageFile(): File {
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(System.currentTimeMillis().toString(), ".jpg", storageDir)
    }

    private fun checkPermission():Boolean{
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        takePicResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    binding.imgPhoto.setImageURI(photoUri)
                }
            }
        //gallery
        galleryResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imgPhoto.setImageURI(it)
        }
    }
}