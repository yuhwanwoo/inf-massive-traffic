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

```sql
/*
 댓글 기능 개발
 */
 create table comment (
     comment_id bigint not null primary key,
     content varchar(3000) not null ,
     article_id bigint not null ,
     parent_comment_id bigint not null ,
     writer_id bigint not null ,
     deleted bool not null ,
     created_at datetime not null 
 );

create index idx_article_id_parent_comment_id_comment_id on comment(
        article_id asc,
        parent_comment_id asc,
        comment_id asc 
    );

/**
  N번 페이지에서 M개의 댓글 조회
 */
 select * from (
     select comment_id from comment
                       where article_id = {article_id}
                       order by parent_comment_id asc, comment_id asc 
                       limit {limit} offset {offset}
               ) t left join comment on t.comment_id = comment.comment_id;

/**
  댓글 개수 조회
 */
select count(*)
from (select comment_id from comment where article_id = {article_id} limit {limit}) t;



/*
 댓글 테이블 설계 - 무한 depth
 */

select table_name, table_collation from information_schema.Tables where table_schema = 'comment';

create table comment_v2 (
     comment_id bigint not null primary key,
     content varchar(3000) not null ,
     article_id bigint not null ,
     writer_id bigint not null ,
    path varchar(25) character set utf8mb4 collate utf8mb4_bin not null,
     deleted bool not null ,
     created_at datetime not null
);

create unique index idx_article_id_path on comment_v2(
        article_id asc, path asc
    );

select table_name, column_name, collation_name from information_schema.COLUMNS where table_schema = 'comment' and table_name = 'comment_v2';;

explain select path from comment_v2 where article_id = 1 and path > '00a0z' 
 and path like '00a0z%'
 order by path desc limit 1;
```