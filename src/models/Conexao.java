package models;

import java.time.LocalDate;

/**
 * Representa o vínculo ou pedido de conexão entre dois perfis na plataforma Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */

public class Conexao {

    private static int contadorId = 1;

    /** Propriedade do tipo INT - Identificador único da conexão. */
    private int idConexao;

    /** Propriedade do tipo INT - Identificador do perfil que enviou o pedido de conexão. */
    private int idPerfilSolicitante;

    /** Propriedade do tipo INT - Identificador do perfil que recebeu o pedido de conexão. */
    private int idPerfilDestinatario;

    /** Propriedade do tipo StatusConexao - Situação atual da conexão (Pendente, Aceita, Recusada).
     * @see StatusConexao
     */
    private StatusConexao status;

    /** Propriedade do tipo LocalDate - Data de envio do pedido de conexão. */
    private LocalDate dataSolicitacao;

    /** Propriedade do tipo LocalDate - Data em que o pedido foi aceito ou recusado (opcional). */
    private LocalDate dataResposta;

    /**
     * Construtor simplificado da classe Conexao (Utilizado ao enviar um novo pedido).
     * O ID é gerado automaticamente, o status inicia como PENDENTE, a data de solicitação
     * é a data atual, e a data de resposta é inicializada como nula.
     *
     * @param idPerfilSolicitante ID do perfil que está enviando o pedido
     * @param idPerfilDestinatario ID do perfil que está recebendo o pedido
     */
    public Conexao(int idPerfilSolicitante, int idPerfilDestinatario) {
        this.idConexao = contadorId++;
        this.idPerfilSolicitante = idPerfilSolicitante;
        this.idPerfilDestinatario = idPerfilDestinatario;
        this.status = StatusConexao.PENDENTE;
        this.dataSolicitacao = LocalDate.now();
        this.dataResposta = null;
    }

    /**
     * Construtor completo da classe Conexao (Utilizado para instanciar conexões já respondidas).
     *
     * @param idPerfilSolicitante ID do perfil que enviou o pedido
     * @param idPerfilDestinatario ID do perfil que recebeu o pedido
     * @param status Situação da conexão
     * @param dataResposta Data em que o pedido foi respondido
     */
    public Conexao(int idPerfilSolicitante, int idPerfilDestinatario, StatusConexao status, LocalDate dataResposta) {
        this.idConexao = contadorId++;
        this.idPerfilSolicitante = idPerfilSolicitante;
        this.idPerfilDestinatario = idPerfilDestinatario;
        this.status = status;
        this.dataSolicitacao = LocalDate.now();
        this.dataResposta = dataResposta;
    }

    // Getters
    public int getIdConexao() {
        return idConexao;
    }

    public int getIdPerfilSolicitante() {
        return idPerfilSolicitante;
    }

    public int getIdPerfilDestinatario() {
        return idPerfilDestinatario;
    }

    public StatusConexao getStatus() {
        return status;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public LocalDate getDataResposta() {
        return dataResposta;
    }

    // Setters
    public void setStatus(StatusConexao status) {
        this.status = status;
    }

    public void setDataResposta(LocalDate dataResposta) {
        this.dataResposta = dataResposta;
    }

    @Override
    public String toString() {
        return "Conexao{" +
                "idConexao=" + idConexao +
                ", idPerfilSolicitante=" + idPerfilSolicitante +
                ", idPerfilDestinatario=" + idPerfilDestinatario +
                ", status=" + status +
                ", dataSolicitacao=" + dataSolicitacao +
                ", dataResposta=" + dataResposta +
                '}';
    }
}
