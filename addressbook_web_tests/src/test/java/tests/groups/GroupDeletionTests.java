package tests.groups;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.ArrayList;
import java.util.Random;

import static model.GroupDataGenerator.randomGroupData;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupDeletionTests extends TestBase {

    @DisplayName("Удаление группы")
    @Test
    public void GroupDeletionTest() {
        if (app.groups().getGroupCount() == 0) {
            app.groups().createGroup(randomGroupData());
        }

        var oldGroups = app.groups().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        app.groups().removeGroup(oldGroups.get(index));
        var newGroups = app.groups().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.remove(index);
        assertEquals(newGroups, expectedList);
    }

    @DisplayName("Удаление всех групп")
    @Test
    public void GroupDeletionAllTest() {
        if (app.groups().getGroupCount() == 0) {
            app.groups().createGroup(randomGroupData());
        }
        app.groups().removeAllGroups();
        assertEquals(0, app.groups().getGroupCount());
    }

}
