package com.example.issue_tracker.ui.issue.write

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentIssueWriteBinding
import com.google.android.material.snackbar.Snackbar
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.image.ImagesPlugin
import io.noties.markwon.image.file.FileSchemeHandler


class IssueWriteFragment : Fragment() {
    private var markDownFlag = false
    private lateinit var popupWindow: PopupWindow
    private var beforeChangedText = ""
    private var copyText = ""
    private lateinit var cutButton: Button
    private lateinit var copyButton: Button
    private lateinit var insertPhotoButton: Button
    private lateinit var pasteButton: Button
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
        markOne = Markwon.builder(requireContext())
            .usePlugin(ImagesPlugin.create())
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configure(registry: MarkwonPlugin.Registry) {
                    registry.require(ImagesPlugin::class.java) { imagesPlugin -> imagesPlugin.addSchemeHandler(FileSchemeHandler.create()) }
                }
            }).build()
        displayMarkDownPreview()
        setLongClickPopupWindow(view)

    }

    private fun setLongClickPopupWindow(view: View) {
        binding.etIssueWriteContent.setOnLongClickListener {
            displayPopup(view)
            true
        }
    }

    private fun addCutAction() {
        cutButton.setOnClickListener {
            copyText = binding.etIssueWriteContent.text.toString()
            binding.etIssueWriteContent.setText("")
        }

    }

    private fun addCopyAction() {
        copyButton.setOnClickListener {
            copyText = binding.etIssueWriteContent.text.toString()
        }
    }

    private fun addPasteAction() {
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

    private fun findMenuButton(popupView: View) {
        copyButton = popupView.findViewById(R.id.btn_copy)
        cutButton = popupView.findViewById(R.id.btn_cut)
        insertPhotoButton = popupView.findViewById(R.id.btn_insert_photo)
        pasteButton = popupView.findViewById(R.id.btn_paste)
    }

    private fun addMenuEventListener() {
        addCopyAction()
        addCutAction()
        addPasteAction()
        addInsertPhotoEventListener()
    }

    private fun addInsertPhotoEventListener() {
        insertPhotoButton.setOnClickListener {
            if (isAllPermissionsGranted()) {
                getPhotoFromGallery()
            } else {
                requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
            }
        }
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

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach { permission ->
                when {
                    permission.value -> {
                        Snackbar.make(binding.root, "Permission granted", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    shouldShowRequestPermissionRationale(permission.key) -> {
                        showContextPopupPermission()
                    }
                    else -> {
                        Snackbar.make(binding.root, "Permission denied", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    private fun showContextPopupPermission() {
        AlertDialog.Builder(requireContext()).setTitle("권한이 필요합니다")
            .setMessage("사진을 불러오기 위해 권한설정이 필요합니다")
            .setPositiveButton("설정하러가기") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:${requireContext().packageName}"))
                startActivity(intent)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()

    }

    private fun isAllPermissionsGranted(): Boolean = REQUIRED_PERMISSIONS.all { permission ->
        ContextCompat.checkSelfPermission(
            this.requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getPhotoFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        getPhoto.launch(intent)
    }

    private val getPhoto: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val currentImageUri = it.data?.data
                println(currentImageUri)
                try {
                    currentImageUri?.let {uri->
                        binding.etIssueWriteContent.append(
                            "![alt](file://${getFullPathFromUri(requireContext(), uri)})"
                        )
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


    private fun getFullPathFromUri(ctx: Context, fileUri: Uri): String? {
        var fullPath: String? = null
        val column = "_data"
        var cursor = ctx.contentResolver.query(fileUri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            var documentId = cursor.getString(0)
            if (documentId == null) {
                for (i in 0 until cursor.columnCount) {
                    if (column.equals(cursor.getColumnName(i), ignoreCase = true)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                documentId = documentId.substring(documentId.lastIndexOf(":") + 1)
                cursor.close()
                val projection = arrayOf(column)
                try {
                    cursor = ctx.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, MediaStore.Images.Media._ID + " = ? ", arrayOf(documentId), null)
                    if (cursor != null) {
                        cursor.moveToFirst()
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column))
                    }
                } finally {
                    cursor.close()
                }
            }
        }
        return fullPath
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
}