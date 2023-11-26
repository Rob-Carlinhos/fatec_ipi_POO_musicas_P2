import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.JOptionPane;

import static java.lang.Integer.parseInt;
public class GerenciaMusicas {
  public static void main(String[] args) {
    
    var musicaDAO = new MusicaDAO();
    int op = -1;
    String menu = "1-Cadastrar música\n2-Avaliar música\n3-Ver músicas\n4-Deletar música\n0-Sair";
    //adicionada a opção de deletar 
    do{
      try{
        op = parseInt(showInputDialog(menu));
        switch(op){
          case 1:{
            String titulo = showInputDialog("Titulo?");
            var musica = new Musica(titulo, 0);
            musicaDAO.cadastrar(musica);
            showMessageDialog(null, "Música cadastrada!");
            break;
          }
          case 2:{
            String titulo = showInputDialog("Titulo?");
            int nota = parseInt(showInputDialog("Nota?"));
            musicaDAO.avaliar(new Musica(titulo, nota));
            showMessageDialog(null, "Música avaliada!!");
            break;
          }
          case 3:{
            var musicas = musicaDAO.listar();
            
            if(musicas.isEmpty()){
              //isEmpty valida se a lista está vazia ou não
              JOptionPane.showMessageDialog(null, "Nenhuma música cadastrada!");
              break;
            }
            
            for (Musica musica : musicas) {
              JOptionPane.showMessageDialog(null, musica);
            }
            break;
          }
          case 4:{
            String titulo = showInputDialog("Digite o título da música que será excluída.");
            if(musicaDAO.buscar(titulo)){
              musicaDAO.deletar(titulo);
              JOptionPane.showMessageDialog(null, "Música " + titulo + " excluída com sucesso!");
              break;
            }
            JOptionPane.showMessageDialog(null, "Nenhuma música encontrada com o título: " + titulo);
            break;
          }
          case 0:
            showMessageDialog(null, "Até logo");
            break;
          default:
            showMessageDialog(null, "Opção inválida");
            break;
        }
      }
      catch(Exception e){
        e.printStackTrace();
        showMessageDialog(null, "Não rolou");
      }
    }while (op != 0);
  }
}