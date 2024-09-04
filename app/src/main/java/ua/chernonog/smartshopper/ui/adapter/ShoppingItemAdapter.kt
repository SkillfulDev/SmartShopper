package ua.chernonog.smartshopper.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.data.entity.Item
import ua.chernonog.smartshopper.databinding.ShoppingItemBinding

class ShoppingItemAdapter : ListAdapter<
        Item,
        ShoppingItemAdapter.ItemViewHolder>(
    ShoppingItemDiffCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return if (viewType == 0) {
            ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.shopping_item,
                    parent,
                    false
                )
            )
        } else {
            ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.shopping_library_item,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (getItem(position).itemType == 0) {
            holder.setItemData(getItem(position))
        } else {
            holder.setLibraryItemData(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun setItemData(item: Item) {
            val binding = ShoppingItemBinding.bind(view)
            binding.apply {
                tvItemName.text = item.name
            }
        }

        fun setLibraryItemData(item: Item) {

        }
    }

    class ShoppingItemDiffCallBack : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}
