package com.elmohandes.storeegypt.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.elmohandes.storeegypt.R
import com.elmohandes.storeegypt.databinding.FragmentEditProfileBinding
import com.elmohandes.storeegypt.utils.CustomProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var firestore: FirebaseFirestore
    private var password: String =""
    private var userId = ""
    private var fullName = ""
    private var downloadUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        binding = FragmentEditProfileBinding.bind(view)

        firestore = FirebaseFirestore.getInstance()

        retrieveDataFromDatabase()

        binding.editProfileUpdateData.setOnClickListener {
            updateData()
        }

        binding.editProfileImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            launcher.launch(intent)
        }

        return view
    }

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val dialog = CustomProgressDialog(requireActivity())
            val data = it.data
            // do whatever with the data in the callback
            if (data != null){
                dialog.startLoading()
                val uri = data.data
                //binding.editProfileImage.setImageURI(uri)
                uploadImageToFirebase(uri,dialog)
            }
        }
    }

    private fun uploadImageToFirebase(uri: Uri?, dialog: CustomProgressDialog) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val storageReference = Firebase.storage.reference
        val imageReference = storageReference.child("users").child(uid).child("$fullName.jpg")
        val uploadTask = imageReference.putFile(uri!!)
        uploadTask.continueWithTask {
            if (!it.isSuccessful){
                it.exception.let {ex ->
                    throw ex!!
                }
            }
            imageReference.downloadUrl
        }.addOnCompleteListener { task ->
            if(task.isSuccessful){
                downloadUrl = task.result.toString()
                Log.d("url",downloadUrl)
                dialog.dismissDialog()

            }else{
                dialog.dismissDialog()
                Log.e("upload-error",task.exception!!.message.toString())
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "there is a problem", Toast.LENGTH_SHORT).show()
            dialog.dismissDialog()
        }
    }

    private fun updateData() {
        val dialog = CustomProgressDialog(requireActivity())
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        Log.d("user-id",userId)
        firestore.collection("users").get().addOnSuccessListener { querySnapshot ->
            dialog.startLoading()
            querySnapshot.forEach {
                val id = it.data.getValue("id").toString()
                if (id == FirebaseAuth.getInstance().uid){
                    val name = binding.editProfileFullName.text.toString()
                    val email = binding.editProfileEmail.text.toString()
                    val age = binding.editProfileAge.text.toString().toInt()
                    val address = binding.editProfileAddress.text.toString()
                    val mobilePhone = binding.editProfileMobilePhone.text.toString()
                    val users = hashMapOf<String,Any>(
                        "id" to uid,
                        "fullName" to name,
                        "email" to email,
                        "age" to age,
                        "address" to address,
                        "mobilePhone" to mobilePhone,
                        "userImage" to downloadUrl,
                        "password" to password
                    )
                    firestore.collection("users").document(uid).update(users).addOnSuccessListener {
                        dialog.dismissDialog()
                        Toast.makeText(requireContext(), "Data updated Successfully", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        dialog.dismissDialog()
                        Toast.makeText(requireContext(), "Error in Update", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun retrieveDataFromDatabase() {
        firestore.collection("users").get().addOnSuccessListener {querySnapshot ->
            querySnapshot.forEach {
                userId = it.data.getValue("id").toString()
                fullName = it.data.getValue("fullName").toString()
                val email = it.data.getValue("email").toString()
                val age = it.data.getValue("age").toString()
                val address = it.data.getValue("address").toString()
                val mobilePhone = it.data.getValue("mobilePhone").toString()
                var userImage = ""
                if (it.data.getValue("userImage") != null){
                    userImage = it.data.getValue("userImage").toString()
                    Glide.with(requireActivity()).load(userImage).placeholder(R.drawable.store_egypt_logo)
                        .into(binding.editProfileImage)
                }
                password = it.data.getValue("password").toString()
                if (userId == FirebaseAuth.getInstance().currentUser!!.uid){
                    putDataInUI(fullName,email,age,address,mobilePhone)
                    Log.d("user-id","success")
                }
            }
        }.addOnFailureListener {
            Log.e("problem","Error found: ${it.message.toString()}")
        }
    }

    private fun putDataInUI(
        fullName: String,
        email: String,
        age: String,
        address: String,
        mobilePhone: String,
    ) {
        binding.editProfileFullName.setText(fullName)
        binding.editProfileEmail.setText(email)
        binding.editProfileMobilePhone.setText(mobilePhone)
        binding.editProfileAge.setText("$age")
        binding.editProfileAddress.setText(address)
    }
}