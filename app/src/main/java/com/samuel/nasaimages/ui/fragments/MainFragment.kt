package com.samuel.nasaimages.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.samuel.domain.models.Image
import com.samuel.nasaimages.R
import com.samuel.nasaimages.adapters.ImageListener
import com.samuel.nasaimages.adapters.ImagesAdapter
import com.samuel.nasaimages.databinding.FragmentMainBinding
import com.samuel.nasaimages.utils.AppUtils
import com.samuel.nasaimages.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : DaggerFragment(), ImageListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val adapter = ImagesAdapter(this)
    lateinit var binding: FragmentMainBinding
    private lateinit var mainActivity: FragmentActivity

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(mainActivity, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.adapter = adapter
        binding.retry.setOnClickListener { getImages() }
        listenForChanges()
        getImages()
    }

    //Listen for changes in the recyclerview paging adapter
    private fun listenForChanges() {
        lifecycleScope.launch {
            adapter.loadStateFlow
                .collectLatest {
                    mainViewModel.page = adapter.itemCount + 1
                    updateProgress(it.refresh)
                }
        }
    }

    //Update UI based on different states while loading images
    private fun updateProgress(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                binding.progress = true
                toggleMessageView(false)
            }
            is LoadState.NotLoading -> {
                binding.progress = false
            }
            is LoadState.Error -> {
                binding.progress = false
                val message = if (AppUtils.hasInternet(mainActivity)) loadState.error.message else
                    getString(R.string.failed)
                toggleMessageView(true, message)
            }
        }
    }

    private fun toggleMessageView(show: Boolean, message: String? = "") {
        binding.messageTv.visibility = if (show) View.VISIBLE else View.GONE
        binding.retry.visibility = if (show) View.VISIBLE else View.GONE
        binding.messageTv.text = message
    }

    //Use kotlin Coroutine to fetch images
    private fun getImages() {
        lifecycleScope.launch {
            mainViewModel.getImages(2017, 2017).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    //Open image details fragment
    override fun openImage(image: Image?) {
        image?.let {
            val action = MainFragmentDirections.actionMainFragmentToImageFragment(it)
            findNavController().navigate(action)
        }
    }

}