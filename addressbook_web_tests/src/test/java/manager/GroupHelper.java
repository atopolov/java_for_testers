package manager;

import model.GroupData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends HelperBase {

    private final By groupCheckboxes = By.name("selected[]");
    private final By createGroupButton = By.name("new");
    private final By editGroupButton = By.name("edit");
    private final By deleteGroupButton = By.name("delete");
    private final By updateGroupButton = By.name("update");
    private final By submitGroupButton = By.name("submit");

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
        var groups = new ArrayList<GroupData>();

        for (var element : groupElements) {
            String name = element.getText();
            String id = element.findElement(groupCheckboxes).getAttribute("value");
            groups.add(new GroupData().withId(id).withName(name));
        }

        return groups;
    }

    public void openGroupsPage() {
        if (!manager.isElementPresent(createGroupButton)) {
            click(By.linkText("groups"));
        }
    }

    private void returnToGroupsPage() {
        click(By.linkText("group page"));
    }

    private void startGroupCreation() {
        click(createGroupButton);
    }

    private void submitGroupCreation() {
        click(submitGroupButton);
    }

    private void startGroupModification() {
        click(editGroupButton);
    }

    private void submitGroupModification() {
        click(updateGroupButton);
    }

    private void deleteSelectedGroups() {
        click(deleteGroupButton);
    }

    private void selectGroup(String groupId) {
        click(By.cssSelector("input[name='selected[]'][value='" + groupId + "']"));
    }

    private void selectAllGroups() {
        for (var checkbox : manager.driver.findElements(groupCheckboxes)) {
            checkbox.click();
        }
    }

    private void fillGroupForm(GroupData group) {
        type(By.name("group_name"), group.name());
        type(By.name("group_header"), group.header());
        type(By.name("group_footer"), group.footer());
    }
}
