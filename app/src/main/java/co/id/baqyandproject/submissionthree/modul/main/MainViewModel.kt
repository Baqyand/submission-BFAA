package co.id.baqyandproject.submissionthree.modul.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.baqyandproject.submissionthree.data.callback.PostCallBack
import co.id.baqyandproject.submissionthree.data.di.DispatchersProvider
import co.id.baqyandproject.submissionthree.data.githubrepo.GithubRepo
import co.id.baqyandproject.submissionthree.data.githubrepo.PreferencesRepo
import co.id.baqyandproject.submissionthree.model.ItemsItem
import co.id.baqyandproject.submissionthree.model.SearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubRepo: GithubRepo,
    private val dispatcher: DispatchersProvider,
    private val preferencesRepo: PreferencesRepo
) : ViewModel() {

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _navigateToUserDetail = MutableLiveData<String?>()
    val navigateToUserDetail: LiveData<String?> = _navigateToUserDetail

    fun onNavigate() {
        _navigateToUserDetail.value = null
    }

    private fun searchUser(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            withContext(dispatcher.io()) {

                try {
                    githubRepo.searchUser(query, object : PostCallBack {
                        override fun <T> onSuccessRequest(data: T) {
                            if (data is SearchResponse) {
                                getUsers(data.items)
                            } else {
                                errorSearch()
                            }

                        }

                        override fun onErrorRequest(var1: Throwable) {
                            errorSearch()
                        }
                    })
                } catch (e: Exception) {
                    Log.e(TAG, "searchUser: ${e.message}")
                    _errorMessage.postValue(e.message)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun setFindUser(user: String) {
        searchUser(user)
    }

    fun onItemsClicked(user: String) {
        _navigateToUserDetail.postValue(user)
    }

    fun getUsers(user: List<ItemsItem>) {
        _listUser.postValue(user)
    }

    fun errorSearch() {
        _errorMessage.postValue("User Not Found")
    }

    fun getThemeGit(): LiveData<Boolean> {
        return preferencesRepo.getTheme()
    }

    fun setTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            try {
                preferencesRepo.setTheme(isDarkMode)
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }

    companion object {
        private const val TAG = "SearchUsers"
    }
}