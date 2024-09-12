package ua.chernonog.smartshopper.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.data.entity.Item
import ua.chernonog.smartshopper.databinding.ShoppingItemBinding
import ua.chernonog.smartshopper.databinding.ShoppingLibraryItemBinding

class ShoppingItemAdapter(private val listener: Listener) : ListAdapter<
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
            holder.setItemData(getItem(position), listener)
        } else {
            holder.setLibraryItemData(getItem(position), listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun setItemData(item: Item, listener: Listener) {
            val binding = ShoppingItemBinding.bind(view)
            binding.apply {
                tvItemName.text = item.name
                tvItemInfo.isVisible = isItemInfoVisible(item.itemInfo)
                tvItemInfo.text = item.itemInfo
                checkBox.isChecked = item.isBought
                setPaintFlagAndColor(binding)
                checkBox.setOnClickListener {
                    listener.setCheckItem(item.copy(isBought = checkBox.isChecked))
                }
                ibEditItem.setOnClickListener {
                    listener.editItem(item)
                }
            }
        }

        fun setLibraryItemData(item: Item, listener: Listener) {
            val binding = ShoppingLibraryItemBinding.bind(view)
            binding.apply {
                tvItemName.text = item.name
                ibEditItem.setOnClickListener {
                    listener.updateLibraryItem(item)
                }
                ibDeleteLibraryItem.setOnClickListener {
                    listener.deleteLibraryItem(item.id!!)
                }
                itemView.setOnClickListener {
                    listener.addLibraryItemToList(item.name)
                }
            }
        }

        private fun setPaintFlagAndColor(binding: ShoppingItemBinding) {
            binding.apply {
                if (checkBox.isChecked) {
                    tvItemName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvItemName.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.picker_green
                        )
                    )
                    tvItemInfo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvItemInfo.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.picker_green
                        )
                    )
                } else {
                    tvItemName.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvItemName.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.black
                        )
                    )
                    tvItemInfo.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvItemInfo.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.grey
                        )
                    )
                }
            }
        }

        private fun isItemInfoVisible(content: String): Boolean {
            return content.isNotBlank()
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

    interface Listener {
        fun setCheckItem(item: Item)
        fun editItem(item: Item)
        fun updateLibraryItem(item: Item)
        fun deleteLibraryItem(id: Int)
        fun addLibraryItemToList(name: String)
    }
}
