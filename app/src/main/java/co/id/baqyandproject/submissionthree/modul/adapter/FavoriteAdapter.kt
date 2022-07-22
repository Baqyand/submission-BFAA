package co.id.baqyandproject.submissionthree.modul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.baqyandproject.submissionthree.data.database.model.CacheUserGithubModel
import co.id.baqyandproject.submissionthree.databinding.ItemUserFavoriteBinding
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private val list: List<CacheUserGithubModel>,
    private val clickListener: (username: String?) -> Unit,
    private val deleteLister: (id: Int) -> Unit
) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemUserFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        holder.itemBinding.btnToDetail.setOnClickListener {
            clickListener(itemsItem.username)
        }

        holder.itemBinding.btnDelete.setOnClickListener {
            deleteLister(itemsItem.id)
        }
    }

    override fun getItemCount(): Int = list.size
}