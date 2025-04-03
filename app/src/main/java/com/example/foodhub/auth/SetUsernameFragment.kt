package com.example.foodhub.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodhub.databinding.FragmentSetUsernameBinding
import com.example.foodhub.ui.MainActivity


class SetUsernameFragment : Fragment() {
    private var _binding: FragmentSetUsernameBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSetUsernameBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        observeErrorMessage()
    }

    private fun initButtons() {
        binding.btnSave.setOnClickListener {
            val username = binding.etSetUsername.text.toString().trim()
                .split(" ")
                .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
            if (username.isEmpty()) {
                showToast("Fill username field")
            } else {
                authViewModel.setUsername(username)
                Intent(requireContext(), MainActivity::class.java).also { startActivity(it) }
                requireActivity().finish()
            }
        }
    }

    private fun observeErrorMessage() {
        authViewModel.errorMessage.observe(viewLifecycleOwner) {
            showToast(it)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}