package ui;

import exceptions.RegraNegocioException;
import models.Musica;
import models.Perfil;
import models.Postagem;
import services.MusicaService;
import services.PerfilService;
import services.PostagemService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Painel gráfico responsável pela publicação e exibição do feed de Postagens.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class PainelPostagens extends JPanel {

    private final PostagemService postagemService;
    private final PerfilService perfilService;
    private final MusicaService musicaService;

    private JComboBox<String> cbAutor;
    private JComboBox<String> cbMusica;
    private JTextField txtConteudo;
    private JTextField txtImagem;

    private JComboBox<String> cbFiltroAutor;
    private JTextArea areaListagem;

    /**
     * Construtor que recebe os serviços de Postagem, Perfil e Música e monta os componentes visuais.
     *
     * @param postagemService Serviço de gerenciamento de postagens
     * @param perfilService Serviço de gerenciamento de perfis
     * @param musicaService Serviço de gerenciamento de músicas
     * @see PostagemService
     * @see PerfilService
     * @see MusicaService
     */
    public PainelPostagens(PostagemService postagemService, PerfilService perfilService, MusicaService musicaService) {
        this.postagemService = postagemService;
        this.perfilService = perfilService;
        this.musicaService = musicaService;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelFeed(), BorderLayout.CENTER);
    }

    private JPanel criarPainelFormulario() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 10, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Publicar Nova Postagem"));

        panel.add(new JLabel("Perfil Autor:"));
        cbAutor = new JComboBox<>();
        panel.add(cbAutor);

        panel.add(new JLabel("Música Compartilhada:"));
        cbMusica = new JComboBox<>();
        panel.add(cbMusica);

        panel.add(new JLabel("Conteúdo (Obrigatório):"));
        txtConteudo = new JTextField();
        panel.add(txtConteudo);

        panel.add(new JLabel("Imagem (Opcional):"));
        txtImagem = new JTextField();
        panel.add(txtImagem);

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        JButton btnPublicar = new JButton("Publicar Postagem");
        btnPublicar.addActionListener(e -> publicarPostagem());
        panel.add(btnPublicar);

        return panel;
    }

    private JPanel criarPainelFeed() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Feed de Postagens"));

        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.add(new JLabel("Filtrar por Autor:"));

        cbFiltroAutor = new JComboBox<>();
        cbFiltroAutor.addItem("TODOS");
        cbFiltroAutor.addActionListener(e -> atualizarListagem());
        painelFiltro.add(cbFiltroAutor);

        areaListagem = new JTextArea();
        areaListagem.setEditable(false);
        areaListagem.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JButton btnAtualizar = new JButton("Atualizar Feed");
        btnAtualizar.addActionListener(e -> atualizarDados());

        panel.add(painelFiltro, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaListagem), BorderLayout.CENTER);
        panel.add(btnAtualizar, BorderLayout.SOUTH);

        return panel;
    }

    private void publicarPostagem() {
        try {
            List<Perfil> perfis = perfilService.listarTodos();
            List<Musica> musicas = musicaService.listarTodas();

            int idxPerfil = cbAutor.getSelectedIndex();
            int idxMusica = cbMusica.getSelectedIndex();

            if (idxPerfil < 0 || perfis.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Cadastre pelo menos um perfil antes de publicar.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (idxMusica < 0 || musicas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Cadastre pelo menos uma música antes de publicar.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Perfil autor = perfis.get(idxPerfil);
            Musica musica = musicas.get(idxMusica);
            String conteudo = txtConteudo.getText().trim();
            String imagem = txtImagem.getText().trim();

            if (imagem.isEmpty()) {
                postagemService.publicar(autor.getIdPerfil(), musica.getIdMusica(), conteudo);
            } else {
                postagemService.publicar(autor.getIdPerfil(), musica.getIdMusica(), conteudo, imagem);
            }

            JOptionPane.showMessageDialog(this,
                    "Postagem publicada com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            txtConteudo.setText("");
            txtImagem.setText("");
            atualizarListagem();

        } catch (RegraNegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Regra de Negócio", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String obterNomePerfil(int idPerfil) {
        try {
            return perfilService.buscarPorId(idPerfil).getNomeApelido();
        } catch (RegraNegocioException e) {
            return "Autor Desconhecido";
        }
    }

    private String obterDescricaoMusica(int idMusica) {
        try {
            Musica m = musicaService.buscarPorId(idMusica);
            return String.format("%s - %s", m.getTitulo(), m.getArtista());
        } catch (RegraNegocioException e) {
            return "Música Desconhecida";
        }
    }

    /**
     * Recarrega os combos de perfis e músicas cadastrados e atualiza o feed.
     */
    public void atualizarDados() {
        List<Perfil> perfis = perfilService.listarTodos();
        cbAutor.removeAllItems();
        for (Perfil p : perfis) {
            cbAutor.addItem(p.getNomeApelido());
        }

        List<Musica> musicas = musicaService.listarTodas();
        cbMusica.removeAllItems();
        for (Musica m : musicas) {
            cbMusica.addItem(String.format("%s - %s", m.getTitulo(), m.getArtista()));
        }

        // Preserva o filtro selecionado se possível
        int filtroIndexAnterior = cbFiltroAutor.getSelectedIndex();
        cbFiltroAutor.removeAllItems();
        cbFiltroAutor.addItem("TODOS");
        for (Perfil p : perfis) {
            cbFiltroAutor.addItem(p.getNomeApelido());
        }
        if (filtroIndexAnterior >= 0 && filtroIndexAnterior < cbFiltroAutor.getItemCount()) {
            cbFiltroAutor.setSelectedIndex(filtroIndexAnterior);
        }

        atualizarListagem();
    }

    /**
     * Atualiza a área de texto com as postagens publicadas, aplicando o filtro de autor selecionado.
     */
    public void atualizarListagem() {
        int idxFiltro = cbFiltroAutor.getSelectedIndex();
        List<Perfil> perfis = perfilService.listarTodos();
        List<Postagem> lista;

        if (idxFiltro <= 0 || (idxFiltro - 1) >= perfis.size()) {
            lista = postagemService.listarTodas();
        } else {
            Perfil perfilFiltrado = perfis.get(idxFiltro - 1);
            lista = postagemService.listarPorPerfil(perfilFiltrado.getIdPerfil());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== POSTAGENS PUBLICADAS ===\n");
        for (Postagem post : lista) {
            String nomeAutor = obterNomePerfil(post.getIdPerfil());
            String descMusica = obterDescricaoMusica(post.getIdMusica());
            String imgStr = (post.getImagem() == null || post.getImagem().isEmpty()) ? "Sem imagem" : post.getImagem();

            sb.append(String.format("[%s] %s compartilhou '%s'\n   \"%s\" (Anexo: %s)\n\n",
                    post.getDataPostagem(), nomeAutor, descMusica, post.getConteudo(), imgStr));
        }

        areaListagem.setText(sb.toString());
    }
}