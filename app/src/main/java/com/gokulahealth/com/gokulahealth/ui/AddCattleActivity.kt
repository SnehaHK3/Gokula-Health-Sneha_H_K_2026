package com.gokulahealth.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gokulahealth.R
import com.gokulahealth.data.AppDatabase
import com.gokulahealth.data.Cattle
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddCattleActivity : AppCompatActivity() {

    private var selectedPhotoUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedPhotoUri = result.data?.data
            val imgCowPhoto = findViewById<ImageView>(R.id.imgCowPhoto)
            Glide.with(this).load(selectedPhotoUri).into(imgCowPhoto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cattle)

        val etEarTag     = findViewById<TextInputEditText>(R.id.etEarTag)
        val etName       = findViewById<TextInputEditText>(R.id.etCowName)
        val etBreed      = findViewById<TextInputEditText>(R.id.etBreed)
        val etDob        = findViewById<TextInputEditText>(R.id.etDob)
        val btnPickPhoto = findViewById<Button>(R.id.btnPickPhoto)

        // Pick photo from gallery
        btnPickPhoto.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            pickImageLauncher.launch(intent)
        }

        findViewById<Button>(R.id.btnSaveCattle).setOnClickListener {
            val earTag = etEarTag.text.toString().trim()
            val name   = etName.text.toString().trim()
            val breed  = etBreed.text.toString().trim()
            val dob    = etDob.text.toString().trim()

            if (earTag.isEmpty() || name.isEmpty()) {
                Toast.makeText(this,
                    "Ear Tag and Name are required!",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val today = SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault()).format(Date())

            val cattle = Cattle(
                earTagId       = earTag,
                name           = name,
                breed          = breed,
                dateOfBirth    = dob,
                photoPath      = selectedPhotoUri?.toString() ?: "",
                registeredDate = today
            )

            val db = AppDatabase.getDatabase(this)
            lifecycleScope.launch {
                db.cattleDao().insertCattle(cattle)
                runOnUiThread {
                    Toast.makeText(
                        this@AddCattleActivity,
                        "✅ $name registered!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }
}