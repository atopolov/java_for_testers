package tests.groups;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import static model.GroupDataGenerator.randomGroupData;
import static model.GroupDataGenerator.randomGroupName;

public class GroupModificationTests extends TestBase {

    @DisplayName("Модификация группы")
    @Test
    public void CanModificationGroup() {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(randomGroupData());
        }

        var oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        var testData = new GroupData().withName(randomGroupName());
        app.groups().modifyGroup(oldGroups.get(index), testData);
        var newGroups = app.hbm().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.set(index, testData.withId(oldGroups.get(index).id()));

        Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
    }
}
