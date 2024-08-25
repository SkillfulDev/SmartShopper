package ua.chernonog.smartshopper.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.chernonog.smartshopper.activity.MainApp
import ua.chernonog.smartshopper.activity.NoteActivity
import ua.chernonog.smartshopper.databinding.FragmentNoteBinding
import ua.chernonog.smartshopper.viewmodel.MainViewModel

class NoteFragment : BaseFragment() {
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }
    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()
    }

    override fun onClickAdd() {
        startActivity(Intent(activity, NoteActivity::class.java))

    }
}
