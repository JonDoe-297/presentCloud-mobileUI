package com.octopus.sample.ui.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.qingmei2.architecture.core.adapter.ViewPagerAdapter
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.octopus.sample.R
import com.octopus.sample.ui.main.home.HomeFragment
import com.octopus.sample.ui.main.profile.ProfileFragment
import com.octopus.sample.ui.main.repos.ReposFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

@Suppress("PLUGIN_WARNING")
@SuppressLint("CheckResult")
class MainFragment : BaseFragment() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(mainKodeinModule)
        bind<FragmentManager>() with instance(childFragmentManager)
    }

    override val layoutId: Int = R.layout.fragment_main

    @Suppress("unused")
    private val mViewModel: MainViewModel by instance()     // not used

    private var isPortMode: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPortMode = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        viewPager.adapter = ViewPagerAdapter(childFragmentManager,
                listOf(HomeFragment(), ReposFragment(), ProfileFragment()))
        viewPager.offscreenPageLimit = 2

        when (isPortMode) {
            true -> bindsPortScreen()
            false -> bindsLandScreen()
        }
    }

    private fun bindsPortScreen() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

            override fun onPageSelected(position: Int) = onPageSelectChanged(position)


            override fun onPageScrollStateChanged(state: Int) = Unit
        })
        navigation.setOnNavigationItemSelectedListener { menuItem ->
            onBottomNavigationSelectChanged(menuItem)
            true
        }
    }

    private fun bindsLandScreen() {
        fabHome.setOnClickListener { onPageSelectChanged(0) }
        fabRepo.setOnClickListener { onPageSelectChanged(1) }
        fabProfile.setOnClickListener { onPageSelectChanged(2) }
    }

    private fun onPageSelectChanged(index: Int) {
        // port-mode
        if (isPortMode) {
            for (position in 0..index) {
                if (navigation.visibility == View.VISIBLE)
                    navigation.menu.getItem(position).isChecked = index == position
            }
        } else {
            // land-mode
            if (viewPager.currentItem != index) {
                viewPager.currentItem = index
                if (fabMenu != null && fabMenu.isExpanded)
                    fabMenu.toggle()
            }
        }
    }

    // port-mode only
    private fun onBottomNavigationSelectChanged(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_home -> {
                viewPager.currentItem = 0
            }
            R.id.nav_repos -> {
                viewPager.currentItem = 1
            }
            R.id.nav_profile -> {
                viewPager.currentItem = 2
            }
        }
    }
}