package com.raphau.licho.experimental

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.raphau.licho.MessagesRepository
import com.raphau.licho.R
import com.raphau.licho.di.InjectableActivity

import kotlinx.android.synthetic.main.activity_experimental.*
import javax.inject.Inject

class ExperimentalActivity : InjectableActivity() {

    @Inject lateinit var messagesRepository: MessagesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS), 1)
        }
        setContentView(R.layout.activity_experimental)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            messagesRepository.refreshMessages()
        }
    }

}
