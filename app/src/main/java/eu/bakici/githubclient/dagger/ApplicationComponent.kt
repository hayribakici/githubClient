package eu.bakici.githubclient.dagger

import android.content.Context
import dagger.Component
import eu.bakici.githubclient.repos.RepositoriesViewModel
import javax.inject.Singleton

/**
 * Created on 13.01.18.
 */

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    fun context(): Context

    fun inject(viewModel: RepositoriesViewModel)
}