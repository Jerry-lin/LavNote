Create DATABASE LavNote;
USE LavNote;
#创建表的语言

# 用户对应的语言
CREATE TABLE `users` (
  `id` char(36) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `is_admin` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_username_unique` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `versions` (
  `id` char(36) NOT NULL,
  `previous_id` char(36) DEFAULT NULL,
  `next_id` char(36) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `content_preview` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `user_id` char(36) NOT NULL,
  `content` mediumtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `versions_previous_id_foreign` (`previous_id`),
  KEY `versions_next_id_foreign` (`next_id`),
  KEY `versions_user_id_foreign` (`user_id`),
  CONSTRAINT `versions_next_id_foreign` FOREIGN KEY (`next_id`) REFERENCES `versions` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `versions_previous_id_foreign` FOREIGN KEY (`previous_id`) REFERENCES `versions` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `versions_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `attachments` (
  `id` char(36) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `filename` varchar(255) NOT NULL,
  `fileextension` varchar(255) NOT NULL,
  `content` text,
  `mimetype` varchar(255) NOT NULL,
  `filesize` bigint(20) unsigned NOT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `attachment_version` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `attachment_id` char(36) NOT NULL,
  `version_id` char(36) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `attachment_version_attachment_id_foreign` (`attachment_id`),
  KEY `attachment_version_version_id_foreign` (`version_id`),
  CONSTRAINT `attachment_version_attachment_id_foreign` FOREIGN KEY (`attachment_id`) REFERENCES `attachments` (`id`) ON DELETE CASCADE,
  CONSTRAINT `attachment_version_version_id_foreign` FOREIGN KEY (`version_id`) REFERENCES `versions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#语言
CREATE TABLE `languages` (
  `id` char(36) NOT NULL,
  `language_code` char(7) NOT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#插入数据
INSERT INTO languages (id,language_code,deleted_at) VALUES
('0c5a3019-0af8-456e-93f6-5365be248bbc','lav',NULL)
,('0c8fcf75-80a3-43cc-8e0d-c7e244d9229a','cat',NULL)
,('0e826dd7-72b7-4a64-8f22-b65fc78fb7d2','rus',NULL)
,('13bde773-0357-4a3c-a146-bd619f99bcd8','grc',NULL)
,('146059f9-553b-44f6-aaf2-1e42310ef538','slk',NULL)
,('15f1ada6-679f-4988-b56e-793e3b69402d','bel',NULL)
,('1a47f833-c27e-4642-a0d7-ffd06a668b0c','swe',NULL)
,('225c2c8e-2de0-48e6-b8c6-d7baa31a6c4c','lit',NULL)
,('2274408d-f1cf-4f47-91a9-4e3087f9cfff','fin',NULL)
,('25f1d6d8-6c23-46fe-87eb-4c834f5733e5','chi-tra',NULL)
;
INSERT INTO languages (id,language_code,deleted_at) VALUES
('299351ba-fcff-4f84-bd97-af4ffe624a9f','jpn',NULL)
,('2c12fcbe-886a-4ee6-9d72-ddc8b3cd26f1','hun',NULL)
,('2fa0f7e9-8c76-4e3e-a3ac-6f8f64011f9b','afr',NULL)
,('3642fc2d-28db-4355-bc55-0f9cc5c36b3b','enm',NULL)
,('3a08dff6-d2cb-475e-ac3c-945371d497c4','eng',NULL)
,('3c080852-b7d3-4949-97ca-bfbb11ab3b18','ron',NULL)
,('42bf443a-8eb6-4eed-a637-2ed5c4987575','ita',NULL)
,('45b3e131-de5f-49d2-97c7-dbe51440ebb1','nor',NULL)
,('48831e34-f24c-4567-a6bd-96addc05ce0d','ell',NULL)
,('4ed3a531-b379-42bd-bee7-5d5fb2a12071','swa',NULL)
;
INSERT INTO languages (id,language_code,deleted_at) VALUES
('5152bb53-800f-4568-8bbb-b178c00ebfc2','frk',NULL)
,('58d944ab-e54c-4932-9eee-1b1a5c4f4a48','heb',NULL)
,('599a5c0a-5b81-48df-9e67-be5f02949af2','sqi',NULL)
,('5afeb90c-c593-4dd0-8a35-72260a108bda','deu',NULL)
,('65c2d889-ce5d-49a8-a8e5-f7bc89427bd8','equ',NULL)
,('6d9f9d2b-9851-41f8-ac75-f3e5a8afc9ef','kan',NULL)
,('71d37de5-eb9b-4a7c-a34e-e6469253f618','chi-sim',NULL)
,('75a349ce-9fd5-48c6-a0b7-966fc57af49e','mlt',NULL)
,('7af43046-be95-4ccf-becc-3c46067b7d92','mkd',NULL)
,('865a5740-7145-4a2e-b460-b5fb6d2f1b25','chr',NULL)
;
INSERT INTO languages (id,language_code,deleted_at) VALUES
('88306635-b29b-41d1-9979-c14c3fa75963','msa',NULL)
,('8c197cba-eff5-449b-8dfa-0fa41f5a762a','spa',NULL)
,('8c2fa78a-eca4-4cea-bb74-ee6e1a566028','epo',NULL)
,('8d32ed04-5a43-4c9a-ac57-ff9ace31066b','ara',NULL)
,('908bbfa9-680f-4c6c-abe1-b725722f9b4e','ben',NULL)
,('98831ba1-2c61-4472-96e6-aadd9874bed2','srp',NULL)
,('9a802d89-bf65-470f-9de4-8e85e3475515','isl',NULL)
,('a3b96c97-5856-4820-bafd-5b2a659b1d2d','tgl',NULL)
,('a771137b-8e47-4076-bca5-ed7c450922a7','hrv',NULL)
,('ad6e2e00-2baa-47f9-b090-587dd7e6dbcc','ind',NULL)
;
INSERT INTO languages (id,language_code,deleted_at) VALUES
('b328bb12-e72b-4e82-acbd-6a81aec4eb3e','tam',NULL)
,('b5659851-4a3a-47d0-ab10-18aed491d354','aze',NULL)
,('b5d3ef14-a8f6-4818-b1b0-00c31d1e30d2','vie',NULL)
,('bbf5d438-81b4-4edc-932f-da0dd62e798b','frm',NULL)
,('c130ead8-e01c-4a41-994a-59c08ed3d8ba','tha',NULL)
,('c3a88bc7-d3cd-4322-8509-c02b93a1e489','est',NULL)
,('c3c259dc-229a-4237-866b-b3e82450eb2f','nld',NULL)
,('c4ba58a3-5f6f-4d3f-a154-491684e715cf','por',NULL)
,('c8413f3e-00a2-4964-a9a3-45e5ef9e7d8c','hin',NULL)
,('d1418771-7df3-4b7b-a966-2822f602ac38','kor',NULL)
;
INSERT INTO languages (id,language_code,deleted_at) VALUES
('d73f2e66-dbb2-40bf-aa90-07d29ac436bc','glg',NULL)
,('d9fd9ead-6a28-4c74-ba81-48b274256f57','ces',NULL)
,('dc378ab9-148f-4f23-934e-e1ca03933d5f','dan',NULL)
,('de86b382-ce24-4918-a75e-9bcf1ed63767','mal',NULL)
,('e135f99b-4fc3-49a4-a100-8ad25779e0c0','bul',NULL)
,('e21ccb72-14e1-4e76-a394-cf43e26125ff','eus',NULL)
,('e45feccb-37c8-464c-a606-cbd1ebc7baa4','tur',NULL)
,('e6ab89fc-5b04-47a0-ad99-d26d6e3dc4b8','tel',NULL)
,('edb1fd04-9d54-491f-81fd-e7cf4fb8fc94','pol',NULL)
,('effaaf72-2e9b-4194-8d42-4b65ac0c6f42','ukr',NULL)
;
INSERT INTO languages (id,language_code,deleted_at) VALUES
('f74660a1-c9a8-4a12-88dc-4e676224b22e','fra',NULL)
,('fb26cc85-9756-48cb-8b63-7d24a53ab563','slv',NULL)
;



CREATE TABLE `language_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` char(36) NOT NULL,
  `language_id` char(36) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `language_user_user_id_foreign` (`user_id`),
  KEY `language_user_language_id_foreign` (`language_id`),
  CONSTRAINT `language_user_language_id_foreign` FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`) ON DELETE CASCADE,
  CONSTRAINT `language_user_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;



CREATE TABLE `migrations` (
  `migration` varchar(255) NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#插入数据
INSERT INTO migrations (migration,batch) VALUES
('2014_07_22_194050_initialize',1)
,('2014_07_24_103915_create_password_reminders_table',1)
,('2014_10_08_203732_add_visibility_to_tags_table',1)
,('2015_01_21_034728_add_admin_to_users',1)
,('2015_05_05_094021_modify_tag_user_relation',1)
,('2015_05_22_220540_add_version_user_relation',1)
,('2015_06_15_224221_add_tag_parent',1)
,('2015_06_30_125536_add_sessions_table',1)
,('2015_07_29_130508_alter_versions',1)
,('2016_10_21_224100_fix_timestamps_for_postgres_again',1)
;

CREATE TABLE `notebooks` (
  `id` char(36) NOT NULL,
  `parent_id` char(36) DEFAULT NULL,
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notebooks_parent_id_foreign` (`parent_id`),
  CONSTRAINT `notebooks_parent_id_foreign` FOREIGN KEY (`parent_id`) REFERENCES `notebooks` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `notes` (
  `id` char(36) NOT NULL,
  `notebook_id` char(36) NOT NULL,
  `version_id` char(36) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notes_notebook_id_foreign` (`notebook_id`),
  KEY `notes_version_id_foreign` (`version_id`),
  CONSTRAINT `notes_notebook_id_foreign` FOREIGN KEY (`notebook_id`) REFERENCES `notebooks` (`id`),
  CONSTRAINT `notes_version_id_foreign` FOREIGN KEY (`version_id`) REFERENCES `versions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `note_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` char(36) NOT NULL,
  `note_id` char(36) NOT NULL,
  `umask` tinyint(4) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `note_user_user_id_foreign` (`user_id`),
  KEY `note_user_note_id_foreign` (`note_id`),
  CONSTRAINT `note_user_note_id_foreign` FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `note_user_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE `notebook_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` char(36) NOT NULL,
  `notebook_id` char(36) NOT NULL,
  `umask` tinyint(4) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `notebook_user_user_id_foreign` (`user_id`),
  KEY `notebook_user_notebook_id_foreign` (`notebook_id`),
  CONSTRAINT `notebook_user_notebook_id_foreign` FOREIGN KEY (`notebook_id`) REFERENCES `notebooks` (`id`) ON DELETE CASCADE,
  CONSTRAINT `notebook_user_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE `password_reminders` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  KEY `password_reminders_email_index` (`email`),
  KEY `password_reminders_token_index` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sessions` (
  `id` varchar(255) NOT NULL,
  `payload` text NOT NULL,
  `last_activity` int(11) NOT NULL,
  UNIQUE KEY `sessions_id_unique` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `settings` (
  `id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `ui_language` char(2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `settings_user_id_foreign` (`user_id`),
  CONSTRAINT `settings_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `shortcuts` (
  `id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `notebook_id` char(36) NOT NULL,
  `sortkey` tinyint(3) unsigned NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `shortcuts_user_id_foreign` (`user_id`),
  KEY `shortcuts_notebook_id_foreign` (`notebook_id`),
  CONSTRAINT `shortcuts_notebook_id_foreign` FOREIGN KEY (`notebook_id`) REFERENCES `notebooks` (`id`) ON DELETE CASCADE,
  CONSTRAINT `shortcuts_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `tags` (
  `id` char(36) NOT NULL,
  `visibility` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `user_id` char(36) NOT NULL,
  `parent_id` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tags_user_id_foreign` (`user_id`),
  KEY `tags_parent_id_foreign` (`parent_id`),
  CONSTRAINT `tags_parent_id_foreign` FOREIGN KEY (`parent_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tags_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `tag_note` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `note_id` char(36) NOT NULL,
  `tag_id` char(36) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `tag_note_note_id_foreign` (`note_id`),
  KEY `tag_note_tag_id_foreign` (`tag_id`),
  CONSTRAINT `tag_note_note_id_foreign` FOREIGN KEY (`note_id`) REFERENCES `notes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tag_note_tag_id_foreign` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;






