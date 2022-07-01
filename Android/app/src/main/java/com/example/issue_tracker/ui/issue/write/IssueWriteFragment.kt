package com.example.issue_tracker.ui.issue.write

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentIssueWriteBinding
import com.example.issue_tracker.domain.model.SpinnerType
import com.example.issue_tracker.ui.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.image.ImagesPlugin
import io.noties.markwon.image.file.FileSchemeHandler
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@AndroidEntryPoint
class IssueWriteFragment : Fragment() {
    private var markDownFlag = false
    private val sharedViewModel: HomeViewModel by activityViewModels()
    private val viewModel: IssueWriteViewModel by viewModels()
    private lateinit var popupWindow: PopupWindow
    private var beforeChangedText = ""
    private var copyText = ""
    private lateinit var cutButton: Button
    private lateinit var copyButton: Button
    private lateinit var insertPhotoButton: Button
    private lateinit var pasteButton: Button
    private lateinit var navigator: NavController
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
        navigator = Navigation.findNavController(view)
        markOne = Markwon.builder(requireContext())
            .usePlugin(ImagesPlugin.create())
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configure(registry: MarkwonPlugin.Registry) {
                    registry.require(ImagesPlugin::class.java) { imagesPlugin ->
                        imagesPlugin.addSchemeHandler(
                            FileSchemeHandler.create()
                        )
                    }
                }
            }).build()
        displayMarkDownPreview()
        setLongClickPopupWindow(view)
        setUpBackButton()
        checkInputContent()
        checkInputTitle()
        setSaveBtn()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { loadLabelList() }
                launch { loadMileStoneList() }
                launch { loadUserList() }
            }
        }
    }

    private fun setSaveIssueBtn(){
        binding.tvIssueWriteSaveTitle.isEnabled = binding.etIssueWriteContent.text.isNotEmpty() && binding.etIssueWriteTitleValue.text.isNotEmpty()
    }

    private fun checkInputTitle(){
        binding.etIssueWriteTitleValue.doAfterTextChanged {
            setSaveIssueBtn()
        }
    }

    private fun checkInputContent(){
        binding.etIssueWriteContent.doAfterTextChanged {
            setSaveIssueBtn()
        }
    }

    private fun setSaveBtn(){
        binding.tvIssueWriteSaveTitle.setOnClickListener {
            if(it.isEnabled) {
                val title = binding.etIssueWriteTitleValue.text.toString()
                val content = binding.etIssueWriteContent.text.toString()
                viewModel.registerIssue(title, content)
            }
        }
    }

    private suspend fun loadUserList() {
        val userList = mutableListOf(getString(R.string.spinner_default))
        sharedViewModel.userList.collect {
            it.forEach { user ->
                userList.add(user.name)
            }
            setSpinner(userList, SpinnerType.WRITER)
        }
    }

    private suspend fun loadLabelList() {
        val labelList = mutableListOf(getString(R.string.spinner_default))
        sharedViewModel.labelList.collect {
            it.forEach { label ->
                labelList.add(label.title)
            }
            setSpinner(labelList, SpinnerType.LABEL)
        }
    }

    private suspend fun loadMileStoneList() {
        val mileStoneList = mutableListOf(getString(R.string.spinner_default))
        sharedViewModel.mileStoneList.collect {
            it.forEach { mileStone ->
                mileStoneList.add(mileStone.title)
            }
            setSpinner(mileStoneList, SpinnerType.MILESTONE)
        }
    }

    private fun setSpinner(list: List<String>, type: SpinnerType) {
        val spinner = when (type) {
            SpinnerType.LABEL -> binding.spinnerIssueWriteLabel
            SpinnerType.MILESTONE -> binding.spinnerIssueWriteMilestone
            else -> binding.spinnerIssueWriteAssignee
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner_menu, list)
        spinner.adapter = adapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                when (position) {
                    0 -> (view as TextView).setTextColor(Color.GRAY)
                    else -> (view as TextView).setTextColor(Color.BLACK)
                }
                when(type){
                    SpinnerType.LABEL-> {
                        val labelId= sharedViewModel.getLabelID(binding.spinnerIssueWriteLabel.adapter.getItem(position) as String)
                        viewModel.selectLabel(labelId)
                    }
                    SpinnerType.MILESTONE->{
                        val mileStoneId= sharedViewModel.getMileStoneID(binding.spinnerIssueWriteMilestone.adapter.getItem(position) as String)
                        viewModel.selectMileStone(mileStoneId)
                    }
                    SpinnerType.WRITER->{
                        val writerId= sharedViewModel.getWriterID(binding.spinnerIssueWriteAssignee.adapter.getItem(position) as String)
                        viewModel.selectWriter(writerId)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setUpBackButton() {
        binding.iBtnIssueWriteBack.setOnClickListener {
            navigator.navigateUp()
        }
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
            binding.etIssueWriteContent.setText(getString(R.string.empty_inout))
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
        popupWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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
                        Snackbar.make(
                            binding.root,
                            getString(R.string.message_permission_granted),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    shouldShowRequestPermissionRationale(permission.key) -> {
                        showContextPopupPermission()
                    }
                    else -> {
                        Snackbar.make(
                            binding.root,
                            getString(R.string.message_permission_denied),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    private fun showContextPopupPermission() {
        AlertDialog.Builder(requireContext()).setTitle(getString(R.string.message_permission_need))
            .setMessage(getString(R.string.message_permission_need_reason))
            .setPositiveButton(getString(R.string.message_move_to_setting)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:${requireContext().packageName}"))
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.message_cancel)) { _, _ -> }
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
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == RESULT_OK && it.data != null) {
                val currentImageUri = it.data?.data

                try {
                    currentImageUri?.let { uri ->
                        val image = File("${getFullPathFromUri(requireContext(), uri)}")
                        viewModel.loadImage(image)
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                viewModel.imageUrl.collect{imageurl->
                                    if(imageurl.isNotEmpty()) {
                                        binding.etIssueWriteContent.append("![alt]($imageurl)")
                                    }
                                }
                            }
                        }

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
                    cursor = ctx.contentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media._ID + " = ? ",
                        arrayOf(documentId),
                        null
                    )
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