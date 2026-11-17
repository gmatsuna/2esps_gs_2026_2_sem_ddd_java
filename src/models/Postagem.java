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

    /** Propriedade do tipo INT - Identificador da música compartilhada na postagem. */
    private int idMusica;

    /** Propriedade do tipo String - Conteúdo de texto da postagem. */
    private String conteudo;

    /** Propriedade do tipo String - Endereço de uma imagem anexada (opcional). */
    private String imagem;

    /** Propriedade do tipo LocalDate - Data em que a postagem foi realizada. */
    private LocalDate dataPostagem;

    /**
     * Construtor simplificado da classe Postagem (Sem imagem anexada).
     * O ID e a data da postagem são gerados automaticamente pelo sistema.
     *
     * @param idPerfil ID do perfil autor
     * @param idMusica ID da música compartilhada
     * @param conteudo Texto ou mensagem da postagem
     */
    public Postagem(int idPerfil, int idMusica, String conteudo) {
        this.idPostagem = contadorId++;
        this.idPerfil = idPerfil;
        this.idMusica = idMusica;
        this.conteudo = conteudo;
        this.imagem = null;
        this.dataPostagem = LocalDate.now();
    }

    /**
     * Construtor completo da classe Postagem (Com imagem anexada).
     * O ID e a data da postagem são gerados automaticamente pelo sistema.
     *
     * @param idPerfil ID do perfil autor
     * @param idMusica ID da música compartilhada
     * @param conteudo Texto ou mensagem da postagem
     * @param imagem Endereço da imagem anexada
     */
    public Postagem(int idPerfil, int idMusica, String conteudo, String imagem) {
        this.idPostagem = contadorId++;
        this.idPerfil = idPerfil;
        this.idMusica = idMusica;
        this.conteudo = conteudo;
        this.imagem = imagem;
        this.dataPostagem = LocalDate.now();
    }

    // Getters
    public int getIdPostagem() {
        return idPostagem;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public int getIdMusica() {
        return idMusica;
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getImagem() {
        return imagem;
    }

    public LocalDate getDataPostagem() {
        return dataPostagem;
    }

    // Setters
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}