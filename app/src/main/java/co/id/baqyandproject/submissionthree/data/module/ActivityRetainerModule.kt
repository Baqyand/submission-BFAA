package co.id.baqyandproject.submissionthree.data.module

import co.id.baqyandproject.submissionthree.data.di.CoroutineDispatchersProvider
import co.id.baqyandproject.submissionthree.data.di.DispatchersProvider
import co.id.baqyandproject.submissionthree.data.githubrepo.GithubRepo
import co.id.baqyandproject.submissionthree.data.githubrepo.GithubRepoImpl
import co.id.baqyandproject.submissionthree.data.githubrepo.PreferencesRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainerModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindPreferencesRepo(repository: GithubRepoImpl): PreferencesRepo

    @Binds
    @ActivityRetainedScoped
    abstract fun bindGithubUserRepository(repository: GithubRepoImpl): GithubRepo

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider): DispatchersProvider

}