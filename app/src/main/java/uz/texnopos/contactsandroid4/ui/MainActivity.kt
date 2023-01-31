package uz.texnopos.contactsandroid4.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import uz.texnopos.contactsandroid4.R
import uz.texnopos.contactsandroid4.databinding.ActivityMainBinding
import uz.texnopos.contactsandroid4.ui.all.AllContactsFragment
import uz.texnopos.contactsandroid4.ui.favorite.FavoriteContactsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, AllContactsFragment())
            .addToBackStack(null)
            .commit()

        binding.apply {
            bnvMain.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_all -> {
                        supportFragmentManager.popBackStack(
                            null,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, AllContactsFragment())
                            .addToBackStack(null)
                            .commit()
                    }
                    R.id.item_favorites -> {
                        supportFragmentManager.popBackStack(
                            null,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, AllContactsFragment())
                            .addToBackStack(null)
                            .commit()

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, FavoriteContactsFragment())
                            .addToBackStack("favorites")
                            .commit()
                    }
                }
                true
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val backStack = supportFragmentManager
            .getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)

        when (backStack.name) {
            null -> {
                binding.bnvMain.selectedItemId = R.id.item_all
            }
            "favorites" -> {
                binding.bnvMain.selectedItemId = R.id.item_favorites
            }
        }
    }
}