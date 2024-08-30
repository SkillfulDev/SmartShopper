package ua.chernonog.smartshopper.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.data.entity.NoteItem
import ua.chernonog.smartshopper.databinding.ActivityNoteBinding
import ua.chernonog.smartshopper.ui.fragment.NoteItemFragment
import ua.chernonog.smartshopper.util.DataTimeUtil
import ua.chernonog.smartshopper.util.data.utils.HtmlManager
import ua.chernonog.smartshopper.util.ui.utils.ColorPickerTouchListener

class NoteActivity : AppCompatActivity() {
    private var noteItem: NoteItem? = null
    private lateinit var binding: ActivityNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        getNoteItem()
        setContentView(binding.root)
        settingToolBar()
        setUpColorPickerTouchListener()
        onClickColorPicker()
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
            R.id.changeColor ->
                if (binding.clColorPicker.isShown) {
                    closeColorPicker()
                } else {
                    openColorPicker()
                }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onClickColorPicker() = with(binding) {
        imgBlue.setOnClickListener { makeTextColored(R.color.picker_blue) }
        imgRed.setOnClickListener { makeTextColored(R.color.picker_red) }
        imgBlack.setOnClickListener { makeTextColored(R.color.picker_black) }
        imgYellow.setOnClickListener { makeTextColored(R.color.picker_yellow) }
        imgOrange.setOnClickListener { makeTextColored(R.color.picker_orange) }
        imgGreen.setOnClickListener { makeTextColored(R.color.picker_green) }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpColorPickerTouchListener() {
        binding.clColorPicker.setOnTouchListener(ColorPickerTouchListener())
    }

    private fun openColorPicker() {
        binding.clColorPicker.visibility = View.VISIBLE
        val openAnimation = AnimationUtils.loadAnimation(
            this,
            R.anim.open_color_picker
        )
        binding.clColorPicker.startAnimation(openAnimation)
    }

    private fun closeColorPicker() {
        val closeAnimation = AnimationUtils.loadAnimation(
            this,
            R.anim.close_color_picker
        )
        closeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.clColorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        binding.clColorPicker.startAnimation(closeAnimation)
    }

    private fun makeTextBold() = with(binding) {
        val startPosition = edContent.selectionStart
        val endPosition = edContent.selectionEnd

        val existingStyles = edContent.text.getSpans(
            startPosition,
            endPosition,
            StyleSpan::class.java
        )
        val boldStyle = StyleSpan(Typeface.BOLD)

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

    private fun makeTextColored(colorId: Int) = with(binding) {
        val startPosition = edContent.selectionStart
        val endPosition = edContent.selectionEnd

        val existingStyles = edContent.text.getSpans(
            startPosition,
            endPosition,
            ForegroundColorSpan::class.java
        )
        if (existingStyles.isNotEmpty()) {
            edContent.text.removeSpan(existingStyles[0])
        }
        edContent.text.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(this@NoteActivity, colorId)
            ),
            startPosition,
            endPosition,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        edContent.setSelection(startPosition)

    }

    private fun getNoteItem() {
        val result =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(
                    NoteItemFragment.EXISTING_NOTE_KEY,
                    NoteItem::class.java
                )
            } else {
                @Suppress("DEPRECATION")
                intent.getSerializableExtra(NoteItemFragment.EXISTING_NOTE_KEY)
            }
        if (result != null) {
            noteItem = result as NoteItem
            fillNoteItemActivity(noteItem!!)
        }
    }

    private fun fillNoteItemActivity(noteItem: NoteItem) = with(binding) {
        edTitle.setText(noteItem.title)
        edContent.setText(HtmlManager.convertHtmlStringToSpanned(noteItem.content))
    }

    private fun settingToolBar() {
        val noteToolBar = binding.tbNote
        setSupportActionBar(noteToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setResultForActivity() {
        val intent = Intent()
        if (noteItem != null) {
            intent.putExtra(NoteItemFragment.EXISTING_NOTE_KEY, updateNoteItem())
        } else {
            intent.putExtra(NoteItemFragment.NEW_NOTE_KEY, getNewNoteItem())
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun updateNoteItem(): NoteItem = with(binding) {
        return noteItem?.copy(
            title = edTitle.text.toString(),
            content = HtmlManager.convertSpannedToHtmlString(edContent.text).trim(),
            time = DataTimeUtil.getCurrentTime()
        )!!
    }

    private fun getNewNoteItem(): NoteItem = with(binding) {
        return NoteItem(
            null,
            edTitle.text.toString(),
            HtmlManager.convertSpannedToHtmlString(edContent.text).trim(),
            DataTimeUtil.getCurrentTime(),
            ""
        )
    }
}
