package com.elmohandes.storeegypt.ui.authentication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.FragmentSignUpBinding
import com.elmohandes.storeegypt.models.UserModel
import com.elmohandes.storeegypt.utils.CustomProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var loading: CustomProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        binding = FragmentSignUpBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        loading = CustomProgressDialog(requireActivity())

        binding.signupBtnRegister.setOnClickListener {
            createAccount()
        }

        return view
    }

    private fun createAccount() {
        val email = binding.signupInputEmail.text.toString()
        val password = binding.signupInputPassword.text.toString()
        val fullName = binding.signupInputFullName.text.toString()
        val address = binding.signupInputAddress.text.toString()
        val age = binding.signupInputAge.text.toString()
        val phone = binding.signupInputPhoneNumber.text.toString()

        when {
            fullName.isEmpty() -> {
                binding.signupInputFullName.error = "Full Name is required"
                binding.signupInputFullName.requestFocus()
                return
            }

            email.isEmpty() -> {
                binding.signupInputEmail.error = "Email is required"
                binding.signupInputEmail.requestFocus()
                return
            }

            age.isEmpty() -> {
                binding.signupInputAge.error = "Age is required"
                binding.signupInputAge.requestFocus()
                return
            }

            address.isEmpty() -> {
                binding.signupInputAddress.error = "Address is required"
                binding.signupInputAddress.requestFocus()
                return
            }

            phone.isEmpty() -> {
                binding.signupInputPhoneNumber.error = "Phone is required"
                binding.signupInputPhoneNumber.requestFocus()
                return
            }

            password.isEmpty() -> {
                binding.signupInputPassword.error = "Password is required"
                binding.signupInputPassword.requestFocus()
                return
            }

            else -> {
                loading.startLoading()
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            val id = auth.currentUser!!.uid
                            val user = UserModel(
                                id, fullName, email, age.toInt(),
                                address, phone,"", password
                            )
                            storeInDatabase(user,id)
                            loading.dismissDialog()
                        }
                    }
            }

        }
    }

    private fun storeInDatabase(user: UserModel, id: String) {
        firestore.collection("users").document(id).set(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "Account is created",
                        Toast.LENGTH_SHORT).show()
                    val controller = findNavController()
                    controller.popBackStack(R.id.loginFragment,true)
                    controller.navigate(R.id.loginFragment)
                }
            }.addOnCanceledListener {
                Log.e("error-user-firestore","canceled")
                Toast.makeText(requireContext(), "Account is Canceled",
                    Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Log.e("error-user-firestore","error ${it.message.toString()}")
                Toast.makeText(requireContext()
                    , "there is an error ${it.message.toString()}",
                    Toast.LENGTH_SHORT).show()
            }

    }
}