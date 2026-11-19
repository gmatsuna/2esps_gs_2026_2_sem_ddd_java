package ui;

import services.*;

import javax.swing.*;
import java.awt.*;

/**
 * Janela principal da interface gráfica (Swing) do projeto Afina.
 * Organiza os módulos da rede social musical em abas através de um JTabbedPane.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class TelaPrincipal extends JFrame {

    private final UsuarioService usuarioService;
    private final PerfilService perfilService;
    private final MusicaService musicaService;
    private final ConexaoService conexaoService;
    private final PostagemService postagemService;
    private final ComunidadeService comunidadeService;
    private final EventoService eventoService;

    private JTabbedPane abas;

    /**
     * Construtor que inicializa todos os serviços do domínio e configura a janela principal.
     */
    public TelaPrincipal() {
        this.usuarioService = new UsuarioService();
        this.perfilService = new PerfilService(usuarioService);
        this.musicaService = new MusicaService();
        this.conexaoService = new ConexaoService(perfilService);
        this.postagemService = new PostagemService(perfilService, musicaService);
        this.comunidadeService = new ComunidadeService(perfilService);
        this.eventoService = new EventoService(comunidadeService);

        configurarJanela();
        inicializarAbas();
    }

    private void configurarJanela() {
        setTitle("Afina - Rede Social Musical");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarAbas() {
        abas = new JTabbedPane();

        PainelUsuariosPerfis painelUsuariosPerfis = new PainelUsuariosPerfis(usuarioService, perfilService);
        abas.addTab("Usuários & Perfis", painelUsuariosPerfis);

        PainelMusicas painelMusicas = new PainelMusicas(musicaService);
        abas.addTab("Músicas", painelMusicas);

        PainelConexoes painelConexoes = new PainelConexoes(conexaoService, perfilService);
        abas.addTab("Conexões", painelConexoes);

        PainelPostagens painelPostagens = new PainelPostagens(postagemService, perfilService, musicaService);
        abas.addTab("Postagens", painelPostagens);

        PainelComunidades painelComunidades = new PainelComunidades(comunidadeService, perfilService);
        abas.addTab("Comunidades", painelComunidades);

        PainelEventos painelEventos = new PainelEventos(eventoService, comunidadeService);
        abas.addTab("Eventos", painelEventos);

        abas.addChangeListener(e -> {
            if (abas.getSelectedComponent() == painelConexoes) {
                painelConexoes.atualizarDados();
            } else if (abas.getSelectedComponent() == painelPostagens) {
                painelPostagens.atualizarDados();
            } else if (abas.getSelectedComponent() == painelComunidades) {
                painelComunidades.atualizarDados();
            } else if (abas.getSelectedComponent() == painelEventos) {
                painelEventos.atualizarDados();
            }
        });

        add(abas, BorderLayout.CENTER);
    }

    /**
     * Ponto de entrada para executar a interface gráfica Swing do Afina.
     *
     * @param args Argumentos de linha de comando
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}