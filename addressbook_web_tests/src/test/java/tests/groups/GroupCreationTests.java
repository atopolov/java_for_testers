package tests.groups;

import model.GroupData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

public class GroupCreationTests extends TestBase {

    @DisplayName("Создание группы")
    @Test
    public void GroupCreationTest() {
        app.groups().createGroup(new GroupData("new group name", "new group header", "new group footer"));
    }

    @DisplayName("Создание группы с пустым названием")
    @Test
    public void GroupCreationTestWithEmptyName() {
        app.groups().createGroup(new GroupData());
    }

    @DisplayName("Создание группы с именем")
    @Test
    public void GroupCreationTestWithName() {
        app.groups().createGroup(new GroupData().withName("some name"));
    }

}
