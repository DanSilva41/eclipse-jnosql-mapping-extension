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
 *   Otavio Santana
 */
package org.eclipse.jnosql.mapping.solr.document;

import jakarta.nosql.mapping.document.DocumentRepositoryProducer;
import jakarta.nosql.tck.test.CDIExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@CDIExtension
public class SolrRepositoryProxyTest {

    private SolrTemplate template;

    @Inject
    private DocumentRepositoryProducer producer;

    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        this.template = Mockito.mock(SolrTemplate.class);

        SolrRepositoryProxy handler = new SolrRepositoryProxy(template,
                PersonRepository.class, producer.get(PersonRepository.class, template));

        when(template.insert(any(Person.class))).thenReturn(new Person());
        when(template.insert(any(Person.class), any(Duration.class))).thenReturn(new Person());
        when(template.update(any(Person.class))).thenReturn(new Person());
        personRepository = (PersonRepository) Proxy.newProxyInstance(PersonRepository.class.getClassLoader(),
                new Class[]{PersonRepository.class},
                handler);
    }

    @Test
    public void shouldFindAll() {
        personRepository.findAll();
        verify(template).solr("_entity:person");
    }

    @Test
    public void shouldFindByNameN1ql() {
        ArgumentCaptor<Map> captor = ArgumentCaptor.forClass(Map.class);
        personRepository.findByName("Ada");
        verify(template).solr(Mockito.eq("name:@name AND _entity:person"), captor.capture());

        Map<String, Object> value = captor.getValue();

        assertEquals("Ada", value.get("name"));
    }

    interface PersonRepository extends SolrRepository<Person, String> {

        @Solr("_entity:person")
        List<Person> findAll();

        @Solr("name:@name AND _entity:person")
        List<Person> findByName(@Param("name") String name);
    }
}