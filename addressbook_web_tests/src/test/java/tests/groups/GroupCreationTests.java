package tests.groups;

import model.GroupData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.TestBase;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static model.GroupDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GroupCreationTests extends TestBase {

    public static List<GroupData> multipleGroupsProvider() throws IOException {
        List<GroupData> groups = new ArrayList<>();

//        String[] names = {"", randomGroupName()};
//        String[] headers = {"", randomGroupHeader()};
//        String[] footers = {"", randomGroupFooter()};
//
//        for (String name : names) {
//            for (String header : headers) {
//                for (String footer : footers) {
//                    groups.add(new GroupData()
//                            .withName(name)
//                            .withHeader(header)
//                            .withFooter(footer));
//                }
//            }
//        }
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(new File("groups.json"), new TypeReference<List<GroupData>>() {});
        groups.addAll(value);
        return groups;
    }

    @DisplayName("Параметризованное создание группы")
    @ParameterizedTest
    @MethodSource("multipleGroupsProvider")
    public void GroupCreationTest(GroupData group) {

        var oldGroups = app.groups().getGroupList();
        app.groups().createGroup(group);
        var newGroups = app.groups().getGroupList();

        GroupData created = null;
        for (GroupData g : newGroups) {
            if (!oldGroups.contains(g)) {
                created = g;
                break;
            }
        }

        assertNotNull(created, "Созданная группа не найдена");
        assertNotNull(created.id(), "У созданной группы нет id");

        List<GroupData> expectedList = new ArrayList<>(oldGroups);
        expectedList.add(created);

        expectedList.sort(Comparator.comparing(GroupData::name));
        newGroups.sort(Comparator.comparing(GroupData::name));

        assertEquals(expectedList, newGroups, "Списки групп не совпадают после создания новой группы");
    }
}

