package generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import model.GroupData;
import model.GroupDataGenerator;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    @Parameter(names = {"--type", "-t"})
    String type;

    @Parameter(names = {"--output", "-o"})
    String output;

    @Parameter(names = {"--format", "-f"})
    String format;

    @Parameter(names = {"--count", "-c"})
    int count;


    public static void main(String[] args) throws IOException {
        var generator = new Generator();
        JCommander.newBuilder()
                .addObject(generator)
                .build()
                .parse(args);
        generator.run();
    }

    private void run() throws IOException {
        var data = generate();
        save(data);
    }

    private Object generate() {
        if ("groups".equals(type)) {
            return generateGroups();
        } else if ("contacts".equals(type)) {
            return generateContacts();
        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    private Object generateGroups() {
        List<GroupData> groups = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            groups.add(GroupDataGenerator.randomGroup());
        }
        return groups;
    }

    private Object generateContacts() {
        return null;
    }

    private void save(Object data) {
        if ("json".equals(format)) {
            JsonMapper mapper = JsonMapper.builder()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .build();
            mapper.writeValue(new File(output), data);
        } else {
            throw new IllegalArgumentException("Unknown format: " + format);
        }
    }
}