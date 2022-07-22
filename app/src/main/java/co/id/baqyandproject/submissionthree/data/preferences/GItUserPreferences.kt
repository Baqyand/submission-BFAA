package co.id.baqyandproject.submissionthree.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import co.id.baqyandproject.submissionthree.data.preferences.GItUserPreferences.Companion.PREFERENCE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.datastore by preferencesDataStore(PREFERENCE)

@Singleton
class GItUserPreferences @Inject constructor(
    @ApplicationContext context: Context
) : Preferences {
    private val settingData = context.datastore

    override suspend fun setAppMode(isDarkMode: Boolean) {
        settingData.edit { preferences ->
            preferences[THEME_KEY] = isDarkMode
        }

    }

    override fun getAppMode(): Flow<Boolean> {
        return settingData.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    companion object {
        const val PREFERENCE = "datastorePref"
        private val THEME_KEY = booleanPreferencesKey("theme_setting")
    }
}