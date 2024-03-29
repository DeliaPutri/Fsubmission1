package com.example.fsubmission1.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fsubmission1.fragment.follower
import com.example.fsubmission1.fragment.following

class SectionsPagerAdapter(activity: AppCompatActivity, private val nama: String?) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> follower.newInstance(nama ?: "")
            1 -> following.newInstance(nama ?: "")
            else -> throw IllegalArgumentException("Invalid Pos")
        }
    }
}