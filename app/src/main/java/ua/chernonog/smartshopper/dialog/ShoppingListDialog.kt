package ua.chernonog.smartshopper.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import ua.chernonog.smartshopper.databinding.ShoppingListDialogBinding

object ShoppingListDialog {

    fun setUpShoppingDialog(context: Context, listener: Listener) {
        val dialog: AlertDialog?
        val binding = ShoppingListDialogBinding
            .inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context).setView(binding.root)
        dialog = builder.create()
        binding.apply {
            btnSetListName.setOnClickListener {
                val listName = edListName.text.toString()
                if (listName.isNotEmpty()) {
                    listener.addShoppingList(listName)
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    interface Listener {
        fun addShoppingList(name: String)
    }
}
