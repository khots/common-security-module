-- Replace the <<database_name>> with proper database name that is to be migrated.

-- USE <<database_name>>;

-- ALTER TABLE CSM_USER MODIFY ( LOGIN_NAME VARCHAR2(500) NOT NULL);
ALTER TABLE CSM_USER ADD ( MIGRATED_FLAG NUMBER(1) DEFAULT '0' NOT NULL);
ALTER TABLE CSM_USER ADD ( PREMGRT_LOGIN_NAME VARCHAR(100));


ALTER TABLE CSM_PG_PE MODIFY ( UPDATE_DATE DATE DEFAULT sysdate);

ALTER TABLE CSM_ROLE_PRIVILEGE DROP (UPDATE_DATE);
ALTER TABLE CSM_USER_PE DROP ( UPDATE_DATE );
DROP TRIGGER SET_CSM_USER_PE_UPDATE_DATE;

ALTER TABLE CSM_FILTER_CLAUSE RENAME COLUMN GENERATED_SQL TO GENERATED_SQL_USER;
-- ALTER TABLE CSM_FILTER_CLAUSE MODIFY ( GENERATED_SQL_USER VARCHAR2(4000) NOT NULL);
ALTER TABLE CSM_FILTER_CLAUSE ADD ( GENERATED_SQL_GROUP VARCHAR2(4000) NOT NULL);

commit;