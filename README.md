# WP_CadastroFiel
O projeto WP_CadastroFiel é um sistema para cadastro e gestão de fiéis, desenvolvido em Java com JPA, utilizando arquitetura em camadas (Service, Business, DAO e Entity).
A entidade principal Fiel armazena CPF, nome, email, idade e telefone, com suporte a operações completas de CRUD, pesquisa por CPF e listagem com filtro por nome.
O sistema utiliza BusinessException personalizada, transações manuais via JNDI e um DAO seguro com proteção contra injeção.
