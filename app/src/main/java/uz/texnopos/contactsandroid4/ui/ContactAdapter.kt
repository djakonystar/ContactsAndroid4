package uz.texnopos.contactsandroid4.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.texnopos.contactsandroid4.R
import uz.texnopos.contactsandroid4.data.Contact
import uz.texnopos.contactsandroid4.databinding.ItemContactBinding

class ContactAdapter : Adapter<ContactAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.apply {
                tvName.text = contact.name
                tvPhone.text = contact.phone

                if (contact.image.isNotEmpty()) {
                    val id = root.context.resources.getIdentifier(
                        contact.image,
                        "drawable",
                        root.context.packageName
                    )
                    ivImage.setImageResource(id)
                } else {
                    ivImage.setImageResource(R.drawable.ic_no_image)
                }

                if (contact.isFavorite == 1) {
                    ivFavorite.setImageResource(R.drawable.ic_star_filled)
                } else {
                    ivFavorite.setImageResource(R.drawable.ic_star_outline)
                }

                ivDelete.setOnClickListener {
                    onDeleteClick.invoke(contact, adapterPosition)
                }

                ivFavorite.setOnClickListener {
                    onFavoriteClick.invoke(contact, adapterPosition)
                }

                ivCall.setOnClickListener {
                    onPhoneClick.invoke(contact.phone)
                }

                cvContact.setOnClickListener {
                    onItemClick.invoke(contact, adapterPosition)
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

    private var onDeleteClick: (contact: Contact, position: Int) -> Unit = { _, _ -> }
    fun setOnDeleteClickListener(onDeleteClick: (contact: Contact, position: Int) -> Unit) {
        this.onDeleteClick = onDeleteClick
    }

    private var onFavoriteClick: (contact: Contact, position: Int) -> Unit = { _, _ -> }
    fun setOnFavoriteClickListener(onFavoriteClick: (contact: Contact, position: Int) -> Unit) {
        this.onFavoriteClick = onFavoriteClick
    }

    private var onPhoneClick: (phoneNumber: String) -> Unit = {}
    fun setOnPhoneClickListener(onPhoneClick: (phoneNumber: String) -> Unit) {
        this.onPhoneClick = onPhoneClick
    }

    private var onItemClick: (contact: Contact, position: Int) -> Unit = { _, _ -> }
    fun setOnItemClickListener(onItemClick: (contact: Contact, position: Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    fun removeAtPosition(position: Int) {
        contacts.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateContact(position: Int) {
        val currentContact = contacts[position]
        val newContact = currentContact.copy(isFavorite = 1 - currentContact.isFavorite)
        contacts[position] = newContact
        notifyItemChanged(position)
    }
}
