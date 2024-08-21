package ua.chernonog.smartshopper.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.chernonog.smartshopper.entity.NoteItem

@Dao
interface Dao {
    @Insert
    suspend fun addNoteItem(item: NoteItem)

    @Query("SELECT * FROM note_item")
    fun getAllNoteItems(): Flow<List<NoteItem>>
}
