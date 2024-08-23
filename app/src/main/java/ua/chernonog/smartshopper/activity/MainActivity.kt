package ua.chernonog.smartshopper.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.databinding.ActivityMainBinding
import ua.chernonog.smartshopper.fragment.FragmentManager
import ua.chernonog.smartshopper.fragment.NoteFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBottomNavigationListener()
    }

    private fun onBottomNavigationListener() {
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> Log.d("MyLog", "Settings")
                R.id.notes -> FragmentManager.setFragment(NoteFragment.newInstance(), this)
                R.id.shopList -> Log.d("MyLog", "Shop List")
                R.id.item -> Log.d("MyLog", "Item")
            }
            true
        }
    }
}
