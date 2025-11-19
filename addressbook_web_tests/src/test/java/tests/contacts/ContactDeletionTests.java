package tests.contacts;

import model.ContactsData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.Random;

import static model.ContactsDataGenerator.randomContactsData;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactDeletionTests extends TestBase {

    @DisplayName("Удаление контакта")
    @Test
    public void deleteContactTest() {
        if (app.contacts().getContactCount() == 0) {
            app.contacts().createContact(randomContactsData());
        }

        var oldContacts = app.contacts().getContactList();
        Random rnd = new Random();
        int index = rnd.nextInt(oldContacts.size());
        ContactsData contactsToDelete = oldContacts.get(index);
        app.contacts().removeContact(contactsToDelete);
        var newContacts = app.contacts().getContactList();
        assertEquals(oldContacts.size() - 1, newContacts.size());
    }

    @DisplayName("Удаление всех контактов")
    @Test
    public void deleteAllContactsTest() {
        if (app.contacts().getContactCount() == 0) {
            app.contacts().createContact(randomContactsData());
        }
        app.contacts().removeAllContacts();
        assertEquals(0, app.contacts().getContactCount());

    }


}
