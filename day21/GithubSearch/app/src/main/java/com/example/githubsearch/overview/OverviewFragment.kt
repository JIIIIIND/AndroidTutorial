package com.example.githubsearch.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.githubsearch.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.searchButton.setOnClickListener {
            val keyword = binding.search.text.toString()
            if (keyword.replace(" ", "").equals("")) {
                val message = Toast.makeText(context, "search keyword is empty!", Toast.LENGTH_SHORT)
                message.show()
            }
            else viewModel.getGithubProperties(keyword)
        }
        binding.list.adapter = GithubListAdapter(GithubListAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                        OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }
}