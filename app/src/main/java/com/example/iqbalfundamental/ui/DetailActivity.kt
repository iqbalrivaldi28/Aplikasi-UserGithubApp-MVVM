package com.example.iqbalfundamental.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.iqbalfundamental.R
import com.example.iqbalfundamental.Util
import com.example.iqbalfundamental.adapters.SectionPagerAdapter
import com.example.iqbalfundamental.data.SettingPreferences.Companion.getInstance
import com.example.iqbalfundamental.data.ViewModelFactory
import com.example.iqbalfundamental.data.ViewModelFactory.Companion.getInstance
import com.example.iqbalfundamental.databinding.ActivityDetailBinding
import com.example.iqbalfundamental.db.Favorite
import com.example.iqbalfundamental.retrofit.DetailUserResponse
import com.example.iqbalfundamental.ui.viewmodels.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val username: String = intent.getStringExtra(USERNAME) ?: ""
        val avatarUrl: String = intent.getStringExtra(AVATAR) ?: ""
        sectionPagerAdapter.username = username

        detailViewModel.isLoading.observe(this) {
            Util.showLoading(it, findViewById(R.id.progressBar))
        }
        detailViewModel.profile.observe(this) { profile ->
            setProfile(profile)
        }

        detailViewModel.getProfile(username)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tab)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLE[position]
        }.attach()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        username?.let { detailViewModel.getFavoriteByUsername(it) }?.observe(this) {
            if (it != null) {
                binding.fab.setImageResource(R.drawable.ic_baseline_favorite_24)
                binding.fab.setOnClickListener {
                    val mUser = Favorite(username.toString(), avatarUrl.toString())
                    Toast.makeText(this, "Data telah dihapus", Toast.LENGTH_SHORT).show()
                    detailViewModel.delete(mUser)
                }
            } else {
                binding.fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                binding.fab.setOnClickListener {
                    val mUser = Favorite(username.toString(), avatarUrl.toString())
                    Toast.makeText(this, "Data telah ditambahkan", Toast.LENGTH_SHORT).show()
                    detailViewModel.insert(mUser)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setProfile(profile: DetailUserResponse) {
        binding.apply {
            itemName.text = profile.name
            itemUsername.text = profile.login
            itemLocation.text = profile.location
            intFollowers.text = profile.followers.toString()
            intFollowing.text = profile.following.toString()
        }

        Glide.with(this)
            .load(profile.avatarUrl)
            .into(binding.itemPhoto)
    }

    companion object {
        const val USERNAME = "username"
        private val TAB_TITLE = arrayOf("Followers", "Following")
        const val AVATAR = "avatar"
        const val NAME = "name"
    }
}