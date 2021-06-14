package com.example.fantansyapp.ui.addSlide

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fantansyapp.R
import com.example.fantansyapp.data.repositories.LimitsRepository
import com.example.fantansyapp.databinding.FragmentSliderBinding
import com.example.fantansyapp.utils.snackBar
import com.example.fantansyapp.utils.visible
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.FileNotFoundException
import java.io.InputStream

class AddSlide : Fragment(R.layout.fragment_slider) {
    val binding: FragmentSliderBinding by viewBinding()
    var body: MultipartBody.Part? = null

    companion object {
        const val PICK_PHOTO = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.image.setOnClickListener {
            pickImage()
        }

        binding.doneButton.setOnClickListener {
            var link = binding.etLink.text.toString()
            if (link.isNullOrEmpty()){
               link = " "
            }

            if (body != null) {

                uploadImage(link)

            } else {
                binding.root.snackBar("Please Choose Image to Upload First")
            }
        }
    }

    private fun uploadImage(link: String) {
        viewLifecycleOwner.lifecycleScope.launch {

            binding.progressBarSlider.visible(true)

            try {
                val result = LimitsRepository(requireContext()).uploadSliderImage(body!!,link)
                if (result.result == "success") {
                    binding.root.snackBar("Image Uploaded Successfully")
                    findNavController().popBackStack()
                    binding.image.setImageURI(null)
                }

                binding.progressBarSlider.visible(false)

            } catch (e: Exception) {
                binding.root.snackBar(e.message.toString())

                binding.progressBarSlider.visible(false)

            }

        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)




        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            try {
                data?.let {

                    binding.image.setImageURI(data.data!!)

                    val inputStream: InputStream? = context?.contentResolver?.openInputStream(it.data!!)
                    inputStream?.let {
                        val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), inputStream.readBytes())
                        body = MultipartBody.Part.createFormData("image", "image", requestBody)

                    }
                }


            } catch (e: FileNotFoundException) {
                e.printStackTrace()

                binding.root.snackBar("File Not Found Plase choose another")

            }
        }
    }
}




