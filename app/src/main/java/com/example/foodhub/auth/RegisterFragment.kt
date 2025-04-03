package com.example.foodhub.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRegister()
        setupRegister()
        initButtons()
    }

    private fun initButtons(){
        binding.tvNavToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun setupRegister() {
        binding.btnSignUp.setOnClickListener {
            val email = binding.etRegisterEmail.text.toString().trim()
            val password = binding.etRegisterPassword.text.toString()
            val confirmPassword = binding.etRegisterConfirmPassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showToast("Please Fill All Fields")
            } else if (password == confirmPassword) {
                authViewModel.register(email, password)
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }else{
                showToast("Passwords are not the same")
            }
        }
    }

    private fun observeRegister() {
        authViewModel.authState.observe(viewLifecycleOwner) { currentUser ->
            if (currentUser != null) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        authViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            showToast(message)
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