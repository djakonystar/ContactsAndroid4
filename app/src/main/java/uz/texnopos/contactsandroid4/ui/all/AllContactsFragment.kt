package uz.texnopos.contactsandroid4.ui.all

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import uz.texnopos.contactsandroid4.R
import uz.texnopos.contactsandroid4.data.ContactDao
import uz.texnopos.contactsandroid4.data.ContactDatabase
import uz.texnopos.contactsandroid4.databinding.FragmentContactsAllBinding
import uz.texnopos.contactsandroid4.ui.ContactAdapter

class AllContactsFragment : Fragment(R.layout.fragment_contacts_all) {
    private lateinit var binding: FragmentContactsAllBinding
    private val adapter = ContactAdapter()
    private lateinit var db: ContactDatabase
    private lateinit var contactDao: ContactDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactsAllBinding.bind(view)

        db = ContactDatabase.getInstance(requireContext())
        contactDao = db.getContactDao()

        binding.apply {
            recyclerView.adapter = adapter

            adapter.setOnDeleteClickListener { contact ->
                contactDao.deleteContact(contact)
                Snackbar.make(
                    fabAddContact,
                    "Contact deleted successfully!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            adapter.contacts = contactDao.getAllContacts().toMutableList()

            fabAddContact.setOnClickListener {
                val dialog = AddContactDialog()
                dialog.show(requireActivity().supportFragmentManager, dialog.tag)

                dialog.setOnAddSuccessListener {
                    adapter.contacts = contactDao.getAllContacts().toMutableList()

                    Snackbar.make(
                        fabAddContact,
                        "Contact added successfully!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
