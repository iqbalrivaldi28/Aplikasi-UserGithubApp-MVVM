package com.example.iqbalfundamental.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iqbalfundamental.data.UserRepository
import com.example.iqbalfundamental.db.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mUserRepository: UserRepository = UserRepository(application)

    private val _listFavorite = MutableLiveData<List<Favorite>>()
    val listFavorite: LiveData<List<Favorite>> = _listFavorite

    fun getAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            val dataFav = mUserRepository.getAllFavoriteUsers()
            runBlocking(Dispatchers.Main) {
                _listFavorite.value = dataFav
            }
        }
    }
}