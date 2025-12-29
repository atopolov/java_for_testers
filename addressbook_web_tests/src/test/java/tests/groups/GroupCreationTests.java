package tests.groups;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.qameta.allure.*;
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

@Epic("Address Book")
@Feature("Groups management")
public class GroupCreationTests extends TestBase {

    public static final String GROUPS_XML = "groups.xml";

    public static List<GroupData> multipleGroupsProvider() throws IOException {
        String filePath = System.getProperty("groups.file", GROUPS_XML);
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("File with groups not found: " + file.getAbsolutePath());
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
            throw new IllegalArgumentException("Unsupported file format: " + file.getAbsolutePath());
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

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Parameterized group creation")
    @Description("Test verifies that a new group can be created using parameterized data")
    @ParameterizedTest
    @MethodSource("multipleGroupProvider")
    public void GroupCreationTest(GroupData group) {

        var oldGroups = Allure.step(
                "Get list of groups before creation",
                () -> app.hbm().getGroupList()
        );
        Allure.step("Create new group", () -> {
            app.groups().createGroup(group);
        });
        var newGroups = Allure.step(
                "Get list of groups after creation",
                () -> app.hbm().getGroupList()
        );

        GroupData created = Allure.step(
                "Find newly created group",
                () -> newGroups.stream()
                        .filter(g -> !oldGroups.contains(g))
                        .findFirst()
                        .orElse(null)
        );

        Allure.step("Verify created group exists", () -> {
            assertNotNull(created, "Created group not found");
        });

        Allure.step("Verify created group has ID", () -> {
            assertNotNull(created.id(), "Created group ID is null");
        });

        List<GroupData> expectedList = Allure.step(
                "Build expected group list",
                () -> {
                    List<GroupData> list = new ArrayList<>(oldGroups);
                    list.add(created);
                    return list;
                }
        );

        Allure.step("Verify group list matches expected (set comparison)", () -> {
            assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
        });

        Allure.step("Verify group list matches expected (list comparison)", () -> {
            assertEquals(expectedList, newGroups,
                    "Lists of groups do not match after creating a new group");
        });

        var newUiGroups = Allure.step(
                "Get group list from UI",
                () -> app.hbm().getGroupList()
        );

        Allure.step("Verify UI group list matches expected", () -> {
            assertEquals(expectedList, newUiGroups,
                    "Lists of groups do not match after creating a new group");
        });
    }
}

