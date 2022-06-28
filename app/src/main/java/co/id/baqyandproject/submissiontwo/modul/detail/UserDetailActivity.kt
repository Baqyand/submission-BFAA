package co.id.baqyandproject.submissiontwo.modul.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import co.id.baqyandproject.submissiontwo.R
import co.id.baqyandproject.submissiontwo.databinding.ActivityUserDetailBinding
import co.id.baqyandproject.submissiontwo.modul.adapter.ViewPagerAdapter
import co.id.baqyandproject.submissiontwo.util.GitConst
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityUserDetailBinding
    private val binding get() = _binding
    private lateinit var username: String
    private val userDetailViewModel: UserDetailViewModel by viewModels()

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
        username = intent.getStringExtra(GitConst.USERNAME).toString()

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar!!.title = ""

        userDetailViewModel.getUser(username)

        val sectionsPagerAdapter = ViewPagerAdapter(this, username)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        isLoading()
        isError()
        setData()


        supportActionBar?.elevation = 0f
    }

    private fun setData() {

        userDetailViewModel.userDetail.observe(this) { userGithub ->

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

    private fun isError() {
        userDetailViewModel.isError.observe(this) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}