package ca.ulaval.ima.tp3

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ca.ulaval.ima.tp3.ui.Formulaire.FragmentFormulaire
import ca.ulaval.ima.tp3.ui.Fragment1
import ca.ulaval.ima.tp3.ui.Fragment2
import ca.ulaval.ima.tp3.ui.ItemList.FragmentItemList

private val TAB_TITLES = arrayOf(
        "OFFRES",
        "VENDRE",
        "MES ANNONCES"
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        lateinit var itemFragment:Fragment
        when(position){
            0 -> itemFragment = FragmentItemList()
            1 -> itemFragment = FragmentFormulaire()
            2 -> itemFragment = Fragment1()
        }
        return itemFragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
        // return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.count()
    }
}