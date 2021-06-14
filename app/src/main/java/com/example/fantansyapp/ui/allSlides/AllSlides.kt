package com.example.fantansyapp.ui.allSlides

import android.app.AlertDialog
import android.os.Bundle
import android.transition.Slide
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fantansyapp.R
import com.example.fantansyapp.data.repositories.LimitsRepository
import com.example.fantansyapp.databinding.FragmentAllSlidesBinding
import com.example.fantansyapp.utils.snackBar
import com.example.fantansyapp.utils.visible

class AllSlides: Fragment(R.layout.fragment_all_slides),SlidesAdapter.OnDeleteClickListener {

    val binding:FragmentAllSlidesBinding by viewBinding()
    lateinit var repo:LimitsRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        getSlides()

        binding.addSlide.setOnClickListener {
            AllSlidesDirections.actionNavigationAllSlidesToNavigationUploads().apply {
                findNavController().navigate(this)
            }
        }

    }

    private fun getSlides() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            try {
                binding.progressBarUser.visible()
                val slidesList = repo.getAllSliderImages()

                binding.recyclerView.adapter = SlidesAdapter(slidesList,this@AllSlides)
                binding.progressBarUser.visible(false)

            }catch (e:Exception){
                try {
                    requireView().snackBar(e.message.toString())
                    binding.progressBarUser.visible(false)

                }catch (e:Exception){

                }
            }
        }
    }

    private fun initData() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            //layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
        }
        repo = LimitsRepository(requireContext())
    }

    override fun clickListener(slideId: String) {

        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Slide")
            setMessage("Do your really want to delete slide? ")
            setPositiveButton("Yes") { dialog, _ ->
                dialog.cancel()
                deleteSlide(slideId)

            }
            setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            create()
            show()
        }

    }

    private fun deleteSlide(slideId: String) {


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            try {
                binding.progressBarUser.visible()
                val result = repo.deleteImageFromSlider(slideId)
                if (result.result == "success"){
                    requireView().snackBar("Slide deleted Successfully")
                    getSlides()
                }

            }catch (e:Exception){

                try {
                    binding.progressBarUser.visible(false)
                    requireView().snackBar(e.message.toString())
                }catch (e:Exception){

                }
            }
        }
    }
}