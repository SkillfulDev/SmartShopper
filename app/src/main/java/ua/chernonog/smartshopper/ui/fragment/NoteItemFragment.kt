package ua.chernonog.smartshopper.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ua.chernonog.smartshopper.data.entity.NoteItem
import ua.chernonog.smartshopper.databinding.FragmentNoteBinding
import ua.chernonog.smartshopper.ui.activity.MainApp
import ua.chernonog.smartshopper.ui.activity.NoteActivity
import ua.chernonog.smartshopper.ui.adapter.NoteItemAdapter
import ua.chernonog.smartshopper.viewmodel.NoteItemViewModel

class NoteItemFragment : BaseFragment(), NoteItemAdapter.Listener {
    companion object {
        const val NEW_NOTE_KEY = "note"
        const val EXISTING_NOTE_KEY = "exist"

        @JvmStatic
        fun newInstance() = NoteItemFragment()
    }

    private val noteItemViewModel: NoteItemViewModel by activityViewModels {
        NoteItemViewModel.NoteItemViewModelFactory((context?.applicationContext as MainApp).database)
    }

    private lateinit var binding: FragmentNoteBinding
    private lateinit var noteActivityLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteItemAdapter

    override fun deleteNoteItem(id: Int) {
        noteItemViewModel.deleteNote(id)
    }

    override fun updateNoteItem(noteItem: NoteItem) {
        val intent = Intent(activity, NoteActivity::class.java)
        intent.putExtra(EXISTING_NOTE_KEY, noteItem)
        noteActivityLauncher.launch(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initNoteAdapter()
        dataObserver()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        noteActivityLauncherInit()
        super.onCreate(savedInstanceState)
    }

    override fun onClickAdd() {
        noteActivityLauncher.launch(Intent(activity, NoteActivity::class.java))
    }

    private fun noteActivityLauncherInit() {
        noteActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data?.hasExtra(NEW_NOTE_KEY) == true) {
                    noteItemViewModel.insertNote(getNoteItemType(it, NEW_NOTE_KEY))
                } else {
                    noteItemViewModel.updateNote(getNoteItemType(it, EXISTING_NOTE_KEY))
                }
            }
        }
    }

    private fun initNoteAdapter() = with(binding) {
        rvNote.layoutManager = LinearLayoutManager(activity)
        adapter = NoteItemAdapter(this@NoteItemFragment)
        rvNote.adapter = adapter
    }

    private fun dataObserver() {
        noteItemViewModel.allNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun getNoteItemType(result: ActivityResult, type: String): NoteItem {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            result.data?.getSerializableExtra(type, NoteItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            result.data?.getSerializableExtra(type) as? NoteItem
        }!!
    }
}
