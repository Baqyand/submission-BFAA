package co.id.baqyandproject.submissiontwo.modul.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.id.baqyandproject.submissiontwo.data.callback.PostCallBack
import co.id.baqyandproject.submissiontwo.data.di.DispatchersProvider
import co.id.baqyandproject.submissiontwo.data.githubrepo.GithubRepo
import co.id.baqyandproject.submissiontwo.model.UserGithub
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val githubRepo: GithubRepo,
    private val dispatcher: DispatchersProvider
) : ViewModel() {
    companion object {
        const val TAG = "UserDetail"
    }

    private val _userDetail = MutableLiveData<UserGithub>()
    val userDetail: LiveData<UserGithub> = _userDetail

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

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

    fun setUser(user: UserGithub) {
        _userDetail.postValue(user)
    }

    fun errorGetDetail() {
        _isError.postValue("error From Your Connection")
    }
}