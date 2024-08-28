package ua.chernonog.smartshopper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.chernonog.smartshopper.databinding.FragmentShoppingListBinding
import ua.chernonog.smartshopper.dialog.ShoppingListDialog


class ShoppingListFragment : BaseFragment(), ShoppingListDialog.Listener {
    companion object {
        @JvmStatic
        fun newInstance() = ShoppingListFragment()
    }

    private lateinit var binding: FragmentShoppingListBinding

    override fun onClickAdd() {
        ShoppingListDialog.setUpShoppingDialog(requireContext(), this)
    }

    override fun addShoppingList(name: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }
}
