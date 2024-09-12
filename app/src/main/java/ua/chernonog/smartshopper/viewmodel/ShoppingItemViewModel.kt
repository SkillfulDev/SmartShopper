package ua.chernonog.smartshopper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.chernonog.smartshopper.data.db.MainDatabase
import ua.chernonog.smartshopper.data.entity.Item
import ua.chernonog.smartshopper.data.entity.LibraryItem

class ShoppingItemViewModel(database: MainDatabase) : ViewModel() {
    val libraryItems = MutableLiveData<List<LibraryItem>>()
    private val shoppingItemDao = database.getShoppingItemDao()
    private val libraryItemDao = database.getLibraryItemDao()

    fun addShoppingItem(item: Item) = viewModelScope.launch {
        shoppingItemDao.addShoppingItem(item)
        if (libraryItemDao.getListLibraryItemByName(item.name).isEmpty()) {
            libraryItemDao.addLibraryItem(LibraryItem(null, item.name))
        }
    }

    fun updateLibraryItem(libraryItem: LibraryItem) = viewModelScope.launch {
        libraryItemDao.updateLibraryItem(libraryItem)
    }

    fun deleteLibraryItem(id: Int) = viewModelScope.launch {
        libraryItemDao.deleteLibraryItem(id)
    }

    fun getAllItems(shoppingListId: Int): LiveData<List<Item>> {
        return shoppingItemDao.getAllItems(shoppingListId).asLiveData()
    }

    fun getAllLibraryItem(name: String) = viewModelScope.launch {
        libraryItems.postValue(libraryItemDao.getListLibraryItemLike(name))
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
