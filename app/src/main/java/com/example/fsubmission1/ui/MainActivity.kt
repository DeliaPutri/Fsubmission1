package com.example.fsubmission1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fsubmission1.adapter.Useradapter
import com.example.fsubmission1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = Useradapter()
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rv.layoutManager = LinearLayoutManager(this@MainActivity)
            rv.adapter = adapter

            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                mainViewModel.getAllUsers(searchView.text.toString()).observe(this@MainActivity){
                    adapter.submitList(it)
                }
                searchView.hide()
                false
            }

            mainViewModel.getLoading().observe(this@MainActivity){
                showLoading(it)
            }
        }

    }
    private fun showLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

}