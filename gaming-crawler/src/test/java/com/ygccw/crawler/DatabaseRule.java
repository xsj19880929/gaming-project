package com.ygccw.crawler;

import core.framework.util.JSONBinder;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DatabaseRule extends ExternalResource {
    private final Logger logger = LoggerFactory.getLogger(DatabaseRule.class);
    @Inject
    DataSource dataSource;
    @Inject
    EntityManagerFactory entityManagerFactory;

    public <T> MockEntity<T> mock(Class<T> type) {
        return new MockEntity<>(type);
    }

    @Override
    protected void before() throws Throwable {
        clearDatabase();
    }

    private void clearDatabase() throws SQLException {
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE SCHEMA public AND COMMIT");
            connection.commit();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    protected void after() {
        try {
            clearDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void save(T instance) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(instance);
            entityManager.flush();
            transaction.commit();
            logger.info("save {}", JSONBinder.toJSON(instance));
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public class MockEntity<T> {
        private final Class<T> type;
        private final T instance;

        public MockEntity(Class<T> type) {
            this.type = type;

            try {
                instance = this.type.getDeclaredConstructor().newInstance();

                fillValues();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private MockEntity<T> fillValues() {
            for (Field field : instance.getClass().getDeclaredFields()) {
                if (isInclude(field)) {
                    Object object = defaultValue(field);

                    if (object != null) {
                        field.setAccessible(true);
                        try {
                            field.set(instance, object);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            return this;
        }

        private boolean isInclude(Field field) {
            return !Modifier.isTransient(field.getModifiers())
                    && !Modifier.isStatic(field.getModifiers())
                    && !Modifier.isFinal(field.getModifiers())
                    && !field.isAnnotationPresent(Transient.class)
                    && !field.isAnnotationPresent(Id.class);
        }

        private Object defaultValue(Field field) {
            if (Long.class.equals(field.getType()) || long.class.equals(field.getType())) {
                return 1L;
            } else if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
                return 1;
            } else if (String.class.equals(field.getType())) {
                return field.getName();
            } else if (Date.class.equals(field.getType())) {
                return new Date();
            } else if (boolean.class.equals(field.getType()) || Boolean.class.equals(field.getType())) {
                return true;
            } else {
                return null;
            }
        }

        public void save() {
            DatabaseRule.this.save(instance);
        }

        public T get() {
            return instance;
        }
    }
}