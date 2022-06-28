package co.id.baqyandproject.submissiontwo.modul.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import co.id.baqyandproject.submissiontwo.databinding.FragmentFollowingBinding
import co.id.baqyandproject.submissiontwo.modul.adapter.FollowAdapter
import co.id.baqyandproject.submissiontwo.util.GitConst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowingViewModel by viewModels()


    fun newInstance(bundle: Bundle): FollowingFragment {
        val fragment = FollowingFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(layoutInflater)

        if (arguments != null) {
            requireArguments().getString(GitConst.USERNAME)?.let { username ->
                viewModel.getUsers(username)
            }
        }

        viewModel.usersWhereFollow.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.rvFollowersList.adapter = FollowAdapter(it)
                binding.llNoData.visibility = View.GONE
                binding.rvFollowersList.visibility = View.VISIBLE
            } else {
                binding.rvFollowersList.visibility = View.GONE
                binding.llNoData.visibility = View.VISIBLE

            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressCircularFollowers.visibility = View.VISIBLE
            } else {
                binding.progressCircularFollowers.visibility = View.GONE
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

}