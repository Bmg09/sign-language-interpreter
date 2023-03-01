package com.bikram.practice

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bikram.practice.databinding.ActivityCameraBinding
import com.bikram.practice.fragments.CameraFragment
import com.shashank.sony.fancydialoglib.Animation
import com.shashank.sony.fancydialoglib.FancyAlertDialog
import org.tensorflow.lite.task.vision.detector.Detection
import java.util.*

class CameraActivity : AppCompatActivity(), CameraFragment.DetectionListener {
    private lateinit var cameraActivityBinding: ActivityCameraBinding
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraActivityBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(cameraActivityBinding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Recognition"
        setSupportActionBar(toolbar)
        textToSpeech = TextToSpeech(this,object : TextToSpeech.OnInitListener{
            override fun onInit(status: Int) {
                if (status == TextToSpeech.SUCCESS){
                    val result = textToSpeech.setLanguage(Locale.getDefault())
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language not supported")
                    }else{
                        Log.e("TTS","Language supported")
                    }
                }else{
                    Log.e("TTS","Initialization failed")
                }
            }

        })
        val languages = listOf(
            Locale("en", "US"),
            Locale("hi", "IN"),
            Locale("fr", "FR"),
        )
        val adapter = ArrayAdapter<Locale>(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cameraActivityBinding.langspinner.adapter = adapter
        cameraActivityBinding.langspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val locale = languages[position]
                textToSpeech.language = locale
                val text = "Hello, world!"
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Info")?.setIcon(android.R.drawable.ic_menu_info_details)?.setShowAsAction(2)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.toString()) {
            "Info" -> {
                FancyAlertDialog.Builder.with(this).setTitle("About Lesson Page")
                    .setBackgroundColor(Color.parseColor("#303F9F"))
                    .setMessage("We have provided Two Categories as lessons for user to practice the signs Letters and Numbers")
                    .setNegativeBtnText("Cancel")
                    .setPositiveBtnBackground(Color.parseColor("#FF4081"))
                    .setPositiveBtnText("Ok")
                    .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))
                    .setAnimation(Animation.SLIDE)
                    .isCancellable(true)
                    .setIcon(R.drawable.lesson, View.VISIBLE)
                    .onPositiveClicked {
                        Toast.makeText(
                            this,
                            "Enjoy!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .onNegativeClicked {
                        Toast.makeText(
                            this,
                            "Cancel",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .build()
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private var previousLabel: String? = null
    override fun onDetectionResult(result: MutableList<Detection>?) {
        println(result)
        Log.d("res", result.toString())
        result?.forEach { detection ->
            runOnUiThread {
                val currentLabel = detection.categories[0].label
                if (currentLabel != previousLabel) { // Check if the label has changed
                    previousLabel = currentLabel // Update the previous label
                    val drawableText = currentLabel + " " +
                            String.format("%.2f", detection.categories[0].score)
                    cameraActivityBinding.Label.text = drawableText
                    textToSpeech.speak(currentLabel, TextToSpeech.QUEUE_FLUSH, null, null) // Speak the label
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}