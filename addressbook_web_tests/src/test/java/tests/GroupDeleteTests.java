package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupDeleteTests extends TestBase {

    @Test
    public void GroupDeletionTest() {
        if (!app.groups().isGroupPresent()) {
            app.groups().createGroup(new GroupData("new group name", "new group header", "new group footer"));
        }
        app.groups().removeGroup();
    }

}
