alter table livros add column usuario_id bigint not null;
alter table livros add foreign key(usuario_id) references usuarios(id);