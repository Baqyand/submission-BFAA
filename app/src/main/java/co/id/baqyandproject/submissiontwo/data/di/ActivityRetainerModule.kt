package co.id.baqyandproject.submissiontwo.data.di

import co.id.baqyandproject.submissiontwo.data.githubrepo.GithubRepo
import co.id.baqyandproject.submissiontwo.data.githubrepo.GithubRepoImpl
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
    abstract fun bindGithubUserRepository(repository: GithubRepoImpl): GithubRepo

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider): DispatchersProvider

}