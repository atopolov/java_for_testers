package tests.groups;

import model.GroupData;
import static model.GroupDataGenerator.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupCreationTests extends TestBase {

    @DisplayName("Создание группы")
    @Test
    public void GroupCreationTest() {
        int groupCount = app.groups().getGroupCount();
        app.groups().createGroup(randomGroup());
        int newGroupCount = app.groups().getGroupCount();
        assertEquals(groupCount + 1, newGroupCount);
    }

    @DisplayName("Создание группы с пустым названием")
    @Test
    public void GroupCreationTestWithEmptyName() {
        app.groups().createGroup(new GroupData());
    }

    @DisplayName("Создание группы с именем")
    @Test
    public void GroupCreationTestWithName() {
        app.groups().createGroup(new GroupData().withName(randomGroupName()));
    }

    @DisplayName("Создание нескольких групп")
    @Test
    public void MultipleGroupsCreationTest() {
        int n = 5;
        int groupCount = app.groups().getGroupCount();
        for (int i = 0; i < n; i++){
            app.groups().createGroup(randomGroup());
        }
        int newGroupCount = app.groups().getGroupCount();
        assertEquals(groupCount + n, newGroupCount);
    }

}
