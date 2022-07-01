package com.example.issue_tracker.ui.milestone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentMileStoneWriteBinding
import com.example.issue_tracker.ui.label.LabelWriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class MileStoneWriteFragment : Fragment() {
    private lateinit var binding: FragmentMileStoneWriteBinding
    private lateinit var navigator: NavController
    private var completeDayFlag: Boolean = true
    private var titleFlag: Boolean = false
    private val viewModel: MileStoneWriteViewModel by viewModels()

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
        binding.etMilestoneWriteCompleteDay.doAfterTextChanged { inputDate ->
            try {
                val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
                dateFormatter.isLenient = true
                dateFormatter.parse(inputDate.toString())
                completeDayFlag = true
            } catch (e: Exception) {
                binding.etMilestoneWriteCompleteDay.error = getString(R.string.message_invalid_input_error)
                completeDayFlag = false
            } finally {
                setCompleteDayLabelBackground()
            }

        }
    }

    private fun validateMileStoneTitle() {
        binding.etMilestoneWriteTitle.doAfterTextChanged { inputTitle ->
            val title = inputTitle.toString()
            titleFlag = title.isNotEmpty()
            setSaveTitleColor()
        }
    }

    private fun setSaveMilestoneBtn() {
        binding.tvMilestoneWriteSaveTitle.setOnClickListener {
            if (titleFlag) {
                viewModel.loadDescription(binding.etMilestoneWriteContent.text.toString())
                viewModel.loadDueDate(binding.etMilestoneWriteCompleteDay.text.toString())
                viewModel.loadTitle(binding.etMilestoneWriteTitle.text.toString())
                viewModel.registerMileStone()
                navigator.navigate(R.id.action_mileStoneWriteFragment_to_navigation_milestone)
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
            binding.etMilestoneWriteTitle.error = getString(R.string.message_need_input_message)
            binding.tvMilestoneWriteSaveTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey1))
        }
    }

    private fun setCompleteDayLabelBackground() {
        if (completeDayFlag) {
            binding.tvMilestoneWriteContentCompleteDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        } else {
            binding.tvMilestoneWriteContentCompleteDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
    }

    private fun setUpBackButton() {
        binding.iBtnMilestoneWriteBack.setOnClickListener {
            navigator.navigateUp()
        }
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }
}