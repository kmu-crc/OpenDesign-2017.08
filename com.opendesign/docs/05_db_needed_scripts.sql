/*
필요 스크립트 : 
--------------------------------------------------
	1. - 전체 데이터 삭제 (메타 데이터 제외) [done]
	2. - 사용자 삭제 
	3. - 그룹 삭제
	4. - 디자인(작품) 삭제
	5. - 디자인 프로젝트 삭제
	6. - 디자인/제작자 의뢰 삭제
--------------------------------------------------
*/
/*
 * sample 조회:
SELECT * FROM t_member T WHERE T.uname LIKE '%';
SELECT * FROM t_member T WHERE T.email LIKE '%';
SELECT * FROM t_pgroup T WHERE T.group_name LIKE '%';
SELECT * FROM t_design_work T WHERE T.title LIKE '%';
SELECT * FROM t_project T WHERE T.project_name LIKE '%';
SELECT * FROM t_request_board T WHERE T.title LIKE '%';
 * 
 */



/* ============== 1. - 전체 데이터 삭제 (메타 데이터 제외<<t_category,t_zip_code>>): 29개 table ============== */
TRUNCATE TABLE t_alarm;
TRUNCATE TABLE t_design_preview_image;
TRUNCATE TABLE t_design_work;
TRUNCATE TABLE t_design_work_category;
TRUNCATE TABLE t_design_work_comment;
TRUNCATE TABLE t_design_work_file;
TRUNCATE TABLE t_design_work_like;
TRUNCATE TABLE t_member;
TRUNCATE TABLE t_member_category;
TRUNCATE TABLE t_order;
TRUNCATE TABLE t_pgroup;
TRUNCATE TABLE t_pgroup_project;
TRUNCATE TABLE t_pgroup_project_req;
TRUNCATE TABLE t_point_history;
TRUNCATE TABLE t_project;
TRUNCATE TABLE t_project_category;
TRUNCATE TABLE t_project_member;
TRUNCATE TABLE t_project_subject;
TRUNCATE TABLE t_project_work;
TRUNCATE TABLE t_project_work_comment;
TRUNCATE TABLE t_project_work_like;
TRUNCATE TABLE t_project_work_member;
TRUNCATE TABLE t_project_work_ver;
TRUNCATE TABLE t_request_board;
TRUNCATE TABLE t_request_board_category;
TRUNCATE TABLE t_request_board_comment;
TRUNCATE TABLE t_request_board_file;
TRUNCATE TABLE t_stat_activities;
TRUNCATE TABLE t_talk;








/* ==============2. - 사용자 삭제  ==============*/
START TRANSACTION;

/* ===2.0 삭제할 사용자 seq set */
SET @userSeq = ''; 

/* ===2.3 기타 공통 삭제  */
DELETE FROM t_alarm WHERE member_seq = @userSeq OR actor_seq = @userSeq;

DELETE FROM t_talk WHERE send_seq = @userSeq OR recieve_seq = @userSeq;

/* main(1=디자이너/제작자, 2=작품) */
DELETE FROM t_stat_activities WHERE target_type = '1' AND target_seq = @userSeq;

DELETE FROM t_stat_activities WHERE target_type = '2' AND  target_seq IN (
	SELECT T.seq FROM t_design_work T WHERE T.member_seq = @userSeq
);

/* ===2.1 디자인(작품) 삭제 */
DELETE FROM t_design_work_category WHERE design_work_seq IN (
  SELECT T.seq FROM t_design_work T WHERE T.member_seq = @userSeq
);

DELETE FROM t_design_work_comment WHERE design_work_seq IN (
  SELECT T.seq FROM t_design_work T WHERE T.member_seq = @userSeq
) OR member_seq = @userSeq;

DELETE FROM t_design_work_file WHERE design_work_seq IN (
  SELECT T.seq FROM t_design_work T WHERE T.member_seq = @userSeq
);

DELETE FROM t_design_work_like WHERE design_work_seq IN (
  SELECT T.seq FROM t_design_work T WHERE T.member_seq = @userSeq
) OR member_seq = @userSeq;

/* 구매 */
DELETE FROM t_point_history WHERE order_seq IN (
  SELECT T.seq FROM t_order T WHERE T.design_work_seq IN (
	  SELECT T.seq FROM t_design_work T WHERE T.member_seq = @userSeq
  ) OR T.member_seq = @userSeq
) OR member_seq = @userSeq;

DELETE FROM t_order WHERE design_work_seq IN (
  SELECT T.seq FROM t_design_work T WHERE T.member_seq = @userSeq
) OR member_seq = @userSeq;

DELETE FROM t_design_work WHERE member_seq = @userSeq;


/* ===2.2 그룹 삭제*/
DELETE FROM t_pgroup_project_req WHERE project_group_seq IN (
  SELECT T.seq FROM t_pgroup T WHERE T.member_seq = @userSeq
);

DELETE FROM t_pgroup_project WHERE project_group_seq IN (
  SELECT T.seq FROM t_pgroup T WHERE T.member_seq = @userSeq
);

DELETE FROM t_pgroup WHERE member_seq = @userSeq;


/* ===2.2 디자인 프로젝트 삭제 */
DELETE FROM t_pgroup_project_req WHERE project_seq IN (
  SELECT T.seq FROM t_project T WHERE T.owner_seq = @userSeq
);

DELETE FROM t_pgroup_project WHERE project_seq IN (
  SELECT T.seq FROM t_project T WHERE T.owner_seq = @userSeq
);


DELETE FROM t_project_work_comment WHERE project_work_seq IN (
	SELECT T.seq FROM t_project_work T WHERE T.project_subject_seq IN (
		SELECT T.seq FROM t_project_subject T WHERE T.project_seq IN (
		  SELECT T.seq FROM t_project T WHERE T.owner_seq = @userSeq
		) OR T.member_seq = @userSeq
	) OR T.member_seq = @userSeq
) OR member_seq = @userSeq;

DELETE FROM t_project_work_like WHERE project_work_seq IN (
	SELECT T.seq FROM t_project_work T WHERE T.project_subject_seq IN (
		SELECT T.seq FROM t_project_subject T WHERE T.project_seq IN (
		  SELECT T.seq FROM t_project T WHERE T.owner_seq = @userSeq
		) OR T.member_seq = @userSeq
	) OR T.member_seq = @userSeq
) OR member_seq = @userSeq;


DELETE FROM t_project_work_member WHERE project_work_seq IN (
	SELECT T.seq FROM t_project_work T WHERE T.project_subject_seq IN (
		SELECT T.seq FROM t_project_subject T WHERE T.project_seq IN (
		  SELECT T.seq FROM t_project T WHERE T.owner_seq = @userSeq
		) OR T.member_seq = @userSeq
	) OR T.member_seq = @userSeq
) OR member_seq = @userSeq;

DELETE FROM t_project_work_ver WHERE project_work_seq IN (
	SELECT T.seq FROM t_project_work T WHERE T.project_subject_seq IN (
		SELECT T.seq FROM t_project_subject T WHERE T.project_seq IN (
		  SELECT T.seq FROM t_project T WHERE T.owner_seq = @userSeq
		) OR T.member_seq = @userSeq
	) OR T.member_seq = @userSeq
);

DELETE FROM t_project_work WHERE project_subject_seq IN (
	SELECT T.seq FROM t_project_subject T WHERE T.project_seq IN (
	  SELECT T.seq FROM t_project T WHERE T.owner_seq = @userSeq
	) OR T.member_seq = @userSeq
) OR member_seq = @userSeq;

DELETE FROM t_project_subject WHERE project_seq IN (
  SELECT T.seq FROM t_project T WHERE T.owner_seq = @userSeq
) OR member_seq = @userSeq;

DELETE FROM t_project_category WHERE project_seq IN (
  SELECT T.seq FROM t_project T WHERE T.owner_seq = @userSeq
);

DELETE FROM t_project_member WHERE project_seq IN (
  SELECT T.seq FROM t_project T WHERE T.owner_seq = @userSeq
) OR member_seq = @userSeq;

DELETE FROM t_project WHERE owner_seq = @userSeq;



/* === 2.4 디자인/제작자 의뢰 삭제 */ 
/* 의뢰 */
DELETE FROM t_request_board_comment WHERE board_seq IN (
  SELECT T.seq FROM t_request_board T WHERE T.member_seq = @userSeq
) OR member_seq = @userSeq;

DELETE FROM t_request_board_category WHERE board_seq IN (
  SELECT T.seq FROM t_request_board T WHERE T.member_seq = @userSeq
);

DELETE FROM t_request_board_file WHERE board_seq IN (
  SELECT T.seq FROM t_request_board T WHERE T.member_seq = @userSeq
);

DELETE FROM t_request_board WHERE member_seq = @userSeq;

/* 회원  */
DELETE FROM t_member_category WHERE member_seq = @userSeq;

DELETE FROM t_member WHERE seq = @userSeq;


COMMIT;






/* ==============3. - 그룹 삭제  ==============*/
START TRANSACTION;

/* ===3.0 삭제할 그룹 seq set */
SET @groupSeq = '';

DELETE FROM t_pgroup_project_req WHERE project_group_seq = @groupSeq;

DELETE FROM t_pgroup_project WHERE project_group_seq = @groupSeq;

DELETE FROM t_pgroup WHERE seq = @groupSeq;

COMMIT;






/* ==============4. - 디자인(작품) 삭제  ==============*/
START TRANSACTION;

/* ===4.0 삭제할 디자인(작품) seq set */
SET @designWorkSeq = '';

DELETE FROM t_design_work_category WHERE design_work_seq = @designWorkSeq;

DELETE FROM t_design_work_comment WHERE design_work_seq = @designWorkSeq;

DELETE FROM t_design_work_file WHERE design_work_seq = @designWorkSeq;

DELETE FROM t_design_work_like WHERE design_work_seq = @designWorkSeq;

/* 구매 */
DELETE FROM t_point_history WHERE order_seq IN (
  SELECT T.seq FROM t_order T WHERE T.design_work_seq = @designWorkSeq
);

DELETE FROM t_order WHERE design_work_seq = @designWorkSeq;

DELETE FROM t_design_work WHERE seq = @designWorkSeq;

/* main (1=디자이너/제작자, 2=작품) */
DELETE FROM t_stat_activities WHERE target_type = '2' AND  target_seq = @designWorkSeq;

COMMIT;






/* ==============5. - 디자인 프로젝트 삭제 ==============*/
START TRANSACTION;

/* ===5.0 디자인 프로젝트 seq set */
SET @projectSeq = '';

DELETE FROM t_pgroup_project_req WHERE project_seq = @projectSeq;

DELETE FROM t_pgroup_project WHERE project_seq = @projectSeq;


DELETE FROM t_project_work_comment WHERE project_work_seq IN (
	SELECT T.seq FROM t_project_work T WHERE T.project_subject_seq IN (
		SELECT T.seq FROM t_project_subject T WHERE T.project_seq = @projectSeq
	)
);

DELETE FROM t_project_work_like WHERE project_work_seq IN (
	SELECT T.seq FROM t_project_work T WHERE T.project_subject_seq IN (
		SELECT T.seq FROM t_project_subject T WHERE T.project_seq = @projectSeq
	)
);


DELETE FROM t_project_work_member WHERE project_work_seq IN (
	SELECT T.seq FROM t_project_work T WHERE T.project_subject_seq IN (
		SELECT T.seq FROM t_project_subject T WHERE T.project_seq = @projectSeq
	)
);

DELETE FROM t_project_work_ver WHERE project_work_seq IN (
	SELECT T.seq FROM t_project_work T WHERE T.project_subject_seq IN (
		SELECT T.seq FROM t_project_subject T WHERE T.project_seq = @projectSeq
	)
);

DELETE FROM t_project_work WHERE project_subject_seq IN (
	SELECT T.seq FROM t_project_subject T WHERE T.project_seq = @projectSeq
);

DELETE FROM t_project_subject WHERE project_seq = @projectSeq;

DELETE FROM t_project_category WHERE project_seq = @projectSeq;

DELETE FROM t_project_member WHERE project_seq = @projectSeq;

DELETE FROM t_project WHERE seq = @projectSeq;

COMMIT;






/* ==============6. - 디자인/제작자 의뢰 삭제 ==============*/
START TRANSACTION;

/* ===6.0 디자인/제작자 의뢰 seq set */
SET @boardSeq = '';

/* 의뢰 */
DELETE FROM t_request_board_comment WHERE board_seq = @boardSeq;

DELETE FROM t_request_board_category WHERE board_seq = @boardSeq;

DELETE FROM t_request_board_file WHERE board_seq = @boardSeq;

DELETE FROM t_request_board WHERE seq = @boardSeq;

COMMIT;





