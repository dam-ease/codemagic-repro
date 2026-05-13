package eu.ccc.mobile.screens.root

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class LaunchActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(
            Intent(this, RootActivity::class.java)
        )
        finish()
    }
}
