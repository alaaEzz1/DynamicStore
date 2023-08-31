package com.elmohandes.storeegypt.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.loginBtnRegister.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.signUpFragment)
        }

        binding.loginBtnLogin.setOnClickListener {
            //TODO: Clear the back stack up to the loginFragment
            val navController = findNavController()
            navController.popBackStack(R.id.loginFragment, true)
            navController.navigate(R.id.homeFragment)

        }

        return view
    }

}