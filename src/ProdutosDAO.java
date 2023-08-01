
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;


public class ProdutosDAO {
    
    private ConectaDAO conexao;
    private Connection conn;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public ProdutosDAO(){
        this.conexao = new ConectaDAO();
        this.conn = this.conexao.conectarDB();
    }
    
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
            JOptionPane.showMessageDialog(null, "Produto "+produto.getNome()+" foi cadastrado com sucesso.");
//            System.out.println("Produto "+produto.getNome()+" foi cadastrado no DB");
            
        } catch (SQLException e) {
            System.out.println("SQL lançou uma excessão durante o cadastro de produto"+e);
        } finally {
            conexaoDAO.desconectarDB();
        }
        
    }
    public List<ProdutosDTO> ListarProdutosVendidos(){
        
        List<ProdutosDTO> listaProdutosVendidos = new ArrayList<>();
        ConectaDAO conexaoDAO = new ConectaDAO();
        Connection conexao = conexaoDAO.conectarDB();
        
        try {
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getDouble("valor"));
                produto.setStatus(rs.getString("status"));
                
                listaProdutosVendidos.add(produto);
            }
            return listaProdutosVendidos;
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar os produtos vendidos: " + e.getMessage());
            return null;
        }
        
    }
    public List<ProdutosDTO> listarProdutos(){
        String sql = "SELECT * FROM produtos";
        
        try {
            
            PreparedStatement st = this.conn.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            
            List<ProdutosDTO> listaProdutos = new ArrayList<>();
            
            while(rs.next()){
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getDouble("valor"));
                produto.setStatus(rs.getString("status"));
                
                listaProdutos.add(produto);
            }
            System.out.println("Produtos enviados");
            return listaProdutos;
        } catch (SQLException e) {
            System.out.println("erro"+e);
            return null;
        } finally{
            desconectar();
        }
        
    }
    
    public void venderProduto(int id){
        
        ConectaDAO conexaoDAO = new ConectaDAO();
        Connection conexao = conexaoDAO.conectarDB();
        
        try {
            String sql = "UPDATE produtos SET status ='Vendido' where id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, id);
            
            int linhasAfetadas = st.executeUpdate();
            System.out.println("linhasAfetadas: "+linhasAfetadas);
            if (linhasAfetadas > 0) {
                System.out.println("Produto vendido com sucesso!");
            } else {
                System.out.println("Produto não encontrado ou não pôde ser vendido.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao vender o produto: " + e.getMessage());
        } finally {
            conexaoDAO.desconectarDB();
        }
        
        
    }
    
    public void desconectar(){
        
        try {
            conn.close();
            System.out.println("Conexão fechada");
        } catch (SQLException e) {
            
        }
    }
    
    
    
        
}

