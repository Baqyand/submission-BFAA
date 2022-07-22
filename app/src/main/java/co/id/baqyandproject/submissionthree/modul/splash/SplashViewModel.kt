package co.id.baqyandproject.submissionthree.modul.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import co.id.baqyandproject.submissionthree.data.githubrepo.PreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferencesRepo: PreferencesRepo
) : ViewModel() {
    fun getThemeGit(): LiveData<Boolean> {
        return preferencesRepo.getTheme()
    }
}