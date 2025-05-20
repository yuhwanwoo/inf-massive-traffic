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

```sql
select * from article where board_id order by created_at desc limit 30 offset 90;
create index idx_board_article_id on article(board_id asc, article_id desc);
select * from article where board_id = 1 order by article_id desc limit 30 offset 90;
select * from article where board_id = 1 order by article_id desc limit 30 offset 1499970;
/*
 인덱스를 알아야해
 
 MySQL의 기본 스토리지 엔진 innoDB
 
 */

select board_id, article_id from article where board_id = 1 order by article_id desc limit 30 offset 1499970;
select * from ( select article_id from article where board_id = 1 order by article_id desc limit 30 offset 1499970) t left join article on t.article_id = article.article_id;

/*
 페이지 공식
 (((n-1) / k) + 1) * m * k + 1
 */
 
 select count(*) from (select article_id from article where board_id = 1 limit 300301) t;
```

```sql
/*
무한 스크롤 예시
*/
select * from article where board_id = 1 order by article_id desc limit 30;
select * from article where  board_id = 1 and article_id < 12345678 order by article_id desc limit 30;

```