/*L
   Copyright Ekagra Software Technologies Ltd.
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/common-security-module/LICENSE.txt for details.
L*/

-- Replace the <<database_name>> with proper database name that is to be migrated.

-- USE <<database_name>>;


CREATE TABLE CSM_MAPPING (
  MAPPING_ID NUMBER(38) NOT NULL ,
  APPLICATION_ID NUMBER(38) NOT NULL,
  OBJECT_NAME VARCHAR2(100) NOT NULL,
  ATTRIBUTE_NAME VARCHAR2(100) NOT NULL,
  OBJECT_PACKAGE_NAME VARCHAR2(100),
  TABLE_NAME VARCHAR2(100),
  TABLE_NAME_GROUP VARCHAR2(100),
  TABLE_NAME_USER VARCHAR2(100),
  VIEW_NAME_GROUP VARCHAR2(100),
  VIEW_NAME_USER VARCHAR2(100),
  ACTIVE_FLAG NUMBER(1) default '0' NOT NULL,
  MAINTAINED_FLAG NUMBER(1) default '0' NOT NULL,    
  UPDATE_DATE DATE
)
/

CREATE SEQUENCE CSM_MAPPING_SEQ
increment by 1
start with 1
NOMAXVALUE
minvalue 1
nocycle
nocache
noorder
/


ALTER TABLE CSM_MAPPING ADD CONSTRAINT PK_MAPPING 
PRIMARY KEY (MAPPING_ID) 
/

ALTER TABLE CSM_MAPPING
ADD CONSTRAINT UQ_MP_OBJ_NAME_ATTRNAME_APP_ID UNIQUE (OBJECT_NAME,ATTRIBUTE_NAME,APPLICATION_ID)
/


ALTER TABLE CSM_MAPPING ADD CONSTRAINT FK_MAPPING_APPLICATION  
FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID)
ON DELETE CASCADE
/

ALTER TABLE CSM_APPLICATION ADD ( CSM_VERSION VARCHAR2(20))
/

