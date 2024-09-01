package ua.chernonog.smartshopper.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ua.chernonog.smartshopper.data.entity.ShoppingList

@Dao
interface ShoppingListDao {
    @Insert
    suspend fun addShoppingList(shoppingList: ShoppingList)

    @Query("DELETE from shopping_list WHERE id= :id")
    suspend fun deleteShoppingListItem(id: Int)

    @Update
    suspend fun updateShoppingList(item: ShoppingList)

    @Query("SELECT * FROM shopping_list")
    fun getAllShoppingList(): Flow<List<ShoppingList>>
}
