package ua.chernonog.smartshopper.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.data.entity.Item
import ua.chernonog.smartshopper.data.entity.ShoppingList
import ua.chernonog.smartshopper.databinding.ActivityShoppingListBinding
import ua.chernonog.smartshopper.ui.adapter.ShoppingItemAdapter
import ua.chernonog.smartshopper.ui.fragment.ShoppingListFragment
import ua.chernonog.smartshopper.viewmodel.ShoppingItemViewModel

class ShoppingListActivity : AppCompatActivity(), ShoppingItemAdapter.Listener {
    private val shoppingItemViewModel: ShoppingItemViewModel by viewModels {
        ShoppingItemViewModel.ShoppingItemViewModelFactory(
            (applicationContext
                    as MainApp).database
        )
    }
    private var shoppingList: ShoppingList? = null
    private var edItem: EditText? = null
    private lateinit var saveMenuItem: MenuItem
    private lateinit var binding: ActivityShoppingListBinding
    private lateinit var adapter: ShoppingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setToolbar()
        rvInit()
        observeItemData()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_list_menu, menu)
        saveMenuItem = menu?.findItem(R.id.saveShoppingItem)!!
        val addMenuItem = menu.findItem(R.id.addShoppingItem)
        edItem = addMenuItem.actionView?.findViewById(R.id.edItem)!!
        addMenuItem.setOnActionExpandListener(expandActionView())
        saveMenuItem.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveShoppingItem -> createShoppingItem()
        }
        return true
    }

    override fun onClick(item: Item) {
        shoppingItemViewModel.updateShoppingItem(item)
    }

    private fun rvInit() = with(binding) {
        rvItem.layoutManager = LinearLayoutManager(this@ShoppingListActivity)
        adapter = ShoppingItemAdapter(this@ShoppingListActivity)
        rvItem.adapter = adapter
    }

    private fun observeItemData() {
        shoppingItemViewModel.getAllItems(shoppingList?.id!!).observe(this) {
            if (it.isEmpty()) {
                binding.tvEmptyList.isVisible = true
                adapter.submitList(it)
            } else {
                binding.tvEmptyList.isVisible = false
                adapter.submitList(it)
            }
        }
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

    private fun createShoppingItem() {
        if (edItem?.text.toString().isEmpty()) {
            return
        }
        val newItem = Item(
            null,
            edItem?.text.toString(),
            null,
            false,
            shoppingList?.id!!,
            0
        )
        shoppingItemViewModel.addShoppingItem(newItem)
        edItem?.setText("")
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
    }

    private fun setToolbar() = with(binding) {
        setSupportActionBar(tbShoppingList)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
