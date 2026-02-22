import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates validation rules for a student registration.
 * Returns a list of error messages; empty list means validation passed.
 */
import java.util.HashSet;
import java.util.Set;

public class StudentValidator {
    private final Set<String> allowedPrograms;

    public StudentValidator(Set<String> allowedPrograms) {
        this.allowedPrograms = new HashSet<>(allowedPrograms);
    }

    public List<String> validate(StudentData data) {
        List<String> errors = new ArrayList<>();
        String name = data.getName();
        String email = data.getEmail();
        String phone = data.getPhone();
        String program = data.getProgram();

        if (name.isBlank()) errors.add("name is required");
        if (email.isBlank() || !email.contains("@")) errors.add("email is invalid");
        if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit)) errors.add("phone is invalid");
        if (!allowedPrograms.contains(program)) errors.add("program is invalid");

        return errors;
    }
}
