create table product_categories (products_id bigint not null, categories_category_name varchar(255) not null);
alter table if exists product_categories add constraint FK_products_id foreign key (categories_category_name) references categories;
alter table if exists product_categories add constraint FK_categories_name foreign key (products_id) references product;

