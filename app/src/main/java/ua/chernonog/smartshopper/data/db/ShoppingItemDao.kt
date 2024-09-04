package ua.chernonog.smartshopper.data.db

import androidx.room.Dao
import androidx.room.Insert
import ua.chernonog.smartshopper.data.entity.Item

@Dao
interface ShoppingItemDao {
    @Insert
    suspend fun addShoppingItem(item: Item)
}
