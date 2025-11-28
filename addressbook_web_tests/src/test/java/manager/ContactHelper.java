package manager;

import model.ContactsData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    private final By contactRows = By.xpath("//tr[@name='entry']");
    private final By contactCheckbox = By.name("selected[]");
    private final By newContactBtn = By.cssSelector("a[href='edit.php']");
    private final By deleteBtn = By.name("delete");
    private final By updateBtn = By.name("update");
    private final By submitBtn = By.xpath("(//input[@name='submit'])[2]");

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createContact(ContactsData contact) {
        openNewContactPage();
        fillContactsForm(contact);
        submitNewContact();
        returnToHomePage();
    }

    public void modifyContact(ContactsData contact, ContactsData modifiedContact) {
        openContactEditPage(contact.id());
        fillContactsForm(modifiedContact);
        submitEditedContact();
        returnToHomePage();
    }

    public void removeContact(ContactsData contact) {
        selectContact(contact.id());
        deleteSelectedContacts();
        returnToHomePage();
    }

    public void removeAllContacts() {
        selectAllContacts();
        deleteSelectedContacts();
        returnToHomePage();
    }

    public int getContactCount() {
        return manager.driver.findElements(contactCheckbox).size();
    }

    public List<ContactsData> getContactList() {
        var contacts = new ArrayList<ContactsData>();
        var rows = manager.driver.findElements(contactRows);

        for (var row : rows) {
            String id = row.findElement(contactCheckbox).getAttribute("value");
            String lastName = row.findElement(By.xpath("./td[2]")).getText();
            String firstName = row.findElement(By.xpath("./td[3]")).getText();

            contacts.add(
                    new ContactsData(
                            id,          // String
                            firstName,   // String
                            lastName,    // String
                            null,        // address
                            null,        // phone
                            null,        // mobile
                            null,        // work
                            null,        // email
                            null,        // email2
                            null,        // email3
                            null,        // address2
                            null,        // phone2
                            null,        // notes
                            null         // photo
                    )
            );
        }

        return contacts;
    }

    private void openNewContactPage() {
        click(newContactBtn);
    }

    private void returnToHomePage() {
        click(By.linkText("home page"));
    }

    private void openContactEditPage(String contactId) {
        click(By.xpath("//tr[.//input[@name='selected[]' and @value='"
                + contactId + "']]//img[@alt='Edit']"));
    }

    private void submitNewContact() {
        click(submitBtn);
    }

    private void submitEditedContact() {
        click(updateBtn);
    }

    private void deleteSelectedContacts() {
        click(deleteBtn);
    }

    private void selectContact(String contactId) {
        click(By.cssSelector("input[name='selected[]'][value='" + contactId + "']"));
    }

    private void selectAllContacts() {
        var checkboxes = manager.driver.findElements(contactCheckbox);
        for (var checkbox : checkboxes) {
            checkbox.click();
        }
    }

    private void fillContactsForm(ContactsData data) {
        type(By.name("firstname"), data.firstname());
        type(By.name("lastname"), data.lastname());
        type(By.name("address"), data.address());
        type(By.name("home"), data.phone());
        attach(By.name("photo"), data.photo());
    }
}