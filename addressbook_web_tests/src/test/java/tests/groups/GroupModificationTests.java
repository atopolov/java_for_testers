package tests.groups;

import model.GroupData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import static model.GroupDataGenerator.randomGroup;
import static model.GroupDataGenerator.randomGroupName;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupModificationTests extends TestBase {

    @DisplayName("Модификация группы")
    @Test
    public void CanModificationGroup() {
        if (app.groups().getGroupCount() == 0) {
            app.groups().createGroup(randomGroup());
        }

        var oldGroups = app.groups().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        var testData = new GroupData().withName(randomGroupName());
        app.groups().modifyGroup(oldGroups.get(index), testData);
        var newGroups = app.groups().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.set(index, testData.withId(oldGroups.get(index).id()));

        expectedList.sort(Comparator.comparing(GroupData::id));
        newGroups.sort(Comparator.comparing(GroupData::id));

        assertEquals(expectedList, newGroups, "Списки групп не совпадают после модификации");
    }
}
