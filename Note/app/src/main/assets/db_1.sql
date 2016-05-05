--以--开头的行为注释行,语句间以go隔开(注意：这不是标准的sql格式，如果--写在一行中间将会被当做sql，--必须独立成行,go也必须独立成行)
--please pay attention to above words！！！UTF-8 coding
--每次升级请不要在原来的db_*.sql中修改，而是新创建一个文件，然后把数据库版本号+1

--联系人缓存表
create table diary(
    owner_user_id char(32) not null,
    user_id char(32) not null,
    name varchar(32) ,
    avatar varchar (50),
    primary key (user_id, owner_user_id)
)
go