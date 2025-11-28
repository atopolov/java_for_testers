package model;

public record ContactsData(
        String id,
        String firstname,
        String lastname,
        String address,
        String phone,
        String mobile,
        String work,
        String email,
        String email2,
        String email3,
        String address2,
        String phone2,
        String notes,
        String photo
) {

    public ContactsData withId(String id) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withName(String firstname) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withLastName(String lastname) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withAddress(String address) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withPhone(String phone) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withMobile(String mobile) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withWork(String work) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withEmail(String email) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withEmail2(String email2) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withEmail3(String email3) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withAddress2(String address2) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withPhone2(String phone2) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withNotes(String notes) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }

    public ContactsData withPhoto(String photo) {
        return new ContactsData(id, firstname, lastname, address, phone, mobile, work, email, email2, email3, address2, phone2, notes, photo);
    }
}