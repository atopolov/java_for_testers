package tests.contacts;

import model.ContactsData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.TestBase;

import java.util.ArrayList;
import java.util.List;

import static model.ContactsDataGenerator.randomContactsData;
import static model.ContactsDataGenerator.randomFirstName;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactCreationTests extends TestBase {

    public static List<ContactsData> multipleContactsProvider() {
        List<ContactsData> contacts = new ArrayList<>();

        contacts.add(new ContactsData());
        contacts.add(new ContactsData().withName(randomFirstName()));
        contacts.add(randomContactsData());
        contacts.add(randomContactsData());
        contacts.add(randomContactsData());

        return contacts;
    }

    @DisplayName("Параметризованное создание контактов")
    @ParameterizedTest
    @MethodSource("multipleContactsProvider")
    public void createContactTest(ContactsData contact) {
        int oldCount = app.contacts().getContactCount();

        app.contacts().createContact(contact);
        int newCount = app.contacts().getContactCount();
        assertEquals(oldCount + 1, newCount);
    }
}
