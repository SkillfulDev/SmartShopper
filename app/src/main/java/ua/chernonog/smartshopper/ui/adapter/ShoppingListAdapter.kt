package ua.chernonog.smartshopper.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.data.entity.ShoppingList
import ua.chernonog.smartshopper.databinding.ShoppingItemLlistBinding

class ShoppingListAdapter(private val listener: Listener) :
    ListAdapter<ShoppingList, ShoppingListAdapter.ShoppingListViewHolder>(
        ShoppingListDiffCallBack()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.shopping_item_llist, parent, false)
        return ShoppingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ShoppingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ShoppingItemLlistBinding.bind(itemView)

        fun setData(item: ShoppingList, listener: Listener) =
            with(binding) {
                tvName.text = item.name
                tvTime.text = item.time
                val colorState = ColorStateList.valueOf(
                    getColorForProgress(
                        item, binding.root.context
                    )
                )
                progressBar.max = item.totalItems
                progressBar.progress = item.boughtItems
                progressBar.progressTintList = colorState
                val counterText = "${item.boughtItems}/${item.totalItems}"
                tvPurchased.text = counterText
                ibDeleteShoppingList.setOnClickListener {
                    listener.deleteShoppingList(item.id!!)
                }
                ibEditShoppingList.setOnClickListener {
                    listener.editShoppingList(item)
                }
                itemView.setOnClickListener {
                    listener.onItemClick(item)
                }
            }

        private fun getColorForProgress(shoppingList: ShoppingList, context: Context): Int {
            return if (shoppingList.totalItems == shoppingList.boughtItems) {
                ContextCompat.getColor(context, R.color.picker_green)
            } else {
                ContextCompat.getColor(context, R.color.picker_red)
            }
        }
    }


    class ShoppingListDiffCallBack : DiffUtil.ItemCallback<ShoppingList>() {
        override fun areItemsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun deleteShoppingList(id: Int)
        fun editShoppingList(item: ShoppingList)
        fun onItemClick(item: ShoppingList)
    }
}
