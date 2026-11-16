/*
 * ============================================================================
 * PROJETO: AFINA - Rede social para amantes da música (Global Solution - 2ESPS)
 * ============================================================================
 * INTEGRANTES DO GRUPO:
 * - Bruno Ventura - RM 568316
 * - Diogo Henrique - RM 568541
 * - Gilberto Hideaki Matsunaga - RM 568191
 * - Giovanna P Zagaroli - RM 567572
 * - Vinicius Nathan - RM 567105
 * ============================================================================
 */

import  models.*;

import java.time.LocalDate;

/**
 * Classe principal para validação incremental e execução dos testes do projeto Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 */

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Teste Imediato: Enums (StatusConta e VisibilidadePerfil) ===\n");

        System.out.println("--- Status da Conta ---");
        for (StatusConta status : StatusConta.values()) {
            System.out.println(String.format("-> %s", status));
        }
        System.out.println("\n--- Visibilidade do Perfil ---");
        for (VisibilidadePerfil visibilidade : VisibilidadePerfil.values()) {
            System.out.println(String.format("-> %s", visibilidade));
        }
        System.out.println("\n--- Status da Conexão ---");
        for (StatusConexao status : StatusConexao.values()) {
            System.out.println(String.format("-> %s", status));
        }
        System.out.println("\n--- Gêneros Musicais ---");
        for (GeneroMusical genero : GeneroMusical.values()) {
            System.out.println(String.format("-> %s", genero));
        }

        //Validação incremental e execução dos testes da classe Usuario.
        System.out.println("\n=== Teste : Usuario - IDs Auto-incrementados ===");

        // Instanciando usuários sem passar ID nem data de cadastro
        Usuario usuario1 = new Usuario(LocalDate.parse("2000-05-15"), StatusConta.ATIVA);
        Usuario usuario2 = new Usuario(LocalDate.parse("1995-10-20"), StatusConta.SUSPENSA);

        System.out.println("\n--- Usuário 1 ---");
        System.out.println(String.format("ID do Usuário: %s", usuario1.getIdUsuario()));
        System.out.println(String.format("Data de Nascimento: %s", usuario1.getDataNascimento()));
        System.out.println(String.format("Data de Cadastro: %s", usuario1.getDataCadastro()));
        System.out.println(String.format("Status da Conta: %s", usuario1.getStatusConta()));

        System.out.println("\n--- Usuário 2 ---");
        System.out.println(String.format("ID do Usuário: %s", usuario2.getIdUsuario()));
        System.out.println(String.format("Data de Nascimento: %s", usuario2.getDataNascimento()));
        System.out.println(String.format("Data de Cadastro: %s", usuario2.getDataCadastro()));
        System.out.println(String.format("Status da Conta: %s", usuario2.getStatusConta()));

        System.out.println("\n=== Teste: Perfil ===");

        // Testando o 1º Construtor (Apenas dados obrigatórios)
        Perfil perfilSimples = new Perfil(
                usuario1.getIdUsuario(),
                "Gilberto Matsunaga",
                "São Paulo",
                VisibilidadePerfil.PUBLICO
        );

        // Testando o 2º Construtor (Dados completos, incluindo os opcionais)
        Perfil perfilCompleto = new Perfil(
                usuario2.getIdUsuario(),
                "Bruno Ventura",
                "Baterista e produtor musical",
                "Rio de Janeiro",
                VisibilidadePerfil.SO_CONEXOES,
                "/imagens/bruno.jpg"
        );

        System.out.println("\n--- Perfil 1 (Criado com Construtor Simples) ---");
        System.out.println(String.format("ID Perfil: %s", perfilSimples.getIdPerfil()));
        System.out.println(String.format("ID Usuário Vinculado: %s", perfilSimples.getIdUsuario()));
        System.out.println(String.format("Nome / Apelido: %s", perfilSimples.getNomeApelido()));
        System.out.println(String.format("BIO: %s", perfilSimples.getBio()));
        System.out.println(String.format("Cidade: %s", perfilSimples.getCidade()));
        System.out.println(String.format("Visibilidade: %s", perfilSimples.getVisibilidade()));
        System.out.println(String.format("Foto de Perfil: %s", perfilSimples.getFotoPerfil()));

        System.out.println("\n--- Perfil 2 (Criado com Construtor Completo) ---");
        System.out.println(String.format("ID Perfil: %s", perfilCompleto.getIdPerfil()));
        System.out.println(String.format("ID Usuário Vinculado: %s", perfilCompleto.getIdUsuario()));
        System.out.println(String.format("Nome / Apelido: %s", perfilCompleto.getNomeApelido()));
        System.out.println(String.format("BIO: %s", perfilCompleto.getBio()));
        System.out.println(String.format("Cidade: %s", perfilCompleto.getCidade()));
        System.out.println(String.format("Visibilidade: %s", perfilCompleto.getVisibilidade()));
        System.out.println(String.format("Foto de Perfil: %s", perfilCompleto.getFotoPerfil()));

        System.out.println("\n=== Teste: Entidade Musica ===");

        // 1. Testando o Construtor Simples de Musica
        Musica musicaSimples = new Musica(
                "Asa Branca",
                "Luiz Gonzaga",
                GeneroMusical.MPB
        );

        // 2. Testando o Construtor Completo de Musica
        Musica musicaCompleta = new Musica(
                "Stairway to Heaven",
                "Led Zeppelin",
                GeneroMusical.ROCK,
                "Led Zeppelin IV",
                1971,
                "https://open.spotify.com/track/..."
        );

        System.out.println("\n--- Música 1 (Construtor Simples) ---");
        System.out.println(String.format("ID Música: %s", musicaSimples.getIdMusica()));
        System.out.println(String.format("Título: %s", musicaSimples.getTitulo()));
        System.out.println(String.format("Artista: %s", musicaSimples.getArtista()));
        System.out.println(String.format("Gênero: %s", musicaSimples.getGenero()));
        System.out.println(String.format("Álbum: %s", musicaSimples.getAlbum()));
        System.out.println(String.format("Ano de lançamento: %s", musicaSimples.getAnoLancamento()));
        System.out.println(String.format("Link: %s", musicaSimples.getLinkExterno()));

        System.out.println("\n--- Música 2 (Construtor Completo) ---");
        System.out.println(String.format("ID Música: %s", musicaCompleta.getIdMusica()));
        System.out.println(String.format("Título: %s", musicaCompleta.getTitulo()));
        System.out.println(String.format("Artista: %s", musicaCompleta.getArtista()));
        System.out.println(String.format("Gênero: %s", musicaCompleta.getGenero()));
        System.out.println(String.format("Álbum: %s", musicaCompleta.getAlbum()));
        System.out.println(String.format("Ano de lançamento: %s", musicaCompleta.getAnoLancamento()));
        System.out.println(String.format("Link: %s", musicaCompleta.getLinkExterno()));

        System.out.println("\n=== Teste: Entidade Conexao ===\n");

        // 1. Testando o Construtor Simples de Conexao (Pendente)
        Conexao conexaoPendente = new Conexao(1, 2);

        // 2. Testando o Construtor Completo de Conexao (Aceita)
        Conexao conexaoAceita = new Conexao(
                2,
                1,
                StatusConexao.ACEITA,
                LocalDate.now()
        );

        System.out.println("--- Conexão 1 (Construtor Simples - Pendente) ---");
        System.out.println(String.format("ID Conexão: %d", conexaoPendente.getIdConexao()));
        System.out.println(String.format("Perfil Solicitante: %d", conexaoPendente.getIdPerfilSolicitante()));
        System.out.println(String.format("Perfil Destinatário: %d", conexaoPendente.getIdPerfilDestinatario()));
        System.out.println(String.format("Status: %s", conexaoPendente.getStatus()));
        System.out.println(String.format("Data de Solicitação: %s", conexaoPendente.getDataSolicitacao()));
        System.out.println(String.format("Data de Resposta (Esperado null): %s", conexaoPendente.getDataResposta()));

        System.out.println(String.format("\n--- Conexão 2 (Construtor Completo - Aceita) ---"));
        System.out.println(String.format("ID Conexão: %d", conexaoAceita.getIdConexao()));
        System.out.println(String.format("Perfil Solicitante: %d", conexaoAceita.getIdPerfilSolicitante()));
        System.out.println(String.format("Perfil Destinatário: %d", conexaoAceita.getIdPerfilDestinatario()));
        System.out.println(String.format("Status: %s", conexaoAceita.getStatus()));
        System.out.println(String.format("Data de Solicitação: %s", conexaoAceita.getDataSolicitacao()));
        System.out.println(String.format("Data de Resposta: %s", conexaoAceita.getDataResposta()));

        System.out.println("=== Teste: Entidade Postagem ===\n");

        // Criando uma nova postagem
        Postagem postagem = new Postagem(
                perfilCompleto.getIdPerfil(),
                "Acabei de escutar o álbum Led Zeppelin IV, obra-prima do rock clássico!"
        );

        System.out.println("--- Postagem 1 ---");
        System.out.println(String.format("ID Postagem: %d", postagem.getIdPostagem()));
        System.out.println(String.format("ID Perfil Autor: %d", postagem.getIdPerfil()));
        System.out.println(String.format("Conteúdo: %s", postagem.getConteudo()));
        System.out.println(String.format("Data da Postagem: %s", postagem.getDataPostagem()));

        System.out.println("\n=== Teste: Comunidade ===");

        // Testando construtor simplificado e completo
        Comunidade comunidade1 = new Comunidade("Rock Clássico Brasil");
        Comunidade comunidade2 = new Comunidade("Jazz e Blues", "Espaço dedicado aos amantes de improvisação e grandes clássicos do jazz.");

        System.out.println("\n--- Comunidade 1 (Construtor Simples) ---");
        System.out.println(String.format("ID Comunidade: %s", comunidade1.getIdComunidade()));
        System.out.println(String.format("Nome: %s", comunidade1.getNomeComunidade()));
        System.out.println(String.format("Descrição: %s", comunidade1.getDescricao()));
        System.out.println(String.format("Total de Membros: %s", comunidade1.getTotalMembros()));
        System.out.println(String.format("Data de Criação: %s", comunidade1.getDataCriacao()));

        System.out.println("\n--- Comunidade 2 (Construtor Completo) ---");
        System.out.println(String.format("ID Comunidade: %s", comunidade2.getIdComunidade()));
        System.out.println(String.format("Nome: %s", comunidade2.getNomeComunidade()));
        System.out.println(String.format("Descrição: %s", comunidade2.getDescricao()));
        System.out.println(String.format("Total de Membros: %s", comunidade2.getTotalMembros()));
        System.out.println(String.format("Data de Criação: %s", comunidade2.getDataCriacao()));

        System.out.println("\n=== Teste: Entidade Evento ===");

        // Criando um evento associado à comunidade 1 criada anteriormente
        Evento evento1 = new Evento(
                comunidade1.getIdComunidade(),
                "Encontro de Rock Clássico",
                "Roda de conversa e audição comentada de álbuns históricos dos anos 70.",
                LocalDate.of(2026, 12, 10),
                "Auditório Central - São Paulo"
        );

        System.out.println("\n--- Evento 1 ---");
        System.out.println(String.format("ID Evento: %s", evento1.getIdEvento()));
        System.out.println(String.format("ID Comunidade Organizadora: %s", evento1.getIdComunidade()));
        System.out.println(String.format("Título: %s", evento1.getTitulo()));
        System.out.println(String.format("Descrição: %s", evento1.getDescricao()));
        System.out.println(String.format("Data do Evento: %s", evento1.getDataEvento()));
        System.out.println(String.format("Local: %s", evento1.getLocal()));


        System.out.println("\n=== Teste Concluído com Sucesso! ===");
    }
}