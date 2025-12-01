package manager.hbm;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "group_list")
public class GroupRecord {

    @Id
    @Column(name = "group_id")
    private int id;

    @Column(name = "group_name")
    private String name;

    @Column(name = "group_header")
    private String header;

    @Column(name = "group_footer")
    private String footer;

    @Column(name = "deprecated")
    private Date deprecated = new Date();

    @ManyToMany
    @JoinTable(name = "address_in_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    public List<ContactRecord> contacts;


    public GroupRecord() {
    }

    public GroupRecord(int id, String name, String header, String footer) {
        this.id = id;
        this.name = name;
        this.header = header;
        this.footer = footer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }
}