package uz.texnopos.contactsandroid4.ui.all

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import uz.texnopos.contactsandroid4.R
import uz.texnopos.contactsandroid4.data.Contact
import uz.texnopos.contactsandroid4.data.ContactDao
import uz.texnopos.contactsandroid4.data.ContactDatabase
import uz.texnopos.contactsandroid4.databinding.FragmentContactEditBinding

class EditContactFragment: Fragment(R.layout.fragment_contact_edit) {
    private lateinit var binding: FragmentContactEditBinding
    private lateinit var db: ContactDatabase
    private lateinit var contactDao: ContactDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactEditBinding.bind(view)

        db = ContactDatabase.getInstance(requireContext())
        contactDao = db.getContactDao()

        val id = arguments?.getInt("id") ?: 0
        val oldName = arguments?.getString("name")
        val oldPhone = arguments?.getString("phone")
        val image = arguments?.getString("image") ?: ""
        val isFavorite = arguments?.getInt("isFavorite") ?: 0

        binding.apply {
            etName.setText(oldName)
            etPhone.setText(oldPhone)

            btnSave.setOnClickListener {
                val name = etName.text.toString()
                val phone = etPhone.text.toString()

                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    val contact = Contact(
                        id = id,
                        name = name,
                        phone = phone,
                        image = image,
                        isFavorite = isFavorite
                    )
                    contactDao.updateContact(contact)
                } else {
                    Toast.makeText(requireContext(), "Fill the fields!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
