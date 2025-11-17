package tests.groups;

import static model.GroupDataGenerator.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupDeletionTests extends TestBase {

    @DisplayName("Удаление группы")
    @Test
    public void GroupDeletionTest() {
        if (app.groups().getGroupCount() == 0) {
            app.groups().createGroup(randomGroup());
        }
        int groupCount = app.groups().getGroupCount();
        app.groups().removeGroup();
        int newGroupCount = app.groups().getGroupCount();
        assertEquals(groupCount - 1, newGroupCount);
    }

    @DisplayName("Удаление всех групп")
    @Test
    public void GroupDeletionAllTest() {
        if (app.groups().getGroupCount() == 0) {
            app.groups().createGroup(randomGroup());
        }
        app.groups().removeAllGroups();
        assertEquals(0, app.groups().getGroupCount());
    }

}
