package com.bikram.practice

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bikram.practice.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {
private lateinit var cameraActivityBinding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraActivityBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(cameraActivityBinding.root)
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}