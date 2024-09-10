package ua.chernonog.smartshopper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.chernonog.smartshopper.data.db.MainDatabase
import ua.chernonog.smartshopper.data.entity.ShoppingList

class ShoppingListViewModel(database: MainDatabase) : ViewModel() {
    private val shoppingListDao = database.getShoppingListDao()
    private val shoppingItemDao = database.getShoppingItemDao()

    fun getAllShoppingList(): LiveData<List<ShoppingList>> {
        return shoppingListDao.getAllShoppingList().asLiveData()
    }

    fun addShoppingList(shoppingList: ShoppingList) {
        viewModelScope.launch {
            shoppingListDao.addShoppingList(shoppingList)
        }
    }

    fun deleteShoppingItem(id: Int) = viewModelScope.launch {
        shoppingListDao.deleteShoppingListItem(id)
        shoppingItemDao.deleteItemsByShoppingListId(id)
    }

    fun updateShoppingList(item: ShoppingList) = viewModelScope.launch {
        shoppingListDao.updateShoppingList(item)
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
