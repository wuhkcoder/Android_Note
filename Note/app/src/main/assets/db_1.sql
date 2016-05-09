--以--开头的行为注释行,语句间以go隔开(注意：这不是标准的sql格式，如果--写在一行中间将会被当做sql，--必须独立成行,go也必须独立成行)
--please pay attention to above words！！！UTF-8 coding
--每次升级请不要在原来的db_*.sql中修改，而是新创建一个文件，然后把数据库版本号+1

--联系人缓存表
create table diary(
    id char(32) not null,
    createTime varchar(32) ,
    weekDay varchar (32),
    weather varchar (32),
    content TEXT,
    pic TEXT,
    encrypt smallint default 1,
    password varchar(32),
    modifyTime time,
    primary key (id)
)
go

--备忘缓存表
create table todo(
    id char(32) not null,
    createTime varchar(32) ,
    title TEXT,
    content TEXT,
    primary key (id)
)
go