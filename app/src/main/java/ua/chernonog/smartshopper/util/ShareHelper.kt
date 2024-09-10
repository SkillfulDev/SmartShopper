package ua.chernonog.smartshopper.util

import android.content.Intent
import ua.chernonog.smartshopper.data.entity.Item

object ShareHelper {

    fun shareList(shoppingList: List<Item>, listName: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plane"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeSharedText(shoppingList, listName))
        }
        return intent
    }

    private fun makeSharedText(shoppingList: List<Item>, listName: String): String {
        val sharedText = StringBuilder()
        sharedText.append("## - $listName - ##").append("\n")
        var itemCounter = 1
        shoppingList.forEach {
            sharedText.append("${itemCounter++} -- ${it.name} // ${it.itemInfo}").append("\n")
        }
        return sharedText.toString()
    }
}
