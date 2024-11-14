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

## Questionário Inicial (Caso seja cliente, opcional):
Algumas perguntas rápidas sobre experiência e objetivos para ajudar a adaptar o conteúdo e as recomendações.

## Trilha de Introdução Rápida (Caso seja prestadora)
A usuária acessa uma seção chamada primeiro impulso?, com conteúdos rápidos e essenciais, por exemplo:
- Como se comunicar com seus clientes, Auto-Divulgação e Precificação de Serviços
- Tarefa Prática: Ao final de cada vídeo, ela faria uma especie de quiz sobre, e no final, registraria o seu serviço utilizando o que foi visto na trilha numa especie de "Sobre Mim" 
(Vamos usar essa tarefa da trilha como o nosso default pra definir a avaliação inicial pra quem nao tem nenhum serviço prestado)

## Criação de Perfil: 
- Perfil Básico: A usuária monta um perfil com as informações obtidas nas atividades da trilha.
- Portfólio Inicial: Caso houver, ela pode adicionar trabalhos anteriores ou feedbacks de clientes que ela teve antes do +Delas, por exemplo.

## Agendamento e contratação
- Agendamento Simplificado: Assim que um cliente faz um match, ele entra em contato com a prestadora para definir o melhor horario para ambos.
- Troca de Mensagens Padrão: Uma vez que o agendamento é confirmado, o sistema envia uma mensagem automática com detalhes e permite uma troca rápida de mensagens para ajustes.

## Avaliação e Feedback
- Após a prestação do serviço, o cliente tem a opção de avaliar a usuária, adicionando uma nota ou comentário curto.
- Perfil Atualizado: A avaliação aparece no perfil da usuária, ajudando a construir sua reputação e atrair novos clientes.

## Rentabilidade - Plano de Assinatura Único
- A plataforma oferece um plano único e opcional, que a usuária pode aderir para ter benefícios como menor taxa nas transações, alem de ter a visibilidade destacada no match. (prestadora) ou alguns cupons de desconto para a contratação de serviços futuros (cliente).
- sistema em que um prestador de serviços pode completar até 3 atendimentos no site sem pagar nenhuma taxa. A partir do 4º serviço, o site passa a cobrar uma porcentagem sobre os ganhos do prestador.

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
