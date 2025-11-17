package tests.groups;

import model.GroupData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.TestBase;

import java.util.ArrayList;
import java.util.List;

import static model.GroupDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupCreationTests extends TestBase {

    public static List<GroupData> multipleGroupsProvider() {
        List<GroupData> groups = new ArrayList<>();

        groups.add(new GroupData());
        groups.add(new GroupData().withName(randomGroupName()));
        groups.add(randomGroup());
        groups.add(randomGroup());
        groups.add(randomGroup());

        String[] names = {"", randomGroupName()};
        String[] headers = {"", randomGroupHeader()};
        String[] footers = {"", randomGroupFooter()};

        for (String name : names) {
            for (String header : headers) {
                for (String footer : footers) {
                    groups.add(new GroupData(name, header, footer));
                }
            }
        }

        return groups;
    }

    @DisplayName("Создание группы")
    @ParameterizedTest
    @MethodSource("multipleGroupsProvider")
    public void GroupCreationTest(GroupData group) {
        int groupCount = app.groups().getGroupCount();
        app.groups().createGroup(group);
        int newGroupCount = app.groups().getGroupCount();
        assertEquals(groupCount + 1, newGroupCount);
    }
}
