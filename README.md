# 🎵 Afina — Rede Social Musical

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
[![GUI](https://img.shields.io/badge/Interface-Java%20Swing-blue?style=for-the-badge&logo=java)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Architecture](https://img.shields.io/badge/Architecture-DDD%20Layered-green?style=for-the-badge)]()
[![FIAP](https://img.shields.io/badge/FIAP-Global%20Solution%202ESPS-ed145b?style=for-the-badge)]()

> **Global Solution — Engenharia de Software (FIAP / Turma 2ESPS)**  
> Plataforma desktop desenvolvida em Java que utiliza a afinidade musical como ponte para conexões humanas genuínas, comunidades temáticas, bem-estar emocional e encontros presenciais.

---

## 📑 Sumário

- [1. Visão Geral e Problema Solucionado](#-1-visão-geral-e-problema-solucionado)
- [2. Alinhamento com os ODSs da ONU](#-2-alinhamento-com-os-odss-da-onu)
- [3. Arquitetura e Estrutura do Projeto](#-3-arquitetura-e-estrutura-do-projeto)
- [4. Modelo de Domínio (Entidades e Enums)](#-4-modelo-de-domínio-entidades-e-enums)
- [5. Regras de Negócio e Segurança](#-5-regras-de-negócio-e-segurança)
- [6. Interface Gráfica (Java Swing)](#-6-interface-gráfica-java-swing)
- [7. Como Compilar e Executar](#-7-como-compilar-e-executar)
- [8. Roteiro de Uso Recomendado](#-8-roteiro-de-uso-recomendado)
- [9. Integrantes do Grupo](#-9-integrantes-do-grupo)

---

## 🎯 1. Visão Geral e Problema Solucionado

Apesar da hiperconectividade das redes sociais modernas, índices de isolamento social, solidão e superficialidade nas interações digitais continuam crescendo. Paralelamente, o consumo musical tornou-se majoritariamente individual e guiado por algoritmos passivos, reduzindo as oportunidades de troca cultural orgânica entre pessoas com gostos semelhantes.

O **Afina** resolve essa lacuna ao transformar a música em um **catalisador de vínculos humanos reais**. Na plataforma, os usuários:
* Criam sua identidade musical com controle granular de privacidade;
* Catalogam obras musicais por gênero e compartilham recomendações comentadas;
* Estabelecem conexões bilaterais baseadas em consentimento mútuo;
* Participam de comunidades temáticas e agendam eventos culturais presenciais.

---

## 🌍 2. Alinhamento com os ODSs da ONU

O projeto foi concebido em consonância com três **Objetivos de Desenvolvimento Sustentável (Agenda 2030 da ONU)**:

| ODS | Título | Contribuição Direta do Afina |
| :--- | :--- | :--- |
| **ODS 3** | **Saúde e Bem-Estar** | Combate ao isolamento digital e promoção da saúde emocional por meio da música, ferramenta reconhecida na redução do estresse e no fortalecimento de redes de apoio humano. |
| **ODS 10** | **Redução das Desigualdades** | Democratização do acesso à expressão cultural e integração inclusiva entre pessoas de diferentes origens sociais e geográficas por meio da linguagem universal da música. |
| **ODS 11** | **Cidades e Comunidades Sustentáveis** | Estímulo ao sentimento de pertencimento comunitário e incentivo à ocupação saudável de espaços públicos e culturais locais por meio de eventos presenciais. |

---

## 🏗️ 3. Arquitetura e Estrutura do Projeto

O sistema foi construído seguindo uma **arquitetura em camadas** inspirada nos princípios de **Domain-Driven Design (DDD)**, com baixo acoplamento, encapsulamento rigoroso, sobrecarga de construtores (*constructor overloading*) e documentação completa em **Javadoc**:

```text
afina-rede-social-musical/
├── docs/
│   └── Documentacao_Projeto_Afina.docx   # Documentação acadêmica formal da entrega
├── src/
│   ├── exceptions/
│   │   └── RegraNegocioException.java    # Exceção verificada para violações de regras do domínio
│   ├── models/
│   │   ├── Comunidade.java               # Entidade de grupos temáticos
│   │   ├── Conexao.java                  # Entidade de vínculo bilateral entre perfis
│   │   ├── Evento.java                   # Entidade de encontros presenciais vinculados a comunidades
│   │   ├── GeneroMusical.java            # Enumeração dos gêneros musicais suportados
│   │   ├── Musica.java                   # Entidade de obras musicais (construtores sobrecarregados)
│   │   ├── Perfil.java                   # Entidade de identidade pública e privacidade musical
│   │   ├── Postagem.java                 # Entidade de publicações com música e anexo opcional
│   │   ├── StatusConexao.java            # Enumeração: PENDENTE, ACEITA, RECUSADA
│   │   ├── StatusConta.java              # Enumeração: ATIVA, INATIVA, SUSPENSA
│   │   ├── Usuario.java                  # Entidade de credenciais e conformidade legal (LGPD)
│   │   └── VisibilidadePerfil.java       # Enumeração: PUBLICO, SOMENTE_CONEXOES, PRIVADO
│   ├── services/
│   │   ├── ComunidadeService.java        # Criação de comunidades (unicidade) e ingresso de membros
│   │   ├── ConexaoService.java           # Solicitações, respostas e liberação de chat por aceite
│   │   ├── EventoService.java            # Agendamento de eventos futuros e filtro por comunidade
│   │   ├── MusicaService.java            # Cadastro de obras e busca filtrada por gênero
│   │   ├── PerfilService.java            # Criação e validação de perfis musicais
│   │   ├── PostagemService.java          # Publicação no feed e filtragem por autor
│   │   └── UsuarioService.java           # Cadastro de contas, maioridade (18+) e aceite LGPD
│   ├── ui/
│   │   ├── PainelComunidades.java        # Aba 5: Criação e ingresso em comunidades
│   │   ├── PainelConexoes.java           # Aba 3: Envio e resposta de solicitações de conexão
│   │   ├── PainelEventos.java            # Aba 6: Agendamento e agenda de eventos musicais
│   │   ├── PainelMusicas.java            # Aba 2: Catálogo e filtro de obras musicais
│   │   ├── PainelPostagens.java          # Aba 4: Publicação e feed filtrável de postagens
│   │   ├── PainelUsuariosPerfis.java     # Aba 1: Gestão de contas e perfis musicais
│   │   └── TelaPrincipal.java            # Janela principal (JFrame + JTabbedPane + ChangeListener)
│   └── Main.java                         # Suíte automatizada de testes funcionais via console
└── README.md
```

### Fluxo de Dependência entre Camadas

```text
[ Camada UI (Swing) ] ──▶ [ Camada Services ] ──▶ [ Camada Models ]
                                  │
                                  ▼
                       [ RegraNegocioException ]
```

---

## 📦 4. Modelo de Domínio (Entidades e Enums)

### Entidades Principais
* **`Usuario`**: Armazena identificador único, nome completo, e-mail, senha, data de nascimento, aceite da LGPD, data de cadastro e `StatusConta`.
* **`Perfil`**: Vinculado a um `Usuario` (`1:1`), guarda nome/apelido artístico, biografia, cidade e nível de `VisibilidadePerfil`.
* **`Musica`**: Representa obras musicais com título, artista e `GeneroMusical` obrigatórios, além de álbum, ano de lançamento e link externo opcionais via *constructor overloading*.
* **`Conexao`**: Modela o relacionamento entre um perfil solicitante e um destinatário, registrando datas de solicitação/resposta e `StatusConexao`.
* **`Postagem`**: Relaciona um `Perfil` autor a uma `Musica` compartilhada, com texto obrigatório, anexo de imagem opcional e data de publicação.
* **`Comunidade`**: Representa grupos musicais temáticos com nome único, descrição opcional, data de criação e contador dinâmico de membros.
* **`Evento`**: Representa encontros presenciais organizados por uma `Comunidade`, contendo título, descrição, data futura e local.

### Enumerações (`enums`)
* **`GeneroMusical`**: `ROCK`, `POP`, `MPB`, `JAZZ`, `SAMBA`, `ELETRONICA`, `HIP_HOP`, `CLASSICA`, `SERTANEJO`, `INDIE`.
* **`VisibilidadePerfil`**: `PUBLICO`, `SOMENTE_CONEXOES`, `PRIVADO`.
* **`StatusConta`**: `ATIVA`, `INATIVA`, `SUSPENSA`.
* **`StatusConexao`**: `PENDENTE`, `ACEITA`, `RECUSADA`.

---

## 🛡️ 5. Regras de Negócio e Segurança

Todas as regras de negócio são centralizadas na camada `services` e protegidas pela exceção customizada **`RegraNegocioException`**:

1. **Conformidade Legal e Proteção de Dados (LGPD):**
   * Bloqueio de cadastro para usuários **menores de 18 anos** (cálculo dinâmico via `ChronoUnit.YEARS`).
   * Obrigatoriedade de **consentimento explícito da LGPD** (`aceiteLgpd == true`).
   * Validação de formato de e-mail e bloqueio de **e-mails duplicados**.
   * Restrição de **apenas um perfil musical por conta de usuário**.
2. **Consentimento Mútuo e Privacidade:**
   * Impedimento de auto-solicitação de conexão (`idSolicitante == idDestinatario`).
   * Transição única de status da solicitação (`PENDENTE` $\rightarrow$ `ACEITA` ou `RECUSADA`).
   * Liberação de mensagens diretas (`podeEnviarMensagem`) **exclusivamente quando há conexão aceita** entre os dois perfis.
3. **Integridade Referencial e Consistência:**
   * Verificação prévia da existência de usuários, perfis, músicas e comunidades antes de qualquer vínculo, postagem, ingresso ou agendamento.
   * Bloqueio de **comunidades com nomes duplicados** (*case-insensitive*).
   * Bloqueio de **eventos agendados em datas anteriores ao dia atual** (`LocalDate.now()`).

---

## 🖥️ 6. Interface Gráfica (Java Swing)

A interface desktop (`ui.TelaPrincipal`) utiliza um componente `JTabbedPane` com **seis abas integradas** e um `ChangeListener` que sincroniza automaticamente os seletores (`JComboBox`) e listagens sempre que o usuário alterna entre os módulos:

1. **Usuários & Perfis:** Cadastro de contas com validação de maioridade/LGPD e criação de perfis musicais com escolha de visibilidade.
2. **Músicas:** Cadastro de obras musicais (com gênero iniciando em branco para validação obrigatória) e catálogo com filtro dinâmico por `GeneroMusical`.
3. **Conexões:** Envio de pedidos de amizade entre perfis cadastrados, painel de aceite/recusa de solicitações pendentes e indicador de liberação de chat.
4. **Postagens:** Publicação de recomendações vinculadas a uma música cadastrada (com suporte a anexo de imagem opcional) e feed filtrável por autor.
5. **Comunidades:** Criação de grupos temáticos e registro de ingresso de perfis participantes com atualização do total de membros.
6. **Eventos:** Agendamento de encontros musicais presenciais vinculados a uma comunidade organizadora e agenda filtrável por comunidade.

---

## 🚀 7. Como Compilar e Executar

### Pré-requisitos
* **Java Development Kit (JDK) 17** ou superior instalado.
* Terminal (Bash, PowerShell, CMD) ou IDE Java (IntelliJ IDEA, Eclipse, VS Code).

### Passo 1: Clonar o Repositório
```bash
git clone https://github.com/SEU-USUARIO/afina-rede-social-musical.git
cd afina-rede-social-musical
```

### Passo 2: Compilar o Projeto
```bash
mkdir -p out
javac -encoding UTF-8 -d out src/exceptions/*.java src/models/*.java src/services/*.java src/ui/*.java src/Main.java
```

### Passo 3A: Executar via Interface Gráfica (Recomendado)
```bash
java -cp out ui.TelaPrincipal
```

### Passo 3B: Executar Suíte de Testes via Console
```bash
java -cp out Main
```

---

## 🔄 8. Roteiro de Uso Recomendado

Para explorar todos os recursos da interface gráfica (`TelaPrincipal`) sem esbarrar em bloqueios de integridade referencial, siga esta sequência:

1. Na aba **Usuários & Perfis**, cadastre **2 usuários maiores de 18 anos** (marcando o aceite da LGPD) e crie **1 perfil musical** para cada um.
2. Na aba **Músicas**, cadastre **2 ou mais músicas** de gêneros diferentes e teste o filtro do catálogo.
3. Na aba **Conexões**, envie uma solicitação entre os dois perfis e clique em **Aceitar** no painel ao lado para liberar o chat mútuo.
4. Na aba **Postagens**, publique uma recomendação musical associada a um perfil e uma música, testando o filtro por autor no feed.
5. Na aba **Comunidades**, crie uma comunidade temática e inscreva os perfis cadastrados.
6. Na aba **Eventos**, agende um encontro presencial com data futura (`AAAA-MM-DD`) vinculado à comunidade criada.

---

## 👥 9. Integrantes do Grupo

**Curso:** Bacharelado em Engenharia de Software — **FIAP (Turma 2ESPS)**  
**Disciplina:** Domain-Driven Design & Java — **Global Solution (Novembro/2026)**

| Integrante | Registro de Matrícula (RM) |
| :--- | :--- |
| **Bruno Ventura** | RM 568316 |
| **Diogo Henrique** | RM 568541 |
| **Gilberto Hideaki Matsunaga** | RM 568191 |
| **Giovanna P Zagaroli** | RM 567572 |
| **Vinicius Nathan** | RM 567105 |
