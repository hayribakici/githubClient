package eu.bakici.githubclient.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import eu.bakici.githubclient.net.BackendAccess
import eu.bakici.githubclient.net.GitHubAPI
import eu.bakici.githubclient.net.RestClient
import rx.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Created on 13.01.18.
 */
@Module
class ApplicationModule(val context: Context) {

    @Provides
    @Singleton
    fun provideBackendAccess(context: Context): BackendAccess {
        return BackendAccess(RestClient.createService(GitHubAPI::class.java), Schedulers.io())
    }

    @Provides
    fun provideContext(): Context = context


}