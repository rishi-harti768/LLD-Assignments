public interface EligibilityRule {
    /**
     * Check the student profile.
     *
     * @param s the profile to evaluate
     * @return a failure reason, or null if the rule passes
     */
    String check(StudentProfile s);
}