package tests.contacts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static model.ContactsDataGenerator.randomContactsData;

public class ContactModificationTests extends TestBase {
    @DisplayName("Изменение контакта")
    @Test
    public void modifyContactTest() {
        if (app.contacts().getContactCount() == 0) {
            app.contacts().createContact(randomContactsData());
        }
        app.contacts().modifyContact(randomContactsData());
    }
}
