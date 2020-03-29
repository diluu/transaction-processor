create table client (
id bigserial not null primary key,
client_type varchar(4),
client_number varchar(4));

create table client_account (
id bigserial not null primary key,
client_id integer references client(id),
account_number varchar(4),
sub_account_number varchar(4));

create table product (
id bigserial not null primary key,
exchange_code varchar(4),
product_group_code varchar(2),
symbol varchar(6),
expiration_date date);

create table transaction (
id bigserial not null primary key,
transacton_date date,
client_id integer references client(id),
client_account_id integer references client_account(id),
product_id integer references product(id),
amount integer);