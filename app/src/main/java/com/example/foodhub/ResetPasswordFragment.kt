package com.example.foodhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodhub.auth.AuthViewModel
import com.example.foodhub.databinding.FragmentResetPasswordBinding


class ResetPasswordFragment : Fragment() {
    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeResetPassword()
        setupResetPassword()
    }


    private fun setupResetPassword() {
        binding.btnSend.setOnClickListener {
            val email = binding.etResetPasswordByEmail.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Put Your E-mail", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.resetPassword(email)
            }
        }
    }

    private fun observeResetPassword() {
        authViewModel.resetPasswordState.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
            }
        }

        authViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}