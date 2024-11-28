# Projeto_Start_+delas

## Descrição Geral do Sistema - +delas

## Problemática
Atualmente, as mulheres enfrentam grandes desafios no mercado de trabalho, pela falta de oportunidades, desigualdade de gênero ou falta de visibilidade. Muitas mulheres têm dificuldade em acessar vagas de emprego, crescer profissionalmente ou se manter em uma escala de trabalho que não é mais compatível quando se tem filhos. Diante deste cenário, a +delas surge como uma resposta para combater esses problemas, criando um espaço que facilita a autonomia financeira e pessoal das mulheres, oferecendo-lhes oportunidades para se destacarem em suas áreas de atuação.

## Solução
A plataforma +delas será um site que conecta mulheres prestadoras de serviços autônomos a pessoas que desejam contratar esses serviços. As mulheres terão a liberdade de escolher o dia e a hora em que desejam trabalhar, desde que haja uma negociação feita com o contratante que deseja o serviço da mesma. O sistema permitirá que elas definam suas áreas de atuação, horários disponíveis e condições de trabalho, promovendo a autonomia e flexibilidade. Além disso, a +delas tem como visão atrair mulheres desempregadas, aumentando suas oportunidades de geração de renda e contribuindo para o crescimento da economia local.


## Com a nossa plataforma conseguiremos aumentar o Desenvolvimento Econômico e Pessoal:
Oferecendo oportunidades para que as mulheres possam ter uma autonomia financeira, um ambiente voltado para a educação e gerenciamento de renda e expandir suas habilidades e conexões profissionais.

Empoderamento Pessoal:
Promover a confiança e a autoestima das mulheres, incentivando-as a alcançar seus objetivos.

Autonomia: Proporcionar às mulheres a capacidade de gerenciar seu próprio tempo e trabalho, aumentando sua independência financeira de acordo com sua rotina e limitações.

## Cadastro
A usuária se cadastra com informações básicas (nome, e-mail, área de atuação) e cria uma senha.

## Jornada do Empreemdedor (Caso seja prestadora) (Opcional)
A usuária acessa uma seção chamada Jornada do Empreendedor, com conteúdos rápidos e essenciais, por exemplo:
- Como se comunicar com seus clientes, Auto-Divulgação e Precificação de Serviços
- Tarefa Prática: Ao final de cada vídeo, ela faria uma especie de quiz sobre, e no final, registraria o seu serviço utilizando o que foi visto na trilha numa especie de "Sobre Mim" 
(Vamos usar essa tarefa da trilha como o nosso default pra definir a avaliação inicial pra quem nao tem nenhum serviço prestado)

## Criação de Perfil: 
- Perfil Básico: A usuária monta um perfil com as informações obtidas nas atividades da trilha.

## Agendamento e contratação
- Agendamento Simplificado: Assim que um cliente encontra o serviço desejado e prestadora, ele entra em contato com a mesma para definir o melhor horario para ambos.

## Avaliação e Feedback
- Após a prestação do serviço, o cliente tem a opção de avaliar a usuária, adicionando uma nota ou comentário curto.
- Perfil Atualizado: A avaliação aparece no perfil da usuária, ajudando a construir sua reputação e atrair novos clientes.

- ## Fluxos de Dados
- Os diagramas abaixo representam os principais fluxos operacionais da plataforma +delas, detalhando tanto a experiência dos usuários (clientes e prestadoras de serviços) quanto as conexões internas entre as entidades do banco de dados, desde o cadastro ate a finalização do serviço contratado.

![Fluxo de dados de cadastro ](https://github.com/babil0nia/maisDelas/blob/master/+Delas%20(3).jpg?raw=true)

- ## Fluxos de Dados da Contratação do serviço.
  
 ![Fluxo de dados de cadastro ](https://github.com/babil0nia/maisDelas/blob/master/+Delas%20(3).jpg?raw=true)

  
## Banco de dados
```mermaid
erDiagram
    usuarios {
        int id PK
        string nome
        string email
        string senha
        string telefone
        enum tipo
        string rua
        string bairro
        int cep
        string cpf
        timestamp datacriacao
    }
    
    favorito {
        int idfavorito PK
        int idclientefavoritou
        int idprestadorfavorito
        datetime datafavoritamento
    }
    
    servicos {
        int idservicos PK
        text descricao
        decimal preco
        string titulo
        timestamp datacriacao
        string categoria
        int idfavorito
    }
    
    contratacao {
        int idcontratacao PK
        int usuarios_id
        int idservicos
        string status
        timestamp datacontratacao
        text comentarios
    }
    
    avaliacao {
        int idavaliacao PK
        int idcontratacao
        int nota
    }

    usuarios ||--o{ favorito : "idclientefavoritou"
    usuarios ||--o{ favorito : "idprestadorfavorito"
    favorito ||--o{ servicos : "idfavorito"
    usuarios ||--o{ contratacao : "usuarios_id"
    servicos ||--o{ contratacao : "idservicos"
    contratacao ||--o{ avaliacao : "idcontratacao"
