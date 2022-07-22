package co.id.baqyandproject.submissionthree.data.module

import co.id.baqyandproject.submissionthree.data.preferences.GItUserPreferences
import co.id.baqyandproject.submissionthree.data.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {
    @Binds
    abstract fun bindPreferences(preferences: GItUserPreferences): Preferences
}