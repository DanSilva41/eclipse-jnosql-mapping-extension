/*
 *  Copyright (c) 2017 Otávio Santana and others
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
 *   Otavio Santana
 */
package org.jnosql.artemis.graph;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * The Graph Traversal that maps {@link org.apache.tinkerpop.gremlin.structure.Vertex}.
 * This Traversal is lazy, in other words, that just run after the
 */
public interface VertexTraversal extends VertexConditionTraversal {


    /**
     * Map the {@link EdgeTraversal} to its outgoing incident edges given the edge labels.
     *
     * @param edgeLabels the edge labels to traverse
     * @return a {@link EdgeTraversal} with the new condition
     * @throws NullPointerException when has any null element
     */
    EdgeTraversal outE(String... edgeLabels) throws NullPointerException;


    /**
     * Map the {@link EdgeTraversal} to its incoming incident edges given the edge labels.
     *
     * @param edgeLabels the edge labels to traverse
     * @return a {@link EdgeTraversal} with the new condition
     * @throws NullPointerException when has any null element
     */
    EdgeTraversal inE(String... edgeLabels) throws NullPointerException;

    /**
     * Map the {@link EdgeTraversal} to its either incoming or outgoing incident edges given the edge labels.
     *
     * @param edgeLabels the edge labels to traverse
     * @return a {@link EdgeTraversal} with the new condition
     * @throws NullPointerException when has any null element
     */
    EdgeTraversal bothE(String... edgeLabels) throws NullPointerException;


    /**
     * Starts the loop traversal graph
     *
     * @return a {@link VertexRepeatTraversal}
     */
    VertexRepeatTraversal repeat();


    /**
     * Map the {@link EdgeTraversal} to its outgoing incident edges given the edge labels.
     *
     * @param label the edge labels to traverse
     * @return a {@link EdgeTraversal} with the new condition
     * @throws NullPointerException when has any null element
     */
    default EdgeTraversal outE(Supplier<String> label) throws NullPointerException {
        requireNonNull(label, "the supplier is required");
        return outE(label.get());
    }


    /**
     * Map the {@link EdgeTraversal} to its incoming incident edges given the edge labels.
     *
     * @param label the edge labels to traverse
     * @return a {@link EdgeTraversal} with the new condition
     * @throws NullPointerException when has any null element
     */
    default EdgeTraversal inE(Supplier<String> label) throws NullPointerException {
        requireNonNull(label, "the supplier is required");
        return inE(label.get());
    }


    /**
     * Map the {@link EdgeTraversal} to its either incoming or outgoing incident edges given the edge labels.
     *
     * @param label the edge labels to traverse
     * @return a {@link EdgeTraversal} with the new condition
     * @throws NullPointerException when has any null element
     */
    default EdgeTraversal bothE(Supplier<String> label) throws NullPointerException {
        requireNonNull(label, "the supplier is required");
        return bothE(label.get());
    }


    /**
     * Filter the objects in the traversal by the number of them to pass through the next, where only the first
     * {@code n} objects are allowed as defined by the {@code limit} argument.
     *
     * @param limit the number at which to end the next
     * @return a {@link VertexTraversal} with the limit
     */
    VertexTraversal limit(long limit);

    /**
     * Returns a VertexTraversal with range defined
     * @param start the start inclusive
     * @param end the end exclusive
     * @return a {@link VertexTraversal} with the range set
     */
    VertexTraversal range(long start, long end);


    /**
     * Returns the next elements in the traversal.
     * If the traversal is empty, then an {@link Optional#empty()} is returned.
     *
     * @param <T> the entity type
     * @return the entity result otherwise {@link Optional#empty()}
     */
    <T> Optional<T> next();

    /**
     * Get all the result in the traversal as Stream
     *
     * @param <T> the entity type
     * @return the entity result as {@link Stream}
     */
    <T> Stream<T> stream();

    /**
     * Get the next n-number of results from the traversal.
     *
     * @param <T>   the entity type
     * @param limit the limit to result
     * @return the entity result as {@link Stream}
     */
    <T> Stream<T> next(int limit);

    /**
     * Map the {@link org.apache.tinkerpop.gremlin.structure.Element} to a {@link java.util.Map} of the properties key'd according
     * to their {@link org.apache.tinkerpop.gremlin.structure.Property#key}.
     * If no property keys are provided, then all properties are retrieved.
     *
     * @param propertyKeys the properties to retrieve
     * @return a {@link ValueMapTraversal} instance
     */
    ValueMapTraversal valueMap(final String... propertyKeys);


    /**
     * Map the traversal next to its reduction as a sum of the elements
     *
     * @return the sum
     */
    long count();
}
