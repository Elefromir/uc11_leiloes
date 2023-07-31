
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class ProdutosDAO {
    
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        
        ConectaDAO conexaoDAO = new ConectaDAO();
        Connection conexao = conexaoDAO.conectarDB();
        
        try {
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, produto.getNome());
            st.setDouble(2, produto.getValor());
            st.setString(3, produto.getStatus());
            
            st.executeUpdate();
            System.out.println("Produto "+produto.getNome()+" foi cadastrado no DB");
            
        } catch (SQLException e) {
            System.out.println("SQL lançou uma excessão durante o cadastro de produto"+e);
        } finally {
            conexaoDAO.desconectarDB();
        }
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        return listagem;
        
    }
    
    
    
        
}

