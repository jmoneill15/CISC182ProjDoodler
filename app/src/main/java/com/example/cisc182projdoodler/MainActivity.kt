package com.example.cisc182projdoodler

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
class MainActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var doodleView: DoodleView
    private lateinit var uploadedImageView: ImageView
    private lateinit var removeImageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doodleView = findViewById(R.id.doodleArea)
        uploadedImageView = findViewById(R.id.uploadedImageView)
        removeImageButton = findViewById(R.id.removeImageButton)

        // Upload button listener
        findViewById<Button>(R.id.uploadButton).setOnClickListener {
            openImagePicker()
        }

        // Remove image button (X) listener
        removeImageButton.setOnClickListener {
            removeImage()
        }

        // Brush size, opacity, and color buttons functionality
        findViewById<SeekBar>(R.id.brushSizeSeekBar).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                doodleView.setBrushSize(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        findViewById<SeekBar>(R.id.opacitySeekBar).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                doodleView.setOpacity(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        findViewById<Button>(R.id.colorButton).setOnClickListener {
            showColorPickerDialog()
        }

        findViewById<Button>(R.id.undoButton).setOnClickListener {
            doodleView.undo()
        }

        findViewById<Button>(R.id.redoButton).setOnClickListener {
            doodleView.redo()
        }

        findViewById<Button>(R.id.clearButton).setOnClickListener {
            doodleView.clearCanvas()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                val inputStream = contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                setImageAsCanvas(bitmap)
            }
        }
    }

    private fun setImageAsCanvas(bitmap: Bitmap) {
        // Display the uploaded image in the ImageView
        uploadedImageView.setImageBitmap(bitmap)
        uploadedImageView.visibility = View.VISIBLE
        removeImageButton.visibility = View.VISIBLE // Show the X button
        doodleView.visibility = View.GONE // Hide the doodle area
    }

    private fun removeImage() {
        // Hide the uploaded image and "X" button, show the doodle area again
        uploadedImageView.visibility = View.GONE
        removeImageButton.visibility = View.GONE
        doodleView.visibility = View.VISIBLE

        doodleView.clearCanvas() // Clear any drawings on the canvas
    }

    private fun showColorPickerDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.color_picker, null)
        val redSeekBar = dialogView.findViewById<SeekBar>(R.id.redSeekBar)
        val greenSeekBar = dialogView.findViewById<SeekBar>(R.id.greenSeekBar)
        val blueSeekBar = dialogView.findViewById<SeekBar>(R.id.blueSeekBar)
        val colorPreview = dialogView.findViewById<View>(R.id.selectedColorPreview)

        var selectedColor = Color.BLACK

        val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val red = redSeekBar.progress
                val green = greenSeekBar.progress
                val blue = blueSeekBar.progress
                selectedColor = Color.rgb(red, green, blue)
                colorPreview.setBackgroundColor(selectedColor)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }

        //Changes Color based on each SeekBar
        redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Pick a Color").setView(dialogView).setPositiveButton("OK") { _, _ ->
                doodleView.setColor(selectedColor)
            }
            .setNegativeButton("Cancel", null)
            .create()



        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }
}

//Version without Background Image
/*
class MainActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var doodleView: DoodleView
    private lateinit var uploadedImageView: ImageView
    private lateinit var removeImageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doodleView = findViewById(R.id.doodleArea)
        uploadedImageView = findViewById(R.id.uploadedImageView)
        removeImageButton = findViewById(R.id.removeImageButton)

        // Upload button listener
        findViewById<Button>(R.id.uploadButton).setOnClickListener {
            openImagePicker()
        }

        // Remove image button listener
        removeImageButton.setOnClickListener {
            removeImage()
        }

        // Brush size, opacity, and color buttons functionality
        findViewById<SeekBar>(R.id.brushSizeSeekBar).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                doodleView.setBrushSize(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        findViewById<SeekBar>(R.id.opacitySeekBar).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                doodleView.setOpacity(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        findViewById<Button>(R.id.colorButton).setOnClickListener {
            showColorPickerDialog()
        }

        findViewById<Button>(R.id.undoButton).setOnClickListener {
            doodleView.undo()
        }

        findViewById<Button>(R.id.redoButton).setOnClickListener {
            doodleView.redo()
        }

        findViewById<Button>(R.id.clearButton).setOnClickListener {
            doodleView.clearCanvas()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                val inputStream = contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                setImageAsCanvas(bitmap)
            }
        }
    }

    private fun setImageAsCanvas(bitmap: Bitmap) {
        // Display the uploaded image in the ImageView
        uploadedImageView.setImageBitmap(bitmap)
        uploadedImageView.visibility = View.VISIBLE
        removeImageButton.visibility = View.VISIBLE
        doodleView.visibility = View.GONE

        // Allow drawing on top of the uploaded image
        doodleView.setBackgroundImage(bitmap)
        doodleView.visibility = View.VISIBLE
    }

    private fun removeImage() {
        // Switch back to the plain canvas
        uploadedImageView.visibility = View.GONE
        removeImageButton.visibility = View.GONE
        doodleView.visibility = View.VISIBLE
        doodleView.clearCanvas()
    }

    private fun showColorPickerDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.color_picker, null)
        val redSeekBar = dialogView.findViewById<SeekBar>(R.id.redSeekBar)
        val greenSeekBar = dialogView.findViewById<SeekBar>(R.id.greenSeekBar)
        val blueSeekBar = dialogView.findViewById<SeekBar>(R.id.blueSeekBar)
        val colorPreview = dialogView.findViewById<View>(R.id.selectedColorPreview)

        var selectedColor = Color.BLACK

        val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val red = redSeekBar.progress
                val green = greenSeekBar.progress
                val blue = blueSeekBar.progress
                selectedColor = Color.rgb(red, green, blue)
                colorPreview.setBackgroundColor(selectedColor)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }

        //Changes Color based on each SeekBar
        redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Pick a Color").setView(dialogView).setPositiveButton("OK") { _, _ ->
                doodleView.setColor(selectedColor)
            }
            .setNegativeButton("Cancel", null)
            .create()



        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }
}


*/