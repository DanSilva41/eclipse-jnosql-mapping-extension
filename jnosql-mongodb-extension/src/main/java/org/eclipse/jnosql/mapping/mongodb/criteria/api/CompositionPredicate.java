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
package org.eclipse.jnosql.mapping.mongodb.criteria.api;

import java.util.Collection;

/**
 * Composition of {@link Predicate}s
 *
 * @param <T> The Entity type whose fetching is to be be restricted
 */
public interface CompositionPredicate<T> extends Predicate<T> {

    /**
     * Return the composed {@link Predicate}s.
     *
     * @return collection of predicates
     */
    Collection<Predicate<T>> getPredicates();

}
