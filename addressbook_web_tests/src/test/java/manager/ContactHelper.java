package manager;

import model.ContactsData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public static final By NEW_GROUP = By.name("new_group");
    public static final By HOME_PAGE = By.linkText("home page");
    public static final By REMOVE_BUTTON = By.name("remove");
    public static final By GROUP_LIST = By.name("group");
    public static final By GROUP_PAGE_RETURN = By.partialLinkText("group page");
    private final By CONTACT_ROWS = By.xpath("//tr[@name='entry']");
    private final By CONTACT_CHECKBOX = By.name("selected[]");
    private final By NEW_CONTACT_BUTTON = By.cssSelector("a[href='edit.php']");
    private final By DELETE_BUTTON = By.name("delete");
    private final By UPDATE_BUTTON = By.name("update");
    private final By SUBMIT_BUTTON = By.xpath("(//input[@name='submit'])[2]");

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
        new Select(manager.driver.findElement(NEW_GROUP)).selectByValue(group.id());
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
        return manager.driver.findElements(CONTACT_CHECKBOX).size();
    }

    public List<ContactsData> getContactList() {
        var contacts = new ArrayList<ContactsData>();
        var rows = manager.driver.findElements(CONTACT_ROWS);

        for (var row : rows) {
            String id = row.findElement(CONTACT_CHECKBOX).getAttribute("value");
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
        click(NEW_CONTACT_BUTTON);
    }

    private void returnToHomePage() {
        click(HOME_PAGE);
    }

    private void openContactEditPage(String contactId) {
        click(By.xpath("//tr[.//input[@name='selected[]' and @value='"
                + contactId + "']]//img[@alt='Edit']"));
    }

    private void submitNewContact() {
        click(SUBMIT_BUTTON);
    }

    private void submitEditedContact() {
        click(UPDATE_BUTTON);
    }

    private void deleteSelectedContacts() {
        click(DELETE_BUTTON);
    }

    private void selectContact(String contactId) {
        click(By.cssSelector("input[name='selected[]'][value='" + contactId + "']"));
    }

    private void selectAllContacts() {
        var checkboxes = manager.driver.findElements(CONTACT_CHECKBOX);
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

    public void removeContactFromGroup(ContactsData contactToRemove, GroupData group) {
        manager.openHomePage();
        selectGroupFromList(group);
        selectContactInGroup(contactToRemove);
        removeContact();
        returnToGroupPage();
    }

    private void returnToGroupPage() {
        click(GROUP_PAGE_RETURN);
    }

    private void removeContact() {
        click(REMOVE_BUTTON);
    }

    private void selectContactInGroup(ContactsData contactToRemove) {
        click(By.cssSelector("input[name='selected[]'][value='" + contactToRemove.id() + "']"));
    }

    private void selectGroupFromList(GroupData group) {
        new Select(manager.driver.findElement(GROUP_LIST))
                .selectByVisibleText(group.name());
    }
}
