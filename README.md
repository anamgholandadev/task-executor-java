## Como rodar o programa

1. Tenha uma versão do JDK instalado no seu computador

2. Abra um terminal na pasta raíz do projeto

3. Execute o comando abaixo para compilar o programa:\
   `javac -d bin -cp src src/TaskExecutor.java`

4. Execute o comando abaixo para executar o programa, passando um arquivo de entrada como argumento\
   `java -Xms8g -cp bin TaskExecutor <caminho para arquivo de entrada>`

O arquivo de entrada estar formatado da seguinte forma, contendo os valores de N, T e E separados por vírgula:

`<valor de N>,<valor de T>,<valor de E>`
