package com.raphau.licho

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("RTEST", "SmsService onReceive()")
        if (context != null) Toast.makeText(context, "Received sms", Toast.LENGTH_LONG).show()
    }
}