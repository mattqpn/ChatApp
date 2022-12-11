package edu.uw.tcss450.uwnetid.raindropapp.ui.list;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentChatRoomCardBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ChatRoomsRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomsRecyclerViewAdapter.ChatRoomViewHolder> {


//    //Store all of the blogs to present
//    private final List<ChatFragment> mChatrooms;
    private final List<ChatDisplay> mChatrooms;

//    public ChatRoomsRecyclerViewAdapter(List<ChatFragment> mChatrooms) {
//        this.mChatrooms = mChatrooms;
//    }

    public ChatRoomsRecyclerViewAdapter(List<ChatDisplay> mChatrooms) {
        this.mChatrooms = mChatrooms;
    }


    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRoomViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_room_card, parent, false));
    }

    //might need some changing we will see
    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        holder.setChatRoomCard(mChatrooms.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatrooms.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatRoomCardBinding mBinding;
        private ChatDisplay mRoom;

        public ChatRoomViewHolder(View view) {
            super(view);
            mView = view;
            mBinding = FragmentChatRoomCardBinding.bind(view);
        }

        //this will be the function to send us to the chat rooms        change this to display the chat card
        void setChatRoomCard(final ChatDisplay chat) {
            this.mRoom = chat;
            String result = chat.getmLastSent();
            //this button will allow us to open that specific group chat
            mBinding.buttonChatRoom.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatRoomsFragmentDirections
                                .actionNavigationChatroomListToNavigationChat());
            });
            //title of the group chat
            mBinding.textChatMembers.setText("chat room");         //need to fix this to read out surface level information about the chat room
            mBinding.textPreview.setText(result);
        }
    }
}