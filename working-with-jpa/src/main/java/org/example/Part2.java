package org.example;

import org.example.classes.Aluno;
import org.example.classes.Estado;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Part2 implements AutoCloseable {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    Estado estado;
    Aluno aluno;

    public Part2() {
        entityManagerFactory = Persistence.createEntityManagerFactory("part2-DIO");
        entityManager = entityManagerFactory.createEntityManager();
    }

    private void create() {
        entityManager.getTransaction().begin();
        entityManager.persist(estado);
        entityManager.persist(aluno);
        entityManager.getTransaction().commit();
    }

    private void find() {
        estado = entityManager.find(Estado.class, estado.getId());
        aluno = entityManager.find(Aluno.class, aluno.getId());
        System.out.println(estado);
        System.out.println(aluno);
    }

    private void update() {
        entityManager.getTransaction().begin();
        aluno.setNome("Karam");
        aluno.setIdade(20);
        entityManager.getTransaction().commit();
    }

    private void delete() {
        entityManager.getTransaction().begin();
        entityManager.remove(aluno);
        entityManager.remove(estado);
        entityManager.getTransaction().commit();
    }

    public void run() {
        estado = new Estado("Rio de Janeiro", "RJ");
        aluno = new Aluno("Daniel", 29, estado);
        create();
        find();
        update();
        delete();
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }
}
