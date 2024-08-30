package ua.chernonog.smartshopper.ui.activity

import android.app.Application
import ua.chernonog.smartshopper.data.db.MainDatabase

class MainApp : Application() {
    val database by lazy { MainDatabase.getDbInstance(this) }
}
