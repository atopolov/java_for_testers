package manager;

import model.ContactsData;
import org.openqa.selenium.By;

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

    public void removeContact() {
        selectContact();
        deleteContact();
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
        click(By.name("middlename"));
        type(By.name("middlename"), contacts.middlename());
        click(By.name("lastname"));
        type(By.name("lastname"), contacts.lastname());
        click(By.name("address"));
        type(By.name("address"), contacts.address());
        click(By.name("home"));
        type(By.name("home"), contacts.home());
        click(By.name("email"));
        type(By.name("email"), contacts.email());
    }

    private void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public boolean isContactPresent() {
        return manager.isElementPresent(By.name("selected[]"));
    }

    private void deleteContact() {
        click(By.name("delete"));
    }

    private void selectContact() {
        click(By.name("selected[]"));
    }
}
