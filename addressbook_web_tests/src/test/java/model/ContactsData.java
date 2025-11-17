package model;

public record ContactsData(String firstname, String lastname, String address, String phone, String email
) {

    public ContactsData() {
        this("", "", "", "", "");
    }

    public ContactsData withName(String name) {
        return new ContactsData(name, this.lastname, this.address, this.phone, this.email);
    }

    public ContactsData withEmail(String email) {
        return new ContactsData(this.firstname, this.lastname, this.address, this.phone, email);
    }

    public ContactsData withLastName(String lastname) {
        return new ContactsData(this.firstname, lastname, this.address, this.phone, this.email);
    }

    public ContactsData withAddress(String address) {
        return new ContactsData(this.firstname, this.lastname, address, this.phone, this.email);
    }

    public ContactsData withPhone(String home) {
        return new ContactsData(this.firstname, this.lastname, this.address, phone, this.email);
    }
}