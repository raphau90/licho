package com.raphau.licho

import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raphau.licho.data.MessageThread
import com.raphau.licho.data.SmsMessage
import com.raphau.licho.di.InjectableFragment
import com.raphau.licho.util.toDateString
import com.raphau.licho.viewmodel.ThreadsListViewModel
import kotlinx.android.synthetic.main.fragment_threads_list.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ThreadsListFragment : InjectableFragment(), SearchView.OnQueryTextListener {

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
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.menu_threads_list, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
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
                if (thread.contact?.thumbnailUri != null) {
                    avatar.setImageURI(thread.contact?.thumbnailUri)
                } else {
                    avatar.setImageResource(R.drawable.ic_avatar)
                }
                address.text = thread.displayName
                val style = if (thread.isRead) {
                    Typeface.NORMAL
                } else {
                    Typeface.BOLD
                }
                address.setTypeface(null, style)
                content.setTypeface(null, style)
                val message = thread.messages.first()
                when (message) {
                    is SmsMessage ->content.text = message.text
                }
                val cal = Calendar.getInstance().apply {
                    time = Date(thread.lastMessageDate)
                }

                date.text = cal.toDateString()
            }
        }
    }

    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.filter = newText.orEmpty()
        return true
    }
}
