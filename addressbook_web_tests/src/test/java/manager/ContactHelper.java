package manager;

import model.ContactsData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createContact(ContactsData contacts) {
        openNewContactPage();
        fillContactsForm(contacts);
        submitNewContact();
        returnToHomePage();
    }

    public void removeContact(ContactsData contact) {
        selectContact(contact);
        deleteContacts();
        returnToHomePage();
    }

    private void submitNewContact() {
        click(By.xpath("(//input[@name=\'submit\'])[2]"));
    }

    private void openNewContactPage() {
        click(By.linkText("add new"));
    }

    private void fillContactsForm(ContactsData contacts) {
        click(By.name("firstname"));
        type(By.name("firstname"), contacts.firstname());
        click(By.name("lastname"));
        type(By.name("lastname"), contacts.lastname());
        click(By.name("address"));
        type(By.name("address"), contacts.address());
        click(By.name("home"));
        type(By.name("home"), contacts.phone());
        click(By.name("email"));
        type(By.name("email"), contacts.email());
    }

    private void returnToHomePage() {
        click(By.linkText("home page"));
    }

    private void deleteContacts() {
        click(By.name("delete"));
    }

    private void selectContact(ContactsData contact) {
        click(By.cssSelector("input[name='selected[]'][value='" + contact.id() + "']"));
    }

    public int getContactCount() {
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void modifyContact(ContactsData modifiedContact) {
        openContactPage();
        fillContactsForm(modifiedContact);
        submitEditedContact();
        returnToHomePage();
    }

    private void openContactPage() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    private void submitEditedContact() {
        click(By.name("update"));
    }

    public void removeAllContacts() {
        selectAllContacts();
        deleteContacts();
        returnToHomePage();
    }

    private void selectAllContacts() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            checkbox.click();
        }
    }

    public List<ContactsData> getContactList() {
        var contactList = new ArrayList<ContactsData>();

        var checkboxes = manager.driver.findElements(By.name("selected[]"));

        for (var checkbox : checkboxes) {
            String id = checkbox.getAttribute("value");
            var row = checkbox.findElement(By.xpath("./ancestor::tr"));
            String lastName = row.findElement(By.xpath(".//td[2]")).getText();
            String firstName = row.findElement(By.xpath(".//td[3]")).getText();

            contactList.add(new ContactsData()
                    .withId(id)
                    .withName(firstName)
                    .withLastName(lastName));
        }

        return contactList;
    }
}

