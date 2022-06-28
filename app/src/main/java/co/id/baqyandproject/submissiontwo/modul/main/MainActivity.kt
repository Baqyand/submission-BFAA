package co.id.baqyandproject.submissiontwo.modul.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.baqyandproject.submissiontwo.databinding.ActivityMainBinding
import co.id.baqyandproject.submissiontwo.modul.adapter.UserGithubAdapter
import co.id.baqyandproject.submissiontwo.modul.detail.UserDetailActivity
import co.id.baqyandproject.submissiontwo.util.GitConst
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        setUpSearchView()

        viewModel.listUser.observe(this) { items ->
            if (items.isNotEmpty()) {
                val adapter =
                    UserGithubAdapter(items) { username -> viewModel.onItemsClicked(username) }
                binding.rvUsersGithub.adapter = adapter
                LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false
                ).also { binding.rvUsersGithub.layoutManager = it }

                binding.llNoData.visibility = View.GONE
                binding.rvUsersGithub.visibility = View.VISIBLE

            } else {
                binding.rvUsersGithub.visibility = View.GONE
                binding.llNoData.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(this) { showProgressBar ->
            if (showProgressBar) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()

        }
        viewModel.navigateToUserDetail.observe(this) { username ->
            username.let {
                val toUserDetail = Intent(this@MainActivity, UserDetailActivity::class.java)
                toUserDetail.putExtra(GitConst.USERNAME, username)
                startActivity(toUserDetail)
            }
        }


    }

    private fun setUpSearchView() {
        binding.svSearchUsers.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.setFindUser(it)
                } ?: return false

                binding.svSearchUsers.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }
}