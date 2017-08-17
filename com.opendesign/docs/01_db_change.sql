/* default 10000 포인트 */
[done] 
/* 
UPDATE t_member SET POINT = 10000 ; 
*/

[done]
/* 중복 카테고리 제거: 단일 카테고리로 변경 
 * t_design_work_category
 * t_member_category
 * t_project_category
 * t_request_board_category 
 */
/*
CREATE TABLE test AS
SELECT
	board_seq,
	MIN(category_code) AS category_code
    FROM
	t_request_board_category
    GROUP BY
       board_seq
;

DELETE FROM t_request_board_category
WHERE (board_seq,category_code) 
NOT IN (SELECT * FROM test)
;

DROP TABLE test;
*/

[done] 
/**
 * 그룹신청 table: 2016-10-14
 */
/*
CREATE TABLE `t_pgroup_project_req` (
  `seq` int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  `project_group_seq` int(11) DEFAULT NULL COMMENT '신청그룹seq',
  `project_seq` int(11) DEFAULT NULL COMMENT '신청프로젝트seq',
  `status` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '상태:0대기,1성공,2실패',
  `register_time` char(12) COLLATE utf8_bin DEFAULT NULL COMMENT '신청일시',
  `update_time` char(12) COLLATE utf8_bin DEFAULT NULL COMMENT '변경일시',
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='그룹신청'
*/

[done]
/**
 * 삭제 flag:  
 * t_design_work, t_project, t_project_work
 * 1. delete 를 update로 바꿈
 *    관련 sub 삭제는 취소해줌 
 * 2. select 를 view 사용으로 바꿈
 * 2016.10.25
 */
/*
alter table `t_design_work` 
   add column `del_flag` char(1) DEFAULT 'N' NULL COMMENT '삭제flag(Y,N)';
create view v_design_work AS
	select * from t_design_work where del_flag = 'N'; 
	
alter table `t_project` 
   add column `del_flag` char(1) DEFAULT 'N' NULL COMMENT '삭제flag(Y,N)';
create view v_project AS
	select * from t_project where del_flag = 'N'; 
	
alter table `t_project_work` 
   add column `del_flag` char(1) DEFAULT 'N' NULL COMMENT '삭제flag(Y,N)';
create view v_project_work AS
	select * from t_project_work where del_flag = 'N'; 
*/

[done]
/*
 * remove unused db:
 * t_member_like  
 */
/*
DROP TABLE `t_member_like`;
DROP TABLE `t_talk_request`;
 */

[done]
/**
 * 작품 title 200
 */
/*
alter table `t_design_work` 
   change `title` `title` char(200) NULL  comment '작품명'
*/

[done]
/**
 * 
 * 의뢰 contents
 * 
 * ALTER TABLE `t_request_board` 
CHANGE COLUMN `contents` `contents` TEXT NULL COMMENT '내용' ;
 * 
 */
[done]
/**
 * 파일명 이미지 이름 크기: t_request_board_file: 
 */
/*
alter table `t_request_board_file` 
   change `filename` `filename` varchar(500) NULL  comment '파일명';
alter table `t_request_board_file` 
   change `file_url` `file_url` varchar(1000) NULL  comment '파일url';
*/
[done]
/**
 * 파일명 이미지 이름 크기: t_request_board_file: 
 */
/*
alter table `t_request_board_file` 
   change `filename` `filename` varchar(500) NULL  comment '파일명';
alter table `t_request_board_file` 
   change `file_url` `file_url` varchar(1000) NULL  comment '파일url';
*/

[done]
/**
 * 테스트기에서 MainSQL.xml sql 실행
 * updateWorkActivities, updateMemberActivities
 */

[done 문서변경: db done]
/*
 * Bug #1890
 * 디자인 프로젝트에서 작품 썸네일 필드 추가: 
 * 
 */
/*
alter table `t_project_work` 
   add column `thumb_uri` varchar(300) NULL COMMENT '썸네일';
*/
/*
 * view 다시 생성
 */
/*
DROP VIEW IF EXISTS v_design_work;
CREATE VIEW v_design_work AS
	SELECT * FROM t_design_work WHERE del_flag = 'N'; 

DROP VIEW IF EXISTS v_project;
CREATE VIEW v_project AS
	SELECT * FROM t_project WHERE del_flag = 'N'; 

DROP VIEW IF EXISTS v_project_work;
CREATE VIEW v_project_work AS
	SELECT * FROM t_project_work WHERE del_flag = 'N'; 
*/  
   