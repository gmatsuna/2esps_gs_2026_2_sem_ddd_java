package services;

import exceptions.RegraNegocioException;
import models.Evento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pelas regras de negócio e gerenciamento de eventos musicais.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class EventoService {

    /** Lista em memória para armazenar os eventos agendados. */
    private List<Evento> eventos = new ArrayList<>();

    /**
     * Referência ao serviço de comunidades para validar a comunidade organizadora do evento.
     * @see ComunidadeService
     */
    private ComunidadeService comunidadeService;

    /**
     * Construtor que recebe o ComunidadeService para validar a comunidade organizadora.
     *
     * @param comunidadeService Serviço de comunidades
     * @see ComunidadeService
     */
    public EventoService(ComunidadeService comunidadeService) {
        this.comunidadeService = comunidadeService;
    }

    /**
     * Agenda um novo evento musical vinculado a uma comunidade.
     *
     * @param idComunidade ID da comunidade organizadora
     * @param titulo Título do evento
     * @param descricao Descrição do evento
     * @param dataEvento Data de realização do evento
     * @param local Local do evento
     * @return O objeto Evento criado
     * @throws RegraNegocioException Caso a comunidade não exista, campos obrigatórios estejam vazios ou a data seja passada
     */
    public Evento agendarEvento(int idComunidade, String titulo, String descricao, LocalDate dataEvento, String local) throws RegraNegocioException {
        comunidadeService.buscarPorId(idComunidade);

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new RegraNegocioException("O título do evento é obrigatório.");
        }
        if (local == null || local.trim().isEmpty()) {
            throw new RegraNegocioException("O local do evento é obrigatório.");
        }
        if (dataEvento == null || dataEvento.isBefore(LocalDate.now())) {
            throw new RegraNegocioException("A data do evento não pode ser anterior à data atual.");
        }

        Evento novoEvento = new Evento(idComunidade, titulo, descricao, dataEvento, local);
        eventos.add(novoEvento);
        return novoEvento;
    }

    /**
     * Lista todos os eventos organizados por uma determinada comunidade.
     *
     * @param idComunidade ID da comunidade
     * @return Lista de eventos da comunidade
     */
    public List<Evento> listarPorComunidade(int idComunidade) {
        List<Evento> resultado = new ArrayList<>();
        for (Evento evento : eventos) {
            if (evento.getIdComunidade() == idComunidade) {
                resultado.add(evento);
            }
        }
        return resultado;
    }

    /**
     * Retorna a lista de todos os eventos agendados.
     *
     * @return Lista de eventos
     */
    public List<Evento> listarTodos() {
        return eventos;
    }
}