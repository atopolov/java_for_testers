package tests.contacts;

import io.qameta.allure.*;
import manager.ContactHelper;
import model.ContactsDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Address Book")
@Feature("Contacts management")
public class ContactInfoTests extends TestBase {

    @Test
    @DisplayName("Checking contact information")
    @Description("Test verifies that contact information matches the edit form")
    @Severity(SeverityLevel.CRITICAL)
    void testContactInfo() {

        Allure.step("Ensure at least one contact exists", () -> {
            if (app.contacts().getContactCount() == 0) {
                var newContact = ContactsDataGenerator.randomContactsData();
                app.contacts().createContact(newContact);
            }
        });

        var contactList = app.contacts().getContactList();
        var contact = Allure.step(
                "Get last contact from contact list",
                () -> app.contacts().getContactList().getLast()
        );


        var phones = Allure.step(
                "Get phones from main page",
                () -> app.contacts().getPhones(contact)
        );

        var emails = Allure.step(
                "Get emails from main page",
                () -> app.contacts().getEmails(contact)
        );

        var address = Allure.step(
                "Get address from main page",
                () -> app.contacts().getAddress(contact)
        );

        var contactFromEdit = Allure.step(
                "Get contact data from edit form",
                () -> app.contacts().getContactFromEditForm(contact)
        );

        var expectedPhones = Allure.step(
                "Build expected phones from edit form",
                () -> Stream.of(
                                contactFromEdit.phone(),
                                contactFromEdit.mobile(),
                                contactFromEdit.work()
                        )
                        .filter(s -> s != null && !s.isEmpty())
                        .map(ContactHelper::cleanPhone)
                        .collect(Collectors.joining("\n"))
        );

        var expectedEmails = Allure.step(
                "Build expected emails from edit form",
                () -> Stream.of(
                                contactFromEdit.email(),
                                contactFromEdit.email2(),
                                contactFromEdit.email3()
                        )
                        .filter(s -> s != null && !s.isBlank())
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .collect(Collectors.joining("\n"))
        );

        var expectedAddress = Allure.step(
                "Build expected address from edit form",
                () -> contactFromEdit.address().trim()
        );

        System.out.println("=== Checking contact info ===");
        System.out.println("Contact ID: " + contact.id());
        System.out.println("Main page - phones:\n" + phones);
        System.out.println("Edit form - phones:\n" + expectedPhones);
        System.out.println("Main page - emails:\n" + emails);
        System.out.println("Edit form - emails:\n" + expectedEmails);
        System.out.println("Main page - address:\n" + address);
        System.out.println("Edit form - address:\n" + expectedAddress);
        System.out.println("==========================");

        Allure.step("Verify phones match", () -> {
            assertEquals(expectedPhones, phones);
        });

        Allure.step("Verify emails match", () -> {
            assertEquals(expectedEmails, emails);
        });

        Allure.step("Verify address matches", () -> {
            assertEquals(expectedAddress, address);
        });
    }
}

