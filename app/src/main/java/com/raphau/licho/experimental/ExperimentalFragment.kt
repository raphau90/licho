package com.raphau.licho.experimental

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.raphau.licho.MessagesRepository
import com.raphau.licho.R
import com.raphau.licho.data.SmsMessage

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ExperimentalFragment : Fragment() {

    lateinit var messagesRepository: MessagesRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        messagesRepository = MessagesRepository(inflater.context)
        return inflater.inflate(R.layout.fragment_experimental, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val destinationAddress = "test_number"
        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            val message = SmsMessage(destinationAddress, null, "test")
            messagesRepository.sendMessage(message)
        }
    }
}
