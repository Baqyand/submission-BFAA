package co.id.baqyandproject.submissiontwo.modul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.baqyandproject.submissiontwo.databinding.ItemUserBinding
import co.id.baqyandproject.submissiontwo.model.UserGithub
import com.bumptech.glide.Glide

class FollowAdapter(
    private val list: List<UserGithub>
) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsItem = list[position]
        holder.itemBinding.txtName.text = itemsItem.username
        holder.itemBinding.txtUrl.text = itemsItem.reposUrl

        holder.itemBinding
        Glide.with(holder.itemBinding.root)
            .load(itemsItem.avatar)
            .centerCrop()
            .into(holder.itemBinding.ivUser)
    }

    override fun getItemCount(): Int = list.size
}