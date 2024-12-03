package com.example.cisc182projdoodler

import android.app.AlertDialog
import android.view.LayoutInflater
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils

class MainActivity : AppCompatActivity() {

    private lateinit var doodleView: DoodleView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doodleView = findViewById(R.id.doodleArea)
        lateinit var brushSizeSeekBar: SeekBar


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
            //doodleView.setColor(Color.RED)
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
