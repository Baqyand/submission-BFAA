package co.id.baqyandproject.submissionthree.modul.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.baqyandproject.submissionthree.data.callback.PostCallBack
import co.id.baqyandproject.submissionthree.data.database.model.CacheUserGithubModel
import co.id.baqyandproject.submissionthree.data.di.DispatchersProvider
import co.id.baqyandproject.submissionthree.data.githubrepo.GithubRepo
import co.id.baqyandproject.submissionthree.data.githubrepo.PreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repo: GithubRepo,
    private val preferencesRepo: PreferencesRepo,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _listFavorites = MutableLiveData<List<CacheUserGithubModel>>()
    val listFavorites: LiveData<List<CacheUserGithubModel>> = _listFavorites

    private val _isSuccessDelete = MutableLiveData<String>()
    val isSuccessDelete: LiveData<String> = _isSuccessDelete

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _navigateToUserDetail = MutableLiveData<String?>()
    val navigateToUserDetail: LiveData<String?> = _navigateToUserDetail


    fun getUser() {
        viewModelScope.launch {
            withContext(dispatchersProvider.io()) {
                try {
                    repo.getFavoriteUsers(object : PostCallBack {
                        override fun <T> onSuccessRequest(data: T) {
                            if (data is List<*>) {
                                getUsers(data as List<CacheUserGithubModel>)
                            } else {
                                errorMessage()
                            }
                        }

                        override fun onErrorRequest(var1: Throwable) {
                            errorMessage()
                        }

                    })
                } catch (e: Exception) {
                    _errorMessage.postValue(e.message)
                }
            }
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            withContext(dispatchersProvider.io()) {
                try {
                    repo.deleteFavoriteUser(id, object : PostCallBack {
                        override fun <T> onSuccessRequest(data: T) {
                            _isSuccessDelete.postValue("Alhamdulillah Delete was Successfull")
                        }

                        override fun onErrorRequest(var1: Throwable) {
                            errorMessage()
                        }

                    })
                } catch (e: Exception) {
                    _errorMessage.postValue(e.message)
                }
            }
        }
    }

    val isDarkMode: LiveData<Boolean> = preferencesRepo.getTheme()

    fun setTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            try {
                preferencesRepo.setTheme(isDarkMode)
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }

    fun onNavigate() {
        _navigateToUserDetail.value = null
    }

    private fun errorMessage() {
        _errorMessage.postValue("Error Get Users")

    }

    private fun getUsers(users: List<CacheUserGithubModel>) {
        _listFavorites.postValue(users)
    }

    fun onItemsClicked(username: String?) {
        _navigateToUserDetail.postValue(username)
    }
}