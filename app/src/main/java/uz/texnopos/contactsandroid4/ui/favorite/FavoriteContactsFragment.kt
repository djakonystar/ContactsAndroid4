package uz.texnopos.contactsandroid4.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.texnopos.contactsandroid4.R
import uz.texnopos.contactsandroid4.databinding.FragmentContactsFavoriteBinding

class FavoriteContactsFragment: Fragment(R.layout.fragment_contacts_favorite) {
    private lateinit var binding: FragmentContactsFavoriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsFavoriteBinding.bind(view)

    }
}
