package tests.groups;

import model.GroupData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

public class GroupDeleteTests extends TestBase {

    @DisplayName("Удаление группы")
    @Test
    public void GroupDeletionTest() {
        if (!app.groups().isGroupPresent()) {
            app.groups().createGroup(new GroupData("new group name", "new group header", "new group footer"));
        }
        app.groups().removeGroup();
    }

}
