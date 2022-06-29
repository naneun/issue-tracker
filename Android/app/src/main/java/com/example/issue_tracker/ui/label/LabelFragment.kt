package com.example.issue_tracker.ui.label

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentLabelBinding
import com.example.issue_tracker.domain.model.Label
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class LabelFragment : Fragment() {

    private lateinit var binding:FragmentLabelBinding
    private lateinit var adapter: LabelAdapter
    private lateinit var navigator: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_label, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= LabelAdapter()
        navigator= Navigation.findNavController(view)
        moveToMakeLabel()
        binding.rvLabel.adapter= adapter
        adapter.submitList(makeDummyLabel())

    }

    private fun makeDummyLabel(): MutableList<Label> {
        val labels= mutableListOf<Label>()
        for(i in 0 .. 10){
            labels.add(Label("제목", "내용입니다", randomHexColor()))
        }
        return labels
    }

    private fun randomHexColor(): String {
        val redValue= Random.nextInt(256).toString(16)
        val greenValue= Random.nextInt(256).toString(16)
        val blueValue = Random.nextInt(256).toString(16)
        return "FF${checkHexLength(redValue)}${checkHexLength(greenValue)}${checkHexLength(blueValue)}"
    }

    private fun checkHexLength(RGBValue:String):String{
        return if(RGBValue.length<2){
            "0${RGBValue}"
        } else{
            RGBValue
        }
    }

    private fun moveToMakeLabel(){
        binding.iBtnLabelAdd.setOnClickListener {
            navigator.navigate(R.id.action_navigation_label_to_labelWriteFragment)
        }
    }
}