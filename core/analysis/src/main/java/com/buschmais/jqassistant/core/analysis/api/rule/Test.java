package com.buschmais.jqassistant.core.analysis.api.rule;

/**
 * Defines a constraint to be validated.
 */
public class Test extends AbstractRule {

    /** Default severity level. */
    public static Severity DEFAULT_SEVERITY = Severity.INFO;

    /**
     * The severity of the test.
     */
    private Severity severity;

    /**
     * The assert part of the test.
     */
    private Asserts asserts;

    /**
     * Returns the severity of the constraint.
     *
     * @return {@link com.buschmais.jqassistant.core.analysis.api.rule.Severity}
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * Sets the severity of the constraint.
     * 
     * @param severity
     *            severity value
     */
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Asserts getAsserts() {
        return asserts;
    }

    public void setAsserts(final Asserts asserts) {
        this.asserts = asserts;
    }
}
