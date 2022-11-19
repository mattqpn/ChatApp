package edu.uw.tcss450.uwnetid.raindropapp.ui.contacts;

import java.util.Arrays;
import java.util.List;

public class ContactGenerator {
    private static final Contact[] CONTACTS;
    public static final int COUNT = 5;
    public static final String[] mTestUsers = {"Stephanie", "Molly", "Elijah", "Brian", "Matthew"};

    static {
        CONTACTS = new Contact[COUNT];
        for (int i = 0; i < CONTACTS.length; i++) {
            CONTACTS[i] = new Contact.Builder(mTestUsers[i]).addContactStatus(true).build();
        }
    }

    public static List<Contact> getContactList() {
        return Arrays.asList(CONTACTS);
    }

    public static Contact[] getContacts() {
        return Arrays.copyOf(CONTACTS, CONTACTS.length);
    }

    private ContactGenerator() { }
}

