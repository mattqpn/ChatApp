package edu.uw.tcss450.uwnetid.raindropapp.ui.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.ui.list.ChatDisplay;

/**
 * ViewModel class of the the possible chat rooms
 * the user can enter
 */
public class ChatRoomsViewModel extends AndroidViewModel {

    /**
     * mChatRoomList holds the list of chat cards to be displayed
     * of what chatroom are available for the user
     */
    private MutableLiveData<List<ChatDisplay>> mChatRoomList;

    public ChatRoomsViewModel(@NonNull Application application) {
        super(application);
        mChatRoomList = new MutableLiveData<>();
        mChatRoomList.setValue(new ArrayList<>());
    }


    public void addChatRoomListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatDisplay>> observer) {
        mChatRoomList.observe(owner, observer);
    }

    /**
     * Connects to web server to grab possible chat rooms
     * @param jwt is the JSON web token of the user
     */
    public void connectGet(String jwt) {
        String url =
                getApplication().getResources().getString(R.string.base_url_service) +
                        "chats";

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Handles the error if cannot build a JSON object
     * @param error object to handle object
     */
    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }

    /**
     * Handles the result by creating chat cards and adding to mChatRoomList
     * @param result JSON object if a successful connection
     */
    private void handleResult(final JSONObject result) {
        List<ChatDisplay> list = getChatRoomList();
        try {
            JSONArray chatRooms = result.getJSONArray("rows");
            Log.e("Made it in Handle Result ChatRoomsViewModel", result.toString());

            for(int i=0; i < chatRooms.length(); i++){

                JSONObject mRoom = chatRooms.getJSONObject(i);
//                ChatDisplay chatDisplay = new ChatDisplay(mRoom.getString("chatid"), "New Message...", "Sent From");        //original display
                ChatDisplay chatDisplay = new ChatDisplay(mRoom.getString("name"), "New Message...", "Sent From");        //current display on the chat card

//                ChatDisplay chatDisplay = new ChatDisplay(mRoom.getString("name"), "New Message...", mRoom.getString("username"));        //modify to display on the chat card to get the sender
                if(list.size()<chatRooms.length()){
                    list.add(0, chatDisplay);
                }
            }
            mChatRoomList.setValue(list);


        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    /**
     * Gets the list of all chatrooms for the user
     * @return list of all possible chat rooms
     */
    public List<ChatDisplay> getChatRoomList(){
        return mChatRoomList.getValue();
    }

}