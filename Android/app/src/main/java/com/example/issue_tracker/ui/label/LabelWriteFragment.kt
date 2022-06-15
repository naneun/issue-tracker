package com.example.issue_tracker.ui.label

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentLabelWriteBinding
import kotlin.random.Random


class LabelWriteFragment : Fragment() {

    private lateinit var binding: FragmentLabelWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_label_write, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initColor()
        changeStatusBarColor()
        refreshColor()
    }

    private fun initColor(){
        binding.labelRandomColor=getString(R.string.label_write_base_color).toLong(16).toInt()
    }

    private fun refreshColor(){
        binding.iBtnLabelWriteColorRefresh.setOnClickListener {
            val colorString= setupRandomColor()
            binding.etLabelWriteColor.setText("#${colorString}")
            binding.labelRandomColor= ("FF${colorString}".toLong(16).toInt())
        }
    }

    private fun setupRandomColor():String{
        val redValue= Random.nextInt(256).toString(16)
        val greenValue= Random.nextInt(256).toString(16)
        val blueValue = Random.nextInt(256).toString(16)
        return "${checkHexLength(redValue)}${checkHexLength(greenValue)}${checkHexLength(blueValue)}"
    }

    private fun checkHexLength(RGBValue:String):String{
        return if(RGBValue.length<2){
            "0${RGBValue}"
        } else{
            RGBValue
        }
    }

    private fun changeStatusBarColor() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.skyBlue)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
    }
}