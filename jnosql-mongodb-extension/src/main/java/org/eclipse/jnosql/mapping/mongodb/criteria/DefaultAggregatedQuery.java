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

import jakarta.nosql.Value;
import org.eclipse.jnosql.mapping.mongodb.criteria.api.AggregatedQuery;
import org.eclipse.jnosql.mapping.mongodb.criteria.api.AggregatedQueryResult;
import org.eclipse.jnosql.mapping.mongodb.criteria.api.Expression;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.eclipse.jnosql.mapping.mongodb.AbstractGenericType;

/**
 * Default implementation for {@link AggregatedQuery}
 *
 * @param <T> the type of the root entity
 */
public class DefaultAggregatedQuery<T> extends AbstractGenericType<T> implements AggregatedQuery<T> {

    private final Collection<Expression<T, ?, ?>> groupings;

    public DefaultAggregatedQuery(Class<T> type, Expression<T, ?, ?>... groupings) {
        super(type);
        this.groupings = Arrays.asList(groupings);
    }

    public Collection<Expression<T, ?, ?>> getGroupings() {
        return groupings;
    }

    @Override
    public AggregatedQueryResult<T> getResult() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AggregatedQuery<T> feed(Stream<List<Value>> results) {
        return this;
    }

}
