`
docker run --name kuke-board-mysql -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql:8.0.38
docker exec -it kuke-board-mysql bash
mysql -u root -p
create database article;
`


```sql
create table article (
    article_id bigint not null primary key,
    title varchar(100) not null,
    content varchar(3000) not null,
    board_id bigint not null,
    writer_id bigint not null,
    created_at datetime not null,
    modified_at datetime not null
)
```