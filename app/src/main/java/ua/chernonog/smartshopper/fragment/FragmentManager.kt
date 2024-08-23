package ua.chernonog.smartshopper.fragment

import androidx.appcompat.app.AppCompatActivity
import ua.chernonog.smartshopper.R

object FragmentManager {
    private var currentFragment: BaseFragment? = null

    fun setFragment(newFragment: BaseFragment, activity: AppCompatActivity) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.flFragmentPlaceHolder, newFragment)
        transaction.commit()
        currentFragment = newFragment
    }
}
