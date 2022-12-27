package uz.texnopos.contactsandroid4.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.texnopos.contactsandroid4.R
import uz.texnopos.contactsandroid4.data.Contact
import uz.texnopos.contactsandroid4.databinding.ItemContactBinding

class ContactAdapter: Adapter<ContactAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(private val binding: ItemContactBinding): ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.apply {
                tvName.text = contact.name
                tvPhone.text = contact.phone

                ivDelete.setOnClickListener {
                    onDeleteClick.invoke(contact)
                    contacts.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }

                ivFavorite.setOnClickListener {

                }
            }
        }
    }

    var contacts = mutableListOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        val binding = ItemContactBinding.bind(v)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    private var onDeleteClick: (contact: Contact) -> Unit = {}
    fun setOnDeleteClickListener(onDeleteClick: (contact: Contact) -> Unit) {
        this.onDeleteClick = onDeleteClick
    }
}
