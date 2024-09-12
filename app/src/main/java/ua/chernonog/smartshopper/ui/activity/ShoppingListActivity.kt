package ua.chernonog.smartshopper.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import ua.chernonog.smartshopper.R
import ua.chernonog.smartshopper.data.entity.Item
import ua.chernonog.smartshopper.data.entity.LibraryItem
import ua.chernonog.smartshopper.data.entity.ShoppingList
import ua.chernonog.smartshopper.databinding.ActivityShoppingListBinding
import ua.chernonog.smartshopper.ui.adapter.ShoppingItemAdapter
import ua.chernonog.smartshopper.ui.dialog.ShoppingItemDialog
import ua.chernonog.smartshopper.ui.fragment.ShoppingListFragment
import ua.chernonog.smartshopper.util.ShareHelper
import ua.chernonog.smartshopper.viewmodel.ShoppingItemViewModel
import ua.chernonog.smartshopper.viewmodel.ShoppingListViewModel

class ShoppingListActivity : AppCompatActivity(), ShoppingItemAdapter.Listener {
    private val shoppingItemViewModel: ShoppingItemViewModel by viewModels {
        ShoppingItemViewModel.ShoppingItemViewModelFactory(
            (applicationContext
                    as MainApp).database
        )
    }
    private val shoppingListViewModel: ShoppingListViewModel by viewModels {
        ShoppingListViewModel.ShoppingListViewModelFactory(
            (applicationContext
                    as MainApp).database
        )
    }
    private var shoppingList: ShoppingList? = null
    private var edItem: EditText? = null
    private lateinit var saveMenuItem: MenuItem
    private lateinit var binding: ActivityShoppingListBinding
    private lateinit var adapter: ShoppingItemAdapter
    private lateinit var textWatcher: TextWatcher

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
        textWatcher = textWatcherInit()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveShoppingItem -> createShoppingItem(edItem?.text.toString())
            android.R.id.home -> finish()
            R.id.clearShoppingItem -> shoppingItemViewModel.clearItemsFromList(shoppingList?.id!!)
            R.id.deleteShoppingItem -> {
                shoppingListViewModel.deleteShoppingItem(shoppingList?.id!!)
                finish()
            }

            R.id.shareShoppingItem -> startActivity(
                Intent.createChooser(
                    ShareHelper.shareList(adapter.currentList, shoppingList?.name!!),
                    "Share by"
                )
            )
        }
        return true
    }

    override fun editItem(item: Item) {
        ShoppingItemDialog.createDialog(this, item, object : ShoppingItemDialog.Listener {
            override fun onUpdateClick(item: Item) {
                if (item.itemType == 1) {
                    shoppingItemViewModel.updateLibraryItem(LibraryItem(item.id, item.name))
                    updateLibraryItemInfo()
                }
                shoppingItemViewModel.updateShoppingItem(item)
            }
        })
    }

    override fun setCheckItem(item: Item) {
        shoppingItemViewModel.updateShoppingItem(item)
    }

    override fun updateLibraryItem(item: Item) {
        editItem(item)
    }

    override fun deleteLibraryItem(id: Int) {
        shoppingItemViewModel.deleteLibraryItem(id)
        updateLibraryItemInfo()
    }

    override fun addLibraryItemToList(name: String) {
        createShoppingItem(name)
    }

    private fun updateLibraryItemInfo() {
        shoppingItemViewModel.getAllLibraryItem("%${edItem?.text.toString()}%")
    }

    private fun rvInit() = with(binding) {
        rvItem.layoutManager = LinearLayoutManager(this@ShoppingListActivity)
        adapter = ShoppingItemAdapter(this@ShoppingListActivity)
        rvItem.adapter = adapter
    }

    private fun observeItemData() {
        shoppingItemViewModel.getAllItems(shoppingList?.id!!).observe(this) {
            adapter.submitList(it)
            binding.tvEmptyList.isVisible = it.isEmpty()
        }
    }

    private fun observeLibraryItem() {
        shoppingItemViewModel.libraryItems.observe(this) {
            binding.tvEmptyList.isVisible = it.isEmpty()
            val tempShoppingItem = ArrayList<Item>()
            it.forEach { item ->
                val convertItem = Item(
                    item.id,
                    item.name,
                    "",
                    false,
                    0,
                    1
                )
                tempShoppingItem.add(convertItem)
            }
            adapter.submitList(tempShoppingItem)
        }
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                saveMenuItem.isVisible = true
                edItem?.addTextChangedListener(textWatcher)
                observeLibraryItem()
                shoppingItemViewModel.getAllItems(shoppingList?.id!!).removeObservers(
                    this@ShoppingListActivity
                )
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                saveMenuItem.isVisible = false
                edItem?.removeTextChangedListener(textWatcher)
                invalidateMenu()
                shoppingItemViewModel.libraryItems.removeObservers(this@ShoppingListActivity)
                observeItemData()
                edItem?.setText("")
                return true
            }
        }
    }

    private fun createShoppingItem(name: String) {
        if (name.isEmpty()) {
            return
        }
        val newItem = Item(
            null,
            name,
            "",
            false,
            shoppingList?.id!!,
            0
        )
        edItem?.setText("")
        shoppingItemViewModel.addShoppingItem(newItem)

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

    private fun textWatcherInit(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shoppingItemViewModel.getAllLibraryItem("%$s%")
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
    }
}
