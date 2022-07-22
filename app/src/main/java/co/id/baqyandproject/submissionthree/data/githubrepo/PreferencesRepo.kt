package co.id.baqyandproject.submissionthree.data.githubrepo

import androidx.lifecycle.LiveData

interface PreferencesRepo {
    suspend fun setTheme(isDarkMode: Boolean)
    fun getTheme(): LiveData<Boolean>
}