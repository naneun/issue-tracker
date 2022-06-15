package com.example.issue_tracker.ui.label

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentLabelWriteBinding
import kotlin.random.Random


class LabelWriteFragment : Fragment() {

    private lateinit var binding: FragmentLabelWriteBinding
    private var titleFlag = false
    private var colorFlag = false
    private lateinit var navigator: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_label_write, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator= Navigation.findNavController(view)
        initColor()
        changeStatusBarColor()
        refreshColor()
        validateLabelTitle()
        validateLabelColor()
        setUpBackButton()
    }

    private fun initColor() {
        binding.labelRandomColor = getString(R.string.label_write_base_color).toLong(16).toInt()
    }

    private fun refreshColor() {
        binding.iBtnLabelWriteColorRefresh.setOnClickListener {
            val colorString = setupRandomColor()
            binding.etLabelWriteColor.setText("#${colorString}")
            binding.labelRandomColor = ("FF${colorString}".toLong(16).toInt())
        }
    }

    private fun setupRandomColor(): String {
        val redValue = Random.nextInt(256).toString(16)
        val greenValue = Random.nextInt(256).toString(16)
        val blueValue = Random.nextInt(256).toString(16)
        return "${checkHexLength(redValue)}${checkHexLength(greenValue)}${checkHexLength(blueValue)}"
    }

    private fun checkHexLength(RGBValue: String): String {
        return if (RGBValue.length < 2) {
            "0${RGBValue}"
        } else {
            RGBValue
        }
    }

    private fun validateLabelColor() {
        binding.etLabelWriteColor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(inputColor: Editable?) {
                val title = inputColor.toString()
                colorFlag= title.isNotEmpty()
                setSaveTitleColor()
            }
        }
        )
    }


    private fun validateLabelTitle() {
        binding.etLabelWriteTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(inputTitle: Editable?) {
                val title = inputTitle.toString()
                titleFlag = title.isNotEmpty()
                setSaveTitleColor()
            }
        }
        )
    }

    private fun setSaveTitleColor() {
        if (titleFlag&&colorFlag) {
            binding.etLabelWriteColor.error = null
            binding.tvLabelWriteSaveTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
        else if(!titleFlag&&colorFlag){
            binding.etLabelWriteColor.error = null
            binding.etLabelWriteTitle.error = "필수입력값입니다"
            binding.tvLabelWriteSaveTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey1))
        }
        else  if(!colorFlag&&titleFlag){
            binding.etLabelWriteColor.error = "필수입력값입니다"
            binding.tvLabelWriteSaveTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey1))
        }
        else{
            binding.tvLabelWriteSaveTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey1))
        }
    }

    private fun changeStatusBarColor() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.skyBlue)
    }

    private fun setUpBackButton() {
        binding.iBtnLabelWriteBack.setOnClickListener {
            navigator.navigateUp()
        }
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
    }
}