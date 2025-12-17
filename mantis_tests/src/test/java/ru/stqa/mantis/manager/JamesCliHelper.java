package ru.stqa.mantis.manager;

import org.openqa.selenium.os.ExternalProcess;

import java.time.Duration;

public class JamesCliHelper extends HelperBase {

    private static final String JAVA_CMD = "java";
    private static final String CP_OPTION = "-cp";
    private static final String LIB_PATH = "james-server-jpa-app.lib/*";
    private static final String SERVER_CMD = "org.apache.james.cli.ServerCmd";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofHours(1);

    public JamesCliHelper(ApplicationManager manager) {
        super(manager);
    }

    public void addUser(String email, String password) throws InterruptedException {
        ExternalProcess.builder()
                .command(JAVA_CMD, CP_OPTION, LIB_PATH,
                        SERVER_CMD,
                        "AddUser", email, password)
                .directory(manager.property("james.workDir"))
                .copyOutputTo(System.err)
                .start()
                .waitFor(DEFAULT_TIMEOUT);
    }
}
