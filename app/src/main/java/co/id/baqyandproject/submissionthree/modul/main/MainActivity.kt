package co.id.baqyandproject.submissionthree.modul.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.baqyandproject.submissionthree.R
import co.id.baqyandproject.submissionthree.databinding.ActivityMainBinding
import co.id.baqyandproject.submissionthree.modul.adapter.UserGithubAdapter
import co.id.baqyandproject.submissionthree.modul.detail.UserDetailActivity
import co.id.baqyandproject.submissionthree.modul.favorite.FavoriteActivity
import co.id.baqyandproject.submissionthree.util.GitConst
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding
    private val viewModel: MainViewModel by viewModels()
    private val from = "search"
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        toolbar.inflateMenu(R.menu.menu_home)
        toolbar.navigationIcon = null

        viewModel.getThemeGit().observe(this) { isDarkMode ->
            (if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                false
            }).also { isChecked = it }

        }

        setUpSearchView()

        showListUserWereSearch()

        showIsLoading()

        showErrorMessage()

        navigateToUserDetail()

    }

    private fun showListUserWereSearch() {
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
    }

    private fun showIsLoading() {
        viewModel.isLoading.observe(this) { showProgressBar ->
            if (showProgressBar) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun navigateToUserDetail() {
        viewModel.navigateToUserDetail.observe(this) { username ->
            username?.let {
                val toUserDetail = Intent(this@MainActivity, UserDetailActivity::class.java)
                toUserDetail.putExtra(GitConst.USERNAME, username)
                toUserDetail.putExtra(GitConst.FROM, from)
                startActivity(toUserDetail)
                viewModel.onNavigate()
            }
        }

    }

    private fun showErrorMessage() {
        viewModel.errorMessage.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_menu -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                true
            }
            R.id.background_menu -> {
                viewModel.setTheme(!isChecked)
                true
            }
            else -> true
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