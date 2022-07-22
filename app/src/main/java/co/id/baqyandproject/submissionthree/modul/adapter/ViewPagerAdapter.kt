package co.id.baqyandproject.submissionthree.modul.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.id.baqyandproject.submissionthree.modul.follower.FollowerFragment
import co.id.baqyandproject.submissionthree.modul.following.FollowingFragment
import co.id.baqyandproject.submissionthree.util.GitConst

class ViewPagerAdapter(
    activity: AppCompatActivity,
    val username: String
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val bundle = Bundle()
        bundle.putString(GitConst.USERNAME, username)
        when (position) {
            0 -> fragment = FollowerFragment().newInstance(bundle)
            1 -> fragment = FollowingFragment().newInstance(bundle)
        }
        return fragment as Fragment
    }
}
