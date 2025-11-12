package tests.contacts;

import model.ContactsData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

public class CreateNewContactTests extends TestBase {

    @DisplayName("Создание контакта")
    @Test
    public void createNewContact() {
        app.contacts().createContact(new ContactsData("Some Name", "Some Middle Name", "Some Last Name", "Some Address", "Some Phone", "random@random.com"));
    }

    @DisplayName("Создание контакта с пустыми данными")
    @Test
    public void createNewContactWithEmptyData() {
        app.contacts().createContact(new ContactsData());
    }

    @DisplayName("Создание контакта с именем")
    @Test
    public void createNewContactWithName() {
        app.contacts().createContact(new ContactsData().withName("Some Name"));
    }

}
