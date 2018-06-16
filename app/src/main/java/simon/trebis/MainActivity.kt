package simon.trebis

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.DisplayMetrics
import android.view.Surface.ROTATION_0
import android.view.Surface.ROTATION_180
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import simon.trebis.Const.Companion.DEFAULT_HEIGHT
import simon.trebis.Const.Companion.DEFAULT_WIDHT


class MainActivity : AppCompatActivity() {

    lateinit var navHost: NavHostFragment

    companion object {

        var deviceWidth: Int = DEFAULT_WIDHT
        var deviceHeight: Int = DEFAULT_HEIGHT

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        loadDisplayMetrics()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHost.navController)
    }

    private fun loadDisplayMetrics() {
        DisplayMetrics().let {
            windowManager.defaultDisplay.let { display ->
                display.getMetrics(it)
                if (display.rotation == ROTATION_180 || display.rotation == ROTATION_0) {
                    deviceHeight = it.heightPixels
                    deviceWidth = it.widthPixels
                } else {
                    deviceHeight = it.widthPixels
                    deviceWidth = it.heightPixels
                }
            }
        }
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onSupportNavigateUp()
    }

}
