create table addresses (address_id  bigserial not null, city varchar(255) not null, house_number varchar(255) not null, postal_code varchar(5) not null, state varchar(255) not null, street varchar(255) not null, primary key (address_id));
create table beverages (beverage_id  bigserial not null, alcohol_percent float8, beverage_pic varchar(255), beverage_price float8, name varchar(255) not null, primary key (beverage_id));
create table bottles (bottle_id  bigserial not null, bottle_in_stock int4 check (bottle_in_stock>=0), supplier varchar(255) not null, volume float8, beverage_id int8, primary key (bottle_id));
create table crates (crate_id  bigserial not null, crate_in_stock int4 check (crate_in_stock>=0), no_of_bottles int4 check (no_of_bottles>=1), bottle_id int8, crate_beverage_id int8, primary key (crate_id));
create table order_items (order_item_id  bigserial not null, order_item_price float8, position int4, quantity int4, order_id int8, beverage_id int8, primary key (order_item_id));
create table orders (order_id  bigserial not null, order_price float8, user_id int8, primary key (order_id));
create table orders_order_items (order_order_id int8 not null, order_items_order_item_id int8 not null, primary key (order_order_id, order_items_order_item_id));
create table users (user_id  bigserial not null, email varchar(255) not null, full_name varchar(255) not null, password varchar(255) not null, role varchar(255), username varchar(255) not null, address_id int8, primary key (user_id));
create table users_orders (user_user_id int8 not null, orders_order_id int8 not null, primary key (user_user_id, orders_order_id));

alter table orders_order_items add constraint UK_70a4sa284yptqe6d1xxson8kn unique (order_items_order_item_id);
alter table users_orders add constraint UK_s3tkawlgthscommdfn5skfrlk unique (orders_order_id);
alter table bottles add constraint FKlr7dikgog6ipwb50fjeb8aoo5 foreign key (beverage_id) references beverages;
alter table crates add constraint FK2s7n5j8f1l2gu1df8kwu7ix4c foreign key (bottle_id) references bottles;
alter table crates add constraint FKh2jca1wlrnwgpwru3hlaex2ju foreign key (crate_beverage_id) references beverages;
alter table order_items add constraint FKbioxgbv59vetrxe0ejfubep1w foreign key (order_id) references orders;
alter table order_items add constraint FK6e1vyi8y9wsk5m5rafsdnhnnw foreign key (beverage_id) references beverages;
alter table orders add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users;
alter table orders_order_items add constraint FKidgsa6ubift7s91b98hwknbli foreign key (order_items_order_item_id) references order_items;
alter table orders_order_items add constraint FK4a5vis32u4bexdg4xyjjc7o4j foreign key (order_order_id) references orders;
alter table users add constraint FKe8vydtk7hf0y16bfm558sywbb foreign key (address_id) references addresses;
alter table users_orders add constraint FKj2lckusflbhjqocgs6m3ve6j8 foreign key (orders_order_id) references orders;
alter table users_orders add constraint FKmou0xj4gi5lfcgqiy2s3pcl4o foreign key (user_user_id) references users;