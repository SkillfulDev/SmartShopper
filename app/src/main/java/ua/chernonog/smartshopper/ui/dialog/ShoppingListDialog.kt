package ua.chernonog.smartshopper.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.databinding.ShoppingListDialogBinding

object ShoppingListDialog {

    fun setUpShoppingDialog(context: Context, listener: Listener, name: String) {
        val dialog: AlertDialog?
        val binding = ShoppingListDialogBinding
            .inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context).setView(binding.root)
        dialog = builder.create()

        binding.apply {
            if (name.isNotEmpty()) {
                setUpdateContent(context, name)
            }
            btnSetListName.setOnClickListener {
                val listName = edListName.text.toString()
                if (listName.isNotEmpty()) {
                    listener.onClick(listName)
                }
                dialog.dismiss()
            }
        }
        dialog.show()
        dialog.window?.setBackgroundDrawable(null)
    }

    private fun ShoppingListDialogBinding.setUpdateContent(
        context: Context,
        name: String
    ) {
        tvListTitle.text = context.getString(R.string.rename_shopping_list)
        edListName.setText(name)
        btnSetListName.text = context.getString(R.string.update_button)
    }

    interface Listener {
        fun onClick(name: String)
    }
}
