package generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import model.ContactsData;
import model.ContactsDataGenerator;
import model.GroupData;
import model.GroupDataGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    @Parameter(names = {"--type", "-t"})
    String type;

    @Parameter(names = {"--groups-output", "-go"})
    String groupsOutput;

    @Parameter(names = {"--contacts-output", "-co"})
    String contactsOutput;

    @Parameter(names = {"--groups-count", "-gc"})
    int groupsCount;

    @Parameter(names = {"--contacts-count", "-cc"})
    int contactsCount;

    @Parameter(names = {"--groups-format", "-gf"})
    String groupsFormat;

    @Parameter(names = {"--contacts-format", "-cf"})
    String contactsFormat;

    public static void main(String[] args) throws IOException {
        Generator generator = new Generator();
        JCommander.newBuilder()
                .addObject(generator)
                .build()
                .parse(args);
        generator.run();
    }

    private void run() throws IOException {
        String baseDir = "addressbook_web_tests/";

        if ("groups".equals(type) || "all".equals(type)) {
            var groups = generateGroups(groupsCount);
            if (groupsOutput != null) groupsOutput = baseDir + groupsOutput;
            save(groups, groupsOutput, groupsFormat);
            System.out.printf("Generated %d groups: %s%n", groups.size(), groupsOutput);
        }

        if ("contacts".equals(type) || "all".equals(type)) {
            var contacts = generateContacts(contactsCount);
            if (contactsOutput != null) contactsOutput = baseDir + contactsOutput;
            save(contacts, contactsOutput, contactsFormat);
            System.out.printf("Generated %d contacts: %s%n", contacts.size(), contactsOutput);
        }
    }

    private List<GroupData> generateGroups(int groupsCount) {
        List<GroupData> groups = new ArrayList<>();
        for (int i = 0; i < groupsCount; i++) {
            groups.add(GroupDataGenerator.randomGroupData());
        }
        return groups;
    }

    private List<ContactsData> generateContacts(int contactsCount) {
        List<ContactsData> contacts = new ArrayList<>();
        for (int i = 0; i < contactsCount; i++) {
            contacts.add(ContactsDataGenerator.randomContactsData());
        }
        return contacts;
    }

    private void save(Object data, String output, String format) throws IOException {
        switch (format.toLowerCase()) {
            case "json" -> {
                JsonMapper mapper = JsonMapper.builder()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .build();
                mapper.writeValue(new File(output), data);
            }
            case "yaml" -> {
                YAMLMapper mapper = YAMLMapper.builder()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .build();
                mapper.writeValue(new File(output), data);
            }
            case "xml" -> {
                var mapper = new XmlMapper()
                        .enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(new File(output), data);
            }
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        }
    }
}