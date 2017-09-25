
--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'GRUKEYDIVERSIFICATION_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('GRUKEYDIVERSIFICATION_MANAGEMENT','grukeydiversification.adminFeature.ManageEncryptionKey.name',1,'jsp/admin/plugins/grukeydiversification/ManageEncryptionKeys.jsp','grukeydiversification.adminFeature.ManageEncryptionKey.description',0,'grukeydiversification',NULL,NULL,NULL,1);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'GRUKEYDIVERSIFICATION_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('GRUKEYDIVERSIFICATION_MANAGEMENT',1);

