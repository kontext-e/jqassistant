package com.buschmais.jqassistant.core.analysis.api.rule;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Defines a set of rules containing all resolved {@link Concept} s,
 * {@link Constraint}s and {@link Group}s.
 */
public class RuleSet {

    private Map<String, Concept> concepts = new TreeMap<>();
    private Map<String, Constraint> constraints = new TreeMap<>();
    private Map<String, Test> tests = new TreeMap<>();
    private Map<String, Group> groups = new TreeMap<>();
    private Map<String, MetricGroup> metricGroups = new TreeMap<>();

    private Set<String> missingConcepts = new TreeSet<>();
    private Set<String> missingConstraints = new TreeSet<>();
    private Set<String> missingTests = new TreeSet<>();
    private Set<String> missingGroups = new TreeSet<>();

    public Map<String, Concept> getConcepts() {
        return concepts;
    }

    public Map<String, Constraint> getConstraints() {
        return constraints;
    }

    public Map<String, Test> getTests() {
        return tests;
    }

    public Map<String, Group> getGroups() {
        return groups;
    }

    public Map<String, MetricGroup> getMetricGroups() {
        return metricGroups;
    }

    public Set<String> getMissingConcepts() {
        return missingConcepts;
    }

    public Set<String> getMissingTests() {
        return missingTests;
    }

    public Set<String> getMissingConstraints() {
        return missingConstraints;
    }

    public Set<String> getMissingGroups() {
        return missingGroups;
    }

    @Override
    public String toString() {
        return "RuleSet{" + "groups=" + groups + ", constraints=" + constraints + ", concepts=" + concepts + '}';
    }
}
