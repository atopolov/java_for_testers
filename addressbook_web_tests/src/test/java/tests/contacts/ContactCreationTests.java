package tests.contacts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
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

public class ContactCreationTests extends TestBase {

    public static final String CONTACTS_XML = "contacts.xml";

    public static List<ContactsData> multipleContactsProvider() throws IOException {
        String filePath = System.getProperty("contacts.file", CONTACTS_XML);
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("Файл с контактами не найден: " + file.getAbsolutePath());
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
            throw new IllegalArgumentException("Неподдерживаемый формат файла: " + CONTACTS_XML);
        }

        return mapper.readValue(file, new TypeReference<>() {
        });
    }

    @DisplayName("Параметризованное создание контактов")
    @ParameterizedTest
    @MethodSource("multipleContactsProvider")
    public void createContactTest(ContactsData contact) {

        var oldContacts = app.hbm().getContactsList();
        app.contacts().createContact(contact);
        var newContacts = app.hbm().getContactsList();

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

    @DisplayName("Добавление контакта в группу")
    @Test
    public void canAddExistingContactToGroup() {

        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(randomGroupData());
        }

        var group = app.hbm().getGroupList().getFirst();

        var contact = ContactsDataGenerator.randomContactsData();

        if (app.hbm().getContactsCount() == 0) {
            app.contacts().createContact(contact,
                    null
            );
        }

        var contactsNotInGroup = app.hbm().getContactsNotInGroup(group);

        if (contactsNotInGroup.isEmpty()) {
            var newContact = ContactsDataGenerator.randomContactsData();
            app.contacts().createContact(newContact,
                    null
            );
            contactsNotInGroup = app.hbm().getContactsNotInGroup(group);
        }

        var contactToAdd = contactsNotInGroup.getFirst();

        var oldRelated = app.hbm().getContactsInGroup(group);

        app.contacts().addContactToGroup(contactToAdd, group);

        var newRelated = app.hbm().getContactsInGroup(group);

        Assertions.assertEquals(
                oldRelated.size() + 1,
                newRelated.size(),
                "Количество контактов в группе должно увеличиться на 1"
        );

        boolean contactAdded = false;
        for (var c : newRelated) {
            if (c.id().equals(contactToAdd.id())) {
                contactAdded = true;
                break;
            }
        }
        Assertions.assertTrue(contactAdded,
                "Добавленный контакт должен присутствовать в группе");
    }
}




