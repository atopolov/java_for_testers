package tests.groups;

import model.GroupData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

public class GroupModificationTests extends TestBase {

    @DisplayName("Модификация группы")
    @Test
    public void CanModificationGroup() {
        if (!app.groups().isGroupPresent()) {
        app.groups().createGroup(new GroupData("new group name", "new group header", "new group footer"));
        }

        app.groups().modifyGroup(new GroupData().withName("modifiedName"));
    }


}
