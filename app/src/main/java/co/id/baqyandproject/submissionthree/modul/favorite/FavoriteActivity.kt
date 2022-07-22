package co.id.baqyandproject.submissionthree.modul.favorite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.baqyandproject.submissionthree.R
import co.id.baqyandproject.submissionthree.databinding.ActivityFavoriteBinding
import co.id.baqyandproject.submissionthree.modul.adapter.FavoriteAdapter
import co.id.baqyandproject.submissionthree.modul.detail.UserDetailActivity
import co.id.baqyandproject.submissionthree.util.GitConst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private val favorite = "favorite"
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar!!.title = ""

        viewModel.getUser()

        showListFavorite()

        setUpTheme()

        setMenu()

        navigateToUserDetail()

        showIsSuccessDelete()

        showErrorMessage()

    }

    private fun showListFavorite() {
        viewModel.listFavorites.observe(this) { list ->
            list.let {
                if (list.isNotEmpty()) {
                    val adapter = FavoriteAdapter(
                        list,
                        { username ->
                            viewModel.onItemsClicked(username)
                        },
                        { id ->
                            viewModel.deleteUser(id)
                        }
                    )
                    LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false
                    ).also { binding.rvUsersGithub.layoutManager = it }

                    binding.rvUsersGithub.adapter = adapter
                    binding.rvUsersGithub.visibility = View.VISIBLE

                } else {
                    binding.rvUsersGithub.visibility = View.GONE
                    binding.llNoData.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun navigateToUserDetail() {
        viewModel.navigateToUserDetail.observe(this) { username ->
            username?.let {
                val toUserDetail = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                toUserDetail.putExtra(GitConst.USERNAME, username)
                toUserDetail.putExtra(GitConst.FROM, favorite)
                startActivity(toUserDetail)
                viewModel.onNavigate()
                finish()
            }
        }
    }

    private fun showIsSuccessDelete() {
        viewModel.isSuccessDelete.observe(this) { message ->
            message.let {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                viewModel.getUser()
            }
        }
    }

    private fun showErrorMessage() {
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setMenu() {
        val menuHost: MenuHost = this@FavoriteActivity

        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_home, menu)
                    menu.removeItem(R.id.favorite_menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.background_menu -> {
                            viewModel.setTheme(!isChecked)
                            true
                        }
                        else -> false
                    }
                }
            }, this, Lifecycle.State.RESUMED
        )
    }

    private fun setUpTheme() {
        viewModel.isDarkMode.observe(this) { isDarkMode ->
            isChecked = if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                false
            }
        }
    }
}