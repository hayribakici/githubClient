package eu.bakici.githubclient.repos

import eu.bakici.githubclient.GitHubRebelTestApplication
import eu.bakici.githubclient.User
import eu.bakici.githubclient.net.BackendAccess
import eu.bakici.githubclient.net.GitHubAPI
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import java.io.IOException
import java.util.*

/**
 * Created on 17.01.18.
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = GitHubRebelTestApplication::class)
class RepositoriesViewModelTest {

    private val REPO_NAME = "tetris"

    private var testSubscriber = TestSubscriber<List<Repository>>()
    private var api: GitHubAPI = Mockito.mock(GitHubAPI::class.java)

    // class under test
    private lateinit var viewModel: RepositoriesViewModel

    @Before
    fun setUp() {
        viewModel = RepositoriesViewModel()
        viewModel.backendAccess = BackendAccess(api, Schedulers.immediate())
    }

    @Test
    fun listOfRepositoriesSuccess() {

        // prepare
        val response = getMockReponse()

        Mockito.`when`(api.getRepositories(ArgumentMatchers.anyString())).thenReturn(Observable.just(response))

        // call
        viewModel.getRepositories(REPO_NAME, testSubscriber)

        // after
        testSubscriber.assertTerminalEvent()
        testSubscriber.assertCompleted()
        testSubscriber.assertValues(response.repositories)
    }

    @Test
    fun listOfRepositoriesIOError() {

        Mockito.`when`(api.getRepositories(ArgumentMatchers.anyString())).thenReturn(Observable.error(IOException("error")))

        // call
        viewModel.getRepositories(REPO_NAME, testSubscriber)

        // after
        testSubscriber.assertError(IOException::class.java)
    }

    private fun getMockReponse(): RepositoryResponse {
        val repoList = arrayListOf<Repository>()
        for (i in 0..3) {
            repoList.add(Repository(
                    i.toLong(),
                    "Repo$i",
                    "Description$i",
                    createMockUser(i.toLong(), "user$i"), randomInt()))
        }
        return RepositoryResponse(repoList.size, repoList)
    }

    private fun createMockUser(id: Long, name: String): User {
        return User(id, name, "http://test.com/avatar.png")
    }

    private fun randomInt(): Int {
        return Random().nextInt(100 - 1) + 100
    }
}