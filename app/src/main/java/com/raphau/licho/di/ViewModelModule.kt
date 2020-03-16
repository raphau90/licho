package com.raphau.licho.di

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raphau.licho.MainActivity
import com.raphau.licho.MessagesListFragment
import com.raphau.licho.MessagesRepository
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
    fun provideMainViewModel(activity: MainActivity,
                             mainInteractor: MainInteractor,
                             messagesRepository: MessagesRepository): MainViewModel {
        val viewModel: MainViewModel by activity.viewModels {
            ViewModelFactory{ MainViewModel(mainInteractor, messagesRepository) }
        }
        return viewModel
    }

    @Provides
    fun provideExperimentalViewModel(activity: ExperimentalActivity,
                                     mainInteractor: MainInteractor): ExperimentalViewModel {
        val viewModel: ExperimentalViewModel by activity.viewModels {
            ViewModelFactory{ ExperimentalViewModel(mainInteractor) }
        }
        return viewModel
    }

    @Provides
    fun provideMessagesListViewModel(fragment: MessagesListFragment,
                                     mainInteractor: MainInteractor,
                                     messagesRepository: MessagesRepository): MessagesListViewModel {
        val viewModel: MessagesListViewModel by fragment.viewModels {
            ViewModelFactory{ MessagesListViewModel(mainInteractor, messagesRepository) }
        }
        return viewModel
    }

    private class ViewModelFactory<T : ViewModel>(val producer: () -> T) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return producer.invoke() as T
        }
    }
}