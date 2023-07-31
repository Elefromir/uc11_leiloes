
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConectaDAO {
    
    public String url = "jdbc:mysql://localhost:3306/uc11?serverTimezone=UTC";
    public String user = "root";
    public String password = "48422394842239";
    
    private Connection conexao;
    
    public Connection conectarDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(url, user, password);
            System.out.println("Conexao obtida");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro na conexao"+e);
        }
        return conexao;
    }
    
    public void desconectarDB(){
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {

            }
        }
    }
    
    
}

