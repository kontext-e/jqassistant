package com.buschmais.jqassistant.core.analysis.api.rule;

import java.util.HashMap;
import java.util.Map;

import com.buschmais.jqassistant.core.analysis.api.RuleException;

/**
 * A rule set builder.
 */
public class RuleSetBuilder {

    private DefaultRuleSet ruleSet = new DefaultRuleSet();

    public static RuleSetBuilder newInstance() {
        return new RuleSetBuilder();
    }

    public RuleSetBuilder addTemplate(Template template) throws RuleException {
        return put(ruleSet.templates, template);
    }

    public RuleSetBuilder addConcept(Concept concept) throws RuleException {
        return put(ruleSet.concepts, concept);
    }

    public RuleSetBuilder addConstraint(Constraint constraint) throws RuleException {
        return put(ruleSet.constraints, constraint);
    }

    public RuleSetBuilder addGroup(Group group) throws RuleException {
        return put(ruleSet.groups, group);
    }

    public RuleSetBuilder addMetricGroup(MetricGroup metricGroup) throws RuleException {
        return put(ruleSet.metricGroups, metricGroup);
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    private <T extends Rule> RuleSetBuilder put(Map<String, T> rules, T rule) throws RuleException {
        T oldRule = rules.put(rule.getId(), rule);
        if (oldRule != null) {
            throw new RuleException("The id of a rule must be unique: " + rule.getId() + "(" + rule.getDescription() + ")");
        }
        return this;
    }

    /**
     * Defines a set of rules containing all resolved {@link Concept} s,
     * {@link Constraint}s and {@link Group}s.
     */
    private static class DefaultRuleSet implements RuleSet {

        private Map<String, Template> templates = new HashMap<>();
        private Map<String, Concept> concepts = new HashMap<>();
        private Map<String, Constraint> constraints = new HashMap<>();
        private Map<String, Group> groups = new HashMap<>();
        private Map<String, MetricGroup> metricGroups = new HashMap<>();

        private DefaultRuleSet() {
        }

        @Override
        public Map<String, Template> getTemplates() {
            return templates;
        }

        @Override
        public Map<String, Concept> getConcepts() {
            return concepts;
        }

        @Override
        public Map<String, Constraint> getConstraints() {
            return constraints;
        }

        @Override
        public Map<String, Group> getGroups() {
            return groups;
        }

        @Override
        public Map<String, MetricGroup> getMetricGroups() {
            return metricGroups;
        }

        @Override
        public String toString() {
            return "RuleSet{" + "groups=" + groups + ", constraints=" + constraints + ", concepts=" + concepts + '}';
        }
    }
}
