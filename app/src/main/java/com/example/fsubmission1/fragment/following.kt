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
import com.example.fsubmission1.databinding.FragmentFollowingBinding

class following : Fragment() {
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val adapter = Fadapter()
    private lateinit var bind: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentFollowingBinding.inflate(inflater, container, false)

        with(bind){
            ListFollowing.layoutManager = LinearLayoutManager(requireActivity())
            ListFollowing.adapter = adapter

            val dataNama = arguments?.getString(NAMA)

            detailViewModel.getFollowing(dataNama!!).observe(viewLifecycleOwner){
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

        fun newInstance(dataNama: String): following {
            val fragment = following()
            val args = Bundle()
            args.putString(NAMA, dataNama)
            fragment.arguments = args
            return fragment
        }

        const val NAMA = "nama"
    }
}