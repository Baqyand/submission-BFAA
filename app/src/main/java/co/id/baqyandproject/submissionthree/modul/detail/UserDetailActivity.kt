package co.id.baqyandproject.submissionthree.modul.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import co.id.baqyandproject.submissionthree.R
import co.id.baqyandproject.submissionthree.databinding.ActivityUserDetailBinding
import co.id.baqyandproject.submissionthree.model.UserGithub
import co.id.baqyandproject.submissionthree.modul.adapter.ViewPagerAdapter
import co.id.baqyandproject.submissionthree.modul.favorite.FavoriteActivity
import co.id.baqyandproject.submissionthree.modul.main.MainActivity
import co.id.baqyandproject.submissionthree.util.GitConst
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityUserDetailBinding
    private val binding get() = _binding
    private lateinit var username: String
    private lateinit var userDetail: UserGithub
    private lateinit var fromActivity: String
    private val userDetailViewModel: UserDetailViewModel by viewModels()
    private var isChecked = false

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setUpTheme()

        setPagerAdapter()
        isLoading()
        isError()
        setData()
        isSuccessAddFavorite()

        binding.fabAddFavorite.setOnClickListener {
            addFavUser()
        }

        supportActionBar?.elevation = 0f
    }

    private fun init() {
        username = intent.getStringExtra(GitConst.USERNAME).toString()
        fromActivity = intent.getStringExtra(
            GitConst.FROM
        ).toString()

        if (fromActivity == "search") {
            binding.fabAddFavorite.visibility = View.VISIBLE
            userDetailViewModel.getUser(username)
        } else {
            binding.fabAddFavorite.visibility = View.GONE
            userDetailViewModel.getUserFromDb(username)
        }

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar!!.title = "Github Detail"

    }

    private fun setPagerAdapter() {
        val sectionsPagerAdapter = ViewPagerAdapter(this, username)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        userDetailViewModel.userDetail.observe(this) { userGithub ->
            userDetail = userGithub
            binding.detailUsername.text = userGithub.username
            binding.detailName.text = userGithub.name
            binding.detailCompany.text = userGithub.company ?: GitConst.NULL_COMPANY
            binding.detailFollowing.text =
                "${getString(R.string.following)} ${userGithub.following}"
            binding.detailFollowers.text = "${getString(R.string.followers)} ${userGithub.follower}"
            binding.repository.text = "${getString(R.string.repository)} ${userGithub.repository}"

            Glide.with(binding.root)
                .load(userGithub.avatar)
                .centerCrop()
                .into(binding.detailAvatar)

        }
    }

    private fun isLoading() {
        userDetailViewModel.isLoading.observe(this) { showProgressBar ->
            if (showProgressBar) {
                binding.contentLoading.visibility = View.VISIBLE
                binding.contentOfUser.visibility = View.INVISIBLE
            } else {
                binding.contentLoading.visibility = View.GONE
                binding.contentOfUser.visibility = View.VISIBLE

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_user_detail, menu)
        if (fromActivity == "search") {
            menu.removeItem(R.id.delete_menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share_user_menu -> {
                val gitHubUrl = userDetail.reposUrl

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, gitHubUrl)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }
            R.id.delete_menu -> {
                userDetailViewModel.userDetail.value?.let {
                    userDetailViewModel.deleteUserFavorite(userDetail.id)
                }

                return true
            }
            R.id.background_menu -> {
                userDetailViewModel.setTheme(!isChecked)
                true
            }
            else -> true
        }
    }


    private fun setUpTheme() {
        userDetailViewModel.isDarkMode.observe(this) { isDarkMode ->
            isChecked = if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                false
            }
        }
    }

    private fun isError() {
        userDetailViewModel.isError.observe(this) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun addFavUser() {
        userDetailViewModel.addUserFavorite(userDetail)
    }

    private fun isSuccessAddFavorite() {
        userDetailViewModel.isSuccess.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        if (fromActivity == "search") {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this, FavoriteActivity::class.java))
            finish()
        }
    }
}