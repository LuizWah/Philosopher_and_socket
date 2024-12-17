Cada mensagem enviada pelo cliente para o servidor segue o formato: id COMMAND.

Mensagens do cliente:
    1 LOGIN: NomeDoFilosofo
    2 THINK
    3 REQUEST_FORKS
    4 RELEASE_FORKS

Comandos aceitos: 
    LOGIN: Realiza o login de um filósofo no servidor.
        Exemplo: 1 LOGIN: Socrates

    THINK: Indica que o filósofo está pensando.
        Exemplo: 2 THINK

    REQUEST_FORKS: Solicita acesso aos garfos.
        Exemplo: 3 REQUEST_FORKS

    RELEASE_FORKS: Libera os garfos após comer.
        Exemplo: 4 RELEASE_FORKS

    EAT: Indica que o filósofo está comendo.
        Exemplo: 5 EAT


O servidor responde no formato: id RESPONSE.
Mensagens do servidor:
    id Filósofo criado e conectado
    id PENSANDO
    id Pegou forks
    id Soltou os garfos
    id COMEU

Caso um comando seja inválido ou a operação não possa ser concluída, o servidor retorna:
    id Comando desconhecido
    id Não pegou forks