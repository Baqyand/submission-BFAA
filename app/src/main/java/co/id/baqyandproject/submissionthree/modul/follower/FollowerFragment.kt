package co.id.baqyandproject.submissionthree.modul.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import co.id.baqyandproject.submissionthree.databinding.FragmentFollowerBinding
import co.id.baqyandproject.submissionthree.modul.adapter.FollowAdapter
import co.id.baqyandproject.submissionthree.util.GitConst
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowerViewModel by viewModels()

    fun newInstance(bundle: Bundle): FollowerFragment {
        val fragment = FollowerFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(layoutInflater)
        if (arguments != null) {
            requireArguments().getString(GitConst.USERNAME)?.let { username ->
                viewModel.getUsers(username)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUserWereFollow()
        showIsLoading()
        showIsError()
    }

    private fun showUserWereFollow() {
        viewModel.userWhereFollowers.observe(viewLifecycleOwner) {

            if (it.isNotEmpty()) {
                binding.rvFollowersList.adapter = FollowAdapter(it)
                binding.llNoData.visibility = View.GONE
            } else {
                binding.llNoData.visibility = View.VISIBLE

            }
        }
    }

    private fun showIsLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressCircularFollowers.visibility = View.VISIBLE
            } else {
                binding.progressCircularFollowers.visibility = View.GONE
            }
        }
    }

    private fun showIsError() {
        viewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }

}