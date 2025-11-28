package tests.groups;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import model.GroupData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.TestBase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static model.GroupDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GroupCreationTests extends TestBase {

    public static final String GROUPS_XML = "groups.xml";

    public static List<GroupData> multipleGroupsProvider() throws IOException {
        String filePath = System.getProperty("groups.file", GROUPS_XML);
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("Файл с группами не найден: " + file.getAbsolutePath());
        }

        ObjectMapper mapper;

        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".json")) {
            mapper = new ObjectMapper();
        } else if (fileName.endsWith(".xml")) {
            mapper = new XmlMapper();
        } else if (fileName.endsWith(".yaml")) {
            mapper = new ObjectMapper(new YAMLFactory());
        } else {
            throw new IllegalArgumentException("Неподдерживаемый формат файла: " + file.getAbsolutePath());
        }

        return mapper.readValue(file, new TypeReference<List<GroupData>>() {
        });
    }

    public static List<GroupData> singleGroupProvider() {
        return List.of(new GroupData()
                .withName(randomGroupName())
                .withHeader(randomGroupHeader())
                .withFooter(randomGroupFooter()));
    }

    @DisplayName("Параметризованное создание группы")
    @ParameterizedTest
    @MethodSource("singleGroupProvider")
    public void GroupCreationTest(GroupData group) {

        var oldGroups = app.hbm().getGroupList();
        app.groups().createGroup(group);
        var newGroups = app.hbm().getGroupList();

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

        expectedList.sort(Comparator.comparing(GroupData::id));
        newGroups.sort(Comparator.comparing(GroupData::id));

        assertEquals(expectedList, newGroups, "Списки групп не совпадают после создания новой группы");

        var newUiGroups = app.hbm().getGroupList();
        assertEquals(expectedList, newUiGroups, "Списки групп не совпадают после создания новой группы");
    }
}

