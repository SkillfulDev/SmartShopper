package ua.chernonog.smartshopper.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "shopping_list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "totalItems")
    val totalItems: Int,
    @ColumnInfo(name = "boughtItems")
    val boughtItems: Int,
    @ColumnInfo(name = "itemsIds")
    val itemsIds: String
) : Serializable
