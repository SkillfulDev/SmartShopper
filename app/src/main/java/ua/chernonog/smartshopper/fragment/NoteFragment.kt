package ua.chernonog.smartshopper.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import ua.chernonog.smartshopper.activity.MainApp
import ua.chernonog.smartshopper.activity.NoteActivity
import ua.chernonog.smartshopper.databinding.FragmentNoteBinding
import ua.chernonog.smartshopper.viewmodel.MainViewModel

class NoteFragment : BaseFragment() {
    companion object {
        const val TITLE_KEY = "title"
        const val CONTENT_KEY = "content"

        @JvmStatic
        fun newInstance() = NoteFragment()
    }

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }
    private lateinit var binding: FragmentNoteBinding

    private lateinit var noteActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
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
                val title = it.data?.getStringExtra(TITLE_KEY)
                val content = it.data?.getStringExtra(CONTENT_KEY)
                Log.d("MyLog", "$title  and $content")
            }
        }
    }
}
