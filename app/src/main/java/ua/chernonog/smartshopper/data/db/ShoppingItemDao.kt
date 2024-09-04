package ua.chernonog.smartshopper.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.chernonog.smartshopper.data.entity.Item

@Dao
interface ShoppingItemDao {
    @Insert
    suspend fun addShoppingItem(item: Item)

    @Query("SELECT * FROM items WHERE shoppingListId = :shoppingListId")
    fun getAllItems(shoppingListId: Int): Flow<List<Item>>
}
