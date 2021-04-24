package org.example;

import org.example.classes.Aluno;
import org.example.classes.Estado;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Part3 implements AutoCloseable {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public Part3() {
        entityManagerFactory = Persistence.createEntityManagerFactory("part2-DIO");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void run() {
        entityManager.getTransaction().begin();
        var estado = new Estado("Rio de Janeiro", "RJ");
        entityManager.persist(estado);
        entityManager.persist(new Estado("Sao Paulo", "SP"));
        entityManager.persist(new Aluno("Daniel", 29, estado));
        entityManager.persist(new Aluno("Joao", 20, estado));
        entityManager.persist(new Aluno("Pedro", 30, estado));
        entityManager.getTransaction().commit();

        System.out.println("Consulta aluno: " + entityManager.find(Aluno.class, 1));

        String nome = "Daniel";

        // =============================================================================================================

        // 2.3 - SQL nativo

        String sql = "SELECT * FROM Aluno WHERE nome = :nome ";
        Aluno alunoSQL = (Aluno) entityManager
                .createNativeQuery(sql, Aluno.class)
                .setParameter("nome", nome)
                .getSingleResult();

        String sqlList = "SELECT * FROM Aluno";
        List<Aluno> alunoSQLList = entityManager
                .createNativeQuery(sqlList, Aluno.class)
                .getResultList();

        // Resultados das consultas acima
        System.out.println("Consulta alunoSQL: " + alunoSQL);
        alunoSQLList.forEach(aluno -> System.out.println("Consulta alunoSQLList: " + aluno));

        // =============================================================================================================

        // 2.4 - JPQL

        // Trazendo somente 1 resultado
        String jpql = "select a from Aluno a where a.nome = :nome";
        Aluno alunoJPQL = entityManager
                .createQuery(jpql, Aluno.class)
                .setParameter("nome", nome)
                .getSingleResult();

        // Trazendo uma lista como resultado
        String jpqlList = "select a from Aluno a where a.estado = :estado";
        List<Aluno> alunoJPQLList = entityManager
                .createQuery(jpqlList, Aluno.class)
                .setParameter("estado", estado)
                .getResultList();

        // Resultados das consultas acima
        System.out.println("Consulta alunoJPQL: " + alunoJPQL);
        alunoJPQLList.forEach(aluno -> System.out.println("Consulta alunoJPQLList: " + aluno));

        // =============================================================================================================

//        // 2.5 - JPA Criteria API + JPA Metamodel
//
//        // Trazendo somente 1 resultado
//        CriteriaQuery<Aluno> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Aluno.class);
//        Root<Aluno> alunoRoot = criteriaQuery.from(Aluno.class);
//        CriteriaBuilder.In<String> inClause = entityManager.getCriteriaBuilder().in(alunoRoot.get(Aluno_.nome));
//        inClause.value(nome);
//        criteriaQuery.select(alunoRoot).where(inClause);
//        Aluno alunoAPICriteria = entityManager.createQuery(criteriaQuery).getSingleResult();
//
//        // Trazendo uma lista como resultado
//        CriteriaQuery<Aluno> criteriaQueryList = entityManager.getCriteriaBuilder().createQuery(Aluno.class);
//        Root<Aluno> alunoRootList = criteriaQueryList.from(Aluno.class);
//        List<Aluno> alunoAPICriteriaList = entityManager.createQuery(criteriaQueryList).getResultList();
//
//        // Resultados das consultas acima
//        System.out.println("Consulta alunoAPICriteria: " + alunoAPICriteria);
//        alunoAPICriteriaList.forEach(Aluno -> System.out.println("Consulta alunoAPICriteriaList: " + Aluno));
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }
}
