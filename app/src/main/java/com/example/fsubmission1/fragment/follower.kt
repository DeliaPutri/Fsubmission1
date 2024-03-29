package com.example.fsubmission1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fsubmission1.ui.DetailViewModel
import com.example.fsubmission1.ui.ViewModelFactory
import com.example.fsubmission1.adapter.Fadapter
import com.example.fsubmission1.databinding.FragmentFollowerBinding

class follower : Fragment() {
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val adapter = Fadapter()
    private lateinit var bind: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentFollowerBinding.inflate(inflater, container, false)

        with(bind){
            ListFollowers.layoutManager = LinearLayoutManager(requireActivity())
            ListFollowers.adapter = adapter

            val dataNama = arguments?.getString(NAMA)

            detailViewModel.getFollower(dataNama!!).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
            detailViewModel.getLoading().observe(viewLifecycleOwner){
                showLoading(it)
            }
        }
        return bind.root
    }
    private fun showLoading(it: Boolean) {
        bind.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    companion object {

        fun newInstance(dataNama: String): follower {
            val fragment = follower()
            val args = Bundle()
            args.putString(NAMA, dataNama)
            fragment.arguments = args
            return fragment
        }

        const val NAMA = "nama"
    }
}