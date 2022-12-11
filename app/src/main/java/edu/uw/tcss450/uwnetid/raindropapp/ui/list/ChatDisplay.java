package edu.uw.tcss450.uwnetid.raindropapp.ui.list;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChatDisplay implements Serializable {

    private String mChatRoomName;
    private String mMessage;
    private String mSender;

    public ChatDisplay() {
        // Required empty public constructor
    }

    public ChatDisplay(String mChatRoomName, String mMessage, String mSender){
        this.mChatRoomName = mChatRoomName;
        this.mMessage = mMessage;
        this.mSender = mSender;
    }

    public String getmChatRoomName(){
        return mChatRoomName;
    }

    public String getmLastSent(){
        String temp = mSender + ": " + mMessage;
        if(temp.length() > 50){
            temp = temp.substring(0,45) + "...";
        }
        return temp;
    }

    public String getUser(){
        return mSender;
    }
    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}