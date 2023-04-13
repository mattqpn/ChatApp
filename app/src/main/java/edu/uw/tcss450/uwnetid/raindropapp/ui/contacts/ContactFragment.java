package edu.uw.tcss450.uwnetid.raindropapp.ui.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.atomic.AtomicBoolean;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentContactBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactFragmentArgs args = ContactFragmentArgs.fromBundle(getArguments());

        FragmentContactBinding binding = FragmentContactBinding.bind(getView());
        binding.textUsername.setText(args.getContact().getTitle());

        int status = args.getContact().getContactStatus();
        String contactStatus;


        if(status == 1) {
            contactStatus = "Friends";
            binding.buttonRemoveContact.setOnClickListener(button -> {
                binding.buttonContactStatus.setText("Add Friend");
                binding.buttonRemoveContact.setVisibility(View.GONE);
                binding.buttonContactStatus.setOnClickListener(request ->{
                        binding.buttonContactStatus.setText("Requested");
                        binding.buttonRemoveContact.setText("Cancel Request");
                        binding.buttonRemoveContact.setVisibility(View.VISIBLE);
                });
            });

        } else {
            contactStatus = "Add Friend";
            binding.buttonContactStatus.setOnClickListener(button -> {
                binding.buttonContactStatus.setText("Requested");
                binding.buttonRemoveContact.setText("Cancel Request");
                binding.buttonRemoveContact.setVisibility(View.VISIBLE);
                binding.buttonRemoveContact.setOnClickListener(request ->{
                        binding.buttonRemoveContact.setVisibility(View.GONE);
                        binding.buttonContactStatus.setText("Add Friend");
                });
            });

            binding.buttonRemoveContact.setVisibility(View.GONE);
        }

        binding.buttonContactStatus.setText(contactStatus);

    }

}



