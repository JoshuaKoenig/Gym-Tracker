package com.koenig.gymtracker

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.koenig.gymtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //TODO: Delete Workout and Exercise
    //TODO: UX, UI
    //TODO: Persistence

    // Explanation:
    // activity_main.xml contains: - DrawerLayout   - app_bar_main.xml  - Navigation View
    // app_bar_main.xml  contains: - Tool Bar       - content_main.xml
    // content_main.xml  contains: - Nav Controller ( Holds the current active Fragment )

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // In order to get the UI from the layout file
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bind the toolbar
        setSupportActionBar(binding.appBarMain.toolbar)

        toolbar = supportActionBar!!
        // Initialize the drawer layout from activity_main.xml
        val drawerLayout: DrawerLayout = binding.drawerLayout

        // Initialize the navigation view form activity_main.xml
        val navView: NavigationView = binding.navView

        // Initialize the nav controller from content_main.xml
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Sets up the drawer layout. Pass All the Fragments to navigate between
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.workoutManagerFragment,
                R.id.workoutDiaryFragment
            ), drawerLayout
        )

        // Sets the action bar binding the nav controller to it => Binds the fragments to the nav controller
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Sets up the navigation view binding the nav controller to it
        navView.setupWithNavController(navController)
    }

    // Sets up the menu on the upper right corner
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // If not a top level destination, show the navigate up arrow
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}