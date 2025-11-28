package tests.contacts;

import model.ContactsData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static model.ContactsDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactModificationTests extends TestBase {
    @DisplayName("Изменение контакта")
    @Test
    public void modifyContactTest() {
        if (app.contacts().getContactCount() == 0) {
            app.contacts().createContact(randomContactsData());
        }

        List<ContactsData> oldContacts = app.contacts().getContactList();
        Random rnd = new Random();
        int index = rnd.nextInt(oldContacts.size());

        ContactsData originalContact = oldContacts.get(index);
        ContactsData modifiedContact = new ContactsData(
                originalContact.id(),
                randomFirstName(),
                randomLastName(),
                randomAddress(),
                randomHomePhone(),
                randomMobilePhone(),
                randomWorkPhone(),
                randomEmail(),
                randomEmail2(),
                randomEmail3(),
                randomAddress2(),
                randomPhone2(),
                randomNotes(),
                randomPhoto()
        );

        app.contacts().modifyContact(originalContact, modifiedContact);

        var newContacts = app.contacts().getContactList();

        var expectedList = new ArrayList<>(oldContacts);
        expectedList.set(index, modifiedContact);

        expectedList.sort(Comparator.comparing(ContactsData::id));
        newContacts.sort(Comparator.comparing(ContactsData::id));

        assertEquals(expectedList, newContacts, "Списки контактов не совпадают после модификации");
    }
}
