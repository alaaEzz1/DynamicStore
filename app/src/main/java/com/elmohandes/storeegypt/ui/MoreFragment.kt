package com.elmohandes.storeegypt.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.FragmentMoreBinding
import com.elmohandes.storeegypt.models.UserModel
import com.elmohandes.storeegypt.utils.CustomProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MoreFragment : Fragment() {

    //let's add Image to user and edit his profile

    private lateinit var binding: FragmentMoreBinding
    private lateinit var firestore: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        binding = FragmentMoreBinding.bind(view)

        firestore = FirebaseFirestore.getInstance()

        getUserData()

        setupActions()

        return view
    }

    private fun logoutFromDatabase() {
        val dialog = CustomProgressDialog(requireActivity())
        val auth = FirebaseAuth.getInstance()
        dialog.startLoading()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Handler.createAsync(Looper.myLooper()!!).postDelayed({
                dialog.dismissDialog()
            }, 1000)
        }else{
            dialog.dismissDialog()
        }
        auth.signOut()
        Navigation.findNavController(requireView()).popBackStack(R.id.moreFragment, true)
         Navigation.findNavController(requireView()).navigate(R.id.loginFragment)
    }

    private fun setupActions() {
        binding.moreProfileSettings.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.editProfileFragment)
        }

        binding.moreProfileImg.setOnClickListener {
            logoutFromDatabase()
        }
    }

    //get image to know profile and other data if needed
    private fun getUserData() {
        firestore.collection("users").get()
            .addOnSuccessListener {
                it.forEach {document->
                    if (document.get("id").toString() == FirebaseAuth.getInstance().currentUser!!.uid){
                        Glide.with(requireActivity()).load(document.get("userImage").toString())
                            .placeholder(R.drawable.store_egypt_logo).into(binding.moreProfileImg)
                    }
                }
            }
    }
}