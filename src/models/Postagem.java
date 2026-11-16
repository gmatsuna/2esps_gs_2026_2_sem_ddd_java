package models;

import java.time.LocalDate;

/**
 * Representa uma postagem realizada por um perfil dentro da plataforma Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class Postagem {

    private static int contadorId = 1;

    /** Propriedade do tipo INT - Identificador único da postagem. */
    private int idPostagem;

    /** Propriedade do tipo INT - Identificador do perfil autor da postagem. */
    private int idPerfil;

    /** Propriedade do tipo String - Conteúdo de texto da postagem. */
    private String conteudo;

    /** Propriedade do tipo LocalDate - Data em que a postagem foi realizada. */
    private LocalDate dataPostagem;

    /**
     * Construtor da classe Postagem.
     * O ID e a data da postagem são gerados automaticamente pelo sistema.
     *
     * @param idPerfil ID do perfil autor
     * @param conteudo Texto ou mensagem da postagem
     */
    public Postagem(int idPerfil, String conteudo) {
        this.idPostagem = contadorId++;
        this.idPerfil = idPerfil;
        this.conteudo = conteudo;
        this.dataPostagem = LocalDate.now();
    }

    // Getters
    public int getIdPostagem() {
        return idPostagem;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDate getDataPostagem() {
        return dataPostagem;
    }

    // Setters
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}