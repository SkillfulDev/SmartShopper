package ua.chernonog.smartshopper.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.databinding.NoteItemListBinding
import ua.chernonog.smartshopper.entity.NoteItem

class NoteItemAdapter :
    ListAdapter<NoteItem, NoteItemAdapter.NoteItemViewHolder>(NoteItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.note_item_list, parent, false)
        return NoteItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class NoteItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = NoteItemListBinding.bind(itemView)

        fun setData(item: NoteItem) = with(binding) {
            tvTitle.text = item.title
            tvContent.text = item.content
            tvTime.text = item.time
        }
    }

    class NoteItemDiffCallBack : DiffUtil.ItemCallback<NoteItem>() {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }
    }
}
