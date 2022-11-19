package edu.uw.tcss450.uwnetid.raindropapp.ui.contacts;

import static edu.uw.tcss450.uwnetid.raindropapp.ui.contacts.ContactListFragmentDirections.actionNavigationContactsToContactFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentContactBinding;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentContactCardBinding;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {

    private final List<Contact> mContacts;

    public ContactRecyclerViewAdapter(List<Contact> items) {
        this.mContacts = items;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contact_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        String username = mContacts.get(position).getUsername();
        holder.mUsername.setText(username);

        holder.setContact(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentContactCardBinding mBinding;
        private ImageView mContactPhoto;
        private TextView mUsername;
        private Contact mContact;

        public ContactViewHolder(View view) {
            super(view);
            mView = view;
            mBinding = FragmentContactCardBinding.bind(view);
            mContactPhoto = itemView.findViewById(R.id.image_contact_photo);
            mUsername = itemView.findViewById(R.id.text_username);
        }

        void setContact(final Contact contact) {
            mContact = contact;
            mBinding.buttonMore.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        actionNavigationContactsToContactFragment((contact)));
            });
            mBinding.textUsername.setText(contact.getUsername());
        }
    }

}
