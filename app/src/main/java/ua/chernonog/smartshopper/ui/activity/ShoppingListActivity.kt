package ua.chernonog.smartshopper.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.data.entity.ShoppingList
import ua.chernonog.smartshopper.databinding.ActivityShoppingListBinding
import ua.chernonog.smartshopper.ui.fragment.ShoppingListFragment
import ua.chernonog.smartshopper.viewmodel.ShoppingListViewModel

class ShoppingListActivity : AppCompatActivity() {
    private val shoppingListViewModel: ShoppingListViewModel by viewModels {
        ShoppingListViewModel.ShoppingListViewModelFactory(
            (applicationContext
                    as MainApp).database
        )
    }
    private var shoppingList: ShoppingList? = null
    private lateinit var saveMenuItem: MenuItem
    private lateinit var binding: ActivityShoppingListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setToolbar()
    }

    private fun setToolbar() = with(binding) {
        setSupportActionBar(tbShoppingList)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_list_menu, menu)
        saveMenuItem = menu?.findItem(R.id.saveShoppingItem)!!
        val addMenuItem = menu.findItem(R.id.addShoppingItem)
        addMenuItem.setOnActionExpandListener(expandActionView())
        saveMenuItem.isVisible = false
        return true
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                saveMenuItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                saveMenuItem.isVisible = false
                invalidateMenu()
                return true
            }
        }
    }

    private fun init() = with(binding) {
        shoppingList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(
                ShoppingListFragment.SHOPPING_LIST_ITEM_KEY,
                ShoppingList::class.java
            )
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(
                ShoppingListFragment.SHOPPING_LIST_ITEM_KEY
            ) as ShoppingList
        }
        tvTest.text = shoppingList?.name
    }
}
