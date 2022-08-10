use projeto_integrador;

INSERT into warehouse values
(null, 'Rua Almeida 259', 'Armazem A'),
(null, 'Rua Botanica 468', 'Armazem B'),
(null, 'Rua Contagem 1024', 'Armazem C');

insert into agent values
(null, 'Arnaldo@email.com', 'Arnaldo Pereira', '1111-1111', 1),
(null, 'Rodrigo@email.com', 'Rodrigo da Silva', '2222-2222', 2),
(null, 'Patricia@email.com', 'Patricia Ferreira', '3333-333', 3);

insert into section values
(null, 10, 100, 'Fresco', 1),
(null, 20, 200, 'Refrigerado', 1),
(null, 30, 300, 'Congelado', 1),
(null, 80, 800, 'Fresco', 2),
(null, 20, 200, 'Refrigerado', 2),
(null, 15, 150, 'Congelado', 2),
(null, 60, 600, 'Congelado', 3);

insert into product values
(null, 'Maçã', 20.10, 'Fresco'),
(null, 'Iogurte', 20.10, 'Refrigerado'),
(null, 'Peito de Frango', 20.10, 'Congelado'),
(null, 'Uva', 20.10,'Fresco'),
(null, 'Manteiga', 20.10, 'Refrigerado'),
(null, 'Nuggets', 20.10, 'Refrigerado'),
(null, 'Morango', 20.10, 'Fresco'),
(null, 'Presunto', 20.10, 'Refrigerado'),
(null, 'Sorvete', 20.10, 'Congelado');

insert into customer values
(null, '111.111.111-11', 'Alberto@email.com', 'Alberto', '1111-1111'),
(null, '222.222.222-22', 'Bernado@email.com', 'Bernardo', '2222-2222'),
(null, '333.333.333-33', 'Carolina@email.com', 'Carolina', '3333-3333');
