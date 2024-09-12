package ua.chernonog.smartshopper.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ua.chernonog.smartshopper.data.entity.LibraryItem

@Dao
interface LibraryItemDao {
    @Insert
    suspend fun addLibraryItem(libraryItem: LibraryItem)

    @Query("SELECT * FROM library WHERE name =:name")
    suspend fun getListLibraryItemByName(name: String): List<LibraryItem>

    @Query("SELECT * FROM library WHERE name LIKE :name")
    suspend fun getListLibraryItemLike(name: String): List<LibraryItem>

    @Update
    suspend fun updateLibraryItem(libraryItem: LibraryItem)

    @Query("DELETE FROM library WHERE id =:id")
    suspend fun deleteLibraryItem(id: Int)
}
