package edu.uw.tcss450.uwnetid.raindropapp.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChatRoomsCardFragment extends Fragment {

    private ChatRoomsViewModel mChatRoomViewModel;
    private UserInfoViewModel mUserInfoViewModel;

    private MutableLiveData<ArrayList<ChatFragment>> mChatRoomList;
    private ChatFragment mChatFrag;



    public ChatRoomsCardFragment() {
        // Required empty public constructor
    }


    //hard code an list of the different chat fragments
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChatRoomViewModel = new ViewModelProvider(getActivity()).get(ChatRoomsViewModel.class);
        mChatRoomViewModel.connectGet(mUserInfoViewModel.getmJwt());
//        ArrayList<ChatFragment> mList = new ArrayList<>();
//
//        for(int i = 0; i < 3; i++){
//            mList.add(mChatFrag);
//        }
//        mChatRoomList.setValue(mList);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_rooms_card, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}