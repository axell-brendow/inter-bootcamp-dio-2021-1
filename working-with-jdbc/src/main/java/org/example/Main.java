package org.example;

public class Main {
    private static final AlunoDAO alunoDAO = new AlunoDAO();
    private static Aluno alunoCriado;

    public static void create()
    {
        System.out.println("\nCriando aluno\n");
        var alunoParaInsercao = new Aluno("Matheus", 43, "SP");
        alunoCriado = alunoDAO.create(alunoParaInsercao);
    }

    public static void getById()
    {
        System.out.println("\nObtendo aluno\n");
        var alunoParaConsulta = alunoDAO.getById(alunoCriado.getId());
        System.out.println(alunoParaConsulta);
    }

    public static void list()
    {
        System.out.println("\nListando alunos\n");
        var alunos = alunoDAO.list();
        alunos.stream().forEach(System.out::println);
    }

    public static void update()
    {
        System.out.println("\nAtualizando aluno\n");
        var alunoParaAtualizar = alunoDAO.getById(alunoCriado.getId());
        alunoParaAtualizar.setNome("Joaquim");
        alunoParaAtualizar.setIdade(18);
        alunoParaAtualizar.setEstado("RS");

        alunoDAO.update(alunoParaAtualizar);
    }

    public static void delete()
    {
        System.out.println("\nRemovendo aluno\n");
        alunoDAO.delete(alunoCriado.getId());
    }

    public static void main(String[] args) {
        create();
        getById();
        list();
        update();
        delete();
    }
}
