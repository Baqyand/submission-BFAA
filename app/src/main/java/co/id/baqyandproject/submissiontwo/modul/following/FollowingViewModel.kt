package co.id.baqyandproject.submissiontwo.modul.following

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
class FollowingViewModel @Inject constructor(
    private val githubRepo: GithubRepo,
    private val dispatcher: DispatchersProvider
) : ViewModel() {
    companion object {
        const val TAG = "User Following"
    }

    private val _usersWhereFollow = MutableLiveData<List<UserGithub>>()
    val usersWhereFollow: LiveData<List<UserGithub>> = _usersWhereFollow

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private fun getUsersFollow(username: String) {
        _isLoading.value = true
        viewModelScope.launch {
            withContext(dispatcher.io()) {
                try {
                    githubRepo.getFollowing(username, object : PostCallBack {
                        override fun <T> onSuccessRequest(data: T) {
                            if (data is List<*>) {
                                setUsers(data as List<UserGithub>)
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

    fun getUsers(username: String) {
        getUsersFollow(username)
    }

    fun setUsers(user: List<UserGithub>) {
        _usersWhereFollow.postValue(user)
    }

    fun errorGetDetail() {
        _isError.postValue("error From Your Connection")
    }
}