package ua.chernonog.smartshopper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.chernonog.smartshopper.data.db.MainDatabase
import ua.chernonog.smartshopper.data.entity.NoteItem

class NoteItemViewModel(database: MainDatabase) : ViewModel() {
    private val noteItemDao = database.getNoteItemDao()
    val allNotes = noteItemDao.getAllNoteItems().asLiveData()

    fun insertNote(noteItem: NoteItem) = viewModelScope.launch {
        noteItemDao.addNoteItem(noteItem)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        noteItemDao.deleteNoteItems(id)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {
        noteItemDao.updateNoteItem(note)
    }

    class NoteItemViewModelFactory(private val database: MainDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteItemViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteItemViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}
