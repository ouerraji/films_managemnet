package ma.sdglr.cinema.Model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Byte languageId;  // Use Short for better flexibility with IDs

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "last_update", nullable = false, updatable = false)
    private Timestamp lastUpdate;  // No need for insertable = false, updatable = false since it's managed

    // Getter and setter for languageId
    public Byte getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Byte languageId) {
        this.languageId = languageId;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for lastUpdate
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // Default constructor
    public Language() {
    }

    // Constructor with all parameters
    public Language(Byte languageId, String name, Timestamp lastUpdate) {
        this.languageId = languageId;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }

    // Automatically set lastUpdate before persisting the entity
    @PrePersist
    public void prePersist() {
        this.lastUpdate = new Timestamp(System.currentTimeMillis()); // set the timestamp when the record is created
    }

    // Optionally, if you want to update the lastUpdate on every modification, use @PreUpdate
    @PreUpdate
    public void preUpdate() {
        this.lastUpdate = new Timestamp(System.currentTimeMillis()); // set the timestamp when the record is updated
    }
}
