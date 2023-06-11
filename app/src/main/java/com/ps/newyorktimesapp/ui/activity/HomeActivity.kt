package com.ps.newyorktimesapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.ps.newyorktimesapp.R
import com.ps.newyorktimesapp.databinding.ActivityHomeBinding
import com.ps.newyorktimesapp.ui.fragment.SearchNewsArticlesFragment
import com.ps.newyorktimesapp.utils.PreferenceManager

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            supportFragmentManager.popBackStack()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        PreferenceManager.init(this)
        updateStatusBarColor(color = R.color.white)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragmentInContainer(SearchNewsArticlesFragment())
        setupAppModeView()
    }

    private fun setupAppModeView() {
        binding.tvModeStatus.apply {
            text = applicationContext.getString(
                if (PreferenceManager.isAppModeOnline()) R.string.app_is_online
                else R.string.app_is_offline
            )
            setBackgroundColor(
                applicationContext.getColor(
                    if (PreferenceManager.isAppModeOnline()) R.color.green_500
                    else R.color.red_500
                )
            )
            setOnClickListener {
                PreferenceManager.updateAppMode(
                    isOffline = PreferenceManager.isAppModeOnline().not()
                )
                setupAppModeView()
            }
        }
    }

    private fun openFragmentInContainer(
        fragment: Fragment,
        isAnimationEnabled: Boolean = true
    ) {
        openFragment(
            fragmentManager = supportFragmentManager,
            containerID = binding.mainContainer.id,
            fragment = fragment,
            isAnimationEnabled = isAnimationEnabled
        )
    }
}