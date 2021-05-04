package org.example;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    public List<Aluno> list() {
        List<Aluno> alunos = new ArrayList<>();

        try (var conn = ConnectionFactory.getConnection()) {
            var sql = "SELECT * FROM aluno";

            var stmt = conn.prepareStatement(sql);

            var rs = stmt.executeQuery();

            while(rs.next()){
                var id = rs.getInt("id");
                var nome = rs.getString("nome");
                var idade = rs.getInt("idade");
                var estado = rs.getString("estado");

                alunos.add(new Aluno(
                    id,
                    nome,
                    idade,
                    estado
                ));
            }
        } catch (SQLException e) {
            System.out.println("Listagem de alunos FALHOU!");
            e.printStackTrace();
        }

        return alunos;
    }

    public Aluno getById(int id) {
        var aluno = new Aluno();

        try (var conn = ConnectionFactory.getConnection()) {
            var sql = "SELECT * FROM aluno WHERE id = ?";

            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            var rs = stmt.executeQuery();

            if (rs.next()){
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setIdade(rs.getInt("idade"));
                aluno.setEstado(rs.getString("estado"));
            }

        } catch (SQLException e) {
            System.out.println("Listagem de alunos FALHOU!");
            e.printStackTrace();
        }

        return aluno;
    }

    public Aluno create(Aluno aluno) {
        try (var conn = ConnectionFactory.getConnection()) {
            var sql = "INSERT INTO aluno(nome, idade, estado) VALUES(?, ?, ?)";

            var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1 , aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3 , aluno.getEstado());

            var rowsAffected = stmt.executeUpdate();
            System.out.println("Inserção BEM SUCEDIDA!. Foi adicionada " + rowsAffected + " linha");

            try (var generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aluno.setId(generatedKeys.getInt(1));
                    return aluno;
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Inserção FALHOU!");
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        try (var conn = ConnectionFactory.getConnection()) {
            var sql = "DELETE FROM aluno WHERE id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1 , id);

            var rowsAffected = stmt.executeUpdate();

            System.out.println("Delete BEM SUCEDIDO! Foi deletada " + rowsAffected + " linha");
        } catch (SQLException e) {
            System.out.println("Delete FALHOU!");
            e.printStackTrace();
        }
    }

    public void update(Aluno aluno) {
        try (var conn = ConnectionFactory.getConnection()) {
            var sql = "UPDATE aluno SET nome = ?, idade = ?, estado = ? WHERE id = ?";

            var stmt = conn.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getIdade());
            stmt.setString(3, aluno.getEstado());
            stmt.setInt(4, aluno.getId());

            var rowsAffected = stmt.executeUpdate();

            System.out.println("Atualização BEM SUCEDIDA! Foi atualizada: " + rowsAffected + " linha");
        } catch (SQLException e) {
            System.out.println("Atualização FALHOU!");
            e.printStackTrace();
        }
    }
}
