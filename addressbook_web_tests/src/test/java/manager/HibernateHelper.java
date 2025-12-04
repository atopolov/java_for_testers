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
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/addressbook?serverTimezone=UTC&zeroDateTimeBehavior=CONVERT_TO_NULL")
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
                .withId("" + record.getId())
                .withName(record.getName())
                .withHeader(record.getHeader())
                .withFooter(record.getFooter());
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
        String id = String.valueOf(record.getId());
        return new ContactsData(
                id,
                record.getFirstName(),
                record.getLastName(),
                record.getMiddleName(),
                record.getNickname(),
                record.getTitle(),
                record.getCompany(),
                record.getAddress(),
                record.getHomePhone(),
                record.getMobilePhone(),
                record.getWorkPhone(),
                record.getEmail(),
                record.getEmail2(),
                record.getEmail3(),
                record.getHomepage(),
                record.getPhoto()
        );
    }

    private static ContactRecord convert(ContactsData data) {
        int id = (data.id() == null || data.id().isEmpty()) ? 0 : Integer.parseInt(data.id());

        return new ContactRecord(
                id,
                data.firstname(),
                data.lastname(),
                data.middlename(),
                data.nickname(),
                data.title(),
                data.company(),
                data.address(),
                data.phone(),
                data.mobile(),
                data.work(),
                data.email(),
                data.email2(),
                data.email3(),
                data.homepage(),
                data.photo());
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

    public List<ContactsData> getContactsInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            return convertContactsList(session.get(GroupRecord.class, group.id()).contacts);
        });
    }

    public List<ContactsData> getContactsNotInGroup(GroupData group) {
        try (Session session = sessionFactory.openSession()) {

            List<ContactRecord> records = session.createQuery(
                            "SELECT c FROM ContactRecord c " +
                                    "WHERE c.id NOT IN (" +
                                    "   SELECT c2.id FROM GroupRecord g " +
                                    "   JOIN g.contacts c2 " +
                                    "   WHERE g.id = :groupId" +
                                    ")",
                            ContactRecord.class
                    )
                    .setParameter("groupId", Integer.parseInt(group.id()))
                    .list();

            return convertContactsList(records);
        }
    }

}