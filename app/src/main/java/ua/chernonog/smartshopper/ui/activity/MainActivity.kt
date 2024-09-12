package ua.chernonog.smartshopper.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.databinding.ActivityMainBinding
import ua.chernonog.smartshopper.settings.SettingsActivity
import ua.chernonog.smartshopper.ui.fragment.FragmentManager
import ua.chernonog.smartshopper.ui.fragment.NoteItemFragment
import ua.chernonog.smartshopper.ui.fragment.ShoppingListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBottomNavigationListener()
        settingToolBar()
        setStartingFragment()
    }

    private fun setStartingFragment() {
        FragmentManager.setFragment(NoteItemFragment.newInstance(), this)
    }

    private fun settingToolBar() {
        val noteToolBar = binding.tbMain
        setSupportActionBar(noteToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.bnvMain.selectedItemId = R.id.notes
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    private fun onBottomNavigationListener() {
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> startActivity(
                    Intent(
                        this,
                        SettingsActivity::class.java
                    )
                )

                R.id.notes -> FragmentManager.setFragment(
                    NoteItemFragment.newInstance(),
                    this
                )

                R.id.shopList -> FragmentManager.setFragment(
                    ShoppingListFragment.newInstance(),
                    this
                )

                R.id.add -> {
                    FragmentManager.currentFragment?.onClickAdd()
                }
            }
            true
        }
    }
}
