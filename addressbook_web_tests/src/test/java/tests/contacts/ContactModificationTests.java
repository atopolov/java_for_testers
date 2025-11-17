package tests.contacts;

import model.ContactsData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

public class ContactModificationTests extends TestBase {
    @DisplayName("Изменение контакта")
    @Test
    public void modifyContactTest() {
        if (app.contacts().getContactCount() == 0) {
            app.contacts().createContact(new ContactsData("SomeName", "SomeMiddleName", "SomeLastName", "SomeAddress", "SomePhone", "random@random.com"));
        }
        app.contacts().modifyContact(new ContactsData().withName("Modified Name").withLastName("Modified Last Name").withAddress("Modified Address").withPhone("Modified Phone").withEmail("modified@random.com"));
    }
}
