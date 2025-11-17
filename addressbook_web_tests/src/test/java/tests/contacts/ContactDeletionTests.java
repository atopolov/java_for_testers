package tests.contacts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static model.ContactsDataGenerator.randomContactsData;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactDeletionTests extends TestBase {

    @DisplayName("Удаление контакта")
    @Test
    public void deleteContactTest() {
        if (app.contacts().getContactCount() == 0) {
            app.contacts().createContact(randomContactsData());
        }
        int contactCount = app.contacts().getContactCount();
        app.contacts().removeContact();
        int newContactCount = app.contacts().getContactCount();
        assertEquals(contactCount - 1, newContactCount);
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
