package models;

import java.time.LocalDate;

/**
 * Representa uma comunidade temática dentro da plataforma Afina.
 * Utiliza o conceito de constructor overloading para suporte a campos opcionais.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class Comunidade {

    private static int contadorId = 1;

    /** Propriedade do tipo INT - Identificador único da comunidade. */
    private int idComunidade;

    /** Propriedade do tipo String - Nome da comunidade. */
    private String nomeComunidade;

    /** Propriedade do tipo String - Proposta da comunidade (opcional). */
    private String descricao;

    /** Propriedade do tipo INT - Quantidade de participantes da comunidade. */
    private int totalMembros;

    /** Propriedade do tipo LocalDate - Data de criação da comunidade. */
    private LocalDate dataCriacao;

    /**
     * Construtor simplificado da classe Comunidade (Apenas campos obrigatórios).
     * O ID é gerado automaticamente, a descrição é inicializada como nula,
     * o total de membros inicia em 0 e a data de criação é a data atual.
     *
     * @param nomeComunidade Nome da comunidade
     */
    public Comunidade(String nomeComunidade) {
        this.idComunidade = contadorId++;
        this.nomeComunidade = nomeComunidade;
        this.descricao = null;
        this.totalMembros = 0;
        this.dataCriacao = LocalDate.now();
    }

    /**
     * Construtor completo da classe Comunidade (Campos obrigatórios e opcionais).
     *
     * @param nomeComunidade Nome da comunidade
     * @param descricao Proposta ou descrição da comunidade
     */
    public Comunidade(String nomeComunidade, String descricao) {
        this.idComunidade = contadorId++;
        this.nomeComunidade = nomeComunidade;
        this.descricao = descricao;
        this.totalMembros = 0;
        this.dataCriacao = LocalDate.now();
    }

    // Getters
    public int getIdComunidade() {
        return idComunidade;
    }

    public String getNomeComunidade() {
        return nomeComunidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getTotalMembros() {
        return totalMembros;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    // Setters
    public void setNomeComunidade(String nomeComunidade) {
        this.nomeComunidade = nomeComunidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTotalMembros(int totalMembros) {
        this.totalMembros = totalMembros;
    }
}