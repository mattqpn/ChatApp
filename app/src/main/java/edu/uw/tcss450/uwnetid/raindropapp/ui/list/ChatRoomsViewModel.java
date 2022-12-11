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
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ChatRoomsViewModel extends AndroidViewModel {

    //list to hold all the possible chats
    private MutableLiveData<List<ChatDisplay>> mChatRoomList;

//    //jwt token used to find all chats
//    private String mJwt;
//    private int mChatId;

    //constructor
    public ChatRoomsViewModel(@NonNull Application application) {
        super(application);
        mChatRoomList = new MutableLiveData<>();
        mChatRoomList.setValue(new ArrayList<>());
    }


    public void addChatRoomListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<ChatDisplay>> observer) {
        mChatRoomList.observe(owner, observer);
    }

    //gets chats from with our JWT token
    public void connectGet(String jwt) {
//        mJwt = jwt;

        String url =
                getApplication().getResources().getString(R.string.base_url_service) +
                        "chats";       //need to figure out the sql table and what is in it

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


    //Work on this
    //need to figure how to fill up the array of the chatroomcardfragments that is what to be displayed on recycler view adapter
    private void handleResult(final JSONObject result) {

//        IntFunction<String> getString =
//                getApplication().getResources()::getString;
//        try {
//            JSONObject root = result;
//
//            if (root.has(getString.apply(R.string.keys_json_chat))) {
//                JSONObject response =
//                        root.getJSONObject(getString.apply(
//                                R.string.keys_json_chat));
//
//                for (int i = 0; i < response.length(); i++) {
//                    ChatFragment chat = new ChatFragment();
//                    if (!mChatRoomList.getValue().contains(chat)) {
//                        mChatRoomList.getValue().add(chat);
//                    }
//                }
//
//            } else {
//
//                Log.e("ERROR!", "No response");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("ERROR!", e.getMessage());
//        }
//        mChatRoomList.setValue(mChatRoomList.getValue());
        List<ChatDisplay> list = getChatRoomList();
        try {
//            Log.i("Made it to try statement", "Made it to try statement");
            JSONArray chatRooms = result.getJSONArray("rows");
            Log.e("Made it in Handle Result ChatRoomsViewModel", result.toString());

            for(int i=0; i < chatRooms.length(); i++){

                JSONObject mRoom = chatRooms.getJSONObject(i);
                ChatDisplay chatDisplay = new ChatDisplay(mRoom.getString("chatid"), "New Message...", "Sent From");
                if(list.size()<chatRooms.length()){
                    list.add(0, chatDisplay);
                }
            }
            mChatRoomList.setValue(list);

//            list = getMessageListByChatId(result.getInt("chatId"));
//            JSONArray messages = result.getJSONArray("rows");
//            for(int i = 0; i < messages.length(); i++) {
//                JSONObject message = messages.getJSONObject(i);
//                ChatMessage cMessage = new ChatMessage(
//                        message.getInt("messageid"),
//                        message.getString("message"),
//                        message.getString("email"),
//                        message.getString("timestamp")
//                );
//                if (!list.contains(cMessage)) {
//                    // don't add a duplicate
//                    list.add(0, cMessage);
//                } else {
//                    // this shouldn't happen but could with the asynchronous
//                    // nature of the application
//                    Log.wtf("Chat message already received",
//                            "Or duplicate id:" + cMessage.getMessageId());
//                }
//
//            }
//            //inform observers of the change (setValue)
//            getOrCreateMapEntry(result.getInt("chatId")).setValue(list);

        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    public List<ChatDisplay> getChatRoomList(){
        return mChatRoomList.getValue();
    }

}