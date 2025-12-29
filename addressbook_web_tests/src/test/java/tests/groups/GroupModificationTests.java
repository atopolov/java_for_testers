package tests.groups;

import io.qameta.allure.*;
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

@Epic("Address Book")
@Feature("Groups management")
public class GroupModificationTests extends TestBase {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Modifying a group")
    @Description("Test verifies that an existing group can be modified successfully")
    public void CanModificationGroup() {
        Allure.step("Ensure at least one group exists", () -> {
            if (app.hbm().getGroupCount() == 0) {
                app.hbm().createGroup(randomGroupData());
            }
        });

        var oldGroups = Allure.step("Get list of groups before modification", () ->
                app.hbm().getGroupList());

        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        var testData = Allure.step(
                "Prepare modified group data",
                () -> new GroupData().withName(randomGroupName())
        );
        Allure.step("Modify selected group", () -> {
                    app.groups().modifyGroup(oldGroups.get(index), testData);
                });
        var newGroups = Allure.step(
                "Get list of groups after modification",
                app.hbm()::getGroupList
        );
        Allure.step("Verify group list updated correctly", () -> {
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.set(index, testData.withId(oldGroups.get(index).id()));

        Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
    });
    }
}
