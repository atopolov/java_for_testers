package manager.hbm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addressbook")
public class ContactRecord {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "middlename")
    private String middleName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "title")
    private String title;

    @Column(name = "company")
    private String company;

    @Column(name = "address")
    private String address;

    @Column(name = "home")
    private String homePhone;

    @Column(name = "mobile")
    private String mobilePhone;

    @Column(name = "work")
    private String workPhone;

    @Column(name = "email")
    private String email;

    @Column(name = "email2")
    private String email2;

    @Column(name = "email3")
    private String email3;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "photo")
    private String photo;

    public ContactRecord() {
    }

    public ContactRecord(int id,
                         String firstName,
                         String lastName,
                         String middleName,
                         String nickname,
                         String title,
                         String company,
                         String address,
                         String homePhone,
                         String mobilePhone,
                         String workPhone,
                         String email,
                         String email2,
                         String email3,
                         String homepage,
                         String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.nickname = nickname;
        this.title = title;
        this.company = company;
        this.address = address;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.email = email;
        this.email2 = email2;
        this.email3 = email3;
        this.homepage = homepage;
        this.photo = photo;
    }

    // getters / setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        return email3;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}