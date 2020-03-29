



-- use your DBA user to create your tables and to create another user and to grant that user certain 
-- privilleges on certain tables


CREATE USER 'cmsapp' identified by 'cmsapp123';

-- give cmsapp user an unlimited amount of space while adding data in all it's tables..
-- note that 
ALTER USER cmsapp quota unlimited on users;


GRANT CREATE SESSION TO CMSAPP;
GRANT SELECT, INSERT, UPDATE, DELETE ON CMSAPP.USER_AUTHORITY TO CMSAPP;


