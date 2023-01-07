package uz.texnopos.contactsandroid4.ui.all

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import uz.texnopos.contactsandroid4.R
import uz.texnopos.contactsandroid4.data.Contact
import uz.texnopos.contactsandroid4.data.ContactDatabase
import uz.texnopos.contactsandroid4.databinding.DialogContactAddBinding
import kotlin.random.Random

class AddContactDialog : DialogFragment(R.layout.dialog_contact_add) {
    private lateinit var binding: DialogContactAddBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogContactAddBinding.bind(view)

        val dao = ContactDatabase.getInstance(requireContext()).getContactDao()

        binding.apply {
            btnAdd.setOnClickListener {
                val name = etName.text.toString()
                val phone = etPhone.text.toString()

                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    val list =
                        listOf("ic_person", "ic_person_pin", "ic_person_pin_circle", "", "", "")
                    val index = Random.nextInt(0, list.size)
                    val contact = Contact(
                        name = name,
                        phone = phone,
                        image = list[index],
                        isFavorite = 0
                    )
                    dao.addContact(contact)
                    onAddSuccess.invoke()
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Fill the fields!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private var onAddSuccess: () -> Unit = {}
    fun setOnAddSuccessListener(onAddSuccess: () -> Unit) {
        this.onAddSuccess = onAddSuccess
    }
}
