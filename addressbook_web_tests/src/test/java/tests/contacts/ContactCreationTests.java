package tests.contacts;

import model.ContactsData;
import static model.ContactsDataGenerator.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactCreationTests extends TestBase {

    @DisplayName("Создание контакта")
    @Test
    public void createNewContact() {
        int contactCount = app.contacts().getContactCount();
        app.contacts().createContact(randomContactsData());
        int newContactCount = app.contacts().getContactCount();
        assertEquals(contactCount + 1, newContactCount);
    }

    @DisplayName("Создание контакта с пустыми данными")
    @Test
    public void createNewContactWithEmptyData() {
        app.contacts().createContact(new ContactsData());
    }

    @DisplayName("Создание контакта с именем")
    @Test
    public void createNewContactWithName() {
        app.contacts().createContact(new ContactsData().withName(randomFirstName()));
    }

    @DisplayName("Создание нескольких контактов")
    @Test
    public void createMultipleContacts() {
        int n = 5;
        int contactCount = app.contacts().getContactCount();
        for (int i = 0; i < n; i++) {
            app.contacts().createContact(randomContactsData());
        }
        int newContactCount = app.contacts().getContactCount();
        assertEquals(contactCount + n, newContactCount);
    }

}
