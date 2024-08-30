package ua.chernonog.smartshopper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.chernonog.smartshopper.data.db.MainDatabase
import ua.chernonog.smartshopper.data.entity.ShoppingList

class ShoppingListViewModel(database: MainDatabase) : ViewModel() {
    private val shoppingListDao = database.getShoppingListDao()

    fun addShoppingList(shoppingList: ShoppingList) {
        viewModelScope.launch {
            shoppingListDao.addShoppingList(shoppingList)
        }
    }

    class ShoppingListViewModelFactory(private val database: MainDatabase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShoppingListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ShoppingListViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}
