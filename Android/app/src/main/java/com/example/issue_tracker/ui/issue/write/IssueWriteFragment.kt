package com.example.issue_tracker.ui.issue.write

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentIssueWriteBinding
import io.noties.markwon.Markwon
import io.noties.markwon.image.ImagesPlugin


class IssueWriteFragment : Fragment() {
    private var markDownFlag = false
    private var beforeChangedText = ""
    private lateinit var binding: FragmentIssueWriteBinding
    private lateinit var markOne: Markwon
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue_write, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        markOne = Markwon.builder(requireContext()).usePlugin(ImagesPlugin.create()).build()
        displayMarkDownPreview()
        binding.etIssueWriteContent.setOnClickListener {

            displayPopup(view)
        }

    }


    private fun displayPopup(view: View){
        val popupWindow= PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        println("call Here")
        val inflater= requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView= inflater.inflate(R.layout.issue_popup_window, null)
        popupWindow.contentView= popupView
        popupWindow.isTouchable= true
        popupWindow.isFocusable=true
        popupWindow.isOutsideTouchable=true
        popupWindow.showAsDropDown(view)
    }

    private fun displayMarkDownPreview() {
        binding.tvIssuePreviewTitle.setOnClickListener {
            if (!markDownFlag) {
                switchToMarkDownView()
            } else {
                switchToEditTextView()
            }
        }
    }

    private fun switchToMarkDownView() {
        beforeChangedText = binding.etIssueWriteContent.text.toString()
        binding.etIssueWriteContent.isFocusable = false
        markOne.setMarkdown(
            binding.etIssueWriteContent,
            binding.etIssueWriteContent.text.toString()
        )
        markDownFlag = true
    }

    private fun switchToEditTextView() {
        binding.etIssueWriteContent.setText(beforeChangedText)
        binding.etIssueWriteContent.isFocusable = true
        beforeChangedText = ""
        markDownFlag = false
    }
}