package manager.hbm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addressbook")
public class ContactRecord {

    @Id
    public int id;
    public String firstName;
    public String lastName;
    public String address;
    public String homePhone;
    public String mobilePhone;
    public String workPhone;
    public String email;
    public String email2;
    public String email3;
    public String address2;
    public String phone2;
    public String notes;
    public String photo;

    public ContactRecord(int id, String firstName, String lastName, String address,
                         String homePhone, String mobilePhone, String workPhone,
                         String email, String email2, String email3,
                         String address2, String phone2, String notes, String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.email = email;
        this.email2 = email2;
        this.email3 = email3;
        this.address2 = address2;
        this.phone2 = phone2;
        this.notes = notes;
        this.photo = photo;
    }

    public ContactRecord() {
    }
}