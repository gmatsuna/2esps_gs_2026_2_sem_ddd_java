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
import  services.*;

import java.time.LocalDate;
import java.util.List;

import exceptions.RegraNegocioException;

/**
 * Classe principal para validação incremental e execução dos testes do projeto Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 */

public class Main {
    public static void main(String[] args) {

        System.out.println("\n**********************************************************");
        System.out.println("******************** Iniciando Testes ********************");
        System.out.println("**********************************************************\n");

        System.out.println("===========================================================");
        System.out.println("=== Teste: Enums");
        System.out.println("===========================================================\n");

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
        System.out.println("\n===========================================================");
        System.out.println("=== Teste : Entidade Usuario");
        System.out.println("===========================================================");

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

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: Entidade Perfil");
        System.out.println("===========================================================");

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

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: Entidade Musica");
        System.out.println("===========================================================");

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

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: Entidade Conexao");
        System.out.println("===========================================================\n");

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

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: Entidade Postagem");
        System.out.println("===========================================================\n");

        // 1. Testando o Construtor Simples (sem imagem)
        Postagem postagem = new Postagem(
                perfilCompleto.getIdPerfil(),
                musicaCompleta.getIdMusica(),
                "Acabei de escutar o álbum Led Zeppelin IV, obra-prima do rock clássico!"
        );

        // 2. Testando o Construtor Completo (com imagem)
        Postagem postagemComImagem = new Postagem(
                perfilSimples.getIdPerfil(),
                musicaSimples.getIdMusica(),
                "Clássico eterno da música nordestina!",
                "/imagens/posts/asa_branca.jpg"
        );

        System.out.println("--- Postagem 1 ---");
        System.out.println(String.format("ID Postagem: %d", postagem.getIdPostagem()));
        System.out.println(String.format("ID Perfil Autor: %d", postagem.getIdPerfil()));
        System.out.println(String.format("Conteúdo: %s", postagem.getConteudo()));
        System.out.println(String.format("Data da Postagem: %s", postagem.getDataPostagem()));

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: Entidade Comunidade");
        System.out.println("===========================================================\n");

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

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: Entidade Evento");
        System.out.println("===========================================================\n");

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

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: Exceção de Regra de Negócio");
        System.out.println("===========================================================\n");

        try {
            throw new RegraNegocioException("Exemplo de validação: Operação não permitida pelas regras do Afina.");
        } catch (RegraNegocioException e) {
            System.out.println(String.format("Exceção capturada com sucesso: %s", e.getMessage()));
        }

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: UsuarioService");
        System.out.println("===========================================================\n");

        UsuarioService usuarioService = new UsuarioService();

        // 1. Testando cadastro válido (maior de 18 anos)
        try {
            Usuario uValidado = usuarioService.cadastrarUsuario(LocalDate.of(1998, 8, 12), StatusConta.ATIVA);
            System.out.println(String.format("Usuário cadastrado com sucesso! ID: %d | Status: %s",
                    uValidado.getIdUsuario(), uValidado.getStatusConta()));

            // Testando alteração de status
            usuarioService.alterarStatusConta(uValidado.getIdUsuario(), StatusConta.SUSPENSA);
            System.out.println(String.format("Status atualizado do ID %d: %s",
                    uValidado.getIdUsuario(), uValidado.getStatusConta()));
        } catch (RegraNegocioException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        // 2. Testando bloqueio de menor de idade
        try {
            usuarioService.cadastrarUsuario(LocalDate.of(2012, 3, 25), StatusConta.ATIVA);
        } catch (RegraNegocioException e) {
            System.out.println(String.format("Bloqueio validado com sucesso: %s", e.getMessage()));
        }

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: PerfilService");
        System.out.println("===========================================================\n");

        PerfilService perfilService = new PerfilService(usuarioService);

        try {
            // Cadastrando um usuário válido no service para vincular ao perfil
            Usuario uPerfil = usuarioService.cadastrarUsuario(LocalDate.of(1995, 4, 10), StatusConta.ATIVA);

            // 1. Criando perfil com visibilidade SO_CONEXOES
            Perfil pCriado = perfilService.criarPerfil(
                    uPerfil.getIdUsuario(),
                    "Giovanna Zagaroli",
                    "São Paulo",
                    VisibilidadePerfil.SO_CONEXOES
            );
            System.out.println(String.format("Perfil criado com sucesso! ID: %d | Nome: %s | Visibilidade: %s",
                    pCriado.getIdPerfil(), pCriado.getNomeApelido(), pCriado.getVisibilidade()));

            // 2. Testando regra de privacidade (LGPD)
            System.out.println("Pode visualizar sem conexão? " + perfilService.podeVisualizarPerfil(pCriado.getIdPerfil(), false));
            System.out.println("Pode visualizar com conexão aceita? " + perfilService.podeVisualizarPerfil(pCriado.getIdPerfil(), true));

            // 3. Testando bloqueio de perfil duplicado para o mesmo usuário (1:1)
            perfilService.criarPerfil(uPerfil.getIdUsuario(), "Perfil Duplicado", "Santos", VisibilidadePerfil.PUBLICO);

        } catch (RegraNegocioException e) {
            System.out.println(String.format("Regra de negócio validada com sucesso: %s", e.getMessage()));
        }

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: ConexaoService");
        System.out.println("===========================================================\n");

        ConexaoService conexaoService = new ConexaoService(perfilService);

        try {
            // Criando um segundo usuário e perfil válido no service para testar a conexão
            Usuario uDiogo = usuarioService.cadastrarUsuario(LocalDate.of(1999, 7, 22), StatusConta.ATIVA);
            Perfil pDiogo = perfilService.criarPerfil(
                    uDiogo.getIdUsuario(),
                    "Diogo Henrique",
                    "São Paulo",
                    VisibilidadePerfil.PUBLICO
            );

            // Pegando o primeiro perfil cadastrado no perfilService (Giovanna)
            Perfil pGiovanna = perfilService.listarTodos().get(0);

            // 1. Solicitando conexão entre Giovanna e Diogo
            Conexao cSolicitada = conexaoService.solicitarConexao(pGiovanna.getIdPerfil(), pDiogo.getIdPerfil());
            System.out.println(String.format("Solicitação enviada! ID Conexão: %d | Status: %s",
                    cSolicitada.getIdConexao(), cSolicitada.getStatus()));

            // 2. Verificando mensagem antes de aceitar (deve ser false)
            System.out.println("Pode enviar mensagem antes do aceite? "
                    + conexaoService.podeEnviarMensagem(pGiovanna.getIdPerfil(), pDiogo.getIdPerfil()));

            // 3. Aceitando a conexão
            conexaoService.responderConexao(cSolicitada.getIdConexao(), StatusConexao.ACEITA);
            System.out.println(String.format("Conexão respondida! Status: %s | Data Resposta: %s",
                    cSolicitada.getStatus(), cSolicitada.getDataResposta()));

            // 4. Verificando mensagem após aceitar (deve ser true)
            System.out.println("Pode enviar mensagem após aceite? "
                    + conexaoService.podeEnviarMensagem(pGiovanna.getIdPerfil(), pDiogo.getIdPerfil()));

            // 5. Testando bloqueio de auto-conexão
            conexaoService.solicitarConexao(pDiogo.getIdPerfil(), pDiogo.getIdPerfil());

        } catch (RegraNegocioException e) {
            System.out.println(String.format("Regra de negócio validada com sucesso: %s", e.getMessage()));
        }

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: MusicaService");
        System.out.println("===========================================================\n");

        MusicaService musicaService = new MusicaService();

        try {
            // 1. Cadastrando músicas válidas
            Musica m1 = musicaService.cadastrarMusica("Construção", "Chico Buarque", GeneroMusical.MPB);
            Musica m2 = musicaService.cadastrarMusica("Tempo Perdido", "Legião Urbana", GeneroMusical.ROCK);
            Musica m3 = musicaService.cadastrarMusica("Aquarela do Brasil", "Ary Barroso", GeneroMusical.MPB);

            System.out.println(String.format("Músicas cadastradas: %d", musicaService.listarTodas().size()));

            // 2. Filtrando músicas por gênero (MPB)
            List<Musica> musicasMpb = musicaService.buscarPorGenero(GeneroMusical.MPB);
            System.out.println(String.format("Músicas encontradas no gênero MPB: %d", musicasMpb.size()));
            for (Musica m : musicasMpb) {
                System.out.println(String.format("-> %s - %s", m.getTitulo(), m.getArtista()));
            }

            // 3. Testando bloqueio de música sem título
            musicaService.cadastrarMusica("", "Artista Desconhecido", GeneroMusical.POP);

        } catch (RegraNegocioException e) {
            System.out.println(String.format("Regra de negócio validada com sucesso: %s", e.getMessage()));
        }

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: PostagemService");
        System.out.println("===========================================================\n");

        PostagemService postagemService = new PostagemService(perfilService, musicaService);

        try {
            Perfil pAutor = perfilService.listarTodos().get(0);
            Musica mCompartilhada = musicaService.listarTodas().get(0);

            // 1. Publicando postagem simples (sem imagem)
            Postagem post1 = postagemService.publicar(
                    pAutor.getIdPerfil(),
                    mCompartilhada.getIdMusica(),
                    "Essa música marcou demais a minha adolescência!"
            );
            System.out.println(String.format("Postagem 1 publicada! ID: %d | Autor ID: %d | Música ID: %d",
                    post1.getIdPostagem(), post1.getIdPerfil(), post1.getIdMusica()));

            // 2. Publicando postagem com imagem
            Postagem post2 = postagemService.publicar(
                    pAutor.getIdPerfil(),
                    mCompartilhada.getIdMusica(),
                    "Registro do vinil original!",
                    "/imagens/vinil.jpg"
            );
            System.out.println(String.format("Postagem 2 publicada! ID: %d | Imagem: %s",
                    post2.getIdPostagem(), post2.getImagem()));

            // 3. Listando postagens do perfil
            System.out.println(String.format("Total de postagens do perfil %s: %d",
                    pAutor.getNomeApelido(), postagemService.listarPorPerfil(pAutor.getIdPerfil()).size()));

            // 4. Testando bloqueio de postagem com conteúdo vazio
            postagemService.publicar(pAutor.getIdPerfil(), mCompartilhada.getIdMusica(), "");

        } catch (RegraNegocioException e) {
            System.out.println(String.format("Regra de negócio validada com sucesso: %s", e.getMessage()));
        }

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: ComunidadeService");
        System.out.println("===========================================================\n");

        ComunidadeService comunidadeService = new ComunidadeService(perfilService);

        try {
            // 1. Criando comunidades (simples e completa)
            Comunidade com1 = comunidadeService.criarComunidade("Fãs de MPB");
            Comunidade com2 = comunidadeService.criarComunidade(
                    "Clube do Vinil",
                    "Troca de experiências, dicas de conservação e recomendações de discos."
            );

            System.out.println(String.format("Comunidade 1 criada! ID: %d | Nome: %s | Membros: %d",
                    com1.getIdComunidade(), com1.getNomeComunidade(), com1.getTotalMembros()));
            System.out.println(String.format("Comunidade 2 criada! ID: %d | Nome: %s | Membros: %d",
                    com2.getIdComunidade(), com2.getNomeComunidade(), com2.getTotalMembros()));

            // 2. Adicionando membros na comunidade
            Perfil pMembro1 = perfilService.listarTodos().get(0);
            Perfil pMembro2 = perfilService.listarTodos().get(1);

            comunidadeService.entrarNaComunidade(com1.getIdComunidade(), pMembro1.getIdPerfil());
            comunidadeService.entrarNaComunidade(com1.getIdComunidade(), pMembro2.getIdPerfil());

            System.out.println(String.format("Total de membros atualizado em '%s': %d",
                    com1.getNomeComunidade(), com1.getTotalMembros()));

            // 3. Testando bloqueio de comunidade com nome duplicado
            comunidadeService.criarComunidade("Fãs de MPB");

        } catch (RegraNegocioException e) {
            System.out.println(String.format("Regra de negócio validada com sucesso: %s", e.getMessage()));
        }

        System.out.println("\n===========================================================");
        System.out.println("=== Teste: EventoService");
        System.out.println("===========================================================\n");

        EventoService eventoService = new EventoService(comunidadeService);

        try {
            Comunidade comOrganizadora = comunidadeService.listarTodas().get(0);

            // 1. Agendando evento válido
            Evento ev1 = eventoService.agendarEvento(
                    comOrganizadora.getIdComunidade(),
                    "Sarau MPB ao Vivo",
                    "Encontro presencial para tocar e cantar clássicos da MPB.",
                    LocalDate.of(2026, 12, 20),
                    "Centro Cultural São Paulo"
            );

            System.out.println(String.format("Evento agendado! ID: %d | Título: %s | Data: %s | Local: %s",
                    ev1.getIdEvento(), ev1.getTitulo(), ev1.getDataEvento(), ev1.getLocal()));

            System.out.println(String.format("Total de eventos da comunidade '%s': %d\n",
                    comOrganizadora.getNomeComunidade(),
                    eventoService.listarPorComunidade(comOrganizadora.getIdComunidade()).size()));

            // 2. Testando bloqueio de evento com data no passado
            eventoService.agendarEvento(
                    comOrganizadora.getIdComunidade(),
                    "Evento Antigo",
                    "Teste de validação de data.",
                    LocalDate.of(2020, 1, 15),
                    "Praça da Sé"
            );

        } catch (RegraNegocioException e) {
            System.out.println(String.format("Regra de negócio validada com sucesso: %s", e.getMessage()));
        }

        System.out.println("\n**********************************************************");
        System.out.println("************** Teste Concluído com Sucesso! **************");
        System.out.println("**********************************************************");
    }
}