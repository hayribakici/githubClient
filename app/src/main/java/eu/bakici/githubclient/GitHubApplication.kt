package eu.bakici.githubclient

import android.app.Application
import android.content.Context
import eu.bakici.githubclient.dagger.ApplicationComponent
import eu.bakici.githubclient.dagger.ApplicationModule
import eu.bakici.githubclient.dagger.DaggerApplicationComponent


/**
 * Created on 13.01.18.
 */
open class GitHubApplication : Application() {

    companion object {
        fun getGitHubRebelApplication(context: Context): GitHubApplication = context.applicationContext as GitHubApplication
    }

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    protected open fun initDagger() {
        component = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }
}