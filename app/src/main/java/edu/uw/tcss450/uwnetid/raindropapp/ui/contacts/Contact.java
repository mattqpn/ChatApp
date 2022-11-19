package edu.uw.tcss450.uwnetid.raindropapp.ui.contacts;

import android.widget.ImageView;

import java.io.Serializable;

public class Contact implements Serializable {
    private final String mUsername;
    private Boolean mContactStatus;
    private ImageView mContactPhoto;

    public static class Builder {
        private final String mUsername;
        private Boolean mContactStatus;
        private ImageView mContactPhoto;

        public Builder(String username) {
            this.mUsername = username;
        }

        public Builder addContactStatus(Boolean contactStatus) {
            mContactStatus = contactStatus;
            return this;
        }

        public Builder addContactPhoto(ImageView contactPhoto) {
            mContactPhoto = contactPhoto;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }

    private Contact(final Builder builder) {
        this.mUsername = builder.mUsername;
        this.mContactStatus = builder.mContactStatus;
        this.mContactPhoto = builder.mContactPhoto;
    }

    public String getUsername() {
        return mUsername;
    }

    public Boolean getContactStatus() {
        return mContactStatus;
    }

    public ImageView getContactPhoto() {
        return mContactPhoto;
    }
}

