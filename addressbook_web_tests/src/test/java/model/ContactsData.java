package model;

public record ContactsData(String id, String firstname, String lastname, String address, String phone, String email
) {

    public ContactsData() {
        this("", "", "", "", "", "");
    }

    public ContactsData withId(String id) {
        return new ContactsData(id, this.firstname, this.lastname, this.address, this.phone, this.email);
    }

    public ContactsData withName(String name) {
        return new ContactsData(this.id, name, this.lastname, this.address, this.phone, this.email);
    }

    public ContactsData withEmail(String email) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.address, this.phone, email);
    }

    public ContactsData withLastName(String lastname) {
        return new ContactsData(this.id, this.firstname, lastname, this.address, this.phone, this.email);
    }

    public ContactsData withAddress(String address) {
        return new ContactsData(this.id, this.firstname, this.lastname, address, this.phone, this.email);
    }

    public ContactsData withPhone(String home) {
        return new ContactsData(this.id, this.firstname, this.lastname, this.address, phone, this.email);
    }
}