--테이블 삭제
drop table uploadfile;
drop table bbs;
drop table member;
drop table notice;
drop table code;
drop table product;

--시퀀스삭제
drop sequence member_member_id_seq;
drop sequence notice_notice_id_seq;
drop sequence bbs_bbs_id_seq;
drop sequence uploadfile_uploadfile_id_seq;
drop sequence product_pid_seq;

--상품관리
create table product(
    pid         number(10),
    pname       varchar(30),
    quantity    number(10),
    price       number(10)
);
--기본키
alter table product add constraint product_pid_pk primary key(pid);

--시퀀스생성
create sequence product_pid_seq;

--생성--
insert into product(pid,pname,quantity,price)
     values(product_pid_seq.nextval, '컴퓨터', 5, 1000000);

insert into product(pid,pname,quantity,price)
     values(product_pid_seq.nextval, '모니터', 5, 500000);

insert into product(pid,pname,quantity,price)
     values(product_pid_seq.nextval, '프린터', 1, 300000);

--조회--
select pid, pname, quantity, price
  from product
 where pid = 2;

--수정--
update product
   set pname = '컴퓨터2',
       quantity = 10,
       price = 1200000;

--삭제
delete from product where pid = 1;

--전체조회-
select pid,pname,quantity,price from product;

commit;