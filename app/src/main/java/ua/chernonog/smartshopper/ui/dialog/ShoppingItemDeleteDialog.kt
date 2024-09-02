package ua.chernonog.smartshopper.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import ua.chernonog.smartshopper.databinding.ShoppingListDeleteConfirmDialogBinding

object ShoppingItemDeleteDialog {
    fun createDeleteShoppingListDialog(context: Context, listener: Listener) {
        val binding = ShoppingListDeleteConfirmDialogBinding
            .inflate(LayoutInflater.from(context))
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setView(binding.root)
        binding.apply {
            btnDelete.setOnClickListener {
                listener.onClick()
                alertDialog.dismiss()
            }
            btnCancel.setOnClickListener {
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

    interface Listener {
        fun onClick()
    }
}
