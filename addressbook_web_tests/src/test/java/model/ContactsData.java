package model;

public record ContactsData(String firstname, String middlename, String lastname, String address, String home,
                           String email) {

    public ContactsData() {
        this("", "", "", "", "", "");
    }

    public ContactsData withName(String name) {
        return new ContactsData(name, this.middlename, this.lastname, this.address, this.home, this.email);
    }

    public ContactsData withMiddleName(String middlename) {
        return new ContactsData(this.firstname, middlename, this.lastname, this.address, this.home, this.email);
    }

    public ContactsData withLastName(String lastname) {
        return new ContactsData(this.firstname, this.middlename, lastname, this.address, this.home, this.email);
    }

    public ContactsData withAddress(String address) {
        return new ContactsData(this.firstname, this.middlename, this.lastname, address, this.home, this.email);
    }

    public ContactsData withPhone(String home) {
        return new ContactsData(this.firstname, this.middlename, this.lastname, this.address, home, this.email);
    }

    public ContactsData withEmail(String email) {
        return new ContactsData(this.firstname, this.middlename, this.lastname, this.address, this.home, email);
    }

}