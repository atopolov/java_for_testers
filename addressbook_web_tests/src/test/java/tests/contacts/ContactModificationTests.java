package tests.contacts;

import io.qameta.allure.*;
import model.ContactsData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static model.ContactsDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Address Book")
@Feature("Contacts management")
public class ContactModificationTests extends TestBase {

    @Test
    @DisplayName("Modifying a contact")
    @Description("Test verifies that contact information matches the edit form")
    @Severity(SeverityLevel.CRITICAL)
    public void modifyContactTest() {

        Allure.step("Ensure at least one contact exists", () -> {
            if (app.hbm().getContactsCount() == 0) {
                app.hbm().createContacts(randomContactsData());
            }
        });

        List<ContactsData> oldContacts = Allure.step(
                "Get list of contacts before modification",
                () -> app.hbm().getContactsList()
        );
        Random rnd = new Random();
        int index = rnd.nextInt(oldContacts.size());

        ContactsData originalContact = oldContacts.get(index);

        Allure.parameter("Original contact", originalContact.toString());

        ContactsData modifiedContact = Allure.step(
                "Prepare modified contact data",
                () -> new ContactsData(
                        originalContact.id(),
                        randomFirstName(),
                        randomLastName(),
                        randomMiddleName(),
                        randomNickname(),
                        randomTitle(),
                        randomCompany(),
                        randomAddress(),
                        randomHomePhone(),
                        randomMobilePhone(),
                        randomWorkPhone(),
                        randomEmail(),
                        randomEmail2(),
                        randomEmail3(),
                        randomHomePage(),
                        originalContact.photo()
                )
        );

        Allure.parameter("Modified contact", modifiedContact.toString());

        Allure.step("Modify selected contact", () -> {
            app.contacts().modifyContact(originalContact, modifiedContact);
        });

        var newContacts = Allure.step("Get list of contacts after modification", () -> app.hbm().getContactsList());

        Allure.step("Verify contact list updated correctly", () -> {
            List<ContactsData> expectedList = new ArrayList<>(oldContacts);
            expectedList.set(index, modifiedContact);

            Assertions.assertEquals(
                    Set.copyOf(expectedList),
                    Set.copyOf(newContacts),
                    "Lists of contacts do not match after modifying a contact"
            );
        });
    }
}
