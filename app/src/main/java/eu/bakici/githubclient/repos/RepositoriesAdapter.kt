package eu.bakici.githubclient.repos

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import eu.bakici.githubclient.BR
import eu.bakici.githubclient.R
import eu.bakici.githubclient.databinding.RvItemRepositoryBinding

/**
 * Created on 13.01.18.
 */
class RepositoriesAdapter : RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder>() {


    private var repositories = listOf<Repository>()

    fun setItems(repositories: List<Repository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = this.repositories.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = DataBindingUtil.inflate<RvItemRepositoryBinding>(inflater, R.layout.rv_item_repository, parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder?, position: Int) {
        holder?.binding?.setVariable(BR.viewModel, RepositoryViewModel(repositories[position]))
        holder?.itemView?.tag = holder?.itemViewType
    }

    class RepositoryViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}