package tests.contacts;

import model.ContactsData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.TestBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static model.ContactsDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContactCreationTests extends TestBase {

    public static List<ContactsData> multipleContactsProvider() {
        List<ContactsData> contacts = new ArrayList<>();

        contacts.add(new ContactsData().withName(randomFirstName()).withLastName(randomLastName()));
        contacts.add(randomContactsData());
        contacts.add(randomContactsData());

        return contacts;
    }

    @DisplayName("Параметризованное создание контактов")
    @ParameterizedTest
    @MethodSource("multipleContactsProvider")
    public void createContactTest(ContactsData contact) {

        var oldContacts = app.contacts().getContactList();
        app.contacts().createContact(contact);
        var newContacts = app.contacts().getContactList();

        assertEquals(oldContacts.size() + 1, newContacts.size(), "Количество контактов не увеличилось");

        ContactsData created = null;
        for (ContactsData c : newContacts) {
            if (!oldContacts.contains(c)) {
                created = c;
                break;
            }
        }

        assertNotNull(created, "Созданный контакт не найден");
        assertNotNull(created.id(), "У созданного контакта нет id");

        List<ContactsData> expectedList = new ArrayList<>(oldContacts);
        expectedList.add(created);

        expectedList.sort(Comparator.comparing(ContactsData::firstname)
                .thenComparing(ContactsData::lastname));
        newContacts.sort(Comparator.comparing(ContactsData::firstname)
                .thenComparing(ContactsData::lastname));

        assertEquals(expectedList, newContacts, "Списки контактов не совпадают после создания нового контакта");
    }
}
