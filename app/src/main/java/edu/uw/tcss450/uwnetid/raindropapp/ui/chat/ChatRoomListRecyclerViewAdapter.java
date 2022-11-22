package edu.uw.tcss450.uwnetid.raindropapp.ui.chat;

import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.databinding.FragmentChatRoomsCardBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ChatRoomListRecyclerViewAdapter extends RecyclerView.Adapter<ChatRoomListRecyclerViewAdapter.ChatRoomViewHolder> {

    //Store the expanded state for each List item, true -> expanded, false -> not
//    private final Map<ChatFragment, Boolean> mExpandedFlags;

    //Store all of the blogs to present
    private final List<ChatFragment> mChatrooms;

    public ChatRoomListRecyclerViewAdapter(List<ChatFragment> items) {
        this.mChatrooms = items;
//        mExpandedFlags = mChatrooms.stream().collect(Collectors.toMap(Function.identity(), blog -> false));
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRoomViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_rooms_card, parent, false));
    }

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
        public FragmentChatRoomsCardBinding binding;
        private ChatFragment mChatrooms;
        public ChatRoomViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatRoomsCardBinding.bind(view);
//            binding.buittonMore.setOnClickListener(this::handleMoreOrLess);
        }
        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state.  When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked
         */
//        private void handleMoreOrLess(final View button) {
//            mExpandedFlags.put(mChatrooms, !mExpandedFlags.get(mChatrooms));
////            displayPreview();
//        }
        /**
         * Helper used to determine if the preview should be displayed or not.
         */
//        private void displayPreview() {
//            if (mExpandedFlags.get(mChatrooms)) {
////                binding.textPreview.setVisibility(View.VISIBLE);
////                binding.buittonMore.setImageIcon(
////                        Icon.createWithResource(
////                                mView.getContext(),
////                                R.drawable.ic_less_grey_24dp));
//            } else {
//                binding.textPreview.setVisibility(View.GONE);
//                binding.buittonMore.setImageIcon(
//                        Icon.createWithResource(
//                                mView.getContext(),
//                                R.drawable.ic_more_grey_24dp));
//            }
//        }

        //this will be the function to send us to the chat rooms
        void setChatRoomCard(final ChatFragment chatFrag) {
            mChatrooms = chatFrag;
            //this button will allow us to open that specific group chat
            binding.buttonFullPost.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatRoomListRecyclerViewAdapterDirections
                                .actionNavigationChatToNavigationChatroom());            });
            //title of the group chat
            binding.textTitle.setText(chatFrag.getHardCodedChatId());

//            binding.textPubdate.setText(blog.getPubDate());
            //Use methods in the HTML class to format the HTML found in the text
//            final String preview =  Html.fromHtml(
//                            blog.getTeaser(),
//                            Html.FROM_HTML_MODE_COMPACT)
//                    .toString().substring(0,100) //just a preview of the teaser
//                    + "...";
//            binding.textPreview.setText(preview);
//            displayPreview();
        }
    }
}