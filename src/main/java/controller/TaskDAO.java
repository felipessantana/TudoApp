/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;
/**
 *
 * @author Imply
 */
public class TaskDAO {
    
    public void save(Task task){
     
        String sql = "INSERT INTO tasks (idProject, name,description,completed,notes,deadline,createdAt,updatedAt) VALUES (?,?,?,?,?,?,?,?)";
        
            Connection conn = null;
            PreparedStatement stmt = null;
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, task.getIdProject());
            stmt.setString(2, task.getName());
            stmt.setString(3,task.getDescription());
            stmt.setBoolean(4,task.isCompleted());
            stmt.setString(5,task.getNotes());
            stmt.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            stmt.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            stmt.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            stmt.execute();
       } catch (SQLException ex) {
           
           throw new RuntimeException("Erro ao salvar tarefa " + ex.getMessage(),ex);
        }
        finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
            
        
        }
    
    }
    public void update(Task task){
        
        String sql = "UPDATE tasks SET "
                + "idProject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "notes = ?,"
                + "completed = ?,"
                + "deadline = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?"
                + "WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt =  null;
        try {
            // Estabelecendo a conexão com banco de dados
            conn = ConnectionFactory.getConnection();
            
            //Preparando a query
            stmt = conn.prepareStatement(sql);
            // Setando os valores do stmt
            
            stmt.setInt(1, task.getIdProject());
            stmt.setString(2, task.getName());
            stmt.setString(3,task.getDescription());
            stmt.setString(4,task.getNotes());
            stmt.setBoolean(5,task.isCompleted());
            stmt.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            stmt.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            stmt.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            stmt.setInt(9,task.getId());
            stmt.execute();
            
            
            
        } catch (SQLException ex) {
            
            throw new RuntimeException("Erro ao atualizar tarefa " ,ex);
        }
        finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        
        }

    }
    public void removeById(int id){
        
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            throw  new RuntimeException("Erro ao deletar a tarefa",ex);
        }
        finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        
        }
        
    }

    
    public List<Task> getAll(int idProject){
        
    
    
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        //lista de tarefas que será devolvida quando a chamada do metodo acontecer
        List<Task> tasks = new ArrayList<>();
        
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            
            //Setando o valor do filtro
            stmt.setInt(1, idProject);
            //Valor retornado do pela execução da query
            rset = stmt.executeQuery();
            
            //Enquanto houver valores a serem percorridos no meu resultset
            while(rset.next()){
            
                Task  task = new Task();
                
                task.setId(rset.getInt("id"));
                task.setIdProject(rset.getInt("idProject"));
                task.setName(rset.getString("name"));
                task.setDescription(rset.getString("description"));
                task.setIsCompleted(rset.getBoolean("completed"));
                task.setNotes(rset.getString("notes"));
                task.setDeadline(rset.getDate("deadline"));
                task.setCreatedAt(rset.getDate("createdAt"));
                task.setUpdatedAt(rset.getDate("updatedAt"));
                tasks.add(task);
                
            
            
            }
            
        } catch (Exception ex) {
            
             throw  new RuntimeException("Erro ao inserir a tarefa",ex);
        
        }finally{
             try {
                if (rset != null) {
                    rset.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        
        
        }
        //Retorno da lista de tarefas
        return tasks;
    
    
    }
    
    
}


