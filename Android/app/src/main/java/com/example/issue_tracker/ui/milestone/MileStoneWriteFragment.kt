package com.example.issue_tracker.ui.milestone

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
import com.example.issue_tracker.databinding.FragmentMileStoneWriteBinding
import java.text.SimpleDateFormat

class MileStoneWriteFragment : Fragment() {
    private lateinit var binding: FragmentMileStoneWriteBinding
    private lateinit var navigator: NavController
    private var completeDayFlag: Boolean = true
    private var titleFlag: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mile_stone_write, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeStatusBarColor()
        setUpBackButton()
        setSaveMilestoneBtn()
        validateCompleteDayFormat()
        validateMileStoneTitle()
        navigator = Navigation.findNavController(view)
    }

    private fun changeStatusBarColor() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.skyBlue)
    }

    private fun validateCompleteDayFormat() {
        binding.etMilestoneWriteCompleteDay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(inputDate: Editable?) {
                try {
                    val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
                    dateFormatter.isLenient = true
                    dateFormatter.parse(inputDate.toString())
                    completeDayFlag = true
                } catch (e: Exception) {
                    binding.etMilestoneWriteCompleteDay.error =
                        getString(R.string.milestone_write_compleday_error)
                    completeDayFlag = false
                } finally {
                    setCompleteDayLabelBackground()
                }
            }
        }
        )
    }

    private fun validateMileStoneTitle() {
        binding.etMilestoneWriteTitle.addTextChangedListener(object : TextWatcher {
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

    private fun setSaveMilestoneBtn() {
        binding.tvMilestoneWriteSaveTitle.setOnClickListener {
            if (titleFlag) {
                //To DO: save MileStone
            }
        }
    }

    private fun setSaveTitleColor() {
        if (titleFlag) {
            binding.tvMilestoneWriteSaveTitle.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        } else {
            binding.etMilestoneWriteTitle.error = "필수입력값입니다"
            binding.tvMilestoneWriteSaveTitle.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.grey1
                )
            )
        }
    }

    private fun setCompleteDayLabelBackground() {
        if (completeDayFlag) {
            binding.tvMilestoneWriteContentCompleteDay.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        } else {
            binding.tvMilestoneWriteContentCompleteDay.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        }
    }

    private fun setUpBackButton() {
        binding.iBtnMilestoneWriteBack.setOnClickListener {
            navigator.navigateUp()
        }
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
    }
}