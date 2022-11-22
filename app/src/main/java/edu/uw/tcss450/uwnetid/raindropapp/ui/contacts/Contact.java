package edu.uw.tcss450.uwnetid.raindropapp.ui.contacts;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Class to encapsulate a Phish.net Blog Post. Building an Object requires a publish date and title.
 *
 * Optional fields include URL, teaser, and Author.
 *
 *
 * @author Charles Bryan
 * @version 14 September 2018
 */
public class Contact implements Serializable {

    private final String mTitle;
    private int mContactStatus;
    //private ImageView mContactPhoto;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     */
    public static class Builder {
        private final String mTitle;
        private int mContactStatus;
        //private ImageView mContactPhoto;


        /**
         * Constructs a new Builder.
         *
        // * @param pubDate the published date of the blog post
         * @param title the title of the blog post
         */
        public Builder(String title, int status) {
            this.mTitle = title;
            this.mContactStatus = status;
            //this.mContactPhoto = photo;
        }

        //public

        public Contact build() {
            return new Contact(this);
        }

    }

    private Contact(final Builder builder) {
        this.mTitle = builder.mTitle;
        this.mContactStatus = builder.mContactStatus;
        //this.mContactPhoto = builder.mContactPhoto;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getContactStatus() {
        return mContactStatus;
    }

}


