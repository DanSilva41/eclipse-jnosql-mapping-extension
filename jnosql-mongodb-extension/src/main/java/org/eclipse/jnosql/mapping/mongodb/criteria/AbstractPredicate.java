/*
 *  Copyright (c) 2022 Contributors to the Eclipse Foundation
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Alessandro Moscatelli
 */
package org.eclipse.jnosql.mapping.mongodb.criteria;

import org.eclipse.jnosql.mapping.mongodb.criteria.api.ConjunctionPredicate;
import org.eclipse.jnosql.mapping.mongodb.criteria.api.DisjunctionPredicate;
import org.eclipse.jnosql.mapping.mongodb.criteria.api.NegationPredicate;
import org.eclipse.jnosql.mapping.mongodb.criteria.api.Predicate;
import java.util.Arrays;

/**
 * Abstract class for {@link Predicate} implementations.
 * Supports basic operations such as negation, conjunction and disjunction.
 *
 * @param <T> The Entity type whose fetching is to be be restricted
 */
public abstract class AbstractPredicate<T> implements Predicate<T>{
    
    @Override
    public NegationPredicate<T> not() {
        return new DefaultNegationPredicate(this);
    }

    @Override
    public ConjunctionPredicate<T> and(Predicate<T> restriction) {
        return new DefaultConjunctionPredicate<>(
                Arrays.asList(
                        this,
                        restriction
                )
        );
    }

    @Override
    public DisjunctionPredicate<T> or(Predicate<T> restriction) {
        return new DefaultDisjunctionPredicate<>(
                Arrays.asList(
                        this,
                        restriction
                )
        );
    }
    
}
