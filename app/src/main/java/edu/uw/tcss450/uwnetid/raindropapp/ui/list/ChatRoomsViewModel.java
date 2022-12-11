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


    private void handleResult(final JSONObject result) {
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


        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    public List<ChatDisplay> getChatRoomList(){
        return mChatRoomList.getValue();
    }

}