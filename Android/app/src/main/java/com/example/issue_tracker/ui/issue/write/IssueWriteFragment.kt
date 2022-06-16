package com.example.issue_tracker.ui.issue.write

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
    private lateinit var popupWindow: PopupWindow
    private var beforeChangedText = ""
    private var copyText=""
    private lateinit var cutButton:Button
    private lateinit var copyButton:Button
    private lateinit var insertPhotoButton:Button
    private lateinit var pasteButton:Button
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
        setLongClickPopupWindow(view)

    }

    private fun setLongClickPopupWindow(view: View) {
        binding.etIssueWriteContent.setOnLongClickListener {
            displayPopup(view)
            true
        }
    }

    private fun addCutAction(){
        cutButton.setOnClickListener {
            copyText= binding.etIssueWriteContent.text.toString()
            binding.etIssueWriteContent.setText("")
        }

    }

    private fun addCopyAction(){
        copyButton.setOnClickListener {
            copyText= binding.etIssueWriteContent.text.toString()
        }
    }

    private fun addPasteAction(){
        pasteButton.setOnClickListener {
            binding.etIssueWriteContent.append(copyText)
        }
    }

    private fun displayPopup(view: View) {
        popupWindow = PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.issue_popup_window, null)
        findMenuButton(popupView)
        addMenuEventListener()
        popupWindow.contentView = popupView
        popupWindow.isTouchable = true
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -200);
    }

    private fun findMenuButton(popupView:View){
        copyButton= popupView.findViewById(R.id.btn_copy)
        cutButton= popupView.findViewById(R.id.btn_cut)
        insertPhotoButton= popupView.findViewById(R.id.btn_insert_photo)
        pasteButton = popupView.findViewById(R.id.btn_paste)
    }

    private fun addMenuEventListener(){
        addCopyAction()
        addCutAction()
        addPasteAction()
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