package ua.chernonog.smartshopper.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import ua.chernonog.smartshopper.R

object FragmentManager {
    var currentFragment: BaseFragment? = null

    fun setFragment(newFragment: BaseFragment, activity: AppCompatActivity) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.flFragmentPlaceHolder, newFragment)
        transaction.commit()
        currentFragment = newFragment
    }
}
