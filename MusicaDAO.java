import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
public class MusicaDAO {

  public void cadastrar(Musica musica) throws Exception{
    //1. Especificar o comando SQL
    String sql = "INSERT INTO tb_musica(titulo, ativo) VALUES(?, true)";
    //2. Estabelecer uma conexão com o SGBD (PostgreSQL)
    var conexao = ConnectionFactory.conectar();
    //3.Preparar o comando
    PreparedStatement ps = conexao.prepareStatement(sql);
    //4. Substituir os eventuais placeholders
    ps.setString(1, musica.getTitulo());
    //5. Executar o comando
    ps.execute();
    //6. Fechar os recursos
    ps.close();
    conexao.close();
  }

  public void avaliar(Musica musica) throws Exception{
    //1. Especificar o comando SQL (update)
    var sql = "UPDATE tb_musica SET avaliacao=? WHERE titulo=?;";
    //2. Estabelecer uma conexão com o banco
    //try-with-resources
    try(
      var conexao = ConnectionFactory.conectar();
      //3. Preparar o comando
      var ps = conexao.prepareStatement(sql);
    ){      
      //4. Substituir os eventuais placeholders
      ps.setInt(1, musica.getAvaliacao());
      ps.setString(2, musica.getTitulo());
      //5. Executar
      ps.execute();
      //6. Fechar os recursos
      //o try-with-resources já fez isso
    }
  }

  public ArrayList<Musica> listar() throws Exception{
    //Buscar por todas as músicas ativas 
    var sql = "SELECT titulo, avaliacao FROM tb_musica WHERE ativo = true";
   
    var musicas = new ArrayList<Musica>();

    try(
      var conexao = ConnectionFactory.conectar();
      var ps = conexao.prepareStatement(sql);

      ){
      
        try(
          ResultSet rs = ps.executeQuery();
        ){
         
        while(rs.next()){
          int avaliacao = rs.getInt("avaliacao");
          String titulo = rs.getString("titulo");
          var musica = new Musica(titulo, avaliacao);
          musicas.add(musica);
        }
      
      }  

    }
    musicas.sort(new ComparadorPorAvaliacao());
    //sort - ordena as músicas 
    return musicas;
  }

  public boolean buscar(String titulo) throws Exception{
    var sql = "SELECT titulo from tb_musica WHERE titulo =?;";

    try(
      var conexao = ConnectionFactory.conectar();
      var ps = conexao.prepareStatement(sql);
    ){      
      ps.setString(1, titulo);
      ResultSet rs = ps.executeQuery();

      //caso não encontre nenhuma musica com o titulo, retorna false
      if(!rs.next())
        return false;

      //caso encontre pelo menos uma musica retorna true
      return true;
    }

  }
  
  public void deletar(String titulo) throws Exception{
      var sql = "UPDATE tb_musica SET ativo = false WHERE titulo =? and ativo = true;";

      try(
        var conexao = ConnectionFactory.conectar();
        var ps = conexao.prepareStatement(sql);
      ){      
        ps.setString(1, titulo);
        ps.execute();
      }
  }
}