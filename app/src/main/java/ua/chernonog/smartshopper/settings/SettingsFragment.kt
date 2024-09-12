package ua.chernonog.smartshopper.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ua.chernonog.smartshopper.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}
