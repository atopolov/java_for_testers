package ru.stqa.mantis.manager;

import org.openqa.selenium.os.ExternalProcess;

import java.time.Duration;

public class JamesCliHelper extends HelperBase {

    public JamesCliHelper(ApplicationManager manager) {
        super(manager);
    }

    public void addUser(String email, String password) throws InterruptedException {
        ExternalProcess.builder()
                .command("java", "-cp", "james-server-jpa-app.lib/*",
                        "org.apache.james.cli.ServerCmd",
                        "AddUser", email, password)
                .directory(manager.property("james.workDir"))
                .copyOutputTo(System.err)
                .start()
                .waitFor(Duration.ofHours(1));
    }
}
