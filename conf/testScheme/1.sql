# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admins (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  created                   timestamp,
  constraint pk_admins primary key (id))
;

create table check (
  id                        bigint not null,
  name                      varchar(255),
  result                    varchar(255),
  created                   timestamp,
  modified                  timestamp,
  constraint pk_check primary key (id))
;

create sequence admins_seq;

create sequence check_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists admins;

drop table if exists check;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists admins_seq;

drop sequence if exists check_seq;

