package edu.uw.tcss450.uwnetid.raindropapp.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentContactListBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {
    private ContactListViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
//        mModel.connectGet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_contact_list, container, false);

        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        if (view instanceof RecyclerView) {
            //Try out a grid layout to achieve rows AND columns. Adjust the widths of the
            //cards on display
//            ((RecyclerView) view).setLayoutManager(new GridLayoutManager(getContext(), 2));

            //Try out horizontal scrolling. Adjust the widths of the card so that it is
            //obvious that there are more cards in either direction. i.e. don't have the cards
            //span the entire witch of the screen. Also, when considering horizontal scroll
            //on recycler view, ensure that thre is other content to fill the screen.
//            ((LinearLayoutManager)((RecyclerView) view).getLayoutManager())
//                    .setOrientation(LinearLayoutManager.HORIZONTAL);

            ((RecyclerView) view).setAdapter(
                    new ContactRecyclerViewAdapter(ContactGenerator.getContactList()));
        }
        return view;
                //return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        FragmentContactListBinding binding = FragmentContactListBinding.bind(getView());
//
////        mModel.addContactObserver(getViewLifecycleOwner(), contactList -> {
////            if (!contactList.isEmpty()) {
////                binding.listRoot.setAdapter(
////                        new ContactRecyclerViewAdapter(contactList)
////                );
////                //binding.layoutWait.setVisibility(view.GONE);
////            }
////        });
//    }
}