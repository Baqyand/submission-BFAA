package co.id.baqyandproject.submissiontwo.modul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.baqyandproject.submissiontwo.databinding.ItemUserBinding
import co.id.baqyandproject.submissiontwo.model.ItemsItem
import com.bumptech.glide.Glide


class UserGithubAdapter(
    private val list: List<ItemsItem>,
    private val clickListener: (username: String) -> Unit
) : RecyclerView.Adapter<UserGithubAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsItem = list[position]
        holder.itemBinding.txtName.text = itemsItem.login
        holder.itemBinding.txtUrl.text = itemsItem.url

        holder.itemBinding
        Glide.with(holder.itemBinding.root)
            .load(itemsItem.avatarUrl)
            .centerCrop()
            .into(holder.itemBinding.ivUser)

        holder.itemBinding.btnToDetail.setOnClickListener {
            clickListener(itemsItem.login)
        }
    }


    override fun getItemCount(): Int = list.size
}