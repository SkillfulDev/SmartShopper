package ua.chernonog.smartshopper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.chernonog.smartshopper.data.entity.ShoppingList
import ua.chernonog.smartshopper.databinding.FragmentShoppingListBinding
import ua.chernonog.smartshopper.ui.activity.MainApp
import ua.chernonog.smartshopper.ui.dialog.ShoppingListDialog
import ua.chernonog.smartshopper.util.DataTimeUtil
import ua.chernonog.smartshopper.viewmodel.ShoppingListViewModel


class ShoppingListFragment : BaseFragment(), ShoppingListDialog.Listener {
    companion object {
        @JvmStatic
        fun newInstance() = ShoppingListFragment()
    }

    private val shoppingListViewModel: ShoppingListViewModel by activityViewModels {
        ShoppingListViewModel.ShoppingListViewModelFactory(
            (context?.applicationContext
                    as MainApp).database
        )
    }
    private lateinit var binding: FragmentShoppingListBinding

    override fun onClickAdd() {
        ShoppingListDialog.setUpShoppingDialog(requireContext(), this)
    }

    override fun addShoppingList(name: String) {
        val shoppingList = ShoppingList(
            null,
            name,
            DataTimeUtil.getCurrentTime(),
            0,
            0,
            ""
        )
        shoppingListViewModel.addShoppingList(shoppingList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }
}
