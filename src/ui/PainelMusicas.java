package ui;

import exceptions.RegraNegocioException;
import models.GeneroMusical;
import models.Musica;
import services.MusicaService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Painel gráfico responsável pelo cadastro, filtro e listagem de Músicas.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class PainelMusicas extends JPanel {

    private final MusicaService musicaService;

    private JTextField txtTitulo;
    private JTextField txtArtista;
    private JComboBox<GeneroMusical> cbGenero;
    private JTextField txtAlbum;
    private JTextField txtAnoLancamento;
    private JTextField txtLinkExterno;

    private JComboBox<String> cbFiltroGenero;
    private JTextArea areaListagem;

    /**
     * Construtor que recebe o serviço de Música e monta os componentes visuais.
     *
     * @param musicaService Serviço de gerenciamento de músicas
     */
    public PainelMusicas(MusicaService musicaService) {
        this.musicaService = musicaService;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelListagem(), BorderLayout.CENTER);
    }

    private JPanel criarPainelFormulario() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 10, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Cadastrar Nova Obra Musical"));

        panel.add(new JLabel("Título (Obrigatório):"));
        txtTitulo = new JTextField();
        panel.add(txtTitulo);

        panel.add(new JLabel("Álbum (Opcional):"));
        txtAlbum = new JTextField();
        panel.add(txtAlbum);

        panel.add(new JLabel("Artista / Banda (Obrigatório):"));
        txtArtista = new JTextField();
        panel.add(txtArtista);

        panel.add(new JLabel("Ano Lançamento (Opcional):"));
        txtAnoLancamento = new JTextField();
        panel.add(txtAnoLancamento);

        panel.add(new JLabel("Gênero Musical:"));
        cbGenero = new JComboBox<>();
        cbGenero.addItem(null);
        for (GeneroMusical genero : GeneroMusical.values()) {
            cbGenero.addItem(genero);
        }
        cbGenero.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "" : value.toString());
                return this;
            }
        });
        cbGenero.setSelectedIndex(0);
        panel.add(cbGenero);

        panel.add(new JLabel("Link Externo (Opcional):"));
        txtLinkExterno = new JTextField();
        panel.add(txtLinkExterno);

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        JButton btnCadastrar = new JButton("Cadastrar Música");
        btnCadastrar.addActionListener(e -> cadastrarMusica());
        panel.add(btnCadastrar);

        return panel;
    }

    private JPanel criarPainelListagem() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Catálogo de Músicas"));

        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.add(new JLabel("Filtrar por Gênero:"));

        cbFiltroGenero = new JComboBox<>();
        cbFiltroGenero.addItem("TODOS");
        for (GeneroMusical genero : GeneroMusical.values()) {
            cbFiltroGenero.addItem(genero.name());
        }
        cbFiltroGenero.addActionListener(e -> atualizarListagem());
        painelFiltro.add(cbFiltroGenero);

        areaListagem = new JTextArea();
        areaListagem.setEditable(false);
        areaListagem.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JButton btnAtualizar = new JButton("Atualizar Catálogo");
        btnAtualizar.addActionListener(e -> atualizarListagem());

        panel.add(painelFiltro, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaListagem), BorderLayout.CENTER);
        panel.add(btnAtualizar, BorderLayout.SOUTH);

        return panel;
    }

    private void cadastrarMusica() {
        try {
            String titulo = txtTitulo.getText().trim();
            String artista = txtArtista.getText().trim();
            GeneroMusical genero = (GeneroMusical) cbGenero.getSelectedItem();

            String album = txtAlbum.getText().trim();
            String anoTexto = txtAnoLancamento.getText().trim();
            String link = txtLinkExterno.getText().trim();

            Integer anoLancamento = null;
            if (!anoTexto.isEmpty()) {
                anoLancamento = Integer.parseInt(anoTexto);
            }

            Musica novaMusica = musicaService.cadastrarMusica(titulo, artista, genero);
            if (!album.isEmpty()) {
                novaMusica.setAlbum(album);
            }
            if (anoLancamento != null) {
                novaMusica.setAnoLancamento(anoLancamento);
            }
            if (!link.isEmpty()) {
                novaMusica.setLinkExterno(link);
            }

            JOptionPane.showMessageDialog(this,
                    "Música cadastrada com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            atualizarListagem();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "O ano de lançamento deve ser um número inteiro válido (ex: 1971).",
                    "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (RegraNegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Regra de Negócio", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtArtista.setText("");
        txtAlbum.setText("");
        txtAnoLancamento.setText("");
        txtLinkExterno.setText("");
        cbGenero.setSelectedIndex(0);
    }

    /**
     * Atualiza a área de texto com as músicas cadastradas, aplicando o filtro de gênero selecionado.
     */
    public void atualizarListagem() {
        String filtroSelecionado = (String) cbFiltroGenero.getSelectedItem();
        List<Musica> lista;

        if (filtroSelecionado == null || "TODOS".equals(filtroSelecionado)) {
            lista = musicaService.listarTodas();
        } else {
            lista = musicaService.buscarPorGenero(GeneroMusical.valueOf(filtroSelecionado));
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== MÚSICAS (%s) ===\n", filtroSelecionado));
        for (Musica m : lista) {
            String albumStr = (m.getAlbum() == null || m.getAlbum().isEmpty()) ? "-" : m.getAlbum();
            String anoStr = (m.getAnoLancamento() == null) ? "-" : m.getAnoLancamento().toString();
            String linkStr = (m.getLinkExterno() == null || m.getLinkExterno().isEmpty()) ? "-" : m.getLinkExterno();

            sb.append(String.format("Título: %s | Artista: %s | Gênero: %s | Álbum: %s | Ano: %s | Link: %s\n",
                    m.getTitulo(), m.getArtista(), m.getGenero(), albumStr, anoStr, linkStr));
        }

        areaListagem.setText(sb.toString());
    }
}