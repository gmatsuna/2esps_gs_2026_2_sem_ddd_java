package models;

import java.time.LocalDate;

/**
 * Representa um encontro ou evento organizado por uma comunidade na plataforma Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class Evento {

    private static int contadorId = 1;

    /** Propriedade do tipo INT - Identificador único do evento. */
    private int idEvento;

    /** Propriedade do tipo INT - Identificador da comunidade organizadora (chave estrangeira). */
    private int idComunidade;

    /** Propriedade do tipo String - Nome ou título do evento. */
    private String titulo;

    /** Propriedade do tipo String - Detalhes sobre o encontro. */
    private String descricao;

    /** Propriedade do tipo LocalDate - Data em que o evento acontece. */
    private LocalDate dataEvento;

    /** Propriedade do tipo String - Local público ou parceiro onde o encontro será realizado. */
    private String local;

    /**
     * Construtor da classe Evento.
     * O ID do evento é gerado automaticamente pelo sistema.
     *
     * @param idComunidade ID da comunidade organizadora
     * @param titulo Título do evento
     * @param descricao Detalhes sobre o encontro
     * @param dataEvento Data de realização do evento
     * @param local Local do evento
     */
    public Evento(int idComunidade, String titulo, String descricao, LocalDate dataEvento, String local) {
        this.idEvento = contadorId++;
        this.idComunidade = idComunidade;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataEvento = dataEvento;
        this.local = local;
    }

    // Getters
    public int getIdEvento() {
        return idEvento;
    }

    public int getIdComunidade() {
        return idComunidade;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public String getLocal() {
        return local;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}