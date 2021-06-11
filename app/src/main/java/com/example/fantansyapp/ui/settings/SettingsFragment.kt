package com.example.fantansyapp.ui.settings

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fantansyapp.R
import com.example.fantansyapp.data.models.Limit
import com.example.fantansyapp.data.repositories.LimitsRepository
import com.example.fantansyapp.databinding.FragmentSettingsBinding
import com.example.fantansyapp.utils.snackBar
import com.example.fantansyapp.utils.visible
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    val binding: FragmentSettingsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLimits()

        binding.updateButton.setOnClickListener {

            updateLimits()

        }

    }

    private fun getLimits() {
        viewLifecycleOwner.lifecycleScope.launch {
            val limits = LimitsRepository(requireContext()).getLimits()

            binding.spinnerLimit.setText(limits.spinner)
            binding.guessLimit.setText(limits.guess)
            binding.referLimit.setText(limits.refer)
            binding.scratchLimit.setText(limits.scratch)
        }
    }

    private fun updateLimits() {
        viewLifecycleOwner.lifecycleScope.launch {

            binding.progressBarSetting.visible(true)

            try {


                val spinner = binding.spinnerLimit.text
                if (!spinner.isNullOrEmpty()){


                    val limit = Limit(
                        spinner =  binding.spinnerLimit.text.toString(),
                        refer =  binding.referLimit.text.toString(),
                        scratch =  binding.scratchLimit.text.toString(),
                        guess =  binding.guessLimit.text.toString()
                    )

                    val result = LimitsRepository(requireContext()).updateLimits(limit)

                    if(result.result == "success"){
                        requireView().snackBar("Data Updated Successfully")
                    }

                    binding.progressBarSetting.visible(false)

                }else{
                    requireView().snackBar("Can not find data to Update")

                    binding.progressBarSetting.visible(false)

                }
            } catch (e: Exception) {
                requireView().snackBar(e.message.toString())

                binding.progressBarSetting.visible(false)

            }
        }
    }
}