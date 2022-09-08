package com.samuel.nasaimages.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.IdlingResource
import com.samuel.nasaimages.databinding.ActivityMainBinding
import com.samuel.nasaimages.viewmodels.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Only called from test, creates and returns a new [AppIdlingResource].
     */
    @VisibleForTesting
    fun getIdlingResource(): IdlingResource? {
        return mainViewModel.getIdlingResource()
    }
}