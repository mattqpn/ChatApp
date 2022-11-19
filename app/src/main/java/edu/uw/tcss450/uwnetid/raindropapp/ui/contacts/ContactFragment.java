package edu.uw.tcss450.uwnetid.raindropapp.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentContactBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactFragmentArgs args = ContactFragmentArgs.fromBundle(getArguments());

        FragmentContactBinding binding = FragmentContactBinding.bind(getView());
        binding.textUsername.setText(args.getContact().getUsername());

        boolean status = args.getContact().getContactStatus();
        String contactStatus;

        if(status == true) {
            contactStatus = "Friends";
        } else {
            contactStatus = "Add Friend";
            binding.buttonContactStatus.setOnClickListener(button ->
                    binding.buttonContactStatus.setText("Requested"));
        }

        binding.buttonContactStatus.setText(contactStatus);

    }

}

