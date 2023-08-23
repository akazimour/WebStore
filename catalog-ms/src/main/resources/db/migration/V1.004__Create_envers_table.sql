create sequence revinfo_seq start with 1 increment by 50;
create table categories_aud (rev integer not null, revtype smallint, category_name varchar(255) not null, consumer_segment varchar(255), primary key (rev, category_name));
create table product_aud (product_price integer, rev integer not null, revtype smallint, id bigint not null, product_name varchar(255), primary key (rev, id));
create table product_categories_aud (rev integer not null, revtype smallint, products_id bigint not null, categories_category_name varchar(255) not null, primary key (rev, products_id, categories_category_name));
create table revinfo (rev integer not null, revtstmp bigint, primary key (rev));
alter table if exists categories_aud add constraint FK_categories_aud_rev foreign key (rev) references revinfo;
alter table if exists product_aud add constraint FK_product_aud_rev foreign key (rev) references revinfo;
alter table if exists product_categories_aud add constraint FK_product_categories_aud_rev foreign key (rev) references revinfo;
