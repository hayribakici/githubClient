package eu.bakici.githubclient.repos

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.Menu
import android.view.View
import eu.bakici.githubclient.GitHubApplication
import eu.bakici.githubclient.R
import eu.bakici.githubclient.dagger.ApplicationComponent
import eu.bakici.githubclient.databinding.ActivityRepositoriesBinding
import rx.Subscriber

open class RepositoriesActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_THRESHOLD = 300L
    }

    val viewModel = RepositoriesViewModel()
    private val adapter = RepositoriesAdapter()
    private lateinit var binding: ActivityRepositoriesBinding
    private val queryThresholdHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repositories)
        setSupportActionBar(findViewById(R.id.app_bar))
        supportActionBar?.title = ""

        val recyclerView = binding.rvRepositories
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        setupEmptyView(recyclerView)
        viewModel.inject(getApplicationComponent())
    }

    private fun setupEmptyView(recyclerView: RecyclerView) {
        val emptyView = binding.emptyView
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (adapter.itemCount == 0) {
                    recyclerView.visibility = View.GONE
                    emptyView.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    emptyView.visibility = View.GONE
                }
            }
        })
    }

    private fun getApplicationComponent(): ApplicationComponent {
        val application = GitHubApplication.getGitHubRebelApplication(this)
        return application.component
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_repositories, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem?.actionView as SearchView
        setupSearchView(searchView)
        return true
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.length!! <= 2) {
                    return false
                }
                queryRepository(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! <= 2) {
                    return false
                }
                queryWithTimerThreshold(newText)
                return true
            }
        })
    }

    private fun queryWithTimerThreshold(name: String?) {
        queryThresholdHandler.removeCallbacks(null)
        queryThresholdHandler.postDelayed({
            queryRepository(name)
        }, SEARCH_THRESHOLD)
    }

    private fun queryRepository(name: String?) {
        if (TextUtils.isEmpty(name)) {
            return
        }
        fetchRepositories(name!!)
    }

    open fun fetchRepositories(name: String) {
        viewModel.getRepositories(name, RepositorySubscriber(name))
    }

    open inner class RepositorySubscriber(private val name: String) : Subscriber<List<Repository>>() {
        override fun onNext(repositories: List<Repository>?) {
            adapter.setItems(repositories ?: emptyList())
        }

        override fun onCompleted() {}

        override fun onError(e: Throwable?) {
            Snackbar.make(binding.root, R.string.search_error, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok, { _ ->
                        fetchRepositories(name)
                    })
        }
    }
}
