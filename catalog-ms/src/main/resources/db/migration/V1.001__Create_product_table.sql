create sequence product_seq start with 1 increment by 50;
create table product (product_price integer, id bigint not null, product_name varchar(255), primary key (id));

