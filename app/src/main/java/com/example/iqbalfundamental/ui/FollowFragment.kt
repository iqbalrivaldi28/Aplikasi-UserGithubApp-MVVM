package com.example.iqbalfundamental.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iqbalfundamental.R
import com.example.iqbalfundamental.Util
import com.example.iqbalfundamental.data.ViewModelFactory
import com.example.iqbalfundamental.databinding.FragmentFollowBinding
import com.example.iqbalfundamental.ui.viewmodels.DetailViewModel


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(ARG_USERNAME, getString(R.string.defus))

        val factory = activity?.let { ViewModelFactory.getInstance(it.application) }
        detailViewModel =
            factory?.let { ViewModelProvider(this, it) }!![DetailViewModel::class.java]

        val layoutManager = LinearLayoutManager(activity)
        binding.sectionFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.sectionFollowers.addItemDecoration(itemDecoration)

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            Util.showLoading(it, binding.progressBar)
        }

        detailViewModel.listFollowers.observe(viewLifecycleOwner) { User ->
            Util.setUserData(User, binding.sectionFollowers, requireActivity())
        }

        detailViewModel.listFollowing.observe(viewLifecycleOwner) { User ->
            Util.setUserData(User, binding.sectionFollowers, requireActivity())
        }

        when (index) {
            1 -> detailViewModel.getFollowers(username ?: "")
            2 -> detailViewModel.getFollowing(username ?: "")
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "number"
        const val ARG_USERNAME = "username"
    }
}