package com.example.issue_tracker.ui.myaccount

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentMyAccountBinding
import com.example.issue_tracker.databinding.FragmentMyAccountNeedLoginBinding
import com.example.issue_tracker.domain.model.MyAccount
import com.example.issue_tracker.ui.HomeViewModel
import com.example.issue_tracker.ui.login.LogInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAccountFragment : Fragment() {

    private lateinit var binding: FragmentMyAccountBinding
    private lateinit var needLoginBinding: FragmentMyAccountNeedLoginBinding
    private val viewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false)
        needLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_account_need_login,
            container,
            false
        )
        return if (checkLogin()) {
            binding.root
        } else {
            needLoginBinding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveToLogin()
        loadLoginUser()
        binding.account = makeDummyAccount()
    }

    private fun moveToLogin() {
        needLoginBinding.btnNeedLogin.setOnClickListener {
            val intent = Intent(requireContext(), LogInActivity::class.java)
            startActivity(intent)
        }
    }


    private fun loadLoginUser() {
        viewModel.loginUser.observe(viewLifecycleOwner) {
            binding.account = MyAccount(it.name, it.profileImage, 3,5,7)
        }
    }

    private fun checkLogin(): Boolean {
        return viewModel.checkLogin()
    }

    private fun makeDummyAccount(): MyAccount {
        return MyAccount(
            "hede@gmail.com",
            "https://images.unsplash.com/photo-1655057011043-158c48f3809d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEyfHhqUFI0aGxrQkdBfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            3,
            5,
            7
        )
    }

}