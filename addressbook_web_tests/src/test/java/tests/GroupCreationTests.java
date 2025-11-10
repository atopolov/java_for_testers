package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase{

    @Test
    public void GroupCreationTest() {
        app.groups().createGroup(new GroupData("new group name", "new group header", "new group footer"));
    }

    @Test
    public void GroupCreationTestWithEmptyName() {
        app.groups().createGroup(new GroupData());
    }

    @Test
    public void GroupCreationTestWithName() {
        app.groups().createGroup(new GroupData().withName("some name"));
    }

}
