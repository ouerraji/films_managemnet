package ma.sdglr.cinema.Service;

import ma.sdglr.cinema.Model.Language;
import ma.sdglr.cinema.Repository.LanguageRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    // Method to add a new language
    public void addLanguage(Language language) {
        languageRepository.addLanguage(language);
    }

    // Method to get a language by its ID
    public Language getLanguageById(Short languageId) {
        return languageRepository.getLanguageById(languageId);
    }

    // Method to get all languages
    public List<Language> getAllLanguages() {
        return languageRepository.getAllLanguages();
    }

    // Method to update an existing language
    public void updateLanguage(Language language) {
        // You can add additional validation or business logic here
        languageRepository.updateLanguage(language);
    }

    // Method to delete a language by its ID
    public void deleteLanguage(Short languageId) {
        try {
            languageRepository.deleteLanguage(languageId);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Language is associated with films, cannot delete.");
        }
    }
}
