package manager;

import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class GroupHelper extends HelperBase {

    public static final By GROUPS = By.linkText("groups");
    public static final By GROUP_PAGE = By.linkText("group page");
    private final By GROUP_CHECKBOXES = By.name("selected[]");
    private final By CREATE_GROUP_BUTTON = By.name("new");
    private final By EDIT_GROUP_BUTTON = By.name("edit");
    private final By DELETE_GROUP_BUTTON = By.name("delete");
    private final By UPDATE_GROUP_BUTTON = By.name("update");
    private final By SUBMIT_GROUP_BUTTON = By.name("submit");

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createGroup(GroupData group) {
        openGroupsPage();
        startGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        returnToGroupsPage();
    }

    public void modifyGroup(GroupData group, GroupData modifiedGroup) {
        openGroupsPage();
        selectGroup(group.id());
        startGroupModification();
        fillGroupForm(modifiedGroup);
        submitGroupModification();
        returnToGroupsPage();
    }

    public void removeGroup(GroupData group) {
        openGroupsPage();
        selectGroup(group.id());
        deleteSelectedGroups();
        returnToGroupsPage();
    }

    public void removeAllGroups() {
        openGroupsPage();
        selectAllGroups();
        deleteSelectedGroups();
        returnToGroupsPage();
    }

    public int getGroupCount() {
        openGroupsPage();
        return manager.driver.findElements(By.cssSelector("span.group")).size();
    }

    public List<GroupData> getGroupList() {
        openGroupsPage();
        var groupElements = manager.driver.findElements(By.cssSelector("span.group"));

        return groupElements.stream()
                .map(element -> new GroupData().withId(element.findElement(GROUP_CHECKBOXES).getAttribute("value")).withName(element.getText()))
                .collect(Collectors.toList());
    }

    public void openGroupsPage() {
        if (!manager.isElementPresent(CREATE_GROUP_BUTTON)) {
            click(GROUPS);
        }
    }

    private void returnToGroupsPage() {
        click(GROUP_PAGE);
    }

    private void startGroupCreation() {
        click(CREATE_GROUP_BUTTON);
    }

    private void submitGroupCreation() {
        click(SUBMIT_GROUP_BUTTON);
    }

    private void startGroupModification() {
        click(EDIT_GROUP_BUTTON);
    }

    private void submitGroupModification() {
        click(UPDATE_GROUP_BUTTON);
    }

    private void deleteSelectedGroups() {
        click(DELETE_GROUP_BUTTON);
    }

    private void selectGroup(String groupId) {
        click(By.cssSelector("input[name='selected[]'][value='" + groupId + "']"));
    }

    private void selectAllGroups() {
        manager.driver
                .findElements(GROUP_CHECKBOXES).forEach(WebElement::click);
    }

    private void fillGroupForm(GroupData group) {
        type(By.name("group_name"), group.name());
        type(By.name("group_header"), group.header());
        type(By.name("group_footer"), group.footer());
    }
}
