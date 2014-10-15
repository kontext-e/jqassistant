package com.buschmais.jqassistant.core.analysis.api.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.buschmais.jqassistant.core.analysis.api.Result;

public class Asserts {
    private final List<String> assertParts = new ArrayList<>();

    private final List<String> messages = new ArrayList<>();

    public Asserts(final String stringRepresentation) {
        if(stringRepresentation != null) {
            for (String line : stringRepresentation.split("\n")) {
                if(line.trim().length() > 0) {
                    assertParts.add(line.trim());
                }
            }
        }
    }

    public boolean verified(final Result<Test> testResult) {
        if(! (testResult.getRows().size() == assertParts.size() ) ) {
            messages.add(String.format("Test result had %s rows, expected were %s (rows=%s, expectations=%s)", testResult.getRows().size(), assertParts.size(), testResult.getRows(), assertParts));
            return false;
        }

        int i = 0;
        for (Map<String, Object> row : testResult.getRows()) {
            Object value = row.values().iterator().next();
            if( ! value.equals(assertParts.get(i))) {
                messages.add(String.format(" *** actual value %s != expected value %s", value, assertParts.get(i)));
                return false;
            }
            i++;
        }

        return true;
    }

    public static Asserts from(final String stringRepresentation) {
        return new Asserts(stringRepresentation);
    }

    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public String toString() {
        return "Asserts{" +
               "assertParts=" + assertParts +
               '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Asserts asserts = (Asserts) o;

        if (assertParts != null ? !assertParts.equals(asserts.assertParts) : asserts.assertParts != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return assertParts != null ? assertParts.hashCode() : 0;
    }
}
