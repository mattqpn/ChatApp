package edu.uw.tcss450.uwnetid.raindropapp.ui.manage;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.uwnetid.raindropapp.R;
import edu.uw.tcss450.uwnetid.raindropapp.io.RequestQueueSingleton;

/**
 * ViewModel class of the management buttons to add
 * users, remove users, leave chats and change name
 */
public class ChatManagerViewModel extends AndroidViewModel {


    public ChatManagerViewModel(@NonNull Application application) {
        super(application);

    }
    // TODO: Implement the ViewModel


    //create chat method


    /**
     * Adds a user to the chatroom
     * @param chatId is the id for the chat in the database
     * @param jwt is the JSON web token of the user
     * @param email is the way to add a specific user
     */
    public void addUser(final int chatId, final String jwt, final String email){
        //need to figure out which url to call
        String url = getApplication().getResources().getString(R.string.base_url_service) +                               //need to modify the endpoint url
                "chats";

        Request request = new JsonObjectRequest(
                Request.Method.PUT,                                                                 //need to modify the request call
                url,
                null, //push token found in the JSONObject body
                null, // we get a response but do nothing with it
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
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }


    /**
     * Removes a user from the chat
      * @param chatId is the id for the chat in the database
     * @param jwt is the JSON web token of the user
     */
    public void removeUser(final int chatId, final String jwt){
        //need to figure out which url to call
        String url = getApplication().getResources().getString(R.string.base_url_service) +                               //need to modify the endpoint url
                "chats";

        Request request = new JsonObjectRequest(
                Request.Method.DELETE,                                                              //need to modify the request call
                url,
                null, //push token found in the JSONObject body
                null, // we get a response but do nothing with it
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
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }


    /**
     * Allows user to leave the chat room
     * @param chatId is the id for the chat in the database
     * @param jwt is the JSON web token of the user
     */
    public void leaveChat(final int chatId, final String jwt){
        String url = getApplication().getResources().getString(R.string.base_url_service) +                               //need to modify the endpoint url
                "chats" + chatId;

        Request request = new JsonObjectRequest(
                Request.Method.DELETE,                                                              //need to modify the request call
                url,
                null, //don't need to handle success because removing the item
                null, // we get a response but do nothing with it
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
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    /**
     * Changes the name of the chosen chat room
     * @param chatId is the id for the chat in the database
     * @param jwt is the JSON web token of the user
     * @param name is the name user wants to change the name to
     */
    public void changeName(final int chatId, final String jwt, final String name){

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

}