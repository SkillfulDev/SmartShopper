package ua.chernonog.smartshopper.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ua.chernonog.smartshopper.data.entity.Item
import ua.chernonog.smartshopper.data.entity.LibraryItem
import ua.chernonog.smartshopper.data.entity.NoteItem
import ua.chernonog.smartshopper.data.entity.ShoppingList

@Database(
    entities = [
        Item::class,
        LibraryItem::class,
        NoteItem::class,
        ShoppingList::class
    ], version = 1
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun getNoteItemDao(): NoteItemDao
    abstract fun getShoppingListDao(): ShoppingListDao
    abstract fun getShoppingItemDao(): ShoppingItemDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDbInstance(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        MainDatabase::class.java,
                        "shopping_list.db"
                    ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
