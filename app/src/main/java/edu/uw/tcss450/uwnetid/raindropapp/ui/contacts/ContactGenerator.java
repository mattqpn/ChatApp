package edu.uw.tcss450.uwnetid.raindropapp.ui.contacts;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used to create Dummy Blogs. Use it for development.
 * In future labs, connect to an API to gain actual Blog Data.
 */
public final class ContactGenerator {

    private static final Contact[] CONTACTS;
    public static final int COUNT = 6;
    private static final String[] mTestUsers = {"StephG", "ElijahR", "CharlesB", "MattewPN", "BrianLS", "MollyT"};
    private static final int[] mConnection = {1, 1, 1, 1, 1, 1};

    static {
        CONTACTS = new Contact[COUNT];
        for (int i = 0; i < CONTACTS.length; i++) {
            CONTACTS[i] = new Contact
                    //.Builder("Blog Title: Blog Post #" + (i + 43), 0)
                    .Builder(mTestUsers[i], mConnection[i])
                    .build();
        }
    }

    public static List<Contact> getContacts() {
        return Arrays.asList(CONTACTS);
    }

    public static Contact[] getCONTACTS() {
        return Arrays.copyOf(CONTACTS, CONTACTS.length);
    }

    private ContactGenerator() { }

}

