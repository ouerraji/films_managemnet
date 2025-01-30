package ma.sdglr.cinema.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short filmId;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Short releaseYear;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "original_language_id", nullable = true)
    private Language originalLanguage;

    @Column(name = "rental_duration", nullable = false)
    private Byte rentalDuration;

    @Column(name = "rental_rate", nullable = false)
    private BigDecimal rentalRate;

    @Column(name = "length")
    private Short length;

    @Column(name = "replacement_cost", nullable = false)
    private BigDecimal replacementCost;

    @Column(name = "rating", columnDefinition = "ENUM('G','PG','PG-13','R','NC-17')")
    private String rating;

    @Column(name = "special_features")
    private String specialFeatures;

    @Column(name = "last_update", nullable = false, updatable = false, insertable = false)
    private Timestamp lastUpdate;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "actor_id")
    )
    private Set<Actor> actors;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "film_category",  // The name of the join table
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),  // Foreign key column for the Film side
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id")  // Foreign key column for the Category side
    )
    private Set<Category> filmCategories;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdate = new Timestamp(System.currentTimeMillis());
    }

    public Short getFilmId() {
        return filmId;
    }

    public void setFilmId(Short filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Short releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(Language originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Byte getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Byte rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Category> getFilmCategories() {
        return filmCategories;
    }

    public void setFilmCategories(Set<Category> filmCategories) {
        this.filmCategories = filmCategories;
    }

    public Film() {
    }

    public Film(Short filmId, String title, String description, Short releaseYear, Language language, Language originalLanguage, Byte rentalDuration, BigDecimal rentalRate, Short length, BigDecimal replacementCost, String rating, String specialFeatures, Timestamp lastUpdate, Set<Actor> actors, Set<Category> filmCategories) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = language;
        this.originalLanguage = originalLanguage;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.specialFeatures = specialFeatures;
        this.lastUpdate = lastUpdate;
        this.actors = actors;
        this.filmCategories = filmCategories;
    }
}
