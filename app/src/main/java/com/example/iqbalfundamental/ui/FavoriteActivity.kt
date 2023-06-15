package com.example.iqbalfundamental.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iqbalfundamental.adapters.FavoriteAdapter
import com.example.iqbalfundamental.data.ViewModelFactory
import com.example.iqbalfundamental.databinding.ActivityFavoriteBinding
import com.example.iqbalfundamental.ui.viewmodels.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>() {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initRecyclerView()
        favoriteViewModel.getAllFavorite()
        favoriteViewModel.listFavorite.observe(this) {
            favoriteAdapter.setData(it)

            Toast.makeText(this, "Data Favorit", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getAllFavorite()
        favoriteViewModel.listFavorite.observe(this) {
            Log.d("cekdata", it.toString())
            val adapter = FavoriteAdapter()
            adapter.setData(it)
            binding.rvFavuser.adapter = adapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initRecyclerView() {
        favoriteAdapter = FavoriteAdapter()
        binding.rvFavuser.layoutManager = LinearLayoutManager(this)
        binding.rvFavuser.adapter = favoriteAdapter
        binding.rvFavuser.setHasFixedSize(false)
    }
}