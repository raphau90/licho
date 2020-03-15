package com.raphau.licho.di

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raphau.licho.MainActivity
import com.raphau.licho.experimental.ExperimentalActivity
import com.raphau.licho.viewmodel.ExperimentalViewModel
import com.raphau.licho.viewmodel.MainViewModel
import com.raphau.licho.viewmodel.MessagesListViewModel
import com.raphau.licho.viewmodel.action.MainInteractor
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideMainViewModel(activity: MainActivity, mainInteractor: MainInteractor): MainViewModel {
        val viewModel: MainViewModel by activity.viewModels { ViewModelFactory(mainInteractor) }
        return viewModel
    }

    @Provides
    fun provideExperimentalViewModel(activity: ExperimentalActivity,
                                     mainInteractor: MainInteractor): ExperimentalViewModel {
        val viewModel: ExperimentalViewModel by activity.viewModels { ViewModelFactory(mainInteractor) }
        return viewModel
    }

    @Provides
    fun provideMessagesListViewModel(fragment: InjectableFragment, mainInteractor: MainInteractor): MessagesListViewModel {
        val viewModel: MessagesListViewModel by fragment.viewModels { ViewModelFactory(mainInteractor) }
        return viewModel
    }

    private class ViewModelFactory(private val mainInteractor: MainInteractor) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return when (modelClass) {
                MainViewModel::class.java -> MainViewModel(mainInteractor) as T
                ExperimentalViewModel::class.java -> ExperimentalViewModel(mainInteractor) as T
                else -> throw AssertionError("No such ViewModel")
            }
        }
    }
}