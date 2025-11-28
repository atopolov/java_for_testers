package manager;

import manager.hbm.GroupRecord;
import model.GroupData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class HibernateHelper extends HelperBase {

    private SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager app) {
        super(app);

        sessionFactory = new Configuration()
                //.addAnnotatedClass(Book.class)
                .addAnnotatedClass(GroupRecord.class)
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/addressbook")
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "root")
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "")
                .buildSessionFactory();
    }

    static List<GroupData> convertList(List<GroupRecord> records) {
        List<GroupData> groupDataList = new ArrayList<>();
        for (GroupRecord record : records) {
            groupDataList.add(convert(record));
        }
        return groupDataList;
    }

    private static GroupData convert(GroupRecord record) {
        return new GroupData()
                .withId("" + record.id)
                .withName(record.name)
                .withHeader(record.header)
                .withFooter(record.footer);
    }

    public List<GroupData> getGroupList() {
        try (Session session = sessionFactory.openSession()) {

            List<GroupRecord> records = session
                    .createQuery("from GroupRecord", GroupRecord.class)
                    .list();

            return convertList(records);
        }
    }
}