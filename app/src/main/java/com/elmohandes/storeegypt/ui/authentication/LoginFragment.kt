package com.elmohandes.storeegypt.ui.authentication

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.FragmentLoginBinding
import com.elmohandes.storeegypt.utils.CustomProgressDialog
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loading: CustomProgressDialog
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.hide()
        loading = CustomProgressDialog(requireActivity())

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            //TODO: Clear the back stack up to the loginFragment
            if (auth.currentUser!!.email == "admin@gmail.com"){
                val navController = findNavController()
                navController.popBackStack(R.id.loginFragment, true)
                navController.navigate(R.id.adminFragment)
            }else{
                val navController = findNavController()
                navController.popBackStack(R.id.loginFragment, true)
                navController.navigate(R.id.homeFragment)
            }

        }

        binding.loginBtnRegister.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.signUpFragment)
        }

        binding.loginBtnLogin.setOnClickListener {
            loginToMyEmail()
            loading.dismissDialog()

        }

        binding.loginTxtHome.setOnClickListener {
            val dialog = CustomProgressDialog(requireActivity())
            dialog.startLoading()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Handler.createAsync(Looper.myLooper()!!).postDelayed({
                    dialog.dismissDialog()
                    findNavController().navigate(R.id.homeFragment)
                },1000)
            }
        }

        return view
    }

    private fun loginToMyEmail() {
        val email = binding.loginInputEmail.text.toString()
        val password = binding.loginInputPassword.text.toString()
        if (email.isEmpty()){
            binding.loginInputEmail.error = "Email is required"
            binding.loginInputEmail.requestFocus()
            return
        }else if (password.isEmpty()){
            binding.loginInputPassword.error = "Password is required"
            binding.loginInputPassword.requestFocus()
            return
        }else{
            loading.startLoading()
            try {
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            //TODO: Clear the back stack up to the loginFragment
                            if (auth.currentUser!!.email == "admin@gmail.com"){
                                val navController = findNavController()
                                navController.popBackStack(R.id.loginFragment, true)
                                navController.navigate(R.id.adminFragment)
                            }else{
                                val navController = findNavController()
                                navController.popBackStack(R.id.loginFragment, true)
                                navController.navigate(R.id.homeFragment)
                            }

                        }else{
                            Toast.makeText(requireContext(),
                                "Email or Password is wrong try again",
                                Toast.LENGTH_SHORT).show()
                        }
                    }.addOnCanceledListener{
                        Toast.makeText(requireContext(),
                            "Login Process is canceled",
                            Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Log.e("login-error",it.message.toString())
                        Toast.makeText(requireContext(),
                            "Network error", Toast.LENGTH_SHORT).show()
                    }
            }catch (e: Exception){
                Toast.makeText(requireContext(), "Network Error",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

}