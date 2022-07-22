package co.id.baqyandproject.submissionthree.modul.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.baqyandproject.submissionthree.data.callback.PostCallBack
import co.id.baqyandproject.submissionthree.data.database.model.CacheUserGithubModel
import co.id.baqyandproject.submissionthree.data.di.DispatchersProvider
import co.id.baqyandproject.submissionthree.data.githubrepo.GithubRepo
import co.id.baqyandproject.submissionthree.data.githubrepo.PreferencesRepo
import co.id.baqyandproject.submissionthree.model.UserGithub
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val githubRepo: GithubRepo,
    private val preferencesRepo: PreferencesRepo,
    private val dispatcher: DispatchersProvider,
) : ViewModel() {


    private val _userDetail = MutableLiveData<UserGithub>()
    val userDetail: LiveData<UserGithub> = _userDetail

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _isSuccess = MutableLiveData<String>()
    val isSuccess: LiveData<String> = _isSuccess

    private fun getUserDetail(username: String) {
        _isLoading.value = true
        viewModelScope.launch {
            withContext(dispatcher.io()) {
                try {
                    githubRepo.getUserDetail(username, object : PostCallBack {
                        override fun <T> onSuccessRequest(data: T) {
                            if (data is UserGithub) {
                                setUser(data)
                            } else {
                                errorGetDetail()
                            }
                        }

                        override fun onErrorRequest(var1: Throwable) {
                            errorGetDetail()
                        }

                    })
                } catch (e: Exception) {
                    Log.e(TAG, "getUser: ${e.message}")

                    _isError.postValue(e.message)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun getUser(username: String) {
        getUserDetail(username)
    }

    fun getUserFromDb(username: String) {
        _isLoading.value = false
        viewModelScope.launch {
            withContext(dispatcher.io()) {
                try {
                    githubRepo.getDetailUser(username, object : PostCallBack {
                        override fun <T> onSuccessRequest(data: T) {
                            if (data is CacheUserGithubModel) {
                                setUser(data.toDomain())
                            } else {
                                errorGetDetail()
                            }
                        }

                        override fun onErrorRequest(var1: Throwable) {
                            errorGetDetail()
                        }

                    })

                } catch (e: Exception) {
                    _isError.postValue(e.message)
                }
            }
        }
    }


    fun addUserFavorite(user: UserGithub) {
        viewModelScope.launch {
            withContext(dispatcher.io()) {
                try {
                    githubRepo.setFavoriteUser(user, object : PostCallBack {
                        override fun <T> onSuccessRequest(data: T) {
                            successAddUser()
                        }

                        override fun onErrorRequest(var1: Throwable) {
                            errorAddUser()
                        }

                    })
                } catch (e: Exception) {
                    _isError.postValue(e.message)
                }
            }
        }
    }

    fun deleteUserFavorite(id: Int){
        viewModelScope.launch {
            withContext(dispatcher.io()) {
                try {
                    githubRepo.deleteFavoriteUser(id, object : PostCallBack {
                        override fun <T> onSuccessRequest(data: T) {
                            _isSuccess.postValue("Success Delete User")
                        }

                        override fun onErrorRequest(var1: Throwable) {
                            errorAddUser()
                        }

                    })
                } catch (e: Exception) {
                    _isError.postValue(e.message)
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
                _isError.postValue(e.message)
            }
        }
    }

    fun setUser(user: UserGithub) {
        _userDetail.postValue(user)
    }

    private fun errorGetDetail() {
        _isError.postValue("error From Your Connection")
    }

    private fun errorAddUser() {
        _isError.postValue("Error Add Favorite User")
    }

    private fun successAddUser() {
        _isSuccess.postValue("Success Add User")
    }

    companion object {
        const val TAG = "UserDetail"
    }
}