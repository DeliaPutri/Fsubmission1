package com.example.fsubmission1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.example.fsubmission1.R
import com.example.fsubmission1.adapter.SectionsPagerAdapter
import com.example.fsubmission1.databinding.ActivityDetailBinding
import com.example.fsubmission1.remote.database.FavoriteUser
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nama=intent.getStringExtra(NAMA)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, nama)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        detailViewModel.getDetail(nama!!).observe(this){user ->
            binding.tvUsername.text=user.login
            binding.tvName.text=user.name
            Picasso.get().load(user.avatarUrl).into(binding.imageView)
            binding.followers.text="${user.followers} Followers "
            binding.following.text="${user.following} Following "

            detailViewModel.getFavUserByUsername(user.login).observe(this@DetailActivity){ gitUser ->
                if (gitUser != null){
                    binding.lopelope.setImageResource(R.drawable.favorite)
                    binding.lopelope.setOnClickListener {
                        detailViewModel.deleteUsers(gitUser)
                    }
                } else {
                    binding.lopelope.setImageResource(R.drawable.favorite_border)
                    binding.lopelope.setOnClickListener {
                        val favoriteUser = FavoriteUser(
                            name = user.login,
                            avatarUrl = user.avatarUrl
                        )
                        detailViewModel.insertUsers(favoriteUser)
                    }
                }
            }
        }
        detailViewModel.getLoading().observe(this){
            showLoading(it)
        }


    }

    private fun showLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }
    companion object {
        const val NAMA="nama"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}