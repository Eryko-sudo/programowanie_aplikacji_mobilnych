package com.example.lego_collector

import android.app.Activity
import android.app.Dialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment : Fragment() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CAMERA_PERMISSION = 2
    private val images = ArrayList<Uri>()
    private lateinit var imageAdapter: ImageAdapter
    private var photoURI: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cameraButton = view.findViewById<Button>(R.id.cameraButton)
        cameraButton.setOnClickListener {
            Log.d("CameraFragment", "Camera button clicked")
            if (checkPermissions()) {
                Log.d("CameraFragment", "Permissions already granted, dispatching intent")
                dispatchTakePictureIntent()
            } else {
                Log.d("CameraFragment", "Requesting camera permission")
                requestPermissions()
            }
        }

        imageAdapter = ImageAdapter(requireContext(), images)
        view.findViewById<GridView>(R.id.gridView).adapter = imageAdapter

        loadImagesFromGallery()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            photoURI?.let {
                images.add(it)
                imageAdapter.notifyDataSetChanged()
                view?.findViewById<GridView>(R.id.gridView)?.adapter = imageAdapter
                Log.d("CameraFragment", "Image captured and added to the gallery")
            } ?: Log.e("CameraFragment", "Failed to capture image: photoURI is null")
        } else {
            Log.e("CameraFragment", "Failed to capture image")
        }
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            Log.e("CameraFragment", "Error occurred while creating the file", ex)
            null
        }
        photoFile?.also {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, it.name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/LegoCollector")
            }
            photoURI = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            Log.d("CameraFragment", "Camera intent dispatched")
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private inner class ImageAdapter(context: Context, private val images: ArrayList<Uri>) : ArrayAdapter<Uri>(context, 0, images) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val imageView = convertView as ImageView? ?: ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(200, 200) // Set your desired size
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            imageView.setImageURI(images[position])

            // Add click listener to preview image
            imageView.setOnClickListener {
                showImagePreview(images[position], position)
            }

            return imageView
        }
    }

    private fun showImagePreview(imageUri: Uri, position: Int) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_image_preview)
        val imageView = dialog.findViewById<ImageView>(R.id.imagePreview)
        imageView.setImageURI(imageUri)
        dialog.findViewById<Button>(R.id.deleteButton).setOnClickListener {
            deleteImage(imageUri, position)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun deleteImage(imageUri: Uri, position: Int) {
        requireContext().contentResolver.delete(imageUri, null, null)
        images.removeAt(position)
        imageAdapter.notifyDataSetChanged()
        Log.d("CameraFragment", "Image deleted")
    }

    private fun loadImagesFromGallery() {
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val cursor = requireActivity().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            "${MediaStore.Images.Media.RELATIVE_PATH} LIKE ?",
            arrayOf("${Environment.DIRECTORY_PICTURES}/LegoCollector%"),
            null
        )
        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                images.add(uri)
            }
        }
        imageAdapter.notifyDataSetChanged()
    }

    private fun checkPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
        Log.d("CameraFragment", "Camera permission: $cameraPermission")
        return cameraPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        Log.d("CameraFragment", "Requesting camera permission")
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("CameraFragment", "Camera permission granted, dispatching intent")
                    dispatchTakePictureIntent()
                } else {
                    Log.e("CameraFragment", "Camera permission not granted")
                }
            }
        }
    }
}
