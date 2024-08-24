package ua.chernonog.smartshopper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ua.chernonog.smartshopper.entity.Item
import ua.chernonog.smartshopper.entity.LibraryItem
import ua.chernonog.smartshopper.entity.NoteItem
import ua.chernonog.smartshopper.entity.ShoppingList

@Database(
    entities = [
        Item::class,
        LibraryItem::class,
        NoteItem::class,
        ShoppingList::class
    ], version = 1
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun getDao(): Dao

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
                return instance
            }
        }
    }
}
