package ma.sdglr.cinema.Repository;

import ma.sdglr.cinema.Model.Actor;
import ma.sdglr.cinema.Model.Category;
import ma.sdglr.cinema.Model.Film;
import ma.sdglr.cinema.Model.Language;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class FilmRepository {
    private final SessionFactory sessionFactory;

    public FilmRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addFilm(Film film) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Handle main language
            if (film.getLanguage() != null && film.getLanguage().getLanguageId() > 0) {
                Language managedLanguage = session.get(Language.class, film.getLanguage().getLanguageId());
                if (managedLanguage == null) {
                    throw new RuntimeException("Language with ID " + film.getLanguage().getLanguageId() + " not found");
                }
                film.setLanguage(managedLanguage);
            }

            // Handle original language if present
            if (film.getOriginalLanguage() != null && film.getOriginalLanguage().getLanguageId() > 0) {
                Language managedOriginalLanguage = session.get(Language.class, film.getOriginalLanguage().getLanguageId());
                if (managedOriginalLanguage == null) {
                    throw new RuntimeException("Original Language with ID " + film.getOriginalLanguage().getLanguageId() + " not found");
                }
                film.setOriginalLanguage(managedOriginalLanguage);
            }

            // Handle actors with explicit existence check
            if (film.getActors() != null && !film.getActors().isEmpty()) {
                Set<Actor> managedActors = new HashSet<>();
                for (Actor actor : film.getActors()) {
                    Actor managedActor = session.get(Actor.class, actor.getActorId());
                    if (managedActor == null) {
                        throw new RuntimeException("Actor with ID " + actor.getActorId() + " not found in the database");
                    }
                    managedActors.add(managedActor);
                }
                film.setActors(managedActors);
            }

            // Handle categories with explicit existence check
            if (film.getFilmCategories() != null && !film.getFilmCategories().isEmpty()) {
                Set<Category> managedCategories = new HashSet<>();
                for (Category category : film.getFilmCategories()) {
                    Category managedCategory = session.get(Category.class, category.getCategoryId());
                    if (managedCategory == null) {
                        throw new RuntimeException("Category with ID " + category.getCategoryId() + " not found in the database");
                    }
                    managedCategories.add(managedCategory);
                }
                film.setFilmCategories(managedCategories);
            }

            session.persist(film);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Film getFilmById(Short filmId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(Film.class, filmId);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<Film> getAllFilms() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Film> query = session.createQuery("FROM Film", Film.class);
            return query.list();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateFilm(Film film) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(film);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void deleteFilm(Short filmId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Film film = session.get(Film.class, filmId);
            if (film != null) {
                session.remove(film);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}