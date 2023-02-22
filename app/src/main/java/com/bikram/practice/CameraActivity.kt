package com.bikram.practice

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bikram.practice.databinding.ActivityCameraBinding
import com.shashank.sony.fancydialoglib.Animation
import com.shashank.sony.fancydialoglib.FancyAlertDialog
import java.security.AccessController.getContext

class CameraActivity : AppCompatActivity() {
    private lateinit var cameraActivityBinding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraActivityBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(cameraActivityBinding.root)
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
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
                    .setBackgroundColor(Color.parseColor("#303F9F")) // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                    .setMessage("We have provided Two Categories as lessons for user to practice the signs Letters and Numbers")
                    .setNegativeBtnText("Cancel")
                    .setPositiveBtnBackground(Color.parseColor("#FF4081")) // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
                    .setPositiveBtnText("Ok")
                    .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8")) // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorvalue)
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
}