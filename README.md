Desenvolvedores: Ryan Pereira Lima e Matheus Gomes

Este é o repositório principal do projeto Interpretador Typescript em Java, que consiste em vários módulos interdependentes: lexer, parser, interpreter e runner. Cada módulo tem sua própria responsabilidade no contexto geral do projeto.

Estrutura do Projeto

:lexer: Módulo responsável pela análise léxica.
:parser: Módulo responsável pela análise sintática, depende do módulo :lexer.
:interpreter: Módulo responsável pela interpretação do código, depende do módulo :parser.
:runner: Módulo que integra todos os módulos anteriores para executar o código final.
Configuração do Ambiente

Certifique-se de ter o Java JDK instalado em sua máquina. O projeto utiliza o sistema de construção Gradle para gerenciar as dependências e as tarefas de construção.

Dependências

As principais dependências do projeto são configuradas no arquivo build.gradle do projeto raiz. Certifique-se de verificar e atualizar as versões conforme necessário

Para Executar(Entre em main), altere o caminho do program.txt, botao direito no arquivo program.txt e seecione copy Path. 
Após colocar o caminho do copy Path dentro das "", execute o código
