package services;

import exceptions.RegraNegocioException;
import models.Conexao;
import models.StatusConexao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pelas regras de negócio e gerenciamento de conexões entre perfis na plataforma Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class ConexaoService {

    /** Lista em memória para armazenar as conexões registradas. */
    private List<Conexao> conexoes = new ArrayList<>();

    /** Referência ao serviço de perfis para validar a existência dos envolvidos. */
    private PerfilService perfilService;

    /**
     * Construtor que recebe o PerfilService para validar os perfis da conexão.
     *
     * @param perfilService Serviço de perfis
     */
    public ConexaoService(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    /**
     * Envia uma nova solicitação de conexão entre dois perfis.
     *
     * @param idPerfilSolicitante ID do perfil que envia o pedido
     * @param idPerfilDestinatario ID do perfil que recebe o pedido
     * @return O objeto Conexao criado com status PENDENTE
     * @throws RegraNegocioException Caso algum perfil não exista ou sejam o mesmo perfil
     */
    public Conexao solicitarConexao(int idPerfilSolicitante, int idPerfilDestinatario) throws RegraNegocioException {
        if (idPerfilSolicitante == idPerfilDestinatario) {
            throw new RegraNegocioException("Não é possível enviar uma solicitação de conexão para si mesmo.");
        }

        // Valida se ambos os perfis existem no sistema
        perfilService.buscarPorId(idPerfilSolicitante);
        perfilService.buscarPorId(idPerfilDestinatario);

        Conexao novaConexao = new Conexao(idPerfilSolicitante, idPerfilDestinatario);
        conexoes.add(novaConexao);
        return novaConexao;
    }

    /**
     * Busca uma conexão pelo seu identificador único.
     *
     * @param idConexao ID da conexão desejada
     * @return O objeto Conexao encontrado
     * @throws RegraNegocioException Caso a conexão não seja encontrada
     */
    public Conexao buscarPorId(int idConexao) throws RegraNegocioException {
        for (Conexao conexao : conexoes) {
            if (conexao.getIdConexao() == idConexao) {
                return conexao;
            }
        }
        throw new RegraNegocioException("Conexão não encontrada com o ID: " + idConexao);
    }

    /**
     * Responde a uma solicitação de conexão pendente (ACEITA ou RECUSADA).
     *
     * @param idConexao ID da conexão
     * @param novoStatus Novo status (ACEITA ou RECUSADA)
     * @throws RegraNegocioException Caso a conexão não exista ou já tenha sido respondida
     */
    public void responderConexao(int idConexao, StatusConexao novoStatus) throws RegraNegocioException {
        Conexao conexao = buscarPorId(idConexao);

        if (conexao.getStatus() != StatusConexao.PENDENTE) {
            throw new RegraNegocioException("Esta solicitação de conexão já foi respondida anteriormente.");
        }

        conexao.setStatus(novoStatus);
        conexao.setDataResposta(LocalDate.now());
    }

    /**
     * Verifica se dois perfis possuem uma conexão mútua aceita, liberando mensagens privadas.
     *
     * @param idPerfil1 ID do primeiro perfil
     * @param idPerfil2 ID do segundo perfil
     * @return true se houver conexão ACEITA entre eles, false caso contrário
     */
    public boolean podeEnviarMensagem(int idPerfil1, int idPerfil2) {
        for (Conexao c : conexoes) {
            boolean mesmosPerfis = (c.getIdPerfilSolicitante() == idPerfil1 && c.getIdPerfilDestinatario() == idPerfil2)
                    || (c.getIdPerfilSolicitante() == idPerfil2 && c.getIdPerfilDestinatario() == idPerfil1);

            if (mesmosPerfis && c.getStatus() == StatusConexao.ACEITA) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna a lista de todas as conexões registradas.
     *
     * @return Lista de conexões
     */
    public List<Conexao> listarTodas() {
        return conexoes;
    }
}