package ua.chernonog.smartshopper.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.settings_place_holder, SettingsFragment()
            ).commit()
        }
        settingToolBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    private fun settingToolBar() {
        val noteToolBar = binding.tbMain
        setSupportActionBar(noteToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
