package today.pathos.companya.codingtest.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import today.pathos.companya.codingtest.presentation.view.FavoriteFragment
import today.pathos.companya.codingtest.presentation.view.SearchFragment

class MainPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if( position == 0) {
            SearchFragment.newInstance()
        } else {
            FavoriteFragment.newInstance()
        }
    }
}