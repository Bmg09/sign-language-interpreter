package com.bikram.practice

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bikram.practice.databinding.ActivityCameraBinding
import com.bikram.practice.fragments.CameraFragment
import com.shashank.sony.fancydialoglib.Animation
import com.shashank.sony.fancydialoglib.FancyAlertDialog
import org.tensorflow.lite.task.vision.detector.Detection

class CameraActivity : AppCompatActivity() ,CameraFragment.DetectionListener{
    private lateinit var cameraActivityBinding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraActivityBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(cameraActivityBinding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Recognition"
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Info")?.setIcon(android.R.drawable.ic_menu_info_details)?.setShowAsAction(2)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.toString()){
            "Info" ->{
                FancyAlertDialog.Builder.with(this).setTitle("About Lesson Page")
                    .setBackgroundColor(Color.parseColor("#303F9F")) // for @ColorRes use setBackgroundColorRes(R.color.colorValue)
                    .setMessage("We have provided Two Categories as lessons for user to practice the signs Letters and Numbers")
                    .setNegativeBtnText("Cancel")
                    .setPositiveBtnBackground(Color.parseColor("#FF4081")) // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorValue)
                    .setPositiveBtnText("Ok")
                    .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8")) // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorValue)
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

    override fun onDetectionResult(result: MutableList<Detection>?) {
        println(result)
        Log.d("res",result.toString())
        result?.forEach { detection ->
            runOnUiThread {
                val drawableText = detection.categories[0].label + " " +
                        String.format("%.2f", detection.categories[0].score)
                cameraActivityBinding.Label.text = drawableText
                cameraActivityBinding.Label.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                    }

                    override fun afterTextChanged(s: Editable?) {

                    }

                })
            }

        }
    }
}