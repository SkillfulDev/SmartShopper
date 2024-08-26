package ua.chernonog.smartshopper.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.databinding.ActivityNoteBinding
import ua.chernonog.smartshopper.entity.NoteItem
import ua.chernonog.smartshopper.fragment.NoteFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NoteActivity : AppCompatActivity() {
    private var noteItem: NoteItem? = null
    private lateinit var binding: ActivityNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        getNoteItem()
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
            R.id.makeBold -> makeTextBold()
            R.id.saveNote -> setResultForActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeTextBold() = with(binding) {
        val startPosition = edContent.selectionStart
        val endPosition = edContent.selectionEnd

        val existingStyles = edContent.text.getSpans(
            startPosition,
            endPosition,
            StyleSpan::class.java
        )
        val boldStyle: StyleSpan = StyleSpan(Typeface.BOLD)

        if (existingStyles.isNotEmpty()) {
            edContent.text.removeSpan(existingStyles[0])
        } else {
            edContent.text.setSpan(
                boldStyle,
                startPosition,
                endPosition,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            edContent.setSelection(startPosition)
        }
    }

    private fun getNoteItem() {
        val result =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(NoteFragment.EXISTING_NOTE_KEY, NoteItem::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getSerializableExtra(NoteFragment.EXISTING_NOTE_KEY)
            }
        if (result != null) {
            noteItem = result as NoteItem
            Log.d("MyLog", "${noteItem?.id}")
            fillNoteItemActivity(noteItem!!)
        }
    }

    private fun fillNoteItemActivity(noteItem: NoteItem) = with(binding) {
        edTitle.setText(noteItem.title)
        edContent.setText(noteItem.content)
    }

    private fun settingToolBar() {
        val noteToolBar = binding.tbNote
        setSupportActionBar(noteToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setResultForActivity() {
        val intent = Intent()
        if (noteItem != null) {
            intent.putExtra(NoteFragment.EXISTING_NOTE_KEY, updateNoteItem())
        } else {
            intent.putExtra(NoteFragment.NEW_NOTE_KEY, getNewNoteItem())
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun updateNoteItem(): NoteItem = with(binding) {
        return noteItem?.copy(
            title = edTitle.text.toString(),
            content = edContent.text.toString(),
            time = getCurrentTime()
        )!!
    }

    private fun getNewNoteItem(): NoteItem = with(binding) {
        return NoteItem(
            null,
            edTitle.text.toString(),
            edContent.text.toString(),
            getCurrentTime(),
            ""
        )
    }

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
}
