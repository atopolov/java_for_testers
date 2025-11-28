package manager;

import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactsData;
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
                .addAnnotatedClass(ContactRecord.class)
                .addAnnotatedClass(GroupRecord.class)
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/addressbook")
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "root")
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "")
                .buildSessionFactory();
    }

    static List<GroupData> convertGroupList(List<GroupRecord> records) {
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

    private static GroupRecord convert(GroupData data) {
        var id = data.id();
        if (id == null || id.isEmpty()) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id) ,data.name(), data.header(), data.footer());
    }

    public List<GroupData> getGroupList() {
        try (Session session = sessionFactory.openSession()) {

            List<GroupRecord> records = session
                    .createQuery("from GroupRecord", GroupRecord.class)
                    .list();

            return HibernateHelper.convertGroupList(records);
        }
    }

    public Long getGroupCount() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
        }
    }

    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(groupData));
            session.getTransaction().commit();
        });
    }

    static List<ContactsData> convertContactsList(List<ContactRecord> records) {
        List<ContactsData> contactsDataList = new ArrayList<>();
        for (ContactRecord record : records) {
            contactsDataList.add(convert(record));
        }
        return contactsDataList;
    }

    private static ContactsData convert(ContactRecord record) {
        String id = String.valueOf(record.id);
        return new ContactsData(
                id,
                record.firstName,
                record.lastName,
                record.address,
                record.homePhone,
                record.mobilePhone,
                record.workPhone,
                record.email,
                record.email2,
                record.email3,
                record.address2,
                record.phone2,
                record.notes,
                record.photo
        );
    }

    private static ContactRecord convert(ContactsData data) {
        int id = (data.id() == null || data.id().isEmpty()) ? 0 : Integer.parseInt(data.id());

        return new ContactRecord(
                id,
                data.firstname(),
                data.lastname(),
                data.address(),
                data.phone(),
                data.mobile(),
                data.work(),
                data.email(),
                data.email2(),
                data.email3(),
                data.address2(),
                data.phone2(),
                data.notes(),
                data.photo()
        );
    }

    public List<ContactsData> getContactsList() {
        try (Session session = sessionFactory.openSession()) {
            return convertContactsList(session.createQuery("from ContactRecord", ContactRecord.class).list());
        }
    }

    public Long getContactsCount() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select count(*) from ContactRecord", Long.class).getSingleResult();
        }
    }

    public void createContacts(ContactsData contactsData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(contactsData));
            session.getTransaction().commit();
        });
    }

}