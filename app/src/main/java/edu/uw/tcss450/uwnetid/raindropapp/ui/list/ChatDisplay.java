package edu.uw.tcss450.uwnetid.raindropapp.ui.list;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import java.io.Serializable;

/**
 * Chat card class of how chat rooms will be displayed in recycler view
 */
public class ChatDisplay implements Serializable {

    /**
     * mChatRoomName is the name of the chatroom
     * mMessage is the last sent message
     * mSender is the last sender
     */
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

    /**
     * Gets the chatroom name
     * @return the chatroom name
     */
    public String getmChatRoomName(){
        return mChatRoomName;
    }

    /**
     * Gets the last message sent
     * @return the last message sent
     */
    public String getmLastSent(){
        String temp = mMessage;
        if(temp.length() > 50){
            temp = temp.substring(0,45) + "...";
        }
        return temp;
    }

    /**
     * Get the user of the last message
     * @return the user of the last message
     */
    public String getUser(){
        return mSender;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}