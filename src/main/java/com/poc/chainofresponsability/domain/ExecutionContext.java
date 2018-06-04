package com.poc.chainofresponsability.domain;

import lombok.*;

import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionContext {

    @Setter(AccessLevel.PRIVATE)
    private List<String> executedBeans;

    @Setter(AccessLevel.PRIVATE)
    private Set<Error> errorSet;

    public void addErrors(final List<Error> errors) {
        if (this.errorSet == null) {
            this.errorSet = new HashSet<>();
        }
        this.errorSet.addAll(errors);
    }

    public void addError(final Error error) {
        if (this.errorSet == null) {
            this.errorSet = new HashSet<>();
        }
        this.errorSet.add(error);
    }

    public void addBean(final String beanName) {
        if (this.executedBeans == null) {
            this.executedBeans = new ArrayList<>();
        }
        int currentListSize = executedBeans.size();
        final String formattedText = String.format("Order %s Bean Name %s", currentListSize, beanName);
        this.executedBeans.add(formattedText);
    }

    public List<String> getExecutedBeans() {
        return Collections.unmodifiableList(this.executedBeans);
    }

    public Set<Error> getErrorSet() {
        return Collections.unmodifiableSet(this.errorSet);
    }

    boolean hasErrors() {
        return this.errorSet != null && !this.errorSet.isEmpty();
    }
}