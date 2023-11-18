package com.elmohandes.storeegypt.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.database.InsertAdminDatabase
import com.elmohandes.storeegypt.databinding.FragmentAdminBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin, container, false)
        binding = FragmentAdminBinding.bind(view)

        setupActions()

        return view
    }

    private fun setupActions() {
        val firestore = FirebaseFirestore.getInstance()
        val insertAdminDatabase = InsertAdminDatabase(requireActivity(),firestore)
        binding.adminAddCategory.setOnClickListener {
            insertAdminDatabase.addCollectionsToDatabase()
        }

        binding.adminAddProduct.setOnClickListener {
            insertAdminDatabase.addProductsToDatabase()
        }
    }

}