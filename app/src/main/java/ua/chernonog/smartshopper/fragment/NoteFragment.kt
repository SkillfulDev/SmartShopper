package ua.chernonog.smartshopper.fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ua.chernonog.smartshopper.activity.MainApp
import ua.chernonog.smartshopper.activity.NoteActivity
import ua.chernonog.smartshopper.databinding.FragmentNoteBinding
import ua.chernonog.smartshopper.entity.NoteItem
import ua.chernonog.smartshopper.util.NoteItemAdapter
import ua.chernonog.smartshopper.viewmodel.MainViewModel

class NoteFragment : BaseFragment() {
    companion object {
        const val NEW_NOTE_KEY = "note"

        @JvmStatic
        fun newInstance() = NoteFragment()
    }

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }
    private lateinit var binding: FragmentNoteBinding

    private lateinit var noteActivityLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteItemAdapter

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
                val noteItem: NoteItem? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra(NEW_NOTE_KEY, NoteItem::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.data?.getSerializableExtra(NEW_NOTE_KEY) as? NoteItem
                }
                mainViewModel.insertNote(noteItem!!)
            }
        }
    }

    private fun initNoteAdapter() = with(binding) {
        rvNote.layoutManager = LinearLayoutManager(activity)
        adapter = NoteItemAdapter()
        rvNote.adapter = adapter
    }

    private fun dataObserver() {
        mainViewModel.allNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
