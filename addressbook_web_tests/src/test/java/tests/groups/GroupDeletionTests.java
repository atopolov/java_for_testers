package tests.groups;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.ArrayList;
import java.util.Random;

import static model.GroupDataGenerator.randomGroupData;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Address Book")
@Feature("Groups management")
public class GroupDeletionTests extends TestBase {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Deleting a group")
    @Description("Test verifies that an existing group can be deleted successfully")
    public void GroupDeletionTest() {

        Allure.step("Ensure at least one group exists", () -> {
            if (app.hbm().getGroupCount() == 0) {
                app.hbm().createGroup(randomGroupData());
            }
        });

        var oldGroups = Allure.step("Get list of existing groups", () -> app.hbm().getGroupList());
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());

        Allure.step("Delete selected group", () -> {
        app.groups().removeGroup(oldGroups.get(index));
        });

        var newGroups = Allure.step("Get list of groups after deletion", () -> app.hbm().getGroupList());

        Allure.step("Verify group list updated correctly", () -> {
            var expectedList = new ArrayList<>(oldGroups);
            expectedList.remove(index);
            assertEquals(expectedList, newGroups);
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Deleting all groups")
    @Description("Test verifies that all groups can be deleted successfully")
    public void GroupDeletionAllTest() {
        Allure.step("Ensure at least one group exists", () -> {
            if (app.hbm().getGroupCount() == 0) {
                app.hbm().createGroup(randomGroupData());
            }
        });
        Allure.step("Delete all groups", () -> {
            app.groups().removeAllGroups();
        });
        Allure.step("Verify no groups remain", () -> {
            assertEquals(0, app.hbm().getGroupCount());
        });
    }

}
