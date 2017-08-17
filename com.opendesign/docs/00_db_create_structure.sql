
/*Table structure for table t_category */

DROP TABLE IF EXISTS t_alarm;

CREATE TABLE t_alarm (
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  contents varchar(1000)  DEFAULT NULL COMMENT '내용',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  action_uri varchar(200)  DEFAULT NULL COMMENT '액션url',
  confirm_time char(12)  DEFAULT NULL COMMENT '확인일시',
  actor_seq int(11) DEFAULT NULL COMMENT '이벤트 유발자',
  PRIMARY KEY (seq)
);

/*Table structure for table t_category */

DROP TABLE IF EXISTS t_category;

CREATE TABLE t_category (
  category_code varchar(9)  NOT NULL COMMENT '카테고리코드',
  depth int(11) DEFAULT NULL COMMENT '뎁스',
  category_name varchar(50)  DEFAULT NULL COMMENT '카테고리명',
  PRIMARY KEY (category_code)
);

/*Table structure for table t_design_preview_image */

DROP TABLE IF EXISTS t_design_preview_image;

CREATE TABLE t_design_preview_image (
  design_work_seq int(11) DEFAULT NULL COMMENT '작품seq',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  file_uri varchar(200)  DEFAULT NULL COMMENT '이미지URL',
  filename varchar(200)  DEFAULT NULL COMMENT '파일명',
  PRIMARY KEY (seq)
);
/*Table structure for table t_design_work */
DROP TABLE IF EXISTS t_design_work;

CREATE TABLE t_design_work (
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  view_cnt int(11) DEFAULT '0' COMMENT '조회수',
  title char(200)  DEFAULT NULL COMMENT '작품명',
  license varchar(3)  DEFAULT NULL COMMENT '라이센스',
  point int(11) DEFAULT NULL COMMENT '포인트',
  thumb_uri varchar(200)  DEFAULT NULL COMMENT '썸네일',
  contents varchar(1000)  DEFAULT NULL COMMENT '작품설명',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  tags varchar(200)  DEFAULT NULL COMMENT '테그',
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  update_time char(12)  DEFAULT NULL COMMENT '수정일시',
  del_flag char(1)  DEFAULT 'N' COMMENT '삭제flag(Y,N)',
  PRIMARY KEY (seq)
);
/*Table structure for table t_design_work_category */

DROP TABLE IF EXISTS t_design_work_category;

CREATE TABLE t_design_work_category (
  design_work_seq int(11) NOT NULL COMMENT '작품seq',
  category_code varchar(9)  NOT NULL COMMENT '카테고리 코드',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (design_work_seq,category_code)
);

/*Table structure for table t_design_work_comment */

DROP TABLE IF EXISTS t_design_work_comment;

CREATE TABLE t_design_work_comment (
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '작품댓글seq',
  design_work_seq int(11) DEFAULT NULL COMMENT '작품seq',
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  contents varchar(3000)  DEFAULT NULL COMMENT '내용',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (seq)
);

/*Table structure for table t_design_work_file */

DROP TABLE IF EXISTS t_design_work_file;

CREATE TABLE t_design_work_file (
  design_work_seq int(11) DEFAULT NULL COMMENT '작품seq',
  comments varchar(1000)  DEFAULT NULL COMMENT '설명',
  file_uri varchar(200)  DEFAULT NULL COMMENT '파일경로',
  filename varchar(200)  DEFAULT NULL COMMENT '파일명',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (seq)
);


/*Table structure for table t_design_work_like */

DROP TABLE IF EXISTS t_design_work_like;

CREATE TABLE t_design_work_like (
  design_work_seq int(11) NOT NULL COMMENT '작품seq',
  member_seq int(11) NOT NULL COMMENT '회원seq',
  register_time char(18)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (design_work_seq,member_seq)
);

/*Table structure for table t_member */

DROP TABLE IF EXISTS t_member;

CREATE TABLE t_member (
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '회원seq',
  email varchar(200)  DEFAULT NULL COMMENT '사용이메일',
  passwd varchar(256)  DEFAULT NULL COMMENT '비밀번호',
  uname varchar(50)  DEFAULT NULL COMMENT '닉네임',
  image_url varchar(200)  DEFAULT NULL COMMENT '프로필사진',
  comments varchar(1000)  DEFAULT NULL COMMENT '소개',
  member_type char(2)  DEFAULT NULL COMMENT '회원구분(00-일반,10-디자인,01-제작자,11-디자이너/제작자)',
  point int(11) DEFAULT '0' COMMENT '포인트',
  fb_access_token varchar(500)  DEFAULT NULL,
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  update_time char(12)  DEFAULT NULL COMMENT '수정일시',
  sido_seq int(11) NOT NULL,
  PRIMARY KEY (seq)
);



/*Table structure for table t_member_category */

DROP TABLE IF EXISTS t_member_category;

CREATE TABLE t_member_category (
  member_seq int(11) NOT NULL COMMENT '회원seq',
  category_code varchar(9)  NOT NULL COMMENT '카테고리코드',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (member_seq,category_code)
);


/*Table structure for table t_order */

DROP TABLE IF EXISTS t_order;

CREATE TABLE t_order (
  design_work_seq int(11) DEFAULT NULL,
  register_time char(12)  DEFAULT NULL,
  point int(11) DEFAULT NULL,
  delivery_info varchar(1000)  DEFAULT NULL,
  status varchar(1)  DEFAULT NULL,
  member_seq int(11) DEFAULT NULL,
  seq int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (seq)
);

/*Table structure for table t_pgroup */

DROP TABLE IF EXISTS t_pgroup;

CREATE TABLE t_pgroup (
  group_name varchar(50)  NOT NULL,
  seq int(11) NOT NULL AUTO_INCREMENT,
  member_seq int(11) DEFAULT NULL,
  register_time char(12)  DEFAULT NULL,
  update_time char(12)  DEFAULT NULL,
  PRIMARY KEY (seq)
);


/*Table structure for table t_pgroup_project */

DROP TABLE IF EXISTS t_pgroup_project;

CREATE TABLE t_pgroup_project (
  project_group_seq int(11) NOT NULL,
  project_seq int(11) NOT NULL,
  register_time char(12)  DEFAULT NULL,
  PRIMARY KEY (project_group_seq,project_seq)
);


/*Table structure for table t_pgroup_project_req */

DROP TABLE IF EXISTS t_pgroup_project_req;

CREATE TABLE t_pgroup_project_req (
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  project_group_seq int(11) DEFAULT NULL COMMENT '신청그룹seq',
  project_seq int(11) DEFAULT NULL COMMENT '신청프로젝트seq',
  status char(1)  DEFAULT '0' COMMENT '상태:0대기,1성공,2실패',
  register_time char(12)  DEFAULT NULL COMMENT '신청일시',
  update_time char(12)  DEFAULT NULL COMMENT '변경일시',
  PRIMARY KEY (seq)
);
/*Table structure for table t_point_history */
DROP TABLE IF EXISTS t_point_history;
CREATE TABLE t_point_history (
  sign varchar(1)  DEFAULT NULL,
  amount int(11) DEFAULT NULL,
  accum_amount int(11) DEFAULT NULL,
  register_time char(12)  DEFAULT NULL,
  member_seq int(11) DEFAULT NULL,
  order_seq int(11) DEFAULT NULL,
  seq int(11) NOT NULL AUTO_INCREMENT,
  comments varchar(1000)  DEFAULT NULL,
  PRIMARY KEY (seq)
);


/*Table structure for table t_project */

DROP TABLE IF EXISTS t_project;

CREATE TABLE t_project (
  public_yn varchar(1)  DEFAULT NULL,
  project_name varchar(200)  DEFAULT NULL,
  progress_status varchar(1)  DEFAULT NULL,
  seq int(11) NOT NULL AUTO_INCREMENT,
  file_url varchar(200)  DEFAULT NULL,
  register_time char(12)  DEFAULT NULL,
  update_time char(12)  DEFAULT NULL,
  owner_seq int(11) DEFAULT NULL,
  file_name varchar(256)  DEFAULT NULL,
  del_flag char(1)  DEFAULT 'N' COMMENT '삭제flag(Y,N)',
  PRIMARY KEY (seq)
);

/*Table structure for table t_project_category */

DROP TABLE IF EXISTS t_project_category;

CREATE TABLE t_project_category (
  category_code varchar(9)  NOT NULL,
  project_seq int(11) NOT NULL,
  register_time char(12)  DEFAULT NULL,
  PRIMARY KEY (category_code,project_seq)
);


/*Table structure for table t_project_member */

DROP TABLE IF EXISTS t_project_member;

CREATE TABLE t_project_member (
  member_seq int(11) NOT NULL,
  project_seq int(11) NOT NULL,
  register_time char(12)  DEFAULT NULL,
  PRIMARY KEY (member_seq,project_seq)
);


/*Table structure for table t_project_subject */

DROP TABLE IF EXISTS t_project_subject;

CREATE TABLE t_project_subject (
  project_seq int(11) DEFAULT NULL COMMENT '프로젝트seq',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '주제seq',
  subject_name varchar(200)  DEFAULT NULL COMMENT '주제명',
  member_seq int(11) DEFAULT NULL COMMENT '생성회원seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (seq)
);


/*Table structure for table t_project_work */

DROP TABLE IF EXISTS t_project_work;

CREATE TABLE t_project_work (
  title varchar(200)  DEFAULT NULL COMMENT '제목',
  contents varchar(3000)  DEFAULT NULL COMMENT '작품설명',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '작품seq',
  project_subject_seq int(11) DEFAULT NULL COMMENT '주제seq',
  member_seq int(11) DEFAULT NULL COMMENT '생성회원seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  refer_project_work_seq int(11) DEFAULT NULL COMMENT '참조작품seq',
  last_ver varchar(5)  DEFAULT NULL COMMENT '마지막버전',
  del_flag char(1)  DEFAULT 'N' COMMENT '삭제flag(Y,N)',
  thumb_uri varchar(300)  DEFAULT NULL COMMENT '썸네일',
  PRIMARY KEY (seq)
);


/*Table structure for table t_project_work_comment */

DROP TABLE IF EXISTS t_project_work_comment;

CREATE TABLE t_project_work_comment (
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  contents varchar(3000)  DEFAULT NULL COMMENT '내용',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '작품댓글seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  project_work_seq int(11) DEFAULT NULL COMMENT '작품seq',
  PRIMARY KEY (seq)
);


/*Table structure for table t_project_work_like */

DROP TABLE IF EXISTS t_project_work_like;

CREATE TABLE t_project_work_like (
  member_seq int(11) NOT NULL,
  project_work_seq int(11) NOT NULL,
  register_time char(12)  DEFAULT NULL,
  PRIMARY KEY (member_seq,project_work_seq)
);


/*Table structure for table t_project_work_member */

DROP TABLE IF EXISTS t_project_work_member;

CREATE TABLE t_project_work_member (
  member_seq int(11) NOT NULL COMMENT '참여회원seq',
  project_work_seq int(11) NOT NULL COMMENT '작품seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (member_seq,project_work_seq)
);


/*Table structure for table t_project_work_ver */

DROP TABLE IF EXISTS t_project_work_ver;

CREATE TABLE t_project_work_ver (
  project_work_seq int(11) DEFAULT NULL COMMENT '작품seq',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '버전seq',
  ver varchar(5)  DEFAULT NULL COMMENT '버전',
  file_uri varchar(200)  DEFAULT NULL COMMENT '파일명',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  filename varchar(200)  DEFAULT NULL COMMENT '첨부파일',
  PRIMARY KEY (seq)
);



/*Table structure for table t_request_board */

DROP TABLE IF EXISTS t_request_board;

CREATE TABLE t_request_board (
  board_type varchar(20)  DEFAULT NULL COMMENT '구분',
  title varchar(200)  DEFAULT NULL COMMENT '제목',
  contents text  COMMENT '내용',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  member_seq int(11) DEFAULT NULL COMMENT '작성자seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  update_time char(18)  DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (seq)
);

/*Table structure for table t_request_board_category */

DROP TABLE IF EXISTS t_request_board_category;

CREATE TABLE t_request_board_category (
  board_seq int(11) NOT NULL COMMENT '게시판seq',
  category_code varchar(9)  NOT NULL COMMENT '카테고리코드',
  PRIMARY KEY (board_seq,category_code)
);


/*Table structure for table t_request_board_comment */

DROP TABLE IF EXISTS t_request_board_comment;

CREATE TABLE t_request_board_comment (
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  board_seq int(11) DEFAULT NULL COMMENT '게시판seq',
  contents varchar(3000)  DEFAULT NULL COMMENT '내용',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '댓글seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (seq)
);


/*Table structure for table t_request_board_file */

DROP TABLE IF EXISTS t_request_board_file;

CREATE TABLE t_request_board_file (
  board_seq int(11) DEFAULT NULL COMMENT '게시판seq',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  filename varchar(500)  DEFAULT NULL COMMENT '파일명',
  file_url varchar(1000)  DEFAULT NULL COMMENT '파일url',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (seq)
);


/*Table structure for table t_stat_activities */

DROP TABLE IF EXISTS t_stat_activities;

CREATE TABLE t_stat_activities (
  stat_month char(6)  NOT NULL COMMENT '집계 월',
  target_seq int(11) NOT NULL COMMENT '대상(제작자/디자이너 또는 작품)  시퀀스',
  target_type char(1)  NOT NULL COMMENT '대상형태 (1=디자이너/제작자, 2=작품)',
  update_time char(14)  NOT NULL COMMENT '수정일자',
  PRIMARY KEY (stat_month,target_seq,target_type)
);


/*Table structure for table t_talk */

DROP TABLE IF EXISTS t_talk;

CREATE TABLE t_talk (
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  contents varchar(3000)  DEFAULT NULL COMMENT '내용',
  recieve_seq int(11) DEFAULT NULL COMMENT '받는seq',
  send_seq int(11) DEFAULT NULL COMMENT '보낸seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  confirm_time char(12)  DEFAULT NULL COMMENT '확인일시',
  PRIMARY KEY (seq)
);


/*Table structure for table t_zip_code */

DROP TABLE IF EXISTS t_zip_code;

CREATE TABLE t_zip_code (
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  sido varchar(50) NOT NULL COMMENT '시/도',
  PRIMARY KEY (seq),
  UNIQUE KEY sido_UNIQUE (sido)
);


/* indexes */
ALTER TABLE t_alarm ADD INDEX(member_seq);
ALTER TABLE t_design_preview_image ADD INDEX(design_work_seq);
ALTER TABLE t_design_work ADD INDEX(member_seq);
ALTER TABLE t_design_work_comment ADD INDEX(design_work_seq);
ALTER TABLE t_design_work_file ADD INDEX(design_work_seq);
ALTER TABLE t_order ADD INDEX(design_work_seq);
ALTER TABLE t_order ADD INDEX(member_seq);
ALTER TABLE t_pgroup ADD INDEX(member_seq);
ALTER TABLE t_point_history ADD INDEX(member_seq);
ALTER TABLE t_point_history ADD INDEX(order_seq);
ALTER TABLE t_project ADD INDEX(owner_seq);
ALTER TABLE t_project_subject ADD INDEX(project_seq);
ALTER TABLE t_project_subject ADD INDEX(member_seq);
ALTER TABLE t_project_work ADD INDEX(project_subject_seq);
ALTER TABLE t_project_work ADD INDEX(member_seq);
ALTER TABLE t_project_work_comment ADD INDEX(member_seq);
ALTER TABLE t_project_work_comment ADD INDEX(project_work_seq);
ALTER TABLE t_project_work_ver ADD INDEX(project_work_seq);
ALTER TABLE t_request_board ADD INDEX(member_seq);
ALTER TABLE t_request_board_comment ADD INDEX(member_seq);
ALTER TABLE t_request_board_comment ADD INDEX(board_seq);
ALTER TABLE t_request_board_file ADD INDEX(board_seq);
ALTER TABLE t_talk ADD INDEX(recieve_seq);
ALTER TABLE t_talk ADD INDEX(send_seq);

/* view */
DROP VIEW IF EXISTS v_design_work;
CREATE VIEW v_design_work AS
	SELECT * FROM t_design_work WHERE del_flag = 'N'; 

DROP VIEW IF EXISTS v_project;
CREATE VIEW v_project AS
	SELECT * FROM t_project WHERE del_flag = 'N'; 

DROP VIEW IF EXISTS v_project_work;
CREATE VIEW v_project_work AS
	SELECT * FROM t_project_work WHERE del_flag = 'N'; 




