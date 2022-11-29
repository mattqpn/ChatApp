package edu.uw.tcss450.uwnetid.raindropapp.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentChatRoomsListBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatRoomsFragment extends Fragment {
    private ChatRoomsViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatRoomsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_rooms_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentChatRoomsListBinding binding = FragmentChatRoomsListBinding.bind(getView());

        mModel.addChatRoomListObserver(getViewLifecycleOwner(), chatRooms -> {
            if(!chatRooms.isEmpty()) {
                binding.listRoot.setAdapter(
                        new ChatRoomsRecyclerViewAdapter(chatRooms)
                );
                binding.layoutWait.setVisibility(View.GONE);
            }

        });
    }
}