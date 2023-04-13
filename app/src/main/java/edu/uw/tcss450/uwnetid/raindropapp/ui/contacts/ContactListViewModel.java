package edu.uw.tcss450.uwnetid.raindropapp.ui.contacts;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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
import java.util.function.IntFunction;

import edu.uw.tcss450.uwnetid.raindropapp.R;

public class ContactListViewModel extends AndroidViewModel {
    private MutableLiveData<List<Contact>> mContacts;
    private static ArrayList contactsList = new ArrayList<>();
    private static int tableSize = 0;

    public ContactListViewModel(@NonNull Application application) {
        super(application);
        mContacts = new MutableLiveData<>();
        mContacts.setValue(new ArrayList<>());
    }

    public void addContactListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<Contact>> observer) {
        mContacts.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }
    private void handleResult(final JSONObject result) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            JSONArray contacts = result.getJSONArray("rows");
            tableSize = contacts.length();
            if (contactsList.size() != tableSize) {
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject jsonContact = contacts.getJSONObject(i);
                    Contact contact = new Contact.Builder(
                            jsonContact.getString(
                                    getString.apply(
                                            R.string.keys_json_contact_username)),
                            jsonContact.getInt(getString.apply(
                                    R.string.keys_json_contact_verified)))
                            .build();
                    if (!contactsList.contains(jsonContact.getString("username"))) {
                        contactsList.add(jsonContact.getString("username"));
                    }
                    if (!mContacts.getValue().contains(contact) && !mContacts.getValue().contains(contactsList)) {
                        mContacts.getValue().add(contact);
                    } else {
                        // this shouldn't happen but could with the asynchronous
                        // nature of the application
                        Log.wtf("Chat message already received", "sorry :/");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }

        mContacts.setValue(mContacts.getValue());
    }

    public void connectGet(final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url_service) +
                "contacts";
                //"https://cfb3-tcss450-labs-2021sp.herokuapp.com/phish/blog/get";
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
                //headers.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImNmYjMxQGZha2UuZW1haWwuY29tIiwibWVtYmVyaWQiOjI4OCwiaWF0IjoxNjY2NTU3ODQ2LCJleHAiOjE2NzUxOTc4NDZ9.f7IVbAzJbX72vjRUbadIksMzNm6xkPi1l_R_C4O9zb4");
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


}
