package ma.sdglr.cinema.Model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Byte categoryId;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "last_update", nullable = false, updatable = false, insertable = false)
    private Timestamp lastUpdate;

    public Byte getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Byte categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Category() {
    }

    public Category(Byte categoryId, String name, Timestamp lastUpdate) {
        this.categoryId = categoryId;
        this.name = name;
        this.lastUpdate = lastUpdate;
    }
}
