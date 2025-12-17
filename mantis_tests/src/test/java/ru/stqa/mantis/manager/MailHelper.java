package ru.stqa.mantis.manager;

import jakarta.mail.*;
import ru.stqa.mantis.model.MailMessage;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailHelper extends HelperBase {

    private static final String PROTOCOL = "pop3";
    private static final String HOST = "localhost";
    private static final String INBOX_FOLDER = "INBOX";
    private static final String CONFIRMATION_LINK_REGEX = "http://\\S+";
    private static final Duration DEFAULT_MAIL_WAIT = Duration.ofSeconds(60);
    private static final long SLEEP_INTERVAL = 1000;

    public MailHelper(ApplicationManager manager) {
        super(manager);
    }

    public List<MailMessage> receive(String username, String password, Duration duration) {
        var start = System.currentTimeMillis();
        while (System.currentTimeMillis() < start + duration.toMillis()) {
            try {
                var inbox = getInbox(username, password);
                inbox.open(Folder.READ_ONLY);
                var messages = inbox.getMessages();
                var result = Arrays.stream(messages)
                        .map(m -> {
                            try {
                                return new MailMessage()
                                        .withFrom(m.getFrom()[0].toString())
                                        .withContent(m.getContent().toString());
                            } catch (MessagingException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).toList();
                inbox.close();
                inbox.getStore().close();
                if (result.size() > 0) {
                    return result;
                }
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("No messages received");
    }

    private static Folder getInbox(String username, String password) {
        try {
            var session = Session.getInstance(new Properties());
            Store store = session.getStore(PROTOCOL);
            store.connect(HOST, username, password);
            var inbox = store.getFolder(INBOX_FOLDER);
            return inbox;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll(String s, String password) {
        try {
            var inbox = getInbox("user1@localhost", "password");
            inbox.open(Folder.READ_WRITE);
            Arrays.stream(inbox.getMessages()).forEach(m -> {
                try {
                    m.setFlag(Flags.Flag.DELETED, true);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
            inbox.close();
            inbox.getStore().close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public String getLinkFromLastMail(String username, String password) {
        var messages = receive(username, password, DEFAULT_MAIL_WAIT);

        var lastMessage = messages.get(messages.size() - 1);
        var text = lastMessage.content();

        Pattern pattern = Pattern.compile(CONFIRMATION_LINK_REGEX);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return text.substring(matcher.start(), matcher.end());
        }

        throw new RuntimeException("No confirmation link found in email");
    }

}
