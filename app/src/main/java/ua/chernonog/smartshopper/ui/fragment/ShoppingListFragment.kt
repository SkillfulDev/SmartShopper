package ua.chernonog.smartshopper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ua.chernonog.smartshopper.data.entity.ShoppingList
import ua.chernonog.smartshopper.databinding.FragmentShoppingListBinding
import ua.chernonog.smartshopper.ui.activity.MainApp
import ua.chernonog.smartshopper.ui.adapter.ShoppingListAdapter
import ua.chernonog.smartshopper.ui.dialog.ShoppingItemDeleteDialog
import ua.chernonog.smartshopper.ui.dialog.ShoppingListDialog
import ua.chernonog.smartshopper.util.DataTimeUtil
import ua.chernonog.smartshopper.viewmodel.ShoppingListViewModel

class ShoppingListFragment : BaseFragment(),
    ShoppingListAdapter.Listener {
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
    private lateinit var adapter: ShoppingListAdapter

    override fun onClickAdd() {
        ShoppingListDialog.setUpShoppingDialog(
            requireContext(),
            object : ShoppingListDialog.Listener {
                override fun onClick(name: String) {
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
            },
            ""
        )
    }

    override fun editShoppingList(item: ShoppingList) {
        ShoppingListDialog.setUpShoppingDialog(
            requireContext(),
            object : ShoppingListDialog.Listener {
                override fun onClick(name: String) {
                    val newShoppingListItem = item.copy(name = name)
                    shoppingListViewModel.updateShoppingList(newShoppingListItem)
                }
            },
            name = item.name
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        dataObserver()
    }

    override fun deleteShoppingList(id: Int) {
        ShoppingItemDeleteDialog.createDeleteShoppingListDialog(requireContext(), object :
            ShoppingItemDeleteDialog.Listener {
            override fun onClick() {
                shoppingListViewModel.deleteShoppingItem(id)
            }
        })
    }

    private fun setAdapter() = with(binding) {
        rvShoppingList.layoutManager = LinearLayoutManager(activity)
        adapter = ShoppingListAdapter(this@ShoppingListFragment)
        rvShoppingList.adapter = adapter
    }

    private fun dataObserver() {
        shoppingListViewModel.getAllShoppingList().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvStatus.isVisible = true
                adapter.submitList(it)
            } else {
                binding.tvStatus.isVisible = false
                adapter.submitList(it)
            }
        }
    }
}
