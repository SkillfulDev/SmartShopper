package ua.chernonog.smartshopper.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import ua.chernonog.smartshopper.data.entity.Item
import ua.chernonog.smartshopper.databinding.ShoppingItemDialogBinding

object ShoppingItemDialog {

    fun createDialog(context: Context, item: Item, listener: Listener) {
        val alertDialog = AlertDialog.Builder(context).create()
        val binding = ShoppingItemDialogBinding.inflate(
            LayoutInflater
                .from(context)
        )

        binding.apply {
            edItemName.setText(item.name)
            edItemInfo.setText(item.itemInfo)
            if (item.itemType == 1) {
                edItemInfo.isVisible = false
            }
            btnSaveChanges.setOnClickListener {
                listener.onUpdateClick(
                    item.copy(
                        name = edItemName.text.toString(),
                        itemInfo = edItemInfo.text.toString()
                    )
                )
                alertDialog.dismiss()
            }
        }
        alertDialog.setView(binding.root)
        alertDialog.show()
        alertDialog.window?.setBackgroundDrawable(null)
    }

    interface Listener {
        fun onUpdateClick(item: Item)
    }
}
