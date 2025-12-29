package tests.contacts;

import io.qameta.allure.*;
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

@Epic("Address Book")
@Feature("Contacts management")
public class ContactDeletionTests extends TestBase {


    @Test
    @DisplayName("Deleting a contact")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that an existing contact can be deleted successfully")
    public void deleteContactTest() {
        Allure.step("Ensure at least one contact exists", () -> {
            if (app.hbm().getContactsCount() == 0) {
                app.hbm().createContacts(randomContactsData());
            }
        });

        var oldContacts = Allure.step(
                "Get list of existing contacts",
                app.contacts()::getContactList
        );
        Random rnd = new Random();
        int index = rnd.nextInt(oldContacts.size());
        ContactsData contactsToDelete = oldContacts.get(index);

        Allure.parameter("Deleted contact", contactsToDelete.toString());

        Allure.step("Delete selected contact", () -> {
            app.contacts().removeContact(contactsToDelete);
        });
        var newContacts = Allure.step(
                "Get list of contacts after deletion",
                app.contacts()::getContactList
        );
        Allure.step("Verify contact count decreased by 1", () -> {
            assertEquals(oldContacts.size() - 1, newContacts.size());
        });
    }

    @Test
    @DisplayName("Deleting all contacts")
    @Description("Test verifies that all contacts can be deleted successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteAllContactsTest() {
        Allure.step("Ensure at least one contact exists", () -> {
            if (app.hbm().getContactsCount() == 0) {
                app.hbm().createContacts(randomContactsData());
            }
        });
        Allure.step("Delete all contacts", () -> {
            app.contacts().removeAllContacts();
        });

        Allure.step("Verify all contacts were deleted", () -> {
            assertEquals(0, app.hbm().getContactsCount());
        });
    }

    @Test
    @DisplayName("Deleting a contact from a group")
    @Description("Test verifies that an existing contact can be deleted from a group successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void CanDeleteContactFromGroup() {

        Allure.step("Ensure at least one contact exists", () -> {
            if (app.hbm().getContactsCount() == 0) {
                app.hbm().createContacts(randomContactsData());
            }
        });
        var group = Allure.step(
                "Get first group from the system",
                () -> app.hbm().getGroupList().getFirst()
        );

        Allure.step("Ensure at least one contact is in the group", () -> {
            if (app.hbm().getContactsInGroup(group).isEmpty()) {
                var contact = ContactsDataGenerator.randomContactsData();
                app.contacts().createContact(contact, group);
            }
        });

        var oldRelated = app.hbm().getContactsInGroup(group);
        var contactToRemove = oldRelated.getFirst();
        Allure.step("Remove contact from group", () -> {
            app.contacts().removeContactFromGroup(contactToRemove, group);
        });
        var newRelated = Allure.step(
                "Get list of contacts in the group after deletion",
                () -> app.hbm().getContactsInGroup(group)
        );

        Allure.step("Verify number of contacts in group decreased by 1", () -> {
            Assertions.assertEquals(oldRelated.size() - 1, newRelated.size(),
                    "Number of contacts in group did not decrease");
        });

        Allure.step("Verify contact was removed from the group", () -> {
            boolean contactRemoved = newRelated.stream()
                    .noneMatch(c -> c.id().equals(contactToRemove.id()));
            Assertions.assertTrue(contactRemoved,
                    "Contact should be removed from the selected group");
        });
    }
}

