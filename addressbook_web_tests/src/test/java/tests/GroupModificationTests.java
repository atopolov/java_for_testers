package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupModificationTests extends TestBase{

    @Test
    public void CanModificationGroup() {
        if (!app.groups().isGroupPresent()) {
        app.groups().createGroup(new GroupData("new group name", "new group header", "new group footer"));
        }

        app.groups().modifyGroup(new GroupData().withName("modifiedName"));
    }


}
