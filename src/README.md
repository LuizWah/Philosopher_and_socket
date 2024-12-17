Como executar:
Em ResourceServer ajeite o numero de garfos, filosofos e porta;
Execute o ResourceServer;
Em terminais diferentes, execute o PhilosopherClient uma vez para cada filosofo diferente que deseja criar;
Em cada terminal digite o nome de identificação daquele filosofo e precione enter;

Para simular a desconeÇão de um filosofo feche seu terminal;
Para simular esse mesmo filosofo reconectando, abra um terminal e insira o nome do filosofo que foi desconectado;
Caso esse filosofo tenha se desconectado e nao tenha voltado voce podera entrar como ele;



PhilosopherClient: Representa um filósofo que interage com o servidor enviando comandos e processando respostas.
philosopherDados: Gerencia os dados de cada filósofo, como número de vezes que pensou ou comeu e o estado atual.
Mordomo: Gerencia o acesso aos garfos para evitar deadlocks, utilizando locks para sincronização.
PhilosopherHandler: Processa as requisições individuais de cada cliente e coordena as respostas apropriadas.
ResourceServer: Gerencia a execução geral do servidor, inicializando os recursos compartilhados e aceitando conexões de clientes.