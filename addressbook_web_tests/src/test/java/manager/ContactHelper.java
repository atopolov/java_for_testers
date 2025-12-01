package manager;

import model.ContactsData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

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

    public void createContact(ContactsData contact, GroupData group) {
        openNewContactPage();
        fillContactsForm(contact);
        selectGroup(group);
        submitNewContact();
        returnToHomePage();
    }

    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
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
                            id,
                            firstName,
                            lastName,
                            "", // middlename
                            "", // nickname
                            "", // title
                            "", // company
                            "", // address
                            "", // phone
                            "", // mobile
                            "", // work
                            "", // email
                            "", // email2
                            "", // email3
                            "", // homepage
                            "", // photo
                            ""  // fax
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
        type(By.name("middlename"), data.middlename());
        type(By.name("nickname"), data.nickname());
        type(By.name("title"), data.title());
        type(By.name("company"), data.company());
        type(By.name("address"), data.address());
        type(By.name("home"), data.phone());
        type(By.name("mobile"), data.mobile());
        type(By.name("work"), data.work());
        type(By.name("email"), data.email());
        type(By.name("email2"), data.email2());
        type(By.name("email3"), data.email3());
        type(By.name("homepage"), data.homepage());
        type(By.name("fax"), data.fax());
    }
}