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

import org.eclipse.jnosql.mapping.mongodb.criteria.api.Path;
import org.eclipse.jnosql.mapping.mongodb.criteria.api.PathFunction;

/**
 * Default implementation for {@link PathFunction}
 * This holds the function and the path to apply the function to
 *
 * @param <X> the root entity type
 * @param <Y> the entity type
 * @param <T> the type of the attribute the function is applied to
 * @param <R> the return type of the function
 */
public class DefaultPathFunction<X, Y, T, R> implements PathFunction<X, Y, T, R> {
    
    private final Path<X, Y> path;
    private final Function function;

    public DefaultPathFunction(Path<X, Y> path, Function function) {
        this.path = path;
        this.function = function;
    }

    @Override
    public Path<X, Y> getPath() {
        return path;
    }
    
    @Override
    public Function getFunction() {
        return function;
    }
    
}
