package ua.chernonog.smartshopper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.chernonog.smartshopper.db.MainDatabase
import ua.chernonog.smartshopper.entity.NoteItem

class MainViewModel(database: MainDatabase) : ViewModel() {
    private val dao = database.getDao()
    private val allNotes = dao.getAllNoteItems().asLiveData()

    fun insertNote(noteItem: NoteItem) = viewModelScope.launch {
        dao.addNoteItem(noteItem)
    }

    class MainViewModelFactory(private val database: MainDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}
