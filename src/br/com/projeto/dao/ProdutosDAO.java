
package br.com.projeto.dao;
import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Produtos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
public class ProdutosDAO {
    private Connection connection;
    
    public ProdutosDAO(){
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void cadastrarProduto(Produtos prod){
        try {
            String sql =
                    "INSERT INTO tb_produtos(descricao, preco, qtd_estoque)"
                    + " values(?,?,?) ";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
      
            stmt.setString(1, prod.getDescrição());
            stmt.setDouble(2, prod.getPreço());
            stmt.setInt(3, prod.getQtd_estoque());
            
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Algo deu errado! "+ ex);
        }
    }
    
    public List<Produtos> listarProdutos(){
        try {

            //1 passo criar a lista
            List<Produtos> lista = new ArrayList<>();

            //2 passo - criar o sql , organizar e executar.
            String sql = "select * from tb_produtos";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produtos prod = new Produtos();

                prod.setId(rs.getInt("id"));
                prod.setDescrição(rs.getString("descricao"));
                prod.setPreço(rs.getDouble("preco"));
                prod.setQtd_estoque(rs.getInt("qtd_estoque"));
                
                lista.add(prod);
            }        
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro: "+ ex);
            return null;
        }        
    }
    
    public void alterarProduto(Produtos prod){
        try {
                       
            String sql = "UPDATE tb_produtos SET descricao=?, preco=?, qtd_estoque=? where id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, prod.getDescrição());
            stmt.setDouble(2, prod.getPreço());
            stmt.setInt(3, prod.getQtd_estoque());
            
            stmt.setInt(4, prod.getId());
                
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!!!");   
        }catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro: "+ ex);           
        }
    }
    
    public void excluirProduto(Produtos prod){
        try {
                       
            String sql = "DELETE FROM tb_produtos WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,prod.getId());
            
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!!!");
                                 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: "+ ex);           
        }
    }
    
    public List<Produtos> consultaPorDescricao(String descricao){
        try {

            List<Produtos> lista = new ArrayList<>();

            String sql = "select * from tb_produtos WHERE descricao like ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, descricao);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Produtos prod = new Produtos();

                prod.setId(rs.getInt("id"));
                prod.setDescrição(rs.getString("descricao"));
                prod.setPreço(rs.getDouble("preco"));
                prod.setQtd_estoque(rs.getInt("qtd_estoque"));
                
                
                lista.add(prod);
            }        
            return lista;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro: "+ ex);
            return null;
        }
    }
    
    public Produtos buscaPorDescricao(String descricao){
        try {
            List<Produtos> lista = new ArrayList<>();

            String sql = "select * from tb_produtos WHERE descricao = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, descricao);
            ResultSet rs = stmt.executeQuery();
            
            Produtos prod = new Produtos();
            
            if (rs.next()) {
                prod.setId(rs.getInt("id"));
                prod.setDescrição(rs.getString("descricao"));
                prod.setPreço(rs.getDouble("preco"));
                prod.setQtd_estoque(rs.getInt("qtd_estoque"));
            }
            return prod;
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            return null;
        }
    }
    
}
   
