package com.ps.newyorktimesapp.ui.activity

import android.view.Window
import android.view.WindowManager
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ps.newyorktimesapp.R

open class BaseActivity : AppCompatActivity() {

    // Hiding the status bar
    open fun updateStatusBarColor(color: Int) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, color)
    }

    fun openFragment(
        fragmentManager: FragmentManager?,
        containerID: Int,
        fragment: Fragment,
        isAnimationEnabled: Boolean = true,
        @AnimRes @AnimatorRes enterAnim: Int? = null,
        @AnimRes @AnimatorRes exitAnim: Int? = null
    ) {
        val transaction = fragmentManager?.beginTransaction()
        if (isAnimationEnabled) {
            transaction?.setCustomAnimations(
                enterAnim ?: R.anim.slide_in_right,
                exitAnim ?: R.anim.slide_out_right,
                enterAnim ?: R.anim.slide_out_right,
                exitAnim ?: R.anim.slide_out_right
            )
        }
        transaction?.add(containerID, fragment, fragment::class.java.simpleName)
        transaction?.addToBackStack(fragment::class.java.simpleName)
        transaction?.setReorderingAllowed(true)
        transaction?.commitAllowingStateLoss()
    }
}
