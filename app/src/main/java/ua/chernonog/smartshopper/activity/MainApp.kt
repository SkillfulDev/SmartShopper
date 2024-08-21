package ua.chernonog.smartshopper.activity

import android.app.Application
import ua.chernonog.smartshopper.db.MainDatabase

class MainApp : Application() {
    val database by lazy { MainDatabase.getDbInstance(this) }
}
