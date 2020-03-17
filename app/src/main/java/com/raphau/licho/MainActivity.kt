package com.raphau.licho

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.raphau.licho.di.InjectableActivity
import com.raphau.licho.viewmodel.MainViewModel
import com.raphau.licho.viewmodel.state.MainState

import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : InjectableActivity() {

    @Inject lateinit var viewModel: MainViewModel
    private lateinit var listFragment: ThreadsListFragment
    private lateinit var threadFragment: ThreadsListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    override fun onInjected() {
        viewModel.getState().observe(this, Observer {
            when (it) {
                is MainState.ThreadsList -> showThreadsList()
                is MainState.ShowThread -> showThread()
            }
        })
    }

    private fun showThreadsList() {
        if (!::listFragment.isInitialized) {
            listFragment = ThreadsListFragment()
        }
        showFragment(listFragment)
    }

    private fun showThread() {
        if (!::threadFragment.isInitialized) {
            listFragment = ThreadsListFragment()
        }
        showFragment(listFragment)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content_main, fragment)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
