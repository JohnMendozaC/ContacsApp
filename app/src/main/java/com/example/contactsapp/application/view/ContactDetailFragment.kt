package com.example.contactsapp.application.view

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.contactsapp.R
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.emailId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.genderId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.locationId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.nameId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.phoneId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.pictureId
import com.example.contactsapp.application.view.home.ContactsViewHolder.Companion.usernameId
import com.example.contactsapp.application.view.shared.setImageOfContact
import com.example.contactsapp.databinding.ContactDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ContactDetailFragment : Fragment() {

    private var _binding: ContactDetailBinding? = null
    private val binding get() = _binding!!

    private var requestCode = 0

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                when (requestCode) {
                    PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                        val drawable = binding.profileImageView.drawable as BitmapDrawable
                        val bitmap = drawable.bitmap
                        saveImage(bitmap)
                    }
                    PERMISSION_REQUEST_CALL_PHONE -> makePhoneCall()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.without_permission,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataOfContact()

        binding.profileImageView.setOnClickListener {
            showSaveConfirmationDialog()
        }
        binding.textEmail.setOnClickListener {
            sendEmail()
        }
        binding.textPhone.setOnClickListener {
            makePhoneCall()
        }
    }

    private fun setDataOfContact() {
        arguments?.let { data ->
            binding.profileImageView.setImageOfContact(data.getString(pictureId) ?: "", 500, 500)
            binding.textName.text = data.getString(nameId) ?: ""
            binding.textGender.text = data.getString(genderId) ?: ""
            binding.textEmail.text = data.getString(emailId) ?: ""
            binding.textUsername.text = data.getString(usernameId) ?: ""
            binding.textPhone.text = data.getString(phoneId) ?: ""
            binding.textLocation.text = data.getString(locationId) ?: ""
        }
    }

    private fun showSaveConfirmationDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(getString(R.string.save_image))
            .setMessage(R.string.question_save_image)
            .setPositiveButton(R.string.accept) { dialog, _ ->
                dialog.dismiss()
                saveImageToGallery()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        builder.show()
    }

    private fun saveImageToGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val drawable = binding.profileImageView.drawable as BitmapDrawable
            val bitmap = drawable.bitmap
            saveImage(bitmap)
        } else {
            requestCode = PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE
            requestPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun saveImage(bitmap: Bitmap) {
        val filename = "contact_image_${System.currentTimeMillis()}.jpg"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = requireActivity().contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            imageUri?.let {
                val outputStream: OutputStream? = resolver.openOutputStream(it)
                outputStream?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    stream.flush()
                }
                Toast.makeText(
                    requireContext(),
                    R.string.image_saved_correct,
                    Toast.LENGTH_SHORT
                ).show()
            } ?: run {
                Toast.makeText(
                    requireContext(),
                    R.string.error_save_image,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            val imagesDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val imageFile = File(imagesDirectory, filename)
            val outputStream = FileOutputStream(imageFile)
            outputStream.use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
            }
            Toast.makeText(
                requireContext(),
                R.string.image_saved_correct,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun sendEmail() {
        val email = binding.textEmail.text.toString().trim()
        val subject = "Contact Details"
        val body = "Hi, I'm sharing the contact details with you."

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)

            val drawable = binding.profileImageView.drawable as BitmapDrawable
            val bitmap = drawable.bitmap
            val bitmapPath = MediaStore.Images.Media.insertImage(
                requireContext().contentResolver,
                bitmap,
                "ContactImage",
                null
            )
            val imageUri = Uri.parse(bitmapPath)
            putExtra(Intent.EXTRA_STREAM, imageUri)
        }

        val packageManager = requireContext().packageManager
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        } else {
            Toast.makeText(
                requireContext(),
                R.string.not_found_app_email,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun makePhoneCall() {
        val phoneNumber = binding.textPhone.text.toString().trim()
        val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(callIntent)
        } else {
            requestCode = PERMISSION_REQUEST_CALL_PHONE
            requestPermission.launch(Manifest.permission.CALL_PHONE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1
        private const val PERMISSION_REQUEST_CALL_PHONE = 2
    }
}