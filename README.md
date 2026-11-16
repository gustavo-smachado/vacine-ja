# Vacine-Já

Aplicação web Java EE (`br.com.esucri:vacine-ja`) empacotada em `.war` com Maven, desenvolvida para o projeto Vacine-Já.

## Tecnologias utilizadas

- Java 8
- Jakarta EE / Java EE 8 (`javaee-api`)
- Apache Maven

## Como compilar

1. Certifique-se de ter o **JDK 8+** e o **Maven** instalados.
2. Execute o build do projeto:
   ```bash
   mvn clean package
   ```
3. O arquivo `.war` será gerado em `target/vacine-ja-1.0.0.war`, pronto para deploy em um servidor de aplicação compatível com Java EE 8 (ex.: GlassFish, Payara, WildFly ou TomEE).
