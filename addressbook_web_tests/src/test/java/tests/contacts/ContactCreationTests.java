package tests.contacts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.qameta.allure.*;
import model.ContactsData;
import model.ContactsDataGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.TestBase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static model.GroupDataGenerator.randomGroupData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Epic("Address Book")
@Feature("Contacts management")
public class ContactCreationTests extends TestBase {

    public static final String CONTACTS_XML = "contacts.xml";

    public static List<ContactsData> multipleContactsProvider() throws IOException {
        String filePath = System.getProperty("contacts.file", CONTACTS_XML);
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("File with contacts not found: " + file.getAbsolutePath());
        }

        ObjectMapper mapper;
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            mapper = new ObjectMapper(new YAMLFactory());
        } else if (fileName.endsWith(".xml")) {
            mapper = new XmlMapper();
        } else if (fileName.endsWith(".json")) {
            mapper = new ObjectMapper();
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + CONTACTS_XML);
        }

        return mapper.readValue(file, new TypeReference<>() {
        });
    }


    @ParameterizedTest
    @DisplayName("Parameterized contact creation")
    @Description("Test verifies that a new contact can be created using parameterized data")
    @Severity(SeverityLevel.CRITICAL)
    @MethodSource("multipleContactsProvider")
    public void createContactTest(ContactsData contact) {

        var oldContacts = Allure.step(
                "Get list of contacts before creation",
                app.hbm()::getContactsList
        );

        Allure.step("Create new contact", () -> {
            app.contacts().createContact(contact);
        });

        var newContacts = Allure.step(
                "Get list of contacts after creation",
                app.hbm()::getContactsList
        );

        Allure.step("Verify contact count increased by 1", () -> {
            assertEquals(oldContacts.size() + 1, newContacts.size(),
                    "Number of contacts did not increase");
        });

        Allure.step("Verify created contact is present in the list", () -> {
            ContactsData created = newContacts.stream()
                    .filter(c -> !oldContacts.contains(c))
                    .findFirst()
                    .orElse(null);

            assertNotNull(created, "Created contact not found");
            assertNotNull(created.id(), "Created contact ID is null");

            List<ContactsData> expectedList = new ArrayList<>(oldContacts);
            expectedList.add(created);

            expectedList.sort(
                    Comparator.comparing(ContactsData::firstname)
                            .thenComparing(ContactsData::lastname)
            );
            newContacts.sort(
                    Comparator.comparing(ContactsData::firstname)
                            .thenComparing(ContactsData::lastname)
            );

            assertEquals(expectedList, newContacts,
                    "Lists of contacts do not match after creating a new contact");
        });
    }


    @Test
    @DisplayName("Adding existing contact to group")
    @Description("Test verifies that an existing contact can be added to a group")
    @Severity(SeverityLevel.CRITICAL)
    public void canAddExistingContactToGroup() {

        Allure.step("Ensure at least one group exists", () -> {
            if (app.hbm().getGroupCount() == 0) {
                app.hbm().createGroup(randomGroupData());
            }
        });

        var group = Allure.step(
                "Get first available group",
                () -> app.hbm().getGroupList().getFirst()
        );

        var contact = ContactsDataGenerator.randomContactsData();

        Allure.step("Ensure at least one contact exists", () -> {
            if (app.hbm().getContactsCount() == 0) {
                app.contacts().createContact(contact,
                        null
                );
            }
        });

        var contactsNotInGroup = Allure.step(
                "Get contacts not assigned to the group",
                () -> app.hbm().getContactsNotInGroup(group)
        );


        if (contactsNotInGroup.isEmpty()) {
            var newContact = ContactsDataGenerator.randomContactsData();
            app.contacts().createContact(newContact,
                    null
            );
            contactsNotInGroup = app.hbm().getContactsNotInGroup(group);
        }

        var contactToAdd = contactsNotInGroup.getFirst();

        var oldRelated = app.hbm().getContactsInGroup(group);

        Allure.step("Add contact to group", () -> {
            app.contacts().addContactToGroup(contactToAdd, group);
        });

        var newRelated =Allure.step(
                "Get contacts in group after adding",
                () -> app.hbm().getContactsInGroup(group)
        );

        Allure.step("Verify number of contacts in group increased by 1", () -> {
            Assertions.assertEquals(
                    oldRelated.size() + 1,
                    newRelated.size(),
                    "Number of contacts in group did not increase"
            );
        });

        Allure.step("Verify contact is present in the group", () -> {
            boolean contactAdded = newRelated.stream()
                    .anyMatch(c -> c.id().equals(contactToAdd.id()));

            Assertions.assertTrue(contactAdded,
                    "Added contact is not present in the group");
        });
    }
}




