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

//        binding.textPubdate.setText(args.getContact().getPubDate());
        binding.textTitle.setText(args.getContact().getTitle());

//        final String preview =  Html.fromHtml(
//                        args.getContact().getTeaser(),
//                        Html.FROM_HTML_MODE_COMPACT)
//                .toString();
//        binding.textPreview.setText(preview);

        //Note we are using an Intent here to start the default system web browser
//        binding.buttonUrl.setOnClickListener(button ->
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(args.getContact().getUrl()))));

    }

}



