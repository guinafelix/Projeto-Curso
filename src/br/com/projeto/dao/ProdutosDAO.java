
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
    
    
 
        
}
   
