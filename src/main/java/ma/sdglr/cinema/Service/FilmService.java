package ma.sdglr.cinema.Service;

import ma.sdglr.cinema.Model.Film;
import ma.sdglr.cinema.Repository.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    // Add a new film
    public void addFilm(Film film) {
        filmRepository.addFilm(film);
    }

    // Get a film by its ID
    public Film getFilmById(Short filmId) {
        return filmRepository.getFilmById(filmId);
    }

    // Get all films
    public List<Film> getAllFilms() {
        return filmRepository.getAllFilms();
    }

    // Update an existing film
    public void updateFilm(Film film) {
        filmRepository.updateFilm(film);
    }

    // Delete a film by its ID
    public void deleteFilm(Short filmId) {
        filmRepository.deleteFilm(filmId);
    }
}
