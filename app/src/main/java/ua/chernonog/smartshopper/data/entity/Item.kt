package ua.chernonog.smartshopper.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "itemInfo")
    val itemInfo: String?,
    @ColumnInfo(name = "isBought")
    val isBought: Boolean = false,
    @ColumnInfo(name = "shoppingListId")
    val shoppingListId: Int,
    @ColumnInfo(name = "itemType")
    val itemType: Int = 0
)
