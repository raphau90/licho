package com.raphau.licho

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raphau.licho.data.MessageThread
import com.raphau.licho.data.SmsMessage
import com.raphau.licho.di.InjectableFragment
import com.raphau.licho.viewmodel.ThreadsListViewModel
import kotlinx.android.synthetic.main.fragment_threads_list.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ThreadsListFragment : InjectableFragment() {

    @Inject lateinit var viewModel: ThreadsListViewModel
    private val threadsAdapter = ThreadsListRecyclerAdapter()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_threads_list, container, false)
    }

    override fun onInjected() {
        threads_list_recycler_view.adapter = threadsAdapter
        threads_list_recycler_view.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewModel.getMessages().observe(this) {
            threadsAdapter.threads = it
            threadsAdapter.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private class ThreadsListRecyclerAdapter() :
        RecyclerView.Adapter<ThreadsListRecyclerAdapter.ThreadViewHolder>() {

        var threads: List<MessageThread> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.threads_list_row, parent, false)
            return ThreadViewHolder(view)
        }

        override fun getItemCount() = threads.size

        override fun onBindViewHolder(holder: ThreadViewHolder, position: Int) {
            holder.setItem(threads[position])
        }

        class ThreadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val avatar = view.findViewById<ImageView>(R.id.avatar)
            val address = view.findViewById<TextView>(R.id.address)
            val content = view.findViewById<TextView>(R.id.content)
            val date = view.findViewById<TextView>(R.id.date)

            fun setItem(thread: MessageThread) {
                //TODO: set avatar
                val displayName = if (!thread?.contact?.displayName.isNullOrBlank()) {
                    thread.contact!!.displayName
                } else {
                    thread.address
                }
                address.text = displayName
                val message = thread.messages.first()
                when (message) {
                    is SmsMessage ->content.text = message.text
                }
                date.text = thread.lastMessageDate.toString()
            }

        }
    }
}
