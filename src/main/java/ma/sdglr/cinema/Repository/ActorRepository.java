package ma.sdglr.cinema.Repository;

import ma.sdglr.cinema.Model.Actor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ActorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Actor> getAllActors() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Actor", Actor.class).list();
        }
    }

    public Actor getActorById(Short id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Actor.class, id);
        }
    }

    public void addActor(Actor actor) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(actor);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public void updateActor(Actor actor) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(actor);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public void deleteActor(Short id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Actor actor = session.get(Actor.class, id);
                if (actor != null) {
                    session.remove(actor);
                }
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}