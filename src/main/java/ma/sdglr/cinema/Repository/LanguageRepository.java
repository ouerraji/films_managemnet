package ma.sdglr.cinema.Repository;

import ma.sdglr.cinema.Model.Language;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LanguageRepository {

    private final SessionFactory sessionFactory;

    public LanguageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Method to add a new language
    public void addLanguage(Language language) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(language);
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

    // Method to get a language by its ID
    public Language getLanguageById(Short languageId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(Language.class, languageId);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // Method to get all languages
    public List<Language> getAllLanguages() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Language> query = session.createQuery("FROM Language", Language.class);
            return query.list();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // Method to update an existing language
    public void updateLanguage(Language language) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(language);
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

    // Method to delete a language by its ID
    public void deleteLanguage(Short languageId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Language language = session.get(Language.class, languageId);
            if (language != null) {
                session.remove(language);
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
