package edu.uw.tcss450.uwnetid.raindropapp.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentContactListBinding;
import edu.uw.tcss450.uwnetid.raindropapp.model.UserInfoViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {

    private ContactListViewModel mModel;
    private UserInfoViewModel mUserModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mModel = provider.get(ContactListViewModel.class);
        mUserModel = provider.get(UserInfoViewModel.class);
        mModel.connectGet(mUserModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentContactListBinding binding = FragmentContactListBinding.bind(getView());

        mModel.addContactListObserver(getViewLifecycleOwner(), contacts -> {
            if (!contacts.isEmpty()) {
                binding.listRoot.setAdapter(
                        new ContactRecyclerViewAdapter(contacts)
                );
                binding.layoutWait.setVisibility(View.GONE);
            }
        });
    }

}