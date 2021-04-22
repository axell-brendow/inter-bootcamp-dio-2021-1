package org.example;

import org.example.classes.Aluno;
import org.example.classes.Estado;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Part1 implements AutoCloseable {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public Part1() {
        entityManagerFactory = Persistence.createEntityManagerFactory("part1-DIO");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void run() {
        Estado estadoParaAdicionar = new Estado("Rio de Janeiro", "RJ");
        Aluno alunoParaAdicionar = new Aluno("Daniel", 29, estadoParaAdicionar);

        entityManager.getTransaction().begin();

        entityManager.persist(estadoParaAdicionar);
        entityManager.persist(alunoParaAdicionar);

        entityManager.getTransaction().commit();
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }
}
