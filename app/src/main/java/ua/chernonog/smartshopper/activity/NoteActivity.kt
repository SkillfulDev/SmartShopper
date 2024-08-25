package ua.chernonog.smartshopper.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.databinding.ActivityNoteBinding
import ua.chernonog.smartshopper.fragment.NoteFragment

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingToolBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.saveNote -> setResultForActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun settingToolBar() {
        val noteToolBar = binding.tbNote
        setSupportActionBar(noteToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setResultForActivity() {
        val intent = Intent()
        intent.putExtra(NoteFragment.TITLE_KEY, binding.edTitle.text.toString())
        intent.putExtra(NoteFragment.CONTENT_KEY, binding.edContent.text.toString())

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
