package ua.chernonog.smartshopper.data.db

import androidx.room.Dao
import androidx.room.Insert
import ua.chernonog.smartshopper.data.entity.ShoppingList

@Dao
interface ShoppingListDao {
    @Insert
    suspend fun addShoppingList(shoppingList: ShoppingList)
}
