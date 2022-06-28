package co.id.baqyandproject.submissiontwo.data.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersProvider {
    fun io(): CoroutineDispatcher = Dispatchers.IO
}