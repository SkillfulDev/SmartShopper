package ua.chernonog.smartshopper.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ua.chernonog.smartshopper.data.entity.NoteItem

@Dao
interface NoteItemDao {
    @Query("SELECT * FROM note_item")
    fun getAllNoteItems(): Flow<List<NoteItem>>

    @Insert
    suspend fun addNoteItem(item: NoteItem)

    @Update
    suspend fun updateNoteItem(item: NoteItem)

    @Query("DELETE FROM note_item WHERE id IS :id")
    suspend fun deleteNoteItems(id: Int)
}
