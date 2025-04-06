package com.example.foodhub.main.components.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodhub.R
import com.example.foodhub.auth.AuthViewModel
import com.example.foodhub.databinding.FragmentProfileBinding
import com.example.foodhub.ui.AuthActivity


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout()
        setupSocialMedia()
        setupNavToFavourites()
        initEmails()

        binding.notificationLayer.setOnClickListener {
            Toast.makeText(requireContext(), "not edited yet", Toast.LENGTH_SHORT).show()
        }

        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun initEmails() {
        profileViewModel.user.observe(viewLifecycleOwner) { user ->
            binding.tvProfileEmail.text = user?.email ?: "Not Found"
            binding.tvProfileUsername.text = user?.displayName ?: "Not Fount"
        }
    }


    private fun setupNavToFavourites() {
        binding.favouritesLayer.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_favouritesFragment)
        }
    }

    private fun setupSocialMedia() {

        val facebookLink = "https://web.facebook.com/mohammed.gomah.98"
        val instagramLink = "https://www.instagram.com/mohammed.gomah/"
        val githubLink = "https://github.com/Mohammed-Gomah"

        binding.ivFacebook.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookLink))
            startActivity(intent)
        }

        binding.ivInstagram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramLink))
            startActivity(intent)
        }

        binding.ivGithub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubLink))
            startActivity(intent)
        }

    }

    private fun logout() {
        binding.logout.setOnClickListener {
            authViewModel.logout()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.refreshUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}