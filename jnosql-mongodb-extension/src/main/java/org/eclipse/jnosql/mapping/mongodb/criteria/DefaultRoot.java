/*
 *  Copyright (c) 2022 Contributors to the Eclipse Foundation
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *g
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Alessandro Moscatelli
 */
package org.eclipse.jnosql.mapping.mongodb.criteria;

import org.eclipse.jnosql.mapping.mongodb.criteria.api.Path;
import org.eclipse.jnosql.mapping.mongodb.criteria.api.PathFunction;
import org.eclipse.jnosql.mapping.mongodb.criteria.api.Root;

/**
 * Default implementation for {@link Root}
 *
 * @param <X> The Entity type whose fetching is to be be restricted
 */
public class DefaultRoot<X> extends DefaultPath<X, X> implements Root<X> {

    public DefaultRoot(Class<X> type) {
        super(type);
    }

    @Override
    public Path<X, ?> getParent() {
        return this;
    }
    
    @Override
    public PathFunction<X, X, X, Number> count() {
        return new DefaultPathFunction<>(this, PathFunction.Function.COUNT);
    }
    
}
