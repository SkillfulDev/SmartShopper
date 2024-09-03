package ua.chernonog.smartshopper.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.Menu
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
    private lateinit var binding: ActivityShoppingListBinding
    private var shoppingList: ShoppingList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setToolbar()
    }

    private fun setToolbar() = with (binding){
        setSupportActionBar(tbShoppingList)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_list_menu,menu)
        return true
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
