package ma.sdglr.cinema.Service;

import ma.sdglr.cinema.Model.Actor;
import ma.sdglr.cinema.Repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    // Get all actors
    public List<Actor> getAllActors() {
        return actorRepository.getAllActors();
    }

    // Get actor by ID
    public Actor getActorById(Short id) {
        return actorRepository.getActorById(id);
    }

    // Add a new actor
    public void addActor(Actor actor) {
        actorRepository.addActor(actor);
    }

    // Update an actor
    public void updateActor(Actor actor) {
        actorRepository.updateActor(actor);
    }

    // Delete an actor
    public void deleteActor(Short id) {
        try {
            actorRepository.deleteActor(id);
        } catch (DataIntegrityViolationException e) {
            // Log and throw a specific exception if foreign key constraint is violated
            throw new RuntimeException("actor is associated with films, cannot delete.");
        }
    }
}
