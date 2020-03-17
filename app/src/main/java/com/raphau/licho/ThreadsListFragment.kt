package com.raphau.licho

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.raphau.licho.data.MessageThread
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
        viewModel.getMessages().observe(this) {
            threadsAdapter.threads = it

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private class ThreadsListRecyclerAdapter() :
        RecyclerView.Adapter<ThreadsListRecyclerAdapter.ThreadViewHolder>() {

        var threads: List<MessageThread> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadViewHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItemCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindViewHolder(holder: ThreadViewHolder, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        class ThreadViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        }
    }
}
