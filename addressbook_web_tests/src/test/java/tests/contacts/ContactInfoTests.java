package tests.contacts;

import manager.ContactHelper;
import model.ContactsDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactInfoTests extends TestBase {

    @Test
    @DisplayName("Проверка информации о контакте")
    void testContactInfo() {

        if (app.contacts().getContactCount() == 0) {
            var newContact = ContactsDataGenerator.randomContactsData();
            app.contacts().createContact(newContact);
        }

        var contactList = app.contacts().getContactList();
        var contact = contactList.getLast();

        var phones = app.contacts().getPhones(contact);
        var emails = app.contacts().getEmails(contact);
        var address = app.contacts().getAddress(contact);

        var contactFromEdit = app.contacts().getContactFromEditForm(contact);

        var expectedPhones = Stream.of(contactFromEdit.phone(), contactFromEdit.mobile(), contactFromEdit.work())
                .filter(s -> s != null && !s.isEmpty())
                .map(ContactHelper::cleanPhone)
                .collect(Collectors.joining("\n"));

        var expectedEmails = Stream.of(
                        contactFromEdit.email(),
                        contactFromEdit.email2(),
                        contactFromEdit.email3()
                )
                .filter(s -> s != null && !s.isBlank())
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.joining("\n"));

        var expectedAddress = contactFromEdit.address().trim();

        System.out.println("=== Проверка контакта ===");
        System.out.println("ID контакта: " + contact.id());
        System.out.println("Главная страница - телефоны:\n" + phones);
        System.out.println("Форма редактирования - телефоны:\n" + expectedPhones);
        System.out.println("Главная страница - emails:\n" + emails);
        System.out.println("Форма редактирования - emails:\n" + expectedEmails);
        System.out.println("Главная страница - адрес:\n" + address);
        System.out.println("Форма редактирования - адрес:\n" + expectedAddress);
        System.out.println("==========================");

        assertEquals(expectedPhones, phones);
        assertEquals(expectedEmails, emails);
        assertEquals(expectedAddress, address);
    }

}
