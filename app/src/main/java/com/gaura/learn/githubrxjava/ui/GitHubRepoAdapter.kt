package com.gaura.learn.githubrxjava.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gaura.learn.githubrxjava.R
import com.gaura.learn.githubrxjava.model.GitHubRepo
import kotlinx.android.synthetic.main.item_github_repo.view.*

class GitHubRepoAdapter : RecyclerView.Adapter<GitHubRepoAdapter.GitHubViewHolder>() {

    var gitHubRepos: List<GitHubRepo> = ArrayList<GitHubRepo>()

    class GitHubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(gitHubRepo: GitHubRepo) {
            itemView.text_repo_name.text = gitHubRepo.name
            itemView.text_language.text = gitHubRepo.language
            itemView.text_repo_description.text = gitHubRepo.description
            itemView.text_stars.text = """${gitHubRepo.stargazersCount} stars"""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_github_repo, parent, false)
        return GitHubViewHolder(view)
    }

    override fun onBindViewHolder(holder: GitHubViewHolder, position: Int) {
        holder.bind(gitHubRepos[position])
    }

    override fun getItemCount(): Int {
        return gitHubRepos.size
    }

    fun updateList(list: List<GitHubRepo>) {
        gitHubRepos = list
        notifyDataSetChanged()
    }
}