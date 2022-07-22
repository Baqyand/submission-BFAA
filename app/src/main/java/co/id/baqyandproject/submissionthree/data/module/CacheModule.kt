package co.id.baqyandproject.submissionthree.data.module

import android.content.Context
import androidx.room.Room
import co.id.baqyandproject.submissionthree.data.database.CacheUserGithub
import co.id.baqyandproject.submissionthree.data.database.GitDatabase
import co.id.baqyandproject.submissionthree.data.database.RoomCache
import co.id.baqyandproject.submissionthree.data.database.dao.GitUserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): CacheUserGithub

    companion object {
        @Provides
        @Singleton
        fun providesWithDatabase(@ApplicationContext context: Context): GitDatabase {
            return Room.databaseBuilder(
                context, GitDatabase::class.java, "gitUser.db"
            ).build()
        }

        @Provides
        fun providesUserGitDao(gitDatabase: GitDatabase): GitUserDao {
            return gitDatabase.gitUserDao()

        }
    }
}