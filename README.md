Detalhes do Projeto Doutorado
===============

O projeto é um aplicativo desktop desenvolvido com as tecnologias JavaFX, JPA (Java Persistence API) e Hibernate. 

A aplicação utiliza o HSQLDB (HyperSQL DataBase), um banco de dados relacional escrito em Java, somente nessa fase inicial, posteriormente é trivial a troca do banco de dados.

Detalhes da implementação
-------
Tecnologias utilizadas na implementação:

* JavaFX: plataforma rich client, criada para que os desenvolvedores que já conhecem a tecnologia Java possam criar interfaces gráficas sofisticadas, em aplicativos Desktop, Web e obile;
* JPA: API alto nível, padrão da tecnologia Java, para definir o mapeamento objeto relacional (ORM);
* Hibernate: provedor JPA, responsável por resolver ORM;
* Bean Validation: mecanismo padrão do Java para determinar regras de validação através de anotações;

Pré-requisitos
-------
* JDK - versão 1.7 do Java, ou mais recente;
* NetBeans - NetBeans IDE mais recente;

Detalhes do repositório
-------
[Dúvidas com git neste link]-> (http://gitimmersion.com/lab_01.html) 

Este repositório seguirá as seguintes regras para commit's:

Branch: master terá somente versões estáveis. (ainda não possui, será TAG 1.0)
Branch: develop está branch será a utilizada para desenvolvimento, após ter uma feature pronta é realizado o merge(pull request) com a branch principal 'master'
Demais branch's devem ser criadas para algum bug fix e novos desenvolvimentos

Sugiro seguir essa metodologia, irá nos garantir um total controle sob o repositório, versões etc.
[Modelo de utilização do repositório](http://nvie.com/posts/a-successful-git-branching-model/)
