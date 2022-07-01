package com.example.issue_tracker.ui.issue.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentIssueDetailMoreDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssueDetailMoreDialogFragment : DialogFragment() {

  //  private val viewModel:IssueDetailViewModel by activityViewModels()
    private lateinit var binding: FragmentIssueDetailMoreDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue_detail_more_dialog, container, false)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.issueDetail.observe(viewLifecycleOwner){
//            binding.tvIssueDetailMoreLabel.text = it.labelName
//            binding.tvIssueDetailMoreAssignee.text= it.assigneeName
//            binding.tvIssueDetailMoreMilestone.text = it.mileStoneName
//        }
        binding.iBtnIssueDetailMoreClose.setOnClickListener {
            dismiss()
        }
    }
}