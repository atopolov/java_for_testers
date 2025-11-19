package tests.groups;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static model.GroupDataGenerator.randomGroup;

public class GroupModificationTests extends TestBase {

    @DisplayName("Модификация группы")
    @Test
    public void CanModificationGroup() {
        if (app.groups().getGroupCount() == 0) {
            app.groups().createGroup(randomGroup());
        }
        app.groups().modifyGroup(randomGroup());
    }
}
