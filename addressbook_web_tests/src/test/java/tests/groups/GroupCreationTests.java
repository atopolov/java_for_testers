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
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

    public static Stream<GroupData> multipleGroupProvider() {

        Supplier<GroupData> randomGroup = () -> new GroupData()
                .withName(randomGroupName())
                .withHeader(randomGroupHeader())
                .withFooter(randomGroupFooter());

        return Stream.generate(randomGroup)
                .limit(3);
    }

    @DisplayName("Параметризованное создание группы")
    @ParameterizedTest
    @MethodSource("multipleGroupProvider")
    public void GroupCreationTest(GroupData group) {

        var oldGroups = app.hbm().getGroupList();
        app.groups().createGroup(group);
        var newGroups = app.hbm().getGroupList();

        GroupData created = null;
        created = newGroups.stream()
                .filter(g -> !oldGroups.contains(g))
                .findFirst()
                .orElse(null);

        assertNotNull(created, "Созданная группа не найдена");
        assertNotNull(created.id(), "У созданной группы нет id");

        List<GroupData> expectedList = new ArrayList<>(oldGroups);
        expectedList.add(created);

        assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));

        assertEquals(expectedList, newGroups, "Списки групп не совпадают после создания новой группы");

        var newUiGroups = app.hbm().getGroupList();
        assertEquals(expectedList, newUiGroups, "Списки групп не совпадают после создания новой группы");
    }
}

