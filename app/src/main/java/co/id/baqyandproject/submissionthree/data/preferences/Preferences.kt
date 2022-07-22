package co.id.baqyandproject.submissionthree.data.preferences

import kotlinx.coroutines.flow.Flow


interface Preferences {
    suspend fun setAppMode(isDarkMode: Boolean)

    fun getAppMode(): Flow<Boolean>
}