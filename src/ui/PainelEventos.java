package ui;

import exceptions.RegraNegocioException;
import models.Comunidade;
import models.Evento;
import services.ComunidadeService;
import services.EventoService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Painel gráfico responsável pelo agendamento, filtro e listagem de Eventos musicais no Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class PainelEventos extends JPanel {

    private final EventoService eventoService;
    private final ComunidadeService comunidadeService;

    private JComboBox<String> cbComunidade;
    private JTextField txtTitulo;
    private JTextField txtLocal;
    private JTextField txtDataEvento;
    private JTextField txtDescricao;

    private JComboBox<String> cbFiltroComunidade;
    private JTextArea areaListagem;

    /**
     * Construtor que recebe os serviços de Evento e Comunidade e monta os componentes visuais.
     *
     * @param eventoService Serviço de gerenciamento de eventos
     * @param comunidadeService Serviço de gerenciamento de comunidades
     * @see EventoService
     * @see ComunidadeService
     */
    public PainelEventos(EventoService eventoService, ComunidadeService comunidadeService) {
        this.eventoService = eventoService;
        this.comunidadeService = comunidadeService;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelAgenda(), BorderLayout.CENTER);
    }

    private JPanel criarPainelFormulario() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 10, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Agendar Novo Evento Musical"));

        panel.add(new JLabel("Comunidade Organizadora:"));
        cbComunidade = new JComboBox<>();
        panel.add(cbComunidade);

        panel.add(new JLabel("Data (AAAA-MM-DD):"));
        txtDataEvento = new JTextField();
        panel.add(txtDataEvento);

        panel.add(new JLabel("Título (Obrigatório):"));
        txtTitulo = new JTextField();
        panel.add(txtTitulo);

        panel.add(new JLabel("Local (Obrigatório):"));
        txtLocal = new JTextField();
        panel.add(txtLocal);

        panel.add(new JLabel("Descrição (Opcional):"));
        txtDescricao = new JTextField();
        panel.add(txtDescricao);

        panel.add(new JLabel(""));
        JButton btnAgendar = new JButton("Agendar Evento");
        btnAgendar.addActionListener(e -> agendarEvento());
        panel.add(btnAgendar);

        return panel;
    }

    private JPanel criarPainelAgenda() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Agenda de Eventos"));

        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.add(new JLabel("Filtrar por Comunidade:"));

        cbFiltroComunidade = new JComboBox<>();
        cbFiltroComunidade.addItem("TODAS");
        cbFiltroComunidade.addActionListener(e -> atualizarListagem());
        painelFiltro.add(cbFiltroComunidade);

        areaListagem = new JTextArea();
        areaListagem.setEditable(false);
        areaListagem.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JButton btnAtualizar = new JButton("Atualizar Agenda");
        btnAtualizar.addActionListener(e -> atualizarDados());

        panel.add(painelFiltro, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaListagem), BorderLayout.CENTER);
        panel.add(btnAtualizar, BorderLayout.SOUTH);

        return panel;
    }

    private void agendarEvento() {
        try {
            List<Comunidade> comunidades = comunidadeService.listarTodas();
            int idxComunidade = cbComunidade.getSelectedIndex();

            if (idxComunidade < 0 || comunidades.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Crie pelo menos uma comunidade antes de agendar um evento.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String dataTexto = txtDataEvento.getText().trim();
            if (dataTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Informe a data do evento no formato AAAA-MM-DD (ex: 2026-12-20).",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Comunidade organizadora = comunidades.get(idxComunidade);
            String titulo = txtTitulo.getText().trim();
            String local = txtLocal.getText().trim();
            String descricao = txtDescricao.getText().trim();
            LocalDate dataEvento = LocalDate.parse(dataTexto);

            eventoService.agendarEvento(
                    organizadora.getIdComunidade(),
                    titulo,
                    descricao,
                    dataEvento,
                    local
            );

            JOptionPane.showMessageDialog(this,
                    "Evento agendado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            atualizarListagem();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Formato de data inválido. Utilize AAAA-MM-DD (ex: 2026-12-20).",
                    "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (RegraNegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Regra de Negócio", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtLocal.setText("");
        txtDataEvento.setText("");
        txtDescricao.setText("");
    }

    private String obterNomeComunidade(int idComunidade) {
        try {
            return comunidadeService.buscarPorId(idComunidade).getNomeComunidade();
        } catch (RegraNegocioException e) {
            return "Comunidade Desconhecida";
        }
    }

    /**
     * Recarrega os combos de comunidades cadastradas e atualiza a agenda de eventos.
     */
    public void atualizarDados() {
        List<Comunidade> comunidades = comunidadeService.listarTodas();
        cbComunidade.removeAllItems();
        for (Comunidade c : comunidades) {
            cbComunidade.addItem(c.getNomeComunidade());
        }

        int filtroIndexAnterior = cbFiltroComunidade.getSelectedIndex();
        cbFiltroComunidade.removeAllItems();
        cbFiltroComunidade.addItem("TODAS");
        for (Comunidade c : comunidades) {
            cbFiltroComunidade.addItem(c.getNomeComunidade());
        }
        if (filtroIndexAnterior >= 0 && filtroIndexAnterior < cbFiltroComunidade.getItemCount()) {
            cbFiltroComunidade.setSelectedIndex(filtroIndexAnterior);
        }

        atualizarListagem();
    }

    /**
     * Atualiza a área de texto com os eventos agendados, aplicando o filtro de comunidade selecionado.
     */
    public void atualizarListagem() {
        int idxFiltro = cbFiltroComunidade.getSelectedIndex();
        List<Comunidade> comunidades = comunidadeService.listarTodas();
        List<Evento> lista;

        if (idxFiltro <= 0 || (idxFiltro - 1) >= comunidades.size()) {
            lista = eventoService.listarTodos();
        } else {
            Comunidade comunidadeFiltrada = comunidades.get(idxFiltro - 1);
            lista = eventoService.listarPorComunidade(comunidadeFiltrada.getIdComunidade());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== EVENTOS AGENDADOS ===\n");
        for (Evento ev : lista) {
            String nomeCom = obterNomeComunidade(ev.getIdComunidade());
            String descStr = (ev.getDescricao() == null || ev.getDescricao().isEmpty()) ? "Sem descrição" : ev.getDescricao();

            sb.append(String.format("[%s] %s — Local: %s (Org: %s)\n   Detalhes: %s\n\n",
                    ev.getDataEvento(), ev.getTitulo(), ev.getLocal(), nomeCom, descStr));
        }

        areaListagem.setText(sb.toString());
    }
}