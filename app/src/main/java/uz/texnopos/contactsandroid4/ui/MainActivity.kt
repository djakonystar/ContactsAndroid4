package uz.texnopos.contactsandroid4.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            .commit()

        binding.apply {
            bnvMain.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_all -> {
                        supportFragmentManager.beginTransaction()
                            .add(R.id.fragment_container, AllContactsFragment())
                            .commit()
                    }
                    R.id.item_favorites -> {
                        supportFragmentManager.beginTransaction()
                            .add(R.id.fragment_container, FavoriteContactsFragment())
                            .commit()
                    }
                }
                true
            }
        }
    }
}