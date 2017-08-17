/*

SQLyog Ultimate v8.5 
MySQL - 5.5.44-MariaDB : Database - opendesign

*********************************************************************

*/



/*!40101 SET NAMES utf8 */;



/*!40101 SET SQL_MODE=''*/;



/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--CREATE DATABASE /*!32312 IF NOT EXISTS*/opendesign /*!40100 DEFAULT CHARACTER SET utf8  */;



--USE opendesign;


/* drop views  */
DROP VIEW IF EXISTS v_design_work;
DROP VIEW IF EXISTS v_project;
DROP VIEW IF EXISTS v_project_work;

/*Table structure for table t_alarm */



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
) ;



/*Data for the table t_alarm */



insert  into t_alarm(seq,member_seq,contents,register_time,action_uri,confirm_time,actor_seq) values (1,37,'“Dancing with 3D” 작품을 13,880 포인트로 구매하였습니다.','201609271500','','201611021827',23),(2,37,'“Dancing with 3D” 작품을 1000 포인트로 구매하였습니다.','201609271600','','201611021827',23),(4,23,'나의 작품 \"작품222\"에 좋아요를 남겼습니다.','201609291204','','201610261738',37),(5,23,'나의 게시물 \"작품444\"에 좋아요를 남겼습니다.','201609291510','','201610261738',37),(10,23,'나의 게시물 \"작품444\"을 11포인트로 구매하셨습니다.','201609291521','','201610261738',37),(11,23,'나의 게시물 \"작품222\"에 댓글을 남겼습니다.','201609291522','','201610261738',37),(12,23,'나의 게시물 \"이런 디자인 의뢰합니다.001\"에 댓글을 남겼습니다.','201609291523','','201610261738',37),(13,23,'나의 게시물 \"작품222\"에 좋아요를 남겼습니다.','201609291531','','201610261738',37),(14,23,'나의 게시물 \"작품444\"에 좋아요를 남겼습니다.','201609291641','','201610261738',38),(15,37,'나의 게시물 \"Koala\"에 좋아요를 남겼습니다.','201609291956','','201611021827',38),(16,37,'나의 게시물 \"Hydrangeas\"에 댓글을 남겼습니다.','201609292003','','201611021827',38),(17,38,'나의 게시물 \"퍼가기 제목\"에 댓글을 남겼습니다.','201610031448','',NULL,39),(18,38,'나의 게시물 \"퍼가기 제목\"에 좋아요를 남겼습니다.','201610031449','',NULL,39),(19,38,'나의 게시물 \"퍼가기 제목\"에 댓글을 남겼습니다.','201610031449','',NULL,39),(20,37,'나의 그룹 \"shg그룹\"에 신청하셨습니다.','201610201040','','201611021827',26),(21,26,'그룹 \"shg그룹\"에 대한 신청을 거절하셨습니다.','201610201043','','201610261745',37),(22,37,'나의 그룹 \"shg그룹\"에 신청하셨습니다.','201610201044','','201611021827',26),(23,26,'그룹 \"shg그룹\"에 대한 신청을 승인하셨습니다.','201610201045','','201610261745',37),(24,37,'나의 게시물 \"ddd\"에 좋아요를 남겼습니다.','201610251405','','201611021827',37),(25,26,'나의 그룹 \"송중기그룹1\"에 신청하셨습니다.','201610261729','','201610261745',23),(26,23,'그룹 \"송중기그룹1\"에 대한 신청을 승인하셨습니다.','201610261735','','201610261738',26),(27,26,'나의 그룹 \"송중기그룹1\"에 신청하셨습니다.','201610261737','','201610261745',23),(28,23,'그룹 \"송중기그룹1\"에 대한 신청을 승인하셨습니다.','201610261738','','201610261738',26),(29,37,'나의 게시물 \"gggg\"을 100포인트로 구매하셨습니다.','201610301925','','201611021827',37),(30,39,'나의 게시물 \"ttttt\"에 좋아요를 남겼습니다.','201610302102','','201610302110',37),(31,39,'나의 게시물 \"fefefee\"에 좋아요를 남겼습니다.','201610302102','','201610302110',37),(32,37,'나의 게시물 \"ggg\"에 좋아요를 남겼습니다.','201610302102','','201611021827',37),(33,39,'나의 게시물 \"안녕용\"을 0포인트로 구매하셨습니다.','201610302102','','201610302110',37),(34,39,'나의 그룹 \"FB그룹\"에 신청하셨습니다.','201610302110','',NULL,39),(35,39,'그룹 \"FB그룹\"에 대한 신청을 승인하셨습니다.','201610302111','',NULL,39),(36,39,'나의 게시물 \"fefefee\"에 좋아요를 남겼습니다.','201610302233','',NULL,39),(37,39,'나의 게시물 \"프로젝트 수정\"을 0포인트로 구매하셨습니다.','201610310054','',NULL,39),(38,39,'나의 게시물 \"프로젝트 수정\"을 0포인트로 구매하셨습니다.','201610310106','',NULL,39),(39,39,'나의 게시물 \"프로젝트 수정\"에 좋아요를 남겼습니다.','201610310106','',NULL,39),(40,39,'나의 게시물 \"fefefee\"을 0포인트로 구매하셨습니다.','201610310107','',NULL,39),(41,39,'나의 게시물 \"이미지 리사이즈\"을 0포인트로 구매하셨습니다.','201610310108','',NULL,39),(42,37,'나의 게시물 \"상업적이용 함\"을 0포인트로 구매하셨습니다.','201610310110','','201611021827',39),(43,37,'나의 게시물 \"상업적이용 함\"을 0포인트로 구매하셨습니다.','201610310110','','201611021827',39),(44,37,'나의 게시물 \"상업적이용 함\"을 0포인트로 구매하셨습니다.','201610310114','','201611021827',39),(45,37,'나의 게시물 \"상업적이용 함\"을 0포인트로 구매하셨습니다.','201610310115','','201611021827',39),(46,37,'나의 게시물 \"상업적이용 함\"을 0포인트로 구매하셨습니다.','201610310115','','201611021827',39),(47,39,'나의 게시물 \"프로젝트 수정\"에 좋아요를 남겼습니다.','201610311558','',NULL,108),(48,39,'나의 게시물 \"프로젝트 수정\"을 0포인트로 구매하셨습니다.','201610311559','',NULL,108),(49,109,'나의 게시물 \"sample\"에 좋아요를 남겼습니다.','201610311604','','201611011029',108),(50,109,'나의 게시물 \"sample\"에 좋아요를 남겼습니다.','201610311604','','201611011029',108),(51,109,'나의 게시물 \"sample\"에 좋아요를 남겼습니다.','201610311604','','201611011029',108),(52,109,'나의 게시물 \"sample\"에 좋아요를 남겼습니다.','201610311604','','201611011029',108),(53,109,'나의 게시물 \"sample\"에 좋아요를 남겼습니다.','201610311604','','201611011029',108),(54,109,'나의 게시물 \"sample\"에 좋아요를 남겼습니다.','201610311605','','201611011029',108),(55,109,'나의 게시물 \"sample\"에 좋아요를 남겼습니다.','201610311605','','201611011029',108),(56,109,'나의 게시물 \"sample\"을 0포인트로 구매하셨습니다.','201610311605','','201611011029',108),(57,109,'나의 게시물 \"sample\"에 댓글을 남겼습니다.','201610311610','','201611011029',108),(58,109,'나의 게시물 \"sample\"에 댓글을 남겼습니다.','201611011028','','201611011029',109),(59,39,'나의 게시물 \"test \"에 댓글을 남겼습니다.','201611011041','',NULL,108),(60,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011105','','201611021827',37),(61,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011105','','201611021827',37),(62,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011106','','201611021827',37),(63,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011106','','201611021827',37),(64,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011106','','201611021827',37),(65,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011106','','201611021827',37),(66,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011106','','201611021827',37),(67,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011106','','201611021827',37),(68,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011106','','201611021827',37),(69,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011106','','201611021827',37),(70,37,'나의 게시물 \"ㅎㅎㅎ\"에 댓글을 남겼습니다.','201611011106','','201611021827',37),(71,37,'나의 게시물 \"국화\"을 100포인트로 구매하셨습니다.','201611011519','','201611021827',26),(72,37,'나의 게시물 \"국화\"을 100포인트로 구매하셨습니다.','201611011528','','201611021827',26),(73,108,'나의 게시물 \"요청2\"에 댓글을 남겼습니다.','201611011559','','201611011600',108),(74,39,'나의 게시물 \"카테고리-전체-테스트\"에 댓글을 남겼습니다.','201611011601','',NULL,108),(75,37,'나의 게시물 \"국화\"을 100포인트로 구매하셨습니다.','201611011640','','201611021827',26),(76,39,'나의 게시물 \"fefefee\"을 0포인트로 구매하셨습니다.','201611011709','',NULL,37),(77,37,'나의 게시물 \"ggg\"에 좋아요를 남겼습니다.','201611021645','','201611021827',37),(78,37,'나의 게시물 \"ggg\"에 댓글을 남겼습니다.','201611021819','','201611021827',37),(79,39,'나의 게시물 \"프로젝트 수정\"을 0포인트로 구매하셨습니다.','201611031830','',NULL,39),(80,39,'나의 게시물 \"프로젝트 수정\"을 0포인트로 구매하셨습니다.','201611031830','',NULL,39),(81,39,'나의 게시물 \"프로젝트 수정\"을 0포인트로 구매하셨습니다.','201611031834','',NULL,39),(82,39,'나의 게시물 \"fefefee\"을 0포인트로 구매하셨습니다.','201611031835','',NULL,39);



/*Table structure for table t_category */



DROP TABLE IF EXISTS t_category;



CREATE TABLE t_category (
  category_code varchar(9)  NOT NULL COMMENT '카테고리코드',
  depth int(11) DEFAULT NULL COMMENT '뎁스',
  category_name varchar(50)  DEFAULT NULL COMMENT '카테고리명',
  PRIMARY KEY (category_code)
) ;



/*Data for the table t_category */



insert  into t_category(category_code,depth,category_name) values ('*',0,'전체'),('001',1,'의상'),('001001',2,'전통'),('001001001',3,'한복'),('001001002',3,'중국'),('001001003',3,'일본'),('001001004',3,'동양'),('001001005',3,'서양'),('001002',2,'남성복'),('001003',2,'여성복'),('001004',2,'아동복'),('001005',2,'패션잡화'),('001005001',3,'속옷'),('001005002',3,'모자'),('001005003',3,'장갑'),('001005004',3,'양말'),('001005005',3,'신발'),('001006',2,'액세서리'),('002',1,'산업'),('002001',2,'기계'),('002002',2,'기기/기구'),('002002001',3,'컴퓨터/통신'),('002002002',3,'가전'),('002002003',3,'가구'),('002002004',3,'완구'),('002003',2,'자동차/운송'),('002004',2,'로봇'),('002005',2,'식품'),('003',1,'시각'),('003001',2,'그래픽'),('003002',2,'영상'),('003003',2,'동영상'),('003004',2,'광고'),('003005',2,'포장'),('003006',2,'캐릭터'),('003007',2,'서체'),('004',1,'공예'),('004001',2,'금속'),('004002',2,'도자'),('004003',2,'목공예'),('004004',2,'수공예'),('004005',2,'유리'),('005',1,'공간'),('005001',2,'건축'),('005002',2,'인테리어'),('005003',2,'무대'),('005004',2,'도시/환경'),('006',1,'정보'),('006001',2,'경험/인터렉션'),('006002',2,'인터페이스'),('006003',2,'웹'),('006004',2,'게임'),('006005',2,'소리/음악'),('006006',2,'음식/요리법'),('007',1,'자연'),('007001',2,'동물'),('007002',2,'식물'),('007003',2,'지구'),('007004',2,'우주'),('007005',2,'디자인의 역사'),('008',1,'새분야');



/*Table structure for table t_design_preview_image */



DROP TABLE IF EXISTS t_design_preview_image;



CREATE TABLE t_design_preview_image (
  design_work_seq int(11) DEFAULT NULL COMMENT '작품seq',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  file_uri varchar(200)  DEFAULT NULL COMMENT '이미지URL',
  filename varchar(200)  DEFAULT NULL COMMENT '파일명',
  PRIMARY KEY (seq)
) ;



/*Data for the table t_design_preview_image */



insert  into t_design_preview_image(design_work_seq,seq,file_uri,filename) values (1,1,'/resources/km_upload/project_work_file/bddaf0e8-5c3a-4ccf-a274-745768766a4b.jpg','flower1.jpg'),(10,8,'/resources/km_upload/product/f67eba83-333c-49dd-be23-e2b707abdf1f.png','Picture3.png'),(11,9,'/resources/km_upload/product/6f2e60f6-a750-4d42-b2f2-1f989d924859.png','thumbIcon2.png'),(12,10,'/resources/km_upload/product/978b97bf-9d5f-43c2-b9d9-81a8439e531c.png','evt_bok3.png'),(13,11,'/resources/km_upload/product/cb15eb92-2b80-479f-b1da-999fdd041d78.jpg','@shop_map01.jpg'),(14,12,'/resources/km_upload/product/b3e9e97e-bc49-485b-a9dd-0354e65e5779.jpg','dwork1.jpg'),(15,13,'/resources/km_upload/product/f300fe51-4d2c-4946-9223-1501ef7106be.jpg','dwork1.jpg'),(16,14,'/resources/km_upload/product/5f8982bd-d23a-4936-b294-e052c0bed8ea.jpg','dwork1.jpg'),(18,16,'/resources/km_upload/product/44f10459-9a9c-4163-a316-be1d7286abfe.jpg','dwork1.jpg'),(20,17,'/resources/km_upload/product/69830e64-ec29-4fa3-b921-03e4d9a324bb.jpg','c7765c10-87a3-4dbe-9f22-072b6be93d3a.jpg'),(20,18,'/resources/km_upload/product/e5ad6e6d-5f2a-47b7-873e-c816dd72cb1e.jpg','Img0404_20151124173217_1.jpg'),(20,19,'/resources/km_upload/product/80249897-95b1-4a2a-914e-01caf26461cf.xlsx','OpenDesign_수정사항_check.xlsx'),(20,20,'/resources/km_upload/product/9e4e875d-fe61-4f25-b586-2fbe6b1df554.torrent','%5BtvN%5D 혼술남녀.E01.160905.720p-NEXT.mp4.torrent'),(21,21,'/resources/km_upload/product/34f30a0b-7900-4bf4-ba0c-da26c1c8c4ad.png','ico_attachment.png'),(22,22,'/resources/km_upload/product/3048a5da-71cd-4f5a-8d87-bb386588be65.png','161018_국민대메인디자인_수정.png'),(23,23,'/resources/km_upload/product/2718890f-1f9f-4a20-a091-620e56bb541f.png','161018_국민대메인디자인_수정.png'),(24,24,'/resources/km_upload/product/ae1ea68c-261b-4811-929c-ba2f4f3691c6.jpg','dwork3.jpg'),(26,26,'/resources/km_upload/product/cc756073-0361-40bd-a6fa-9353f2bbed3f.jpg','small_2.jpg'),(27,27,'/resources/km_upload/product/57fdfd9b-57bf-474a-b98d-bdffbdcf1baa.png','tttttt.png'),(28,28,'/resources/km_upload/product/83e4da35-4555-49f4-9a32-96f469620b7e.jpg','P0000037_OKO_3352-1.jpg'),(28,29,'/resources/km_upload/product/f0713489-dea6-48f2-b4e7-9466d606488d.jpg','RO51410221_w1000_h0.jpg'),(29,30,'/resources/km_upload/product/107c859c-5dba-4573-a461-9f61c844d890.png','emart_everyday3.png'),(30,31,'/resources/km_upload/product/97e10278-f42b-47af-9aef-8a9d444dd346.png','tttttt.png'),(31,32,'/resources/km_upload/product/bef62094-e18e-4f37-8ec5-1904bd79f75a.jpg','Img0404_20151124173217_1.jpg'),(32,33,'/resources/km_upload/product/d9753569-f368-4e8d-8d0d-aeba6a7cc8b2.png','emart_everyday3.png'),(32,34,'/resources/km_upload/product/5e3b19a8-96a2-4ef8-9065-a2fd75a1c3c1.gif','notice_1.gif'),(32,35,'/resources/km_upload/product/83023756-9b7d-4d59-bcd0-ff22ebda05f6.jsp','fcmPositionInfo.jsp'),(34,36,'/resources/km_upload/product/ebe7a845-4335-4945-b120-90761f0293cb.jpg','Chrysanthemum.jpg');



/*Table structure for table t_design_work */



DROP TABLE IF EXISTS t_design_work;



CREATE TABLE t_design_work (
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  view_cnt int(11) DEFAULT '0' COMMENT '조회수',
  title char(30)  DEFAULT NULL COMMENT '작품명',
  license varchar(3)  DEFAULT NULL COMMENT '라이센스',
  point int(11) DEFAULT NULL COMMENT '포인트',
  thumb_uri varchar(200)  DEFAULT NULL COMMENT '썸네일',
  contents varchar(1000)  DEFAULT NULL COMMENT '작품설명',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  tags varchar(200)  DEFAULT NULL COMMENT '테그',
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  update_time char(12)  DEFAULT NULL COMMENT '수정일시',
  del_flag char(1)  DEFAULT 'N' COMMENT '삭제 flag(Y,N)',
  PRIMARY KEY (seq)
) ;


/*Data for the table t_design_work */



insert  into t_design_work(seq,view_cnt,title,license,point,thumb_uri,contents,register_time,tags,member_seq,update_time,del_flag) values (1,47,'작품1','101',10,'/resources/image/main/img_portfolio1.jpg','gogogo','201609061200','|의상|',23,'201609061200','N'),(2,50,'작품222','001',20,'/resources/image/main/img_portfolio1.jpg','gogogo','201609070701','|의상|작품|',23,'201609071200','N'),(3,24,'작품333','100',23,'/resources/image/main/img_portfolio1.jpg','gogogo','201609070920','|의상|작품|',23,'201609071200','N'),(4,132,'작품444','101',11,'/resources/image/main/img_portfolio1.jpg','gogogo','201609070930','|의상|작품|',23,'201609071200','N'),(5,43,'작품222','101',33,'/resources/image/main/img_portfolio1.jpg','3344','201609071548','|의상|공간|정보|',24,'201609071548','N'),(6,154,'작품555','101',11,'/resources/image/main/img_portfolio1.jpg','ㅎㅎㅅㅎㅅ','201609071628','|의상|공간|정보|',28,'201609071628','N'),(10,14,'choihcpd','111',0,'D:/01.Dev/eclipse-jee-luna-SR2-win32-x86_64/workspace/com.opendesign/WebContent/resources/km_upload/product/13cf8044-aa56-4ebc-8f19-05e4c913271e.png','goood','201609291612','|tag1|tag2|tag3|',24,'201609291632','N'),(11,3,'산업디자인','111',0,'/resources/km_upload/product/840bbdc6-f844-4228-a994-b0b9e14b0161.png','간단한 설명 입니다. 올레 올레','201610041424','|산업|',39,'201610041424','N'),(12,4,'산업2 디자인','111',0,'/resources/km_upload/product/2cd08b2b-2bcc-4cf2-8094-9887aa67a13e.jpg','테스트 설명','201610041425','|태그|',39,'201610041425','N'),(13,12,'전체 작품','111',0,'/resources/km_upload/product/d2fb28b4-d1dd-4de7-8d66-cf1c8e020162.jpg','간단설명','201610041808','|전체 태그|',39,'201610041808','Y'),(14,15,'작품-shg1','111',0,'/resources/km_upload/product/6de507e2-95e8-464f-b4e8-c9b429bbdde4.jpg','ㅇㅇㅇㅇ','201610061626','|트렌드|',37,'201610061627','N'),(15,22,'작품-shg1','111',100,'/resources/km_upload/product/e5188ebf-87f6-4bfc-91be-6d8592a5d5d2.jpg','ㅇㅇㅇ','201610141117','|디자인|유행|',37,'201610141117','N'),(16,4,'작품-shg2','111',0,'/resources/km_upload/product/4548c0d8-7f42-4308-8abf-2f3b528a6e9f.jpg','ㅇㅇㅇㅇ','201610141434','|유행|',37,'201610141434','N'),(18,20,'작품-shg4','111',0,'/resources/km_upload/product/a12bf0d7-fb78-49ae-96ce-c4612d5896cb.jpg','dddd','201610141703','|유행|',37,'201610141720','N'),(19,9,'gogog','111',0,'/resources/km_upload/product/a3640764-51b9-4be2-90bf-7d408261f2b8.jpg','','201610211715','',37,'201610211715','Y'),(20,51,'테스트1','111',0,'/resources/km_upload/product/ded32e57-0466-492e-aa60-28a9ec206725.jpg','테스트 설명 입니다.','201610291716','|안녕|바이|',39,'201610291858','Y'),(21,11,'이미지 리사이즈','111',0,'/resources/km_upload/product/358f30c3-8d1b-45b7-82cc-a220f610d2d9.jpg','','201610292008','',39,'201610292008','N'),(22,1,'상업적이용 안함','101',500,'/resources/km_upload/product/33962277-61b2-4e4c-8fb7-c75bec63bd2a.png','ㄹㅇㄹㅇㄹㅇㄹㅇ','201610301828','',37,'201610301828','N'),(23,9,'상업적이용 함','111',0,'/resources/km_upload/product/f73ebdc9-d98a-43ef-8160-ce051f49e6ec.png','','201610301829','',37,'201610301829','N'),(24,12,'ggg','111',20,'/resources/km_upload/product/bd163959-45d2-4e61-aad2-d74eb18e27df.jpg','','201610301912','',37,'201610301912','N'),(25,5,'gggg','101',100,'/resources/km_upload/product/d83d734d-fb52-40ea-a521-a2272ebc559d.jpg','','201610301923','',37,'201610301924','N'),(26,4,'안녕용','111',0,'/resources/km_upload/product/4d48d467-bc55-4106-b3fb-a8a6fdb9cd79.jpg','','201610302034','',39,'201610302035','N'),(27,1,'tttttt','111',0,'/resources/km_upload/product/3f3376c4-78d5-44e5-937d-21f385affb35.png','','201610302038','',39,'201610302038','N'),(28,0,'ttttt','111',0,'/resources/km_upload/product/eb0ac8dd-3775-496c-a160-2e9f831ff669.png','','201610302042','',39,'201610302042','N'),(29,2,'tttttt','111',0,'/resources/km_upload/product/1c97cb13-224c-49b0-b05f-453af380626e.png','','201610302043','',39,'201610302043','N'),(30,0,'ttttt','111',0,'/resources/km_upload/product/993abf10-d7d7-4bb0-87b6-eb0d505487b7.png','','201610302045','',39,'201610302045','N'),(31,16,'fefefee','111',0,'/resources/km_upload/product/ad15292e-03ae-4d15-bc2d-e36d67a0f2f6.png','','201610302046','',39,'201610302046','N'),(32,46,'프로젝트 수정','111',0,'/resources/km_upload/product/c3212516-7f1c-403a-8d73-08f1c19244ec.jpg','','201610302053','',39,'201610302231','N'),(33,93,'sample','111',0,'/resources/km_upload/product/eecef715-7c27-423d-9784-7fb7bab4ed2f.png','','201610311604','',109,'201611011628','N'),(34,13,'국화','111',100,'/resources/km_upload/product/d79d62be-8a60-4090-904c-72772427be4c.jpg','국화','201611010120','|국화|',37,'201611010120','N');


/*Table structure for table t_design_work_category */



DROP TABLE IF EXISTS t_design_work_category;



CREATE TABLE t_design_work_category (
  design_work_seq int(11) NOT NULL COMMENT '작품seq',
  category_code varchar(9)  NOT NULL COMMENT '카테고리 코드',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (design_work_seq,category_code)
) ;



/*Data for the table t_design_work_category */



insert  into t_design_work_category(design_work_seq,category_code,register_time) values (1,'001','201609061200'),(2,'002','201609061200'),(3,'001','201609061200'),(4,'002','201609061200'),(5,'003','201609061200'),(6,'004','201609061200'),(10,'004','201609291632'),(11,'002','201610041424'),(12,'002','201610041425'),(13,'*','201610041808'),(14,'001','201610061627'),(15,'001','201610141117'),(16,'001','201610141434'),(18,'*','201610141720'),(19,'*','201610211715'),(20,'*','201610291858'),(21,'*','201610292008'),(22,'*','201610301828'),(23,'*','201610301829'),(24,'*','201610301912'),(25,'*','201610301924'),(26,'*','201610302035'),(27,'*','201610302038'),(28,'*','201610302042'),(29,'*','201610302043'),(30,'*','201610302045'),(31,'*','201610302046'),(32,'*','201610302231'),(33,'001001001','201611011628'),(34,'*','201611011447');



/*Table structure for table t_design_work_comment */



DROP TABLE IF EXISTS t_design_work_comment;



CREATE TABLE t_design_work_comment (
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '작품댓글seq',
  design_work_seq int(11) DEFAULT NULL COMMENT '작품seq',
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  contents varchar(3000)  DEFAULT NULL COMMENT '내용',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (seq)
) ;



/*Data for the table t_design_work_comment */



insert  into t_design_work_comment(seq,design_work_seq,member_seq,contents,register_time) values (1,1,23,'good','201609061200'),(2,1,26,'hello','201609201940'),(3,1,26,'rff','201609201943'),(4,1,26,'그래,좋아 ','201609201954'),(5,1,26,'ㅇㅇㅇ','201609201956'),(6,1,26,'gogogo','201609202002'),(7,1,26,'ok,go','201609202003'),(8,1,23,'잘 그렷다','201609202007'),(9,1,23,'ㅇ,그래','201609202007'),(10,1,26,'gogogo','201609211439'),(11,1,26,'hello','201609211439'),(12,1,26,'who is there','201609211439'),(13,1,26,'who is there','201609211440'),(14,4,26,'hello','201609211442'),(15,4,26,'hi','201609211442'),(16,4,26,'who','201609211442'),(17,4,26,'hhh','201609211442'),(18,4,26,'ggg','201609211442'),(19,4,23,'hhhhhh\\','201609231718'),(20,4,23,'aaeeee','201609231718'),(21,4,37,'hello','201609291513'),(22,2,37,'good work!','201609291522'),(23,33,108,'댓글','201610311610'),(24,33,109,'댓글','201611011028');



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
) ;



/*Data for the table t_design_work_file */



insert  into t_design_work_file(design_work_seq,comments,file_uri,filename,seq,register_time) values (1,'gogo','/resources/km_upload/project_work_file/bddaf0e8-5c3a-4ccf-a274-745768766a4b.jpg','flower.jpg',1,'201609061200'),(10,NULL,'D:/01.Dev/eclipse-jee-luna-SR2-win32-x86_64/workspace/com.opendesign/WebContent/resources/km_upload/product/28fee09b-3f1d-4f21-954f-34e5dd3528a8.png','filter_api_설치_001.png',6,'201609291612'),(15,NULL,'/resources/km_upload/product/27945ef4-dba6-4b19-97a6-7797abc062dd.jpg','dwork2.jpg',7,'201610141117'),(16,NULL,'/resources/km_upload/product/542e5c24-fe4b-4a29-80d2-f59c2c374d1d.jpg','dwork1.jpg',8,'201610141434'),(18,NULL,'/resources/km_upload/product/a18fbd29-aca5-444e-ae9b-0707efa763bb.jpg','gogog',15,'201610141720');



/*Table structure for table t_design_work_like */



DROP TABLE IF EXISTS t_design_work_like;



CREATE TABLE t_design_work_like (
  design_work_seq int(11) NOT NULL COMMENT '작품seq',
  member_seq int(11) NOT NULL COMMENT '회원seq',
  register_time char(18)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (design_work_seq,member_seq)
) ;



/*Data for the table t_design_work_like */



insert  into t_design_work_like(design_work_seq,member_seq,register_time) values (1,23,'201609061200'),(1,26,'201609201343'),(2,26,'201609201400'),(2,37,'201609291531'),(3,38,'201609291341'),(4,23,'201609061200'),(4,26,'201609201400'),(4,37,'201609291520'),(4,38,'201609291641'),(5,23,'201609061200'),(6,23,'201609071200'),(6,38,'201609291420'),(24,37,'201610302102'),(31,37,'201610302102'),(31,39,'201610302233'),(32,37,'201610302102'),(32,39,'201610310106'),(32,108,'201610311558');



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
  PRIMARY KEY (seq)
) ;



/*Data for the table t_member */



insert  into t_member(seq,email,passwd,uname,image_url,comments,member_type,point,fb_access_token,register_time,update_time) values (23,'chhan@naver.com','281e63590a813a9be5160a7fd42cbe34c01f044ee06a5c9cd90b848f559b55ce','한창호','/resources/km_upload/user_profile/00f802e0-2e1e-43c5-bb9d-65efe4fb0470.png','good work!!','11',10022,NULL,'201608261710','201610051650'),(24,'choihcpd@naver.com','3e0a3501a65b4a7bf889c6f180cc6e35747e5aaff931cc90b760671efa09aeac','최현철','/resources/km_upload/user_profile/7068d9b0-6bb6-4752-af8e-90c396cd8fd3.jpg','ㅎㅎ','11',10000,NULL,'201608261710','201608261710'),(26,'szg@naver.com','281e63590a813a9be5160a7fd42cbe34c01f044ee06a5c9cd90b848f559b55ce','송중기','/resources/image/common/pic_profile.jpg','comments','11',9700,NULL,'201608301511','201608301511'),(28,'kmc774@gmail.com','32b5346dd118b7fe8dd6856a16d121e88ebf44e7d8aff5124b2c4af8a4e2a684','윤상진','','상진 윤','11',10000,NULL,'201608310716','201608310716'),(29,'kmc774@wdfall.com','32b5346dd118b7fe8dd6856a16d121e88ebf44e7d8aff5124b2c4af8a4e2a684','윈드폴','','솔루션','11',10000,NULL,'201608311309','201608311309'),(30,'iamkyungyong@gmail.com','fbf2bed288f1abc12c350928c3cfd9b75f4437e76bab27cbe3d6a8e62e39f8e0','이경용','','','11',10000,NULL,'201608311326','201608311326'),(31,'borosolee@naver.com','de09073ea37c033315bb9b59adf11399e955dca0126ddbeebdaa4545bed5fc90','이형재','/resources/km_upload/user_profile/93afa706-f305-43ee-a394-faaa0fa28695.gif','윈드폴..','10',10000,NULL,'201609011003','201609231918'),(32,'borosolee@hanmail.net','ac7ebcdb95d80ee40606ec5ee044adeb0e3aee35078021873a8625aa1d3ab497','이형재rr','/resources/km_upload/user_profile/b68dbbe8-6bfb-47f1-b456-5be646b35d2c.png','윈드폴..rrrr','00',10000,NULL,'201609011017','201609232028'),(33,'aaaa@gmail.com','91a493759ac22cddb56316ab0485b5905d01700c7af5345a0344f48b55dc11e1','rrr','','aawqqqq','10',10000,NULL,'201609011652','201609011652'),(34,'borosolee@aaaae.com','e450a98e50964999779738e1821d42ad2d4032ec7931f36956888090bd43329f','이형재','/resources/km_upload/user_profile/97921b42-17d6-4cd6-a8f9-dfb6bcbc17e0.png','test update888','11',10000,NULL,'201609121721','201609211955'),(35,'borosolee@na.com','0442fdebac574a9ee1e14ac87de1cc9d4e186f0b017d1bd0154b07c100dcbf9b','이형재','/resources/km_upload/user_profile/1f75dfdf-8277-4c04-822f-6930538533a7.png','','10',10000,NULL,'201609121723','201609121723'),(36,'chhan@google.com','281e63590a813a9be5160a7fd42cbe34c01f044ee06a5c9cd90b848f559b55ce','송송','/resources/km_upload/user_profile/dc3ed515-0b95-4229-903b-41da5e8d3644.jpg','송송','11',10000,NULL,'201609221620','201609221620'),(37,'shg@naver.com','281e63590a813a9be5160a7fd42cbe34c01f044ee06a5c9cd90b848f559b55ce','송해교test','/resources/km_upload/user_profile/27a83ac2-0d29-4f1f-aa58-90c52c25ff34.jpg','송해교','11',10378,NULL,'201609221628','201611011450'),(38,'seaoh@wdfall.com','f6f2ea8f45d8a057c9566a33f99474da2e5c6a6604d736121650e2730c6fb0a3','오세천','/resources/km_upload/user_profile/2451a17d-3fd1-4830-9ad2-46c2d12c0e1e.jpg','오세천 입니다.','00',10000,NULL,'201609281658','201609281658'),(39,'weedbitch@nate.com','281e63590a813a9be5160a7fd42cbe34c01f044ee06a5c9cd90b848f559b55ce','오페이스북','/resources/km_upload/user_profile/c1d8d7a5-496f-467e-8494-692a2c01fc05.jpg','오세천 페이스북 회원가입 테스트 입니다.','10',10000,'EAAITnbkGIAMBAL4wYNuod87g27GuF448sbFs1FOl6nmaTPXZC8HBWf37fOoMAZB7jDnAIcWjETy5jk2yi70RIyQQ0NhEYZA369RAaAzXkUVWAhi7MK63KgjgeCsZB0vfp5UZB3PVJS602ifzDDYWAihRVvIcK0ZBbgEtAEzTnk36mJwEqVbWEN','201610031436','201610041008'),(40,'weedbutch@gmail.com','d0923aa2b543b2ef750877200e97b71073eab669b013cd567728cfd339d60018','신민아','/resources/km_upload/user_profile/6169abd7-74c2-46f5-975c-9b9743af3307.jpg','사랑해요','11',10000,NULL,'201610041449','201610041449'),(61,'weedbutch@hotmail.com','a5776cd09772bae30681e97e7831690a3da034b5661137123752bbb8636a0c2d','박민영','/resources/km_upload/user_profile/273b7943-145f-4b1b-b1e6-02a907e16e75.JPG','좋아요 박민영','01',10000,NULL,'201610042009','201610042009'),(96,'weedbutch3@gmail.com','d0923aa2b543b2ef750877200e97b71073eab669b013cd567728cfd339d60018','이쁜박민영','/resources/km_upload/user_profile/fa2d37fd-6949-4cd4-a70d-82b37f62c374.JPG','이뻐요 이뻐요','10',10000,NULL,'201610042204','201610042207'),(105,'sss@naver.com','2bea54742ffde92797a813317c721f8676447b93c71aee09ab1ae637fc6c05fe','sss','','sss','11',10000,NULL,'201610051523','201610051523'),(106,'chhan@wdfall.com','281e63590a813a9be5160a7fd42cbe34c01f044ee06a5c9cd90b848f559b55ce','chhan','','chhan','00',10000,NULL,'201610051625','201610061552'),(107,'seaoh2@wdfall.com','d0923aa2b543b2ef750877200e97b71073eab669b013cd567728cfd339d60018','hello','','','00',10000,NULL,'201610310031','201610310031'),(108,'bin@myfamis.net','e1b5b59686efa1732d25cbbe41e9164230063d03dc10b597a95e7b14869e2485','최한빈','','','11',10000,NULL,'201610311518','201611031702'),(109,'zeta1@naver.com','0ad42637fe5b16b6c53375e905136d5e210adfb0b7232c2779187292dac9b867','choi','','','00',10000,NULL,'201610311602','201611031336');



/*Table structure for table t_member_category */



DROP TABLE IF EXISTS t_member_category;



CREATE TABLE t_member_category (
  member_seq int(11) NOT NULL COMMENT '회원seq',
  category_code varchar(9)  NOT NULL COMMENT '카테고리코드',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (member_seq,category_code)
) ;



/*Data for the table t_member_category */



insert  into t_member_category(member_seq,category_code,register_time) values (23,'001','201610051650'),(25,'001','201608301456'),(27,'001001','201608310715'),(28,'001001','201608310716'),(29,'002','201608311309'),(31,'002','201609231918'),(32,'001','201609232028'),(33,'001001','201609011652'),(34,'001','201609211955'),(36,'002','201609221620'),(37,'002','201611011450'),(39,'001','201610041008'),(40,'002','201610041449'),(41,'001','201610041501'),(61,'*','201610042009'),(66,'001','201610042026'),(69,'001','201610042032'),(71,'001','201610042038'),(72,'001','201610042039'),(74,'001','201610042051'),(77,'001','201610042058'),(78,'001','201610042059'),(79,'001','201610042101'),(80,'001','201610042103'),(81,'001','201610042105'),(82,'001','201610042106'),(83,'001','201610042106'),(84,'001','201610042113'),(85,'001','201610042118'),(96,'005','201610042207'),(105,'001','201610051523'),(106,'001','201610061552'),(107,'*','201610310031'),(108,'*','201611031702'),(109,'*','201611031336');



/*Table structure for table t_order */



DROP TABLE IF EXISTS t_order;



CREATE TABLE t_order (
  design_work_seq int(11) DEFAULT NULL COMMENT '작품seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  point int(11) DEFAULT NULL COMMENT '포인트',
  delivery_info varchar(1000)  DEFAULT NULL COMMENT '배송정보',
  status varchar(1)  DEFAULT NULL COMMENT '상태',
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  PRIMARY KEY (seq)
) ;



/*Data for the table t_order */



insert  into t_order(design_work_seq,register_time,point,delivery_info,status,member_seq,seq) values (34,'201611011640',100,'받는 사람: aaa | 전화 번호: 0103333 | 주소: 광명 리더스빌딩 707',NULL,26,24),(31,'201611011709',0,'',NULL,37,25),(32,'201611031830',0,'',NULL,39,26),(32,'201611031830',0,'',NULL,39,27),(32,'201611031834',0,'',NULL,39,28),(31,'201611031835',0,'',NULL,39,29);



/*Table structure for table t_pgroup */



DROP TABLE IF EXISTS t_pgroup;



CREATE TABLE t_pgroup (
  group_name varchar(50)  NOT NULL,
  seq int(11) NOT NULL AUTO_INCREMENT,
  member_seq int(11) DEFAULT NULL,
  register_time char(12)  DEFAULT NULL,
  update_time char(12)  DEFAULT NULL,
  PRIMARY KEY (seq)
) ;



/*Data for the table t_pgroup */



insert  into t_pgroup(group_name,seq,member_seq,register_time,update_time) values ('마이크',7,24,'201609102050','201609102050'),('여캐',8,24,'201609102051','201609102051'),('그룹테스트..1',11,23,'201609130936','201609130936'),('그냥 그룹',14,38,'201609282309','201609282309'),('그룹A',15,38,'201609291447','201609291447'),('shg그룹',16,37,'201610141743','201610141743'),('송중기그룹1',18,26,'201610261701','201610261701'),('FB그룹',19,39,'201610302108','201610302108');



/*Table structure for table t_pgroup_project */



DROP TABLE IF EXISTS t_pgroup_project;



CREATE TABLE t_pgroup_project (
  project_group_seq int(11) NOT NULL,
  project_seq int(11) NOT NULL,
  register_time char(12)  DEFAULT NULL,
  PRIMARY KEY (project_group_seq,project_seq)
) ;



/*Data for the table t_pgroup_project */



insert  into t_pgroup_project(project_group_seq,project_seq,register_time) values (16,14,'201610201045'),(16,23,'201610191846'),(16,39,'201610191821'),(18,42,'201610261738'),(19,46,'201610302111');



/*Table structure for table t_pgroup_project_req */



DROP TABLE IF EXISTS t_pgroup_project_req;



CREATE TABLE t_pgroup_project_req (
  project_group_seq int(11) NOT NULL COMMENT '신청그룹seq',
  project_seq int(11) NOT NULL COMMENT '신청프로젝트seq',
  status char(1)  DEFAULT '0' COMMENT '상태:0대기,1성공,2실패',
  register_time char(12)  DEFAULT NULL COMMENT '신청일시',
  update_time char(12)  DEFAULT NULL COMMENT '변경일시',
  PRIMARY KEY (project_group_seq,project_seq)
)  COMMENT='그룹신청';



/*Data for the table t_pgroup_project_req */



insert  into t_pgroup_project_req(project_group_seq,project_seq,status,register_time,update_time) values (16,14,'1','201610201044','201610201045'),(16,23,'1','201610191846','201610191846'),(16,39,'1','201610191821','201610191821'),(18,42,'1','201610261737','201610261738'),(19,46,'1','201610302110','201610302111');



/*Table structure for table t_point_history */



DROP TABLE IF EXISTS t_point_history;



CREATE TABLE t_point_history (
  sign varchar(1)  DEFAULT NULL COMMENT '가감구분',
  amount int(11) DEFAULT NULL COMMENT '금액',
  accum_amount int(11) DEFAULT NULL COMMENT '누적결과프인트',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  order_seq int(11) DEFAULT NULL COMMENT '구매seq',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  comments varchar(1000)  DEFAULT NULL COMMENT '비고',
  PRIMARY KEY (seq)
) ;



/*Data for the table t_point_history */



insert  into t_point_history(sign,amount,accum_amount,register_time,member_seq,order_seq,seq,comments) values ('C',100,9700,'201611011640',26,24,47,'국화, 광명 리더스빌딩 707'),('S',100,10378,'201611011640',37,24,48,'국화, 광명 리더스빌딩 707'),('C',0,10378,'201611011709',37,25,49,'fefefee'),('S',0,10000,'201611011709',39,25,50,'fefefee'),('C',0,10000,'201611031830',39,26,51,'프로젝트 수정'),('S',0,10000,'201611031830',39,26,52,'프로젝트 수정'),('C',0,10000,'201611031830',39,27,53,'프로젝트 수정'),('S',0,10000,'201611031830',39,27,54,'프로젝트 수정'),('C',0,10000,'201611031834',39,28,55,'프로젝트 수정'),('S',0,10000,'201611031834',39,28,56,'프로젝트 수정'),('C',0,10000,'201611031835',39,29,57,'fefefee'),('S',0,10000,'201611031835',39,29,58,'fefefee');



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
) ;



/*Data for the table t_project */



insert  into t_project(public_yn,project_name,progress_status,seq,file_url,register_time,update_time,owner_seq,file_name,del_flag) values ('Y','윤상진 테스트','C',9,'/resources/km_upload/project/efa1d7cb-a93e-4c48-958b-a2fcf2679b93.jpeg','201608310718','201608310718',28,NULL,'N'),('Y','2016-09 졸업','P',10,'/resources/km_upload/project/77be13e2-7d8c-4cf7-981b-b6d47b8c534c.jpg','201608311042','201608311042',28,NULL,'N'),('Y','2015졸업','P',12,'/resources/km_upload/project/f4c323ed-9cde-419d-93d5-a71250a43435.jpg','201608311047','201608311047',28,NULL,'N'),('Y','2019-입학','P',13,'/resources/km_upload/project/67f2b940-6868-488d-b5bd-23611c8a0a6d.jpg','201608311134','201608311134',28,NULL,'N'),('N','프로젝트s1','P',14,'/resources/km_upload/project/f5bdcf42-3b36-4e98-9db5-b1664a2a2ec1.jpg','201609021026','201609021026',26,NULL,'N'),('Y','2016 방학','P',15,'/resources/km_upload/project/f0ace698-8b6e-4e4c-94ca-9240a6616d61.png','201609031622','201609031655',24,NULL,'N'),('N','2016  비공개','P',16,'/resources/km_upload/project/f0ace698-8b6e-4e4c-94ca-9240a6616d61.png','201609031739','201609031739',24,NULL,'N'),('Y','프로젝트--1','P',17,'/resources/km_upload/project/f5bdcf42-3b36-4e98-9db5-b1664a2a2ec1.jpg','201609071739','201609071739',23,NULL,'N'),('Y','프로젝트--2','P',18,'/resources/km_upload/project/f5bdcf42-3b36-4e98-9db5-b1664a2a2ec1.jpg','201609071739','201609071739',23,NULL,'N'),('Y','프로젝트--3','P',19,'/resources/km_upload/project/f5bdcf42-3b36-4e98-9db5-b1664a2a2ec1.jpg','201609071739','201609071739',23,NULL,'N'),('Y','프로젝트--4','P',20,'/resources/km_upload/project/f5bdcf42-3b36-4e98-9db5-b1664a2a2ec1.jpg','201609071739','201609071739',23,NULL,'N'),('Y','프로젝트--5','P',21,'/resources/km_upload/project/f5bdcf42-3b36-4e98-9db5-b1664a2a2ec1.jpg','201609071739','201609071739',23,NULL,'N'),('Y','프로-송1','P',22,'/resources/km_upload/project/153a327a-72cc-4beb-9888-eabb6847bc62.jpg','201609221820','201611021356',37,NULL,'N'),('Y','프로-송2','P',23,'/resources/km_upload/project/6cb87a66-4ad9-4046-9b85-18405cfbedbe.jpg','201609221821','201610071046',37,NULL,'Y'),('Y','프로-송3','C',24,'','201609221821','201610041438',37,NULL,'Y'),('Y','테스트 프로젝트프로젝트 ','P',25,'/resources/km_upload/project/0d04dc90-5d64-4a4e-bd89-ac95fb52daff.jpg','201609282016','201609282016',38,NULL,'N'),('N','할로롤로로로로ㅗ로로로로로롤','P',26,'/resources/km_upload/project/d27e914c-6c2f-46be-9127-b2f4bd79ee79.jpg','201609282115','201609282115',38,NULL,'N'),('Y','feffefe','P',27,'/resources/km_upload/project/ddf12701-578b-4a24-be8a-42dd87b7a11b.yml','201609282151','201609282151',38,NULL,'N'),('Y','아아아아아아아아아아아아아','P',28,'/resources/km_upload/project/b478f3dc-6469-424d-90a8-02d0c6b17ea2.jpg','201609282202','201609282202',38,NULL,'N'),('Y','파일명 테스트','P',29,'/resources/km_upload/project/836eca22-cf9c-4a5b-95b2-7c5e5c0eb6fa.jpg','201609282221','201609282221',38,'','N'),('Y','파일명 테스트22','P',30,'/resources/km_upload/project/7b9b4a2b-8ef8-44c1-8dfc-946830ec8444.jpg','201609282223','201609282223',38,'','N'),('Y','파일명 테스트333','P',31,'/resources/km_upload/project/1c0218a6-269d-4d35-9de7-4425cd8fd96e.jpg','201609282223','201609282223',38,'','N'),('Y','파일명 테스트 입니다.3','P',32,'/resources/km_upload/project/5914e8ce-1395-425b-843c-ff2e411fc63d.jpg','201609282226','201609282226',38,'','N'),('Y','테스터터터2','P',33,'/resources/km_upload/project/bd7aadf9-f5a0-454a-b057-d7c30a6c32e2.jpg','201609282227','201609282237',38,'@temp05.jpg','N'),('Y','사용자 테스트','P',34,'/resources/km_upload/project/4e8f69d3-aba5-4668-b4b6-dfa6b40caf0b.png','201609282254','201609291419',38,'bgGiftCoffee_on.png','N'),('Y','신규플젝','P',35,'/resources/km_upload/project/97ff287d-32ea-4f11-a4bc-cbd8c2fffeed.jpg','201609292108','201609292108',38,'@taamp_attend.jpg','N'),('Y','검색어테스트','P',36,'/resources/km_upload/project/a4c23f9e-5e34-4c93-9998-091e7735a151.jpg','201610041255','201610041255',39,'@temp05.jpg','N'),('Y','페이스북 프로젝트','P',37,'/resources/km_upload/project/f32ef5e5-aaad-48da-81b9-cd5e7a641106.jpg','201610041438','201610042003',39,'@temp02.jpg','N'),('Y','전체 프로젝트','P',38,'/resources/km_upload/project/b4476f8c-6941-4477-b096-3ac5e7bbea8f.jpg','201610041752','201610042012',39,'@temp02.jpg','N'),('Y','프로-shg1','P',39,'/resources/km_upload/project/af354946-2f2b-41b8-a2bb-1abdbcde79e7.jpg','201610061641','201610301542',37,'dwork2.jpg','N'),('N','shg_p1','C',40,'/resources/km_upload/project/01b879a9-3d7e-461d-b6e2-89cdc1c33cd6.jpg','201610211709','201611021825',37,'dwork3.jpg','N'),('N','젝젝1','P',41,'/resources/km_upload/project/4fa75bed-a4ff-4fc7-88d6-8592bd5dff52.jpg','201610261659','201610261659',26,'dwork1.jpg','N'),('N','프젝한1','P',42,'/resources/km_upload/project/104b4d77-88d8-4d80-bbca-957c90196e4b.jpg','201610261737','201610271428',23,'dwork1.jpg','N'),('Y','썸네일 테스트 프로젝트','P',43,'/resources/km_upload/project/404892ef-afed-4174-866f-62eca68ccd1b.png','201610291904','201610291904',39,'049-19-625_(39).png','N'),('Y','fijeijfei','P',44,'/resources/km_upload/project/ac050abf-b0d0-44c1-a91a-298577ce6b37.png','201610291905','201610291905',39,'03_(3).png','N'),('Y','썸네일 테스트','P',45,'/resources/km_upload/project/24d90681-c276-4c45-ba74-3ef5434678d1.png','201610291909','201610291909',39,'03_(3).png','N'),('Y','FB프로젝트','P',46,'/resources/km_upload/project/deb03188-cba9-4645-8ef4-ad8b9073e7ec.jpg','201610302108','201610302225',39,'RO51410221_w1000_h0.jpg','N'),('Y','test','P',47,'/Users/binn/Documents/workspace/com.opendesign/WebContent/resources/km_upload/project/e6db734a-7ee0-4c1c-96b6-21ea33a6d949.png','201611011736','201611011737',108,'스크린샷 2016-10-31 오전 9.51.18.png','N');



/*Table structure for table t_project_category */



DROP TABLE IF EXISTS t_project_category;



CREATE TABLE t_project_category (
  category_code varchar(9)  NOT NULL,
  project_seq int(11) NOT NULL,
  register_time char(12)  DEFAULT NULL,
  PRIMARY KEY (category_code,project_seq)
) ;



/*Data for the table t_project_category */



insert  into t_project_category(category_code,project_seq,register_time) values ('*',37,'201610042003'),('*',38,'201610042012'),('*',40,'201611021825'),('*',41,'201610261659'),('*',42,'201610271428'),('*',43,'201610291904'),('*',44,'201610291905'),('*',45,'201610291909'),('*',47,'201611011737'),('001',14,'201609021026'),('001',22,'201611021356'),('001',24,'201610041438'),('001',39,'201610301542'),('001',46,'201610302225'),('001001',9,'201608310718'),('001001',10,'201608311042'),('001001',12,'201608311047'),('001001',13,'201608311134'),('001001',15,'201609031655'),('003',26,'201609282115'),('003',34,'201609291419'),('004',36,'201610041255');



/*Table structure for table t_project_member */



DROP TABLE IF EXISTS t_project_member;



CREATE TABLE t_project_member (
  member_seq int(11) NOT NULL,
  project_seq int(11) NOT NULL,
  register_time char(12)  DEFAULT NULL,
  PRIMARY KEY (member_seq,project_seq)
) ;



/*Data for the table t_project_member */



insert  into t_project_member(member_seq,project_seq,register_time) values (23,12,'201608311047'),(23,15,'201609031655'),(23,34,'201609291419'),(23,42,'201610271428'),(24,9,'201608310718'),(24,10,'201608311042'),(26,22,'201611021356'),(26,41,'201610261659'),(37,24,NULL),(37,39,'201610301542'),(37,40,'201611021825'),(38,26,'201609282115'),(38,34,'201609291419'),(38,35,'201609292108'),(38,36,'201610041255'),(38,37,'201610042003'),(39,36,'201610041255'),(39,37,'201610042003'),(39,38,'201610042012'),(39,43,'201610291904'),(39,44,'201610291905'),(39,45,'201610291909'),(39,46,'201610302225'),(108,47,'201611011737');



/*Table structure for table t_project_subject */



DROP TABLE IF EXISTS t_project_subject;



CREATE TABLE t_project_subject (
  project_seq int(11) DEFAULT NULL COMMENT '프로젝트seq',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '주제seq',
  subject_name varchar(200)  DEFAULT NULL COMMENT '주제명',
  member_seq int(11) DEFAULT NULL COMMENT '생성회원seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (seq)
) ;



/*Data for the table t_project_subject */



insert  into t_project_subject(project_seq,seq,subject_name,member_seq,register_time) values (1,1,'주제test1',23,'201608301120'),(1,2,'주제test2',23,'201608301123'),(1,5,'주제hi',26,'201608301818'),(11,9,'주제',26,'201608311045'),(10,10,'주제1',28,'201608311047'),(10,11,'주제2',28,'201608311047'),(12,12,'1반',28,'201608311048'),(12,13,'2반',28,'201608311048'),(12,14,'주제3',28,'201608311059'),(12,15,'주제4',28,'201608311133'),(13,17,'주제1',28,'201608311135'),(12,19,'MySub',28,'201608311313'),(12,20,'test topic 1',30,'201608311327'),(12,21,'test topic 2',30,'201608311327'),(12,22,'test topic 3',30,'201608311336'),(13,23,'입학2',26,'201609011428'),(13,24,'final',26,'201609011811'),(13,25,'입학3',23,'201609130939'),(19,26,'rrrrrr',23,'201609131413'),(17,27,'ㅎㅎㅎ',26,'201609191315'),(22,28,'주제-송',37,'201609221824'),(22,29,'주제-2',37,'201609221826'),(22,30,'주제-3',37,'201609221826'),(22,31,'주제-4',37,'201609221826'),(22,33,'주제-6',37,'201609221827'),(22,34,'주제-7',37,'201609221827'),(22,35,'주제-8',37,'201609221827'),(23,36,'주제-1',37,'201609251952'),(23,37,'gogogo',37,'201609251956'),(17,38,'주제2',37,'201609252011'),(17,40,'주제3',37,'201609252015'),(17,41,'gggg',37,'201609252026'),(12,44,'주제라인 추가 ',38,'201609290912'),(12,45,'안녕주제라인추가',38,'201609290912'),(12,46,'주제라인 추가 ',38,'201609290913'),(12,47,'추가가가',38,'201609290939'),(12,48,'feefeffe',38,'201609290940'),(12,49,'feeff',38,'201609290941'),(12,50,'feefef',38,'201609290942'),(12,51,'feeffe',38,'201609290942'),(12,52,'fefeef',38,'201609290943'),(12,53,'마지막 주제1',38,'201609290949'),(12,54,'마지막 주제3',38,'201609290949'),(12,55,'마지막 주제4',38,'201609290950'),(12,56,'마지막 주제5',38,'201609290950'),(12,57,'마지막 주제6',38,'201609290951'),(12,58,'마지막 주게7',38,'201609290953'),(12,59,'마지막 주게8',38,'201609290953'),(12,60,'마지막 주게9',38,'201609290953'),(12,61,'마지막 주게11',38,'201609290954'),(12,62,'ㄷㄹㄷㄹㄹ',38,'201609290958'),(26,63,'주제 추가 추가',38,'201609291417'),(13,64,'나의 주제 입님',38,'201609291532'),(34,65,'복사된 주제입니',38,'201609291951'),(34,66,'Work다중입력',38,'201609292009'),(34,67,'버전테스트',38,'201609292044'),(34,68,'FEIJFIE',38,'201609292054'),(35,69,'퍼가기테스트',38,'201609292108'),(35,70,'퍼가는 곳',38,'201609292109'),(22,73,'주제9',26,'201610051746'),(39,74,'gogo',37,'201610201605'),(40,75,'ggg',37,'201610211712'),(24,76,'ddd',37,'201610251404'),(45,77,'썸네일 테스트 ',39,'201610291913'),(46,78,'Minyoung',39,'201610302109'),(46,80,'테스트 주제',39,'201610302152'),(46,81,'안녕',39,'201610302200');

insert  into t_project_subject(project_seq,seq,subject_name,member_seq,register_time) values(39,83,'주제2',37,'201611101722'),(39,84,'주제3',37,'201611101722');

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
  PRIMARY KEY (seq)
) ;



/*Data for the table t_project_work */



insert  into t_project_work(title,contents,seq,project_subject_seq,member_seq,register_time,refer_project_work_seq,last_ver,del_flag) values ('작품1-mod2','설명1-mod2',5,1,23,'201608301401',NULL,'1.5','N'),('작품2','설명2',6,1,23,'201608301404',NULL,'1.0','N'),('작품3','설명3',7,1,23,'201608301408',NULL,'1.0','N'),('작품21','설명21',8,2,23,'201608301408',NULL,'1.0','N'),('작품22','설명22',9,2,23,'201608301409',NULL,'1.0','N'),('Desert','Desert',13,9,26,'201608311045',NULL,'1.0','N'),('화이팅.','열심히.',14,12,28,'201608311049',NULL,'1.0','N'),('우리도','\\1.png',15,13,28,'201608311049',NULL,'1.1','N'),('작품1','작품입니다.',16,14,28,'201608311059',NULL,'1.0','N'),('작품입니다','작품 설명입니다.',17,14,28,'201608311100',NULL,'1.0','N'),('제목','내용',18,15,28,'201608311133',NULL,'1.0','N'),('키보드','\\꼬리물고3D.jpg',19,17,28,'201608311135',NULL,'1.1','N'),('New1','Description',20,19,28,'201608311314',NULL,'1.0','N'),('Lighthouse','\\Chrysanthemum.jpg',21,17,26,'201608311845',NULL,'1.1','N'),('Desert','Desert',23,17,26,'201608311906',NULL,'1.0','N'),('Lighthouse','Lighthouse23',24,17,26,'201608312013',NULL,'1.0','N'),('Penguins','Penguins',25,23,26,'201609011430',NULL,'1.0','N'),('Lighthouse','\\Chrysanthemum.jpg',26,23,26,'201609011802',21,'1.0','N'),('Lighthouse','\\Chrysanthemum.jpg',27,23,26,'201609011803',21,'1.1','N'),('Lighthouse','\\Jellyfish.jpg',28,24,26,'201609011812',21,'1.4','N'),('Lighthouse','Lighthouse23',29,24,26,'201609021712',24,'1.0','N'),('Lighthouse','\\Koala.jpg',30,24,26,'201609051126',27,'1.2','N'),('ㅁㅁㅁㅁㅁㅁ','C:\\Users\\boros\\Desktop\\수정2.png',32,23,23,'201609130941',31,'1.1','N'),('Lighthouse','\\Koala.jpg',33,23,23,'201609131024',30,'1.2','N'),('ㅁㅁㅁㅁ','ㄷㄷㄷㄷㄷㄷ',34,26,23,'201609131413',NULL,'1.0','N'),('그림','그림',35,28,37,'201609221825',NULL,'1.0','N'),('Tulips','\\Penguins.jpg',36,28,37,'201609221826',NULL,'1.1','N'),('Chrysanthemum','Chrysanthemum',37,29,37,'201609221827',NULL,'1.0','N'),('Desert','Desert',38,29,37,'201609221828',NULL,'1.0','N'),('Tulips','Tulips',39,30,37,'201609221828',36,'1.0','N'),('Koala','Koala',40,31,26,'201609221851',NULL,'1.0','N'),('Desert','Desert',41,31,26,'201609221852',NULL,'1.0','N'),('Desert','Desert',42,30,26,'201609221943',NULL,'1.0','N'),('Lighthouse','Lighthouse',43,30,26,'201609221946',NULL,'1.0','N'),('Hydrangeas','Hydrangeas',44,27,37,'201609252012',NULL,'1.0','N'),('Jellyfish','Jellyfish',45,27,37,'201609252013',NULL,'1.0','N'),('Koala','\\Hydrangeas.jpg',46,38,37,'201609252014',NULL,'1.1','N'),('Jellyfish','Jellyfish',48,40,37,'201609252023',45,'1.0','N'),('desert','Desert',49,40,37,'201609252023',NULL,'1.0','N'),('Hydrangeas','Hydrangeas',50,41,37,'201609252026',NULL,'1.0','N'),('Tulips','Tulips',51,41,37,'201609252040',NULL,'1.0','N'),('img_sample','img_sample',52,25,37,'201609261831',NULL,'1.0','N'),('pic_profile','pic_profile',53,25,37,'201609261834',NULL,'1.0','N'),('pic_profile2','pic_profile2',54,25,37,'201609261836',NULL,'1.0','N'),('제목모ㅗㅗㅗㅗㅗㅗㅗㅗㅗㅗㅗ','제목모ㅗㅗㅗㅗㅗㅗㅗㅗㅗㅗㅗ제목모ㅗㅗㅗㅗㅗㅗㅗㅗㅗㅗㅗ제목모ㅗㅗㅗㅗㅗㅗㅗㅗㅗㅗㅗ제목모ㅗㅗㅗㅗㅗ',55,20,38,'201609291029',NULL,'1.0','N'),('안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕','안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕안녕',56,20,38,'201609291031',NULL,'1.0','N'),('제목 입니다.123','작품 설명입니다.작품 설명입니다.작품 설명입니다.작품 설명입니다.123',57,12,38,'201609291059',NULL,'1.1','N'),('수정','설명 수정수정수정',61,19,38,'201609291450',NULL,'1.1','N'),('작은 이미지 제목','작은 이미지 설명',62,65,38,'201609291951',59,'1.0','N'),('행운의 룰렛 이벤트 소문내기','SNS로 이벤트를 소문내주신 고객 중 10분을 추첨하여 10,000원 할인 쿠폰을 드립니다',63,65,38,'201609291951',58,'1.1','N'),('제목:jpeg 후 png 업로드-3차','설명:jpeg 후 png 업로드 테스트 입니다.-3차',64,66,38,'201609292010',NULL,'1.1','N'),('버전 테스트 제목-1.1','버전 테스트 설명',65,67,38,'201609292044',NULL,'1.1','N'),('퍼가기 제목','퍼가기 설명',67,69,38,'201609292109',NULL,'1.0','N'),('퍼가기 제목','퍼가기 설명',68,70,38,'201609292110',67,'1.0','N'),('ddd','ddd',69,75,37,'201610211712',NULL,'1.0','N'),('ddd','ddd',70,76,37,'201610251405',NULL,'1.0','N'),('ggg','ggg',71,75,37,'201610251635',NULL,'1.1','N'),('썸네일 테스트','작품 썸네일 리사이징',72,77,39,'201610291932',NULL,'1.2','N'),('ddd','ddd',73,74,37,'201610301542',NULL,'1.0','N'),('ㅆㅅ','ㅆㅆㅆㅅ',74,78,39,'201610302109',NULL,'1.0','N'),('ㅅㄷㄴ','ㄸㄹㄹㄹㄷ',75,80,39,'201610302152',NULL,'1.0','N'),('ㅇㅇㅇ','ㅇㅇㅇ',77,74,37,'201610302206',NULL,'1.0','N'),('ㅎㅎㅎ','ㅎㅎㅎ',78,74,37,'201610302213',NULL,'1.1','N'),('test ','test',79,81,39,'201610302326',NULL,'1.0','N');


alter table `t_project_work` 
   add column `thumb_uri` varchar(300) NULL COMMENT '썸네일';

/*Table structure for table t_project_work_comment */



DROP TABLE IF EXISTS t_project_work_comment;



CREATE TABLE t_project_work_comment (
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  contents varchar(3000)  DEFAULT NULL COMMENT '내용',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '작품댓글seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  project_work_seq int(11) DEFAULT NULL COMMENT '작품seq',
  PRIMARY KEY (seq)
) ;



/*Data for the table t_project_work_comment */



insert  into t_project_work_comment(member_seq,contents,seq,register_time,project_work_seq) values (26,'kawaii',5,'201609051548',30),(26,'gogo',6,'201609051549',30),(26,'ggg',7,'201609051550',30),(26,'Koala',8,'201609051550',30),(26,'hhh',9,'201609051551',30),(23,'ㄱㄱㄱㄱㄱ',10,'201609130940',31),(26,'ㅎㅎㅎ',11,'201609191831',33),(26,'good',12,'201609201403',30),(26,'hi',14,'201609201546',30),(26,'gogogo',15,'201609201611',30),(26,'abc',16,'201609201620',30),(26,'bbb',17,'201609201621',30),(26,'ccc',18,'201609201621',30),(26,'ddd',19,'201609201621',30),(26,'ggg',20,'201609201903',29),(26,'그래',21,'201609201911',30),(26,'ㅇㅇ',22,'201609201912',30),(26,'gogo',23,'201609211417',29),(37,'ㅇㅇㅇ',25,'201609221828',36),(26,'팽긴',26,'201609221850',36),(26,'ddd',27,'201609221856',36),(38,'댓글 테스트 입니다.',28,'201609281659',45),(38,'댓글 테스트 입니다. ',29,'201609281700',46),(38,'댓글 테스트 입니다.',30,'201609281915',48),(38,'댓글 테스트',31,'201609281916',51),(38,'댓글 테스트2',32,'201609281916',51),(38,'댓글 테스트3',33,'201609281926',51),(38,'댓글 테스트4',34,'201609281926',51),(38,'댓글 테스트 5',35,'201609281930',51),(38,'댓글 테스트6',36,'201609281940',51),(38,'댓글 테스트7',37,'201609281940',51),(38,'댓글 테스트8',38,'201609281943',51),(38,'댓글테스트9',39,'201609281944',51),(38,' 댓글 테스트3',40,'201609281946',51),(38,'댓글 테스트 11',41,'201609281947',51),(38,'댓글 테스트 12',42,'201609281948',51),(38,'댓글 테스트13',43,'201609281949',51),(38,'댓글 테스트14',44,'201609281953',51),(38,'댓글 테스트 15',45,'201609281955',51),(38,'댓글 테스트 16',46,'201609281956',51),(38,'댓글 테스트 17',47,'201609281957',51),(38,'댓글 테스트 18',48,'201609281958',51),(38,'댓글 테스트 19',49,'201609282010',51),(38,'댓글 테스트 20',50,'201609282011',51),(38,'댓글 테스트 댓글 테스트 댓글 테스트 댓글 테스트 댓글 테스트 댓글 테스트 댓글 테스트 댓',51,'201609291345',58),(38,'댓글 테스트 댓글 테스트 댓글 테스트 댓글 테스트 댓글 테스트 댓글 테스트 댓글 테스트 댓',52,'201609291345',58),(38,'너무 좋네요....',53,'201609292003',50),(39,'페이스북으로 댓글이나 달아볼까? 로그인 후 처리 테스트 입니다.',54,'201610031448',68),(39,'또한번 달아볼까요?',55,'201610031449',68),(108,'댓글',56,'201611011041',79),(37,'ㅇㅇㅇㅇ',57,'201611011105',78),(37,'dddd',58,'201611011105',78),(37,'gggg',59,'201611011106',78),(37,'hhhh',60,'201611011106',78),(37,'dddd',61,'201611011106',78),(37,'sdf',62,'201611011106',78),(37,'ga',63,'201611011106',78),(37,'ttt',64,'201611011106',78),(37,'sdfsdf',65,'201611011106',78),(37,'eeee',66,'201611011106',78),(37,'dddd',67,'201611011106',78),(37,'ddd',68,'201611021819',71);



/*Table structure for table t_project_work_like */



DROP TABLE IF EXISTS t_project_work_like;



CREATE TABLE t_project_work_like (
  member_seq int(11) NOT NULL,
  project_work_seq int(11) NOT NULL,
  register_time char(12)  DEFAULT NULL,
  PRIMARY KEY (member_seq,project_work_seq)
) ;



/*Data for the table t_project_work_like */



insert  into t_project_work_like(member_seq,project_work_seq,register_time) values (23,27,'201609221718'),(23,28,'201609051336'),(23,30,'201609131023'),(23,31,'201609130940'),(23,32,'201609130944'),(23,34,'201609191355'),(26,24,'201609200943'),(26,25,'201609201355'),(26,29,'201609201355'),(26,33,'201609201107'),(37,36,'201609221828'),(37,70,'201610251405'),(38,59,'201609291333'),(39,68,'201610031449');



/*Table structure for table t_project_work_member */



DROP TABLE IF EXISTS t_project_work_member;



CREATE TABLE t_project_work_member (
  member_seq int(11) NOT NULL COMMENT '참여회원seq',
  project_work_seq int(11) NOT NULL COMMENT '작품seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (member_seq,project_work_seq)
) ;



/*Data for the table t_project_work_member */



insert  into t_project_work_member(member_seq,project_work_seq,register_time) values (23,5,'201608301427'),(23,6,'201608301405'),(23,7,'201608301408'),(23,8,'201608301408'),(23,9,'201608301409'),(23,21,'201609011758'),(23,23,'201608312012'),(23,30,'201609051150'),(23,32,'201609130948'),(23,33,'201609131024'),(23,34,'201609131413'),(24,21,'201609011758'),(24,24,'201609021711'),(26,13,'201608311045'),(26,21,'201609011758'),(26,23,'201608312012'),(26,24,'201609021711'),(26,25,'201609011430'),(26,26,'201609011802'),(26,27,'201609011803'),(26,28,'201609021726'),(26,29,'201609021712'),(26,30,'201609051150'),(26,40,'201609221851'),(26,41,'201609221852'),(26,42,'201609221943'),(26,43,'201609221946'),(28,14,'201608311049'),(28,15,'201608311049'),(28,16,'201608311059'),(28,17,'201608311100'),(28,18,'201608311133'),(28,19,'201608311135'),(28,20,'201608311314'),(28,56,'201609291031'),(34,34,'201609131413'),(37,35,'201609221825'),(37,36,'201609221829'),(37,37,'201609221827'),(37,38,'201609221828'),(37,39,'201609221828'),(37,44,'201609252012'),(37,45,'201609252013'),(37,46,'201609252015'),(37,48,'201609252023'),(37,49,'201609252023'),(37,50,'201609252026'),(37,51,'201609252040'),(37,52,'201609261831'),(37,53,'201609261834'),(37,54,'201609261836'),(37,69,'201610211712'),(37,70,'201610251405'),(37,71,'201611021644'),(37,73,'201610301542'),(37,77,'201610302208'),(37,78,'201610302224'),(38,55,'201609291029'),(38,56,'201609291031'),(38,57,'201609291105'),(38,61,'201609291452'),(38,62,'201609291951'),(38,63,'201609291951'),(38,64,'201609292038'),(38,65,'201609292047'),(38,67,'201609292109'),(38,68,'201609292110'),(38,72,'201610292003'),(38,74,'201610302109'),(39,72,'201610292003'),(39,74,'201610302109'),(39,75,'201610302152'),(39,79,'201610302326');



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
) ;



/*Data for the table t_project_work_ver */



insert  into t_project_work_ver(project_work_seq,seq,ver,file_uri,register_time,filename) values (5,3,'1.0','/resources/km_upload/project_work_file/3c0396cc-dd45-422f-8e8a-b28d7523575a.jpg','201608301401','Hydrangeas.jpg'),(6,4,'1.0','/resources/km_upload/project_work_file/3c0396cc-dd45-422f-8e8a-b28d7523575a.jpg','201608301405','Hydrangeas.jpg'),(7,5,'1.0','/resources/km_upload/project_work_file/3c0396cc-dd45-422f-8e8a-b28d7523575a.jpg','201608301408','Hydrangeas.jpg'),(8,6,'1.0','/resources/km_upload/project_work_file/3c0396cc-dd45-422f-8e8a-b28d7523575a.jpg','201608301408','Hydrangeas.jpg'),(9,7,'1.0','/resources/km_upload/project_work_file/3c0396cc-dd45-422f-8e8a-b28d7523575a.jpg','201608301409','Hydrangeas.jpg'),(5,8,'1.1','/resources/km_upload/project_work_file/bddaf0e8-5c3a-4ccf-a274-745768766a4b.jpg','201608301416','Hydrangeas.jpg'),(5,9,'1.2','/resources/km_upload/project_work_file/3c0396cc-dd45-422f-8e8a-b28d7523575a.jpg','201608301427','Hydrangeas.jpg'),(5,10,'1.3','/resources/km_upload/project_work_file/bddaf0e8-5c3a-4ccf-a274-745768766a4b.jpg','201608301428','Hydrangeas.jpg'),(5,11,'1.4','/resources/km_upload/project_work_file/3c0396cc-dd45-422f-8e8a-b28d7523575a.jpg','201608301430','Hydrangeas.jpg'),(5,12,'1.5','/resources/km_upload/project_work_file/bddaf0e8-5c3a-4ccf-a274-745768766a4b.jpg','201608301431','Hydrangeas.jpg'),(13,19,'1.0','/resources/km_upload/project_work_file/c322e742-de2b-4b01-a058-5d40f40c716d.jpg','201608311045','Desert.jpg'),(14,20,'1.0','/resources/km_upload/project_work_file/68b02bb9-8623-450a-817e-6ce25d95a48f.jpg','201608311049','b5f6ac19b09de0b27fe57feaaed99dcf.jpg'),(15,21,'1.0','/resources/km_upload/project_work_file/56493c08-47ac-473f-9863-5f61436e6de4.JPG','201608311049','this is my world.JPG'),(16,22,'1.0','/resources/km_upload/project_work_file/812bc4a2-0d2e-4c2c-857c-bc85cf6be61e.jpg','201608311059','f192c64ceea0d39f132eb42c76b498f8.jpg'),(17,23,'1.0','/resources/km_upload/project_work_file/2e31e30f-d140-4b1d-9255-03700d014c36.jpg','201608311100','68a88b932f30a1d0682f608ab1776b23.jpg'),(18,24,'1.0','/resources/km_upload/project_work_file/7956edd8-08eb-494e-afae-9dc42d60e107.jpg','201608311133','68a88b932f30a1d0682f608ab1776b23.jpg'),(19,25,'1.0','/resources/km_upload/project_work_file/35d797e8-48fd-41bd-a5ef-a53cdf549abd.jpeg','201608311135','etnews_20141222132208990.jpeg'),(19,26,'1.1','/resources/km_upload/project_work_file/beb1e6e1-491c-4cf3-ac6e-ca609bcf5e0c.jpg','201608311135','꼬리물고3D.jpg'),(15,27,'1.1','/resources/km_upload/project_work_file/6804e451-c4bf-4b7c-8369-de059701b8ae.png','201608311312','1.png'),(20,28,'1.0','/resources/km_upload/project_work_file/133c8cde-98d5-459d-808f-2c4ba3ef080a.jpeg','201608311314','2.jpeg'),(21,29,'1.0','/resources/km_upload/project_work_file/c19154b4-f457-4975-9444-80adb0c4c4c5.jpg','201608311845','Lighthouse.jpg'),(23,31,'1.0','/resources/km_upload/project_work_file/7b3bfe18-a4ac-4212-964e-be0f825faaf6.jpg','201608311906','Desert.jpg'),(24,32,'1.0','/resources/km_upload/project_work_file/a975ec4a-f69b-4eeb-83bc-76f82f8a9631.jpg','201608312013','Lighthouse.jpg'),(25,33,'1.0','/resources/km_upload/project_work_file/2f87de6d-f8e3-4764-99ea-227f3ae5eccd.jpg','201609011430','Penguins.jpg'),(21,34,'1.1','/resources/km_upload/project_work_file/4eb3b70b-f520-4f16-908e-4aebc2d418f2.jpg','201609011758','Chrysanthemum.jpg'),(26,35,'1.0','/resources/km_upload/project_work_file/c19154b4-f457-4975-9444-80adb0c4c4c5.jpg','201609011802','Lighthouse.jpg'),(27,36,'1.1','/resources/km_upload/project_work_file/4eb3b70b-f520-4f16-908e-4aebc2d418f2.jpg','201609011803','Chrysanthemum.jpg'),(28,37,'1.1','/resources/km_upload/project_work_file/4eb3b70b-f520-4f16-908e-4aebc2d418f2.jpg','201609011812','Chrysanthemum.jpg'),(28,38,'1.2','/resources/km_upload/project_work_file/b2179875-8196-41a0-b95d-81ab283798c9.jpg','201609011812','Hydrangeas.jpg'),(29,39,'1.0','/resources/km_upload/project_work_file/a975ec4a-f69b-4eeb-83bc-76f82f8a9631.jpg','201609021712','Lighthouse.jpg'),(28,40,'1.3','/resources/km_upload/project_work_file/14cfe7ce-0447-4586-b198-3eaf0a7a9cf0.jpg','201609021722','Koala.jpg'),(28,41,'1.4','/resources/km_upload/project_work_file/9a1ccc7c-6f70-4b89-8f31-7c0634dbf805.jpg','201609021726','Jellyfish.jpg'),(30,42,'1.1','/resources/km_upload/project_work_file/4eb3b70b-f520-4f16-908e-4aebc2d418f2.jpg','201609051126','Chrysanthemum.jpg'),(30,43,'1.2','/resources/km_upload/project_work_file/446bef77-71c4-448b-9dc2-58b4d29bd0dd.jpg','201609051150','Koala.jpg'),(32,45,'1.0','/resources/km_upload/project_work_file/57660caf-e268-4ddc-ac41-c5a2d4eafa81.png','201609130941','수정1.png'),(32,46,'1.1','/resources/km_upload/project_work_file/13e1329e-3faf-4006-8fbf-b84c69b7b41f.png','201609130948','수정2.png'),(33,47,'1.2','/resources/km_upload/project_work_file/446bef77-71c4-448b-9dc2-58b4d29bd0dd.jpg','201609131024','Koala.jpg'),(34,48,'1.0','/resources/km_upload/project_work_file/d1609b85-6f87-4a63-aa07-94089e481e67.png','201609131413','수정1.png'),(35,49,'1.0','/resources/km_upload/project_work_file/71cb6ac9-6335-4461-b34c-748dc547106b.jpg','201609221825','pic_profile3.jpg'),(36,50,'1.0','/resources/km_upload/project_work_file/a4b75280-2b05-4e34-a96e-628adfc19a67.jpg','201609221826','Tulips.jpg'),(37,51,'1.0','/resources/km_upload/project_work_file/8a5239ed-333e-43c1-8859-d54bf33a4e30.jpg','201609221827','Chrysanthemum.jpg'),(38,52,'1.0','/resources/km_upload/project_work_file/ae506fc3-32d7-48bc-9142-24734daee7b0.jpg','201609221828','Desert.jpg'),(39,53,'1.0','/resources/km_upload/project_work_file/a4b75280-2b05-4e34-a96e-628adfc19a67.jpg','201609221828','Tulips.jpg'),(36,54,'1.1','/resources/km_upload/project_work_file/10ba5332-b80b-459f-81be-958c65eeb95f.jpg','201609221829','Penguins.jpg'),(40,55,'1.0','/resources/km_upload/project_work_file/91012042-8c0f-4bdf-93f5-ac761f070ad3.jpg','201609221851','Koala.jpg'),(41,56,'1.0','/resources/km_upload/project_work_file/ea454bcf-38dd-4d55-bad8-5949a32485af.jpg','201609221852','Desert.jpg'),(42,57,'1.0','/resources/km_upload/project_work_file/d1861eb7-aee2-42d8-9e1e-94eb41049e28.jpg','201609221943','Desert.jpg'),(43,58,'1.0','/resources/km_upload/project_work_file/ea8f9034-4519-43c8-b0cb-c1fe7b192f97.jpg','201609221946','Lighthouse.jpg'),(44,59,'1.0','/resources/km_upload/project_work_file/3ce3178a-0ebd-450c-886c-4ee1fc4d429a.jpg','201609252012','Hydrangeas.jpg'),(45,60,'1.0','/resources/km_upload/project_work_file/1cc7caeb-7fa2-4d07-9a00-d245b6129273.jpg','201609252013','Jellyfish.jpg'),(46,61,'1.0','/resources/km_upload/project_work_file/e9fb4cb8-b907-4dbc-9552-6d159267a23d.jpg','201609252014','Koala.jpg'),(46,62,'1.1','/resources/km_upload/project_work_file/471b18f4-ad40-4bbc-a4ae-533410818a1c.jpg','201609252015','Hydrangeas.jpg'),(48,64,'1.0','/resources/km_upload/project_work_file/1cc7caeb-7fa2-4d07-9a00-d245b6129273.jpg','201609252023','Jellyfish.jpg'),(49,65,'1.0','/resources/km_upload/project_work_file/f3faafc3-c6f0-4a57-b157-7a7ab9289a40.jpg','201609252023','Desert.jpg'),(50,66,'1.0','/resources/km_upload/project_work_file/426be6ad-d26c-4d2b-930a-4e5f808b6668.jpg','201609252026','Hydrangeas.jpg'),(51,67,'1.0','/resources/km_upload/project_work_file/6256977a-a73e-498c-a419-70cecf0f8fdf.jpg','201609252040','Tulips.jpg'),(52,68,'1.0','/resources/km_upload/project_work_file/7607e680-66f2-41f9-8aaa-8de58a86964c.jpg','201609261831','img_sample.jpg'),(53,69,'1.0','/resources/km_upload/project_work_file/380ab08b-49ba-4ed6-baa4-afcdeb2c767f.jpg','201609261834','pic_profile.jpg'),(54,70,'1.0','/resources/km_upload/project_work_file/8c084c1d-9b34-48f6-98e9-ecae52cbf807.jpg','201609261836','pic_profile2.jpg'),(55,71,'1.0','/resources/km_upload/project_work_file/649f85c9-2719-4f59-9dc7-8d9ffa3f6341.jpg','201609291029','@temp02.jpg'),(56,72,'1.0','/resources/km_upload/project_work_file/40f5092d-d34d-45da-a172-837676b9a7ce.png','201609291031','bg_evt_bug.png'),(57,73,'1.0','/resources/km_upload/project_work_file/2cf731bf-2d8d-4b78-934b-31c51c0a0805.jpg','201609291059','@temp03.jpg'),(57,74,'1.1','/resources/km_upload/project_work_file/3c39fd3e-bbc7-4ce6-90ca-e3d7b06856d6.jpg','201609291100','@temp05.jpg'),(57,75,'1.1','/resources/km_upload/project_work_file/6d751652-4687-4c38-99fd-4d532d5e1df1.jpg','201609291105','evt_fortune2_3.jpg'),(61,81,'1.0','/resources/km_upload/project_work_file/d323909d-8f5a-44ac-bb74-15ce2bb6618a.jpg','201609291450','@temp04.jpg'),(61,82,'1.1','/resources/km_upload/project_work_file/4423211e-4ca2-4bdc-b984-f2c0a702aed7.png','201609291452','bg_lose.png'),(62,83,'1.0','/resources/km_upload/project_work_file/c8b86c7e-1068-4072-b2f4-487f12f9aa52.png','201609291951','small.png'),(63,84,'1.1','/resources/km_upload/project_work_file/efe0d62b-239f-4db2-b97e-0a1940605da5.jpg','201609291951','@main_pop.jpg'),(64,85,'1.0','/resources/km_upload/project_work_file/cf081c6a-7923-44d4-8a62-d57c98c44b05.jpg','201609292010','@taamp_attend.jpg'),(64,86,'1.1','/resources/km_upload/project_work_file/264ae8af-efb7-4dae-9581-71f0f9fde04d.png','201609292010','bgGiftCoffee_on.png'),(64,87,'1.1','/resources/km_upload/project_work_file/f0219cc3-191a-4f3b-a1b6-493070fed26c.jpg','201609292038','@temp02.jpg'),(65,88,'1.0','/resources/km_upload/project_work_file/e212b8b5-c648-4573-aaba-cd1968a90695.jpg','201609292044','@stamp_view.jpg'),(65,89,'1.1','/resources/km_upload/project_work_file/a3fc3548-6c1c-4798-8557-42e6cb0f6fa8.jpg','201609292046','@temp05.jpg'),(65,90,'1.1','/resources/km_upload/project_work_file/b0be7586-3ae2-477d-9ca1-a6071e5432c4.jpg','201609292047','evt_coupon3.jpg'),(67,103,'1.0','/resources/km_upload/project_work_file/bab69689-a69f-4d16-99da-f34c0fd82347.png','201609292109','bgGiftCoffee.png'),(68,104,'1.0','/resources/km_upload/project_work_file/bab69689-a69f-4d16-99da-f34c0fd82347.png','201609292110','bgGiftCoffee.png'),(69,105,'1.0','/resources/km_upload/project_work_file/0cdda49e-7102-45c3-98d7-af5ad15cb90a.jpg','201610211712','dwork3.jpg'),(70,106,'1.0','/resources/km_upload/project_work_file/be8c08d9-782e-4cd8-92ce-3ae5e0862784.jpg','201610251405','dwork1.jpg'),(71,107,'1.0','/resources/km_upload/project_work_file/80aa75c0-0552-463d-a9ca-893165c62a80.jpg','201610251635','dwork2.jpg'),(72,108,'1.0','/resources/km_upload/project_work_file/b88cdf7c-4e12-4dc4-bda3-c1a6c461c144.jpg','201610291932','Sculputre in Motion1.jpg'),(72,109,'1.1','/resources/km_upload/project_work_file/c150e7f0-842c-4a72-9a62-bd937995c3df.jpg','201610291956','ded32e57-0466-492e-aa60-28a9ec206725.jpg'),(72,110,'1.2','/resources/km_upload/project_work_file/131a40f0-a841-421e-8e4c-948ee5cd2166.jpeg','201610292003','KakaoTalk_Photo_2016-10-28-17-14-14_94.jpeg'),(73,111,'1.0','/resources/km_upload/project_work_file/5cd51969-7cb5-4874-a0c5-c3dd4365cead.jpg','201610301542','dwork3.jpg'),(74,112,'1.0','/resources/km_upload/project_work_file/5d80aa24-c9a8-4424-9f6f-8b0f62b5d519.jpg','201610302109','Img0404_20151208103343_3.jpg'),(75,113,'1.0','/resources/km_upload/project_work_file/c83fc8bb-2041-435e-874c-68d03e8c3494.png','201610302152','emart_everyday3.png'),(77,114,'1.0','/resources/km_upload/project_work_file/a945d1ba-9381-4105-97b3-f5728d51ce49.jpg','201610302208','dwork3.jpg'),(78,115,'1.0','/resources/km_upload/project_work_file/07df79a1-103e-4974-a351-11f79b90c179.jpg','201610302213','dwork1.jpg'),(78,116,'1.1','/resources/km_upload/project_work_file/acb43ec0-73c2-4c63-bd9a-f4ed5251ad55.jpg','201610302224','dwork2.jpg'),(79,117,'1.0','/resources/km_upload/project_work_file/40c1ae66-c5be-4ced-8b30-031bf0ebac6c.png','201610302326','emart_everyday3.png'),(71,118,'1.1','/resources/km_upload/project_work_file/2e1990c7-e9b4-41e9-be3c-9e31904fa2bf.jpg','201611021644','2.jpg');



/*Table structure for table t_request_board */



DROP TABLE IF EXISTS t_request_board;



CREATE TABLE t_request_board (
  board_type varchar(20)  DEFAULT NULL COMMENT '구분',
  title varchar(200)  DEFAULT NULL COMMENT '제목',
  contents varchar(3000)  DEFAULT NULL COMMENT '내용',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  member_seq int(11) DEFAULT NULL COMMENT '작성자seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  update_time char(18)  DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (seq)
) ;



/*Data for the table t_request_board */



insert  into t_request_board(board_type,title,contents,seq,member_seq,register_time,update_time) values ('D','디자인 요첨','디자인 요청',21,108,'201611031421','201611031421');



/*Table structure for table t_request_board_category */



DROP TABLE IF EXISTS t_request_board_category;



CREATE TABLE t_request_board_category (
  board_seq int(11) NOT NULL COMMENT '게시판seq',
  category_code varchar(9)  NOT NULL COMMENT '카테고리코드',
  PRIMARY KEY (board_seq,category_code)
) ;



/*Data for the table t_request_board_category */



insert  into t_request_board_category(board_seq,category_code) values (1,'001'),(6,'002'),(8,'001'),(9,'002'),(10,'001'),(11,'001'),(12,'001'),(13,'001'),(14,'002'),(15,'*'),(17,'002'),(18,'*'),(21,'*');



/*Table structure for table t_request_board_comment */



DROP TABLE IF EXISTS t_request_board_comment;



CREATE TABLE t_request_board_comment (
  member_seq int(11) DEFAULT NULL COMMENT '회원seq',
  board_seq int(11) DEFAULT NULL COMMENT '게시판seq',
  contents varchar(3000)  DEFAULT NULL COMMENT '내용',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT '댓글seq',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (seq)
) ;



/*Data for the table t_request_board_comment */



insert  into t_request_board_comment(member_seq,board_seq,contents,seq,register_time) values (26,1,'ㅇㅇ,나도',1,'201609081005'),(23,10,'gogogo',2,'201609211645'),(23,10,'hello',3,'201609211650'),(23,10,'good',4,'201609211650'),(23,10,'test',5,'201609211650'),(23,10,'gogogo',6,'201609211650'),(23,10,'abc',7,'201609211651'),(23,10,'bbb',8,'201609211651'),(26,10,'aaaa',9,'201609211653'),(26,10,'bbbb',10,'201609211653'),(26,9,'산업',11,'201609211653'),(26,9,'hello',12,'201609211653'),(37,1,'hello',13,'201609291523'),(108,20,'댓글',14,'201611011559'),(108,15,'댓글',15,'201611011601');



/*Table structure for table t_request_board_file */



DROP TABLE IF EXISTS t_request_board_file;



CREATE TABLE t_request_board_file (
  board_seq int(11) DEFAULT NULL COMMENT '게시판seq',
  seq int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  filename varchar(50)  DEFAULT NULL COMMENT '파일명',
  file_url varchar(200)  DEFAULT NULL COMMENT '파일url',
  register_time char(12)  DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (seq)
) ;



/*Data for the table t_request_board_file */



insert  into t_request_board_file(board_seq,seq,filename,file_url,register_time) values (1,1,'sample.jpg','/resources/image/sub/img_sample.jpg','201609081200'),(8,6,'pic_profile2.jpg','/resources/km_upload/request_board/636a551d-9db4-49da-85a5-f079b48fbc60.jpg','201609091410'),(8,7,'pic_profile3.jpg','/resources/km_upload/request_board/47f2df36-cb57-4e35-928c-7f40f196dcd2.jpg','201609091410'),(8,8,'test.jpg','/resources/km_upload/request_board/3e709f9b-fbad-4bc0-a1cf-4c1890721252.jpg','201609091410'),(6,10,'test.jpg','/resources/km_upload/request_board/ca02f1c0-1c52-4322-b21d-41096d9cfa05.jpg','201609091411'),(9,11,'pic_profile5.jpg','/resources/km_upload/request_board/0042570e-6c70-45e5-bc1e-c3aa1649efb1.jpg','201609091851'),(10,12,'pic_profile3.jpg','/resources/km_upload/request_board/4608ab8a-6919-453c-a000-8ff2f3b4f6bf.jpg','201609191000'),(11,13,'pic_profile.jpg','/resources/km_upload/request_board/3600e47c-82da-4016-aeb4-29631564bbef.jpg','201609191544'),(12,14,'test.jpg','/resources/km_upload/request_board/2b04de23-03d1-4795-b2aa-0bc1e21d735c.jpg','201609191547'),(13,15,'pic_profile2.jpg','/resources/km_upload/request_board/bc4d7fd2-b83e-4b11-9930-fd75e524425f.jpg','201609191549'),(14,16,'pic_profile.jpg','/resources/km_upload/request_board/861854ec-5f3e-4cdd-b5d8-243de4cd607b.jpg','201609191556'),(18,17,'161018_국민대메인디자인_수정.png','/resources/km_upload/request_board/9b04fbb5-93ca-417b-8fca-88709783d41e.png','201610301718');



/*Table structure for table t_stat_activities */



DROP TABLE IF EXISTS t_stat_activities;



CREATE TABLE t_stat_activities (
  stat_month char(6)  NOT NULL COMMENT '집계 월',
  target_seq int(11) NOT NULL COMMENT '대상(제작자/디자이너 또는 작품)  시퀀스',
  target_type char(1)  NOT NULL COMMENT '대상형태 (1=디자이너/제작자, 2=작품)',
  update_time char(14)  NOT NULL COMMENT '수정일자',
  PRIMARY KEY (stat_month,target_seq,target_type)
) ;



/*Data for the table t_stat_activities */



insert  into t_stat_activities(stat_month,target_seq,target_type,update_time) values ('201610',1,'2','20161026180254'),('201610',2,'2','20161026180254'),('201610',3,'2','20161026180254'),('201610',4,'2','20161026180254'),('201610',5,'2','20161026180254'),('201610',6,'2','20161026180254'),('201610',13,'2','20161026180254'),('201610',14,'2','20161026180254'),('201610',15,'2','20161026180254'),('201610',16,'2','20161026180254'),('201610',23,'1','20161026161135'),('201610',24,'1','20161026161135'),('201610',28,'1','20161026161135'),('201610',37,'1','20161026161135'),('201610',39,'1','20161026161135'),('201610',40,'1','20161026161135'),('201610',61,'1','20161026161135'),('201610',96,'1','20161026161135');



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
) ;



/*Data for the table t_talk */



insert  into t_talk(seq,contents,recieve_seq,send_seq,register_time,confirm_time) values (1,'hi',37,26,'201609290600','201611011741'),(2,'hello',26,37,'201609290601','201610302250'),(3,'oo',37,26,'201609290603','201611011741'),(4,'그래',26,37,'201609290604','201610302250'),(5,'hi',37,23,'201609290600','201611011741'),(8,'gogogo',26,37,'201609302022','201610302250'),(9,'where are you',26,37,'201609302108','201610302250'),(10,'shanghai',37,26,'201609302114','201611011741'),(11,'o hi!',26,37,'201609302114','201610302250'),(12,'good',37,26,'201609302114','201611011741'),(13,'go?',37,26,'201609302115','201611011741'),(14,'ggg',26,37,'201609302121','201610302250'),(15,'hi',37,26,'201609302121','201611011741'),(16,'ggg',37,26,'201609302121','201611011741'),(17,'gddd',37,26,'201609302121','201611011741'),(18,'hi',26,37,'201609302140','201610302250'),(19,'gogog',37,26,'201609302140','201611011741'),(20,'dddd',37,26,'201609302140','201611011741'),(21,'eee',26,37,'201609302141','201610302250'),(22,'gddd',24,37,'201609302208',NULL),(23,'zczxczxc',23,37,'201609302213','201610051651'),(24,'ㅇㅇㅇㅇ',26,37,'201610031403','201610302250'),(25,'ggg',26,37,'201610031410','201610302250'),(26,'hi',37,26,'201610031411','201611011741'),(27,'ghi',26,37,'201610051031','201610302250'),(28,'yeah',37,26,'201610051031','201611011741'),(31,'ttt',37,26,'201610302249','201611011741'),(32,'ggg',26,37,'201610302250','201610302250'),(33,'hellp',37,26,'201610302250','201611011741');




DROP TABLE IF EXISTS `t_zip_code`;

CREATE TABLE `t_zip_code` (
  `seq` int(11) NOT NULL AUTO_INCREMENT COMMENT 'seq',
  `sido` varchar(50) NOT NULL COMMENT '시/도',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `sido_UNIQUE` (`sido`)
);

insert  into `t_zip_code`(`seq`,`sido`) values (1,'강원도'),(2,'경기도'),(3,'경상남도'),(4,'경상북도'),(5,'광주광역시'),(6,'대구광역시'),(7,'대전광역시'),(8,'부산광역시'),(9,'서울특별시'),(10,'세종특별자치시'),(11,'울산광역시'),(12,'인천광역시'),(13,'전라남도'),(14,'전라북도'),(15,'제주특별자치도'),(16,'충청남도'),(17,'충청북도');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


/*  views  */
create view v_design_work AS
	select * from t_design_work where del_flag = 'N'; 
create view v_project AS
	select * from t_project where del_flag = 'N';
create view v_project_work AS
	select * from t_project_work where del_flag = 'N';



