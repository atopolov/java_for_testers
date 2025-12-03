package tests.contacts;

import model.ContactsData;
import model.ContactsDataGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.Random;

import static model.ContactsDataGenerator.randomContactsData;
import static model.GroupDataGenerator.randomGroupData;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactDeletionTests extends TestBase {

    @DisplayName("Удаление контакта")
    @Test
    public void deleteContactTest() {
        if (app.hbm().getContactsCount() == 0) {
            app.hbm().createContacts(randomContactsData());
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
        if (app.hbm().getContactsCount() == 0) {
            app.hbm().createContacts(randomContactsData());
        }
        app.contacts().removeAllContacts();
        assertEquals(0, app.hbm().getContactsCount());

    }

    @DisplayName("Удаление контакта из группы")
    @Test
    public void CanDeleteContactFromGroup() {

        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(randomGroupData());
        }
        var group = app.hbm().getGroupList().getFirst();

        if (app.hbm().getContactsInGroup(group).isEmpty()) {
            var contact = ContactsDataGenerator.randomContactsData();
            app.contacts().createContact(contact, group);
        }

        var oldRelated = app.hbm().getContactsInGroup(group);
        var contactToRemove = oldRelated.getFirst();
        app.contacts().removeContactFromGroup(contactToRemove, group);
        var newRelated = app.hbm().getContactsInGroup(group);

        Assertions.assertEquals(oldRelated.size() - 1, newRelated.size(),
                "Количество контактов в группе должно уменьшиться на 1");

        boolean contactRemoved = true;

        for (var c : newRelated) {
            if (c.id().equals(contactToRemove.id())) {
                contactRemoved = false;
                break;
            }
        }

        Assertions.assertTrue(contactRemoved,
                "Контакт должен быть удалён из выбранной группы");
    }

}

