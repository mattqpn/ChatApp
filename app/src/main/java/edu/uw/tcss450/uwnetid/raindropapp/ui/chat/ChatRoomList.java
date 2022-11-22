package edu.uw.tcss450.uwnetid.raindropapp.ui.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;

public class ChatRoomList extends AndroidViewModel {

    private ArrayList<String> mChatRooms;

    private String mJwt;

    public ChatRoomList(@NonNull Application application) {
        super(application);
        mChatRooms = new ArrayList<>();

    }

    public String getmJwt() {
        return mJwt;
    }

    public int getSize(){
        return mChatRooms.size();
    }


}