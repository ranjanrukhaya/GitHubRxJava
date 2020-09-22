package com.gaura.learn.githubrxjava.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gaura.learn.githubrxjava.R
import com.gaura.learn.githubrxjava.data.GitHubService
import com.gaura.learn.githubrxjava.model.GitHubRepo
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var gitHubRepoAdapter: GitHubRepoAdapter
    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_search.setOnClickListener {
            val searchStr = edit_text_username.text.toString()
            if (searchStr.isNotEmpty()) {
                getStarredRepos(searchStr)
            }
        }

        gitHubRepoAdapter = GitHubRepoAdapter()
        recycler_view.apply {
            adapter = gitHubRepoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun getStarredRepos(str: String) {

        disposable = GitHubService.create().getStarredRepos(str)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    handleResult(it)
                }, {
                    handleError(it)
                }
            )
    }

    private fun handleResult(list: List<GitHubRepo>) {
        gitHubRepoAdapter.updateList(list)
    }

    private fun handleError(throwable: Throwable) {
        Log.d("MainActivity", throwable.message)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}