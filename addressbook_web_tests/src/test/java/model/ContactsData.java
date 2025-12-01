package model;

public record ContactsData(
        String id,
        String firstname,
        String lastname,
        String middlename,
        String nickname,
        String title,
        String company,
        String address,
        String phone,
        String mobile,
        String work,
        String email,
        String email2,
        String email3,
        String homepage,
        String photo,
        String fax
) {

    public ContactsData() {
        this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
    }

    public ContactsData withId(String id) {
        return new ContactsData(id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withName(String firstname) {
        return new ContactsData(this.id, firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withLastName(String lastname) {
        return new ContactsData(this.id, this.firstname, lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withMiddleName(String middlename) {
        return new ContactsData(this.id, this.firstname, this.lastname, middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withNickname(String nickname) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withTitle(String title) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, title, this.company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withCompany(String company) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withAddress(String address) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withPhone(String phone) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withMobile(String mobile) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withWork(String work) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, work, this.email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withEmail(String email) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, email, this.email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withEmail2(String email2) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, this.email, email2, this.email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withEmail3(String email3) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, email3, this.homepage, this.photo, this.fax);
    }

    public ContactsData withHomepage(String homepage) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, homepage, this.photo, this.fax);
    }

    public ContactsData withPhoto(String photo) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, photo, this.fax);
    }

    public ContactsData withFax(String fax) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.middlename, this.nickname, this.title, this.company, this.address, this.phone, this.mobile, this.work, this.email, this.email2, this.email3, this.homepage, this.photo, fax);
    }
}