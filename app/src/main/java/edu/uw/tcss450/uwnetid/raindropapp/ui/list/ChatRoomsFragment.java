package edu.uw.tcss450.uwnetid.raindropapp.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentChatRoomsListBinding;
import edu.uw.tcss450.uwnetid.raindropapp.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatRoomsFragment extends Fragment {
    private ChatRoomsViewModel mChatRoomsModel;
    private UserInfoViewModel mUserModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatRoomsModel = provider.get(ChatRoomsViewModel.class);
        mChatRoomsModel.connectGet(mUserModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_rooms_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatRoomsListBinding binding = FragmentChatRoomsListBinding.bind(getView());
        binding.listRoot.setAdapter(
                new ChatRoomsRecyclerViewAdapter(mChatRoomsModel.getChatRoomList())
        );

        mChatRoomsModel.addChatRoomListObserver(getViewLifecycleOwner(), chatRooms -> {
            if(!mChatRoomsModel.getChatRoomList().isEmpty()) {
                binding.listRoot.getAdapter().notifyDataSetChanged();
                binding.layoutWait.setVisibility(View.GONE);
            }
        });
    }
}