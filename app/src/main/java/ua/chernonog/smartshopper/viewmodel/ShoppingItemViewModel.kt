package ua.chernonog.smartshopper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.chernonog.smartshopper.data.db.MainDatabase
import ua.chernonog.smartshopper.data.entity.Item

class ShoppingItemViewModel(database: MainDatabase) : ViewModel() {
    private val shoppingItemDao = database.getShoppingItemDao()

    fun addShoppingItem(item: Item) = viewModelScope.launch {
        shoppingItemDao.addShoppingItem(item)
    }

    fun getAllItems(shoppingListId: Int): LiveData<List<Item>> {
        return shoppingItemDao.getAllItems(shoppingListId).asLiveData()
    }

    fun updateShoppingItem(item: Item) = viewModelScope.launch {
        shoppingItemDao.updateShoppingItem(item)
    }

    fun clearItemsFromList(shoppingListId: Int) = viewModelScope.launch {
        shoppingItemDao.deleteItemsByShoppingListId(shoppingListId)
    }

    class ShoppingItemViewModelFactory(private val database: MainDatabase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShoppingItemViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ShoppingItemViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}
