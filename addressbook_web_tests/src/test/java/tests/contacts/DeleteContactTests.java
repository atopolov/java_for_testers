package tests.contacts;

import model.ContactsData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

public class DeleteContactTests extends TestBase {

    @DisplayName("Удаление контакта")
    @Test
    public void deleteContactTest() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactsData("SomeName", "SomeMiddleName", "SomeLastName", "SomeAddress", "SomePhone", "random@random.com"));
        }
        app.contacts().removeContact();
    }


}
