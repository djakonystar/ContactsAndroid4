package uz.texnopos.contactsandroid4.ui.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import uz.texnopos.contactsandroid4.R
import uz.texnopos.contactsandroid4.data.ContactDao
import uz.texnopos.contactsandroid4.data.ContactDatabase
import uz.texnopos.contactsandroid4.databinding.FragmentContactsFavoriteBinding
import uz.texnopos.contactsandroid4.ui.ContactAdapter
import uz.texnopos.contactsandroid4.ui.all.EditContactFragment

class FavoriteContactsFragment: Fragment(R.layout.fragment_contacts_favorite) {
    private lateinit var binding: FragmentContactsFavoriteBinding
    private lateinit var dao: ContactDao
    private val adapter = ContactAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsFavoriteBinding.bind(view)

        dao = ContactDatabase.getInstance(requireContext()).getContactDao()

        binding.apply {
            recyclerView.adapter = adapter

            adapter.contacts = dao.getFavorites().toMutableList()

            adapter.setOnDeleteClickListener { contact, position ->
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete contact")
                    .setMessage("Are you sure to delete ${contact.name} from your contacts list?")
                    .setPositiveButton("Yes") { dialog, _ ->
                        dao.deleteContact(contact)
                        adapter.removeAtPosition(position)
                        Snackbar.make(
                            recyclerView,
                            "Contact deleted successfully!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

            adapter.setOnFavoriteClickListener { contact, position ->
                val newContact = contact.copy(isFavorite = 1 - contact.isFavorite)
                dao.updateContact(newContact)
                adapter.removeAtPosition(position)
            }

            adapter.setOnPhoneClickListener { phoneNumber ->
                val uri = "tel:$phoneNumber"
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(uri))
                startActivity(intent)
            }

            adapter.setOnItemClickListener { contact, position ->
                val bundle = Bundle()

                bundle.putInt("id", contact.id)
                bundle.putString("name", contact.name)
                bundle.putString("phone", contact.phone)
                bundle.putString("image", contact.image)
                bundle.putInt("isFavorite", contact.isFavorite)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, EditContactFragment::class.java, bundle)
                    .addToBackStack(EditContactFragment::class.java.simpleName)
                    .commit()
            }
        }
    }
}
