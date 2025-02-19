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
package org.eclipse.jnosql.mapping.cassandra.column;

import jakarta.nosql.column.ColumnDeleteQuery;
import jakarta.nosql.mapping.column.ColumnRepositoryProducer;
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
public class CassandraRepositoryProxyTest {

    private CassandraTemplate template;

    @Inject
    private ColumnRepositoryProducer producer;

    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        this.template = Mockito.mock(CassandraTemplate.class);
        PersonRepository personRepository = producer.get(PersonRepository.class, template);
        CassandraRepositoryProxy handler = new CassandraRepositoryProxy(template,
                PersonRepository.class, personRepository);

        when(template.insert(any(Person.class))).thenReturn(new Person());
        when(template.insert(any(Person.class), any(Duration.class))).thenReturn(new Person());
        when(template.update(any(Person.class))).thenReturn(new Person());
        this.personRepository = (PersonRepository) Proxy.newProxyInstance(PersonRepository.class.getClassLoader(),
                new Class[]{PersonRepository.class},
                handler);
    }


    @Test
    public void shouldFindByName() {
        personRepository.findByName("Ada");
        verify(template).cql("select * from Person where name = ?", "Ada");
    }

    @Test
    public void shouldDeleteByName() {
        personRepository.deleteByName("Ada");
        verify(template).delete(Mockito.any(ColumnDeleteQuery.class));
    }

    @Test
    public void shouldFindAll() {
        personRepository.findAll();
        verify(template).cql("select * from Person");
    }

    @Test
    public void shouldFindByNameCQL() {
        personRepository.findByName("Ada");
        verify(template).cql(Mockito.eq("select * from Person where name = ?"), Mockito.any(Object.class));
    }

    @Test
    public void shouldFindByName2CQL() {
        ArgumentCaptor<Map> captor = ArgumentCaptor.forClass(Map.class);

        personRepository.findByName2("Ada");
        verify(template).cql(Mockito.eq("select * from Person where name = :name"), captor.capture());
        Map map = captor.getValue();
        assertEquals("Ada", map.get("name"));
    }

    interface PersonRepository extends CassandraRepository<Person, String> {

        void deleteByName(String namel);

        @CQL("select * from Person")
        List<Person> findAll();

        @CQL("select * from Person where name = ?")
        List<Person> findByName(String name);

        @CQL("select * from Person where name = :name")
        List<Person> findByName2(@Param("name") String name);
    }

}