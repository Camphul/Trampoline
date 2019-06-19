-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: e96d70b07f15
-- Generation Time: Jun 19, 2019 at 11:00 AM
-- Server version: 10.3.13-MariaDB-1:10.3.13+maria~bionic
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ccs`
--

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_comment`
--

CREATE TABLE `ptrs_comment` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `content` varchar(5000) NOT NULL,
  `comment_author` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_paint_area`
--

CREATE TABLE `ptrs_paint_area` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `area` varchar(32) NOT NULL,
  `deck` varchar(32) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_paint_area_stages`
--

CREATE TABLE `ptrs_paint_area_stages` (
  `paint_area_id` binary(16) NOT NULL,
  `stages_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_paint_stage`
--

CREATE TABLE `ptrs_paint_stage` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `is_completed` bit(1) NOT NULL,
  `completed_at` datetime DEFAULT NULL,
  `is_enabled` bit(1) NOT NULL,
  `name` varchar(32) NOT NULL,
  `requires_release_form` bit(1) NOT NULL,
  `status` varchar(255) NOT NULL,
  `paint_step` int(11) NOT NULL,
  `previous_paint_stage` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_paint_stage_comments`
--

CREATE TABLE `ptrs_paint_stage_comments` (
  `paint_stage_id` binary(16) NOT NULL,
  `comments_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_paint_stage_punch_list`
--

CREATE TABLE `ptrs_paint_stage_punch_list` (
  `paint_stage_id` binary(16) NOT NULL,
  `punch_list_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_paint_stage_required_signer`
--

CREATE TABLE `ptrs_paint_stage_required_signer` (
  `paint_stage_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_project`
--

CREATE TABLE `ptrs_project` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `is_completed` bit(1) DEFAULT NULL,
  `completed_at` datetime DEFAULT NULL,
  `description` varchar(64) DEFAULT NULL,
  `is_locked` bit(1) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `project_type` varchar(255) DEFAULT NULL,
  `is_started` bit(1) DEFAULT NULL,
  `project_author` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_project_activity`
--

CREATE TABLE `ptrs_project_activity` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `principal` binary(16) DEFAULT NULL,
  `project` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_project_area_stage_signer`
--

CREATE TABLE `ptrs_project_area_stage_signer` (
  `paint_stage_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_project_default_signer`
--

CREATE TABLE `ptrs_project_default_signer` (
  `project_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_project_doc`
--

CREATE TABLE `ptrs_project_doc` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `document_type` varchar(255) NOT NULL,
  `asset_meta` binary(16) DEFAULT NULL,
  `document_author` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_project_document`
--

CREATE TABLE `ptrs_project_document` (
  `project_id` binary(16) NOT NULL,
  `document_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_project_member`
--

CREATE TABLE `ptrs_project_member` (
  `project_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_project_paint_areas`
--

CREATE TABLE `ptrs_project_paint_areas` (
  `project_id` binary(16) NOT NULL,
  `paint_areas_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_punchlist_item`
--

CREATE TABLE `ptrs_punchlist_item` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `is_completed` bit(1) NOT NULL,
  `completion_date` datetime DEFAULT NULL,
  `name` varchar(256) NOT NULL,
  `punch_item_author` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_punch_item_signer`
--

CREATE TABLE `ptrs_punch_item_signer` (
  `punch_item_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_sign_reminder`
--

CREATE TABLE `ptrs_sign_reminder` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `reminder_area` binary(16) DEFAULT NULL,
  `reminder_project` binary(16) DEFAULT NULL,
  `reminder_stage` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ptrs_user_asset`
--

CREATE TABLE `ptrs_user_asset` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `asset_meta` binary(16) DEFAULT NULL,
  `asset_author` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `trampoline_asset_meta_data`
--

CREATE TABLE `trampoline_asset_meta_data` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `asset_name` varchar(255) NOT NULL,
  `original_filename` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `trampoline_policy_rule`
--

CREATE TABLE `trampoline_policy_rule` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `condition_expression` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `target_expression` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trampoline_policy_rule`
--

INSERT INTO `trampoline_policy_rule` (`id`, `auditing_created_at`, `auditing_updated_at`, `condition_expression`, `description`, `name`, `target_expression`) VALUES
(0xb12c4a548e1f43e796b4a3f9cbaef8b7, '2019-06-19 10:58:17', '2019-06-19 10:58:17', 'true', 'Allow all for ROLE_ADMIN', 'Internal#ADMIN_OVERRIDE', 'isAdmin()'),
(0x8b5e3d9e017441ffb9eaa190444bfa6d, '2019-06-19 10:58:17', '2019-06-19 10:58:17', 'isProjectMember() and isProjectUnlocked()', 'Create a new project', 'Project#IS_MEMBER', 'isAction(\'PROJECT_IS_MEMBER\')'),
(0x8dfa236595eb4d35a4c53e541853299a, '2019-06-19 10:58:17', '2019-06-19 10:58:17', 'isUser()', 'View GET /whoami', 'Internal#WHO_AM_I', 'isAction(\'WHO_AM_I\')'),
(0x76a2c274f0f74067bac78adb32d21099, '2019-06-19 10:58:17', '2019-06-19 10:58:17', 'isAdmin()', 'Lock or unlock user accounts', 'User#LOCK', 'isAction(\'USER_LOCK\')'),
(0x67d8d5c7f93746c5a37a645786f671f1, '2019-06-19 10:58:17', '2019-06-19 10:58:17', 'isAdmin()', 'Create a new project', 'Project#CREATE', 'isAction(\'PROJECT_CREATE\')'),
(0x40dfcaf378d64f359adf0fda2866f1a2, '2019-06-19 10:58:17', '2019-06-19 10:58:17', 'isUser() and isProjectMember()', 'View project', 'Project#VIEW', 'isAction(\'PROJECT_VIEW\')'),
(0x76570bf4ad3b4be0862a72fed00d6aa8, '2019-06-19 10:58:17', '2019-06-19 10:58:17', 'isSurveyor()', 'Mark a project as started.', 'Project#START', 'isAction(\'PROJECT_START\')'),
(0x22eb6bd67ea0430ab227d155850c7346, '2019-06-19 10:58:17', '2019-06-19 10:58:17', 'isSurveyor()', 'Set locked flag for project.', 'Project#LOCK', 'isAction(\'PROJECT_LOCK\')'),
(0x0bde1bad601b416abe7b73ebd49e8dd4, '2019-06-19 10:58:17', '2019-06-19 10:58:17', 'isProjectMember()', 'View project logs', 'Project.Log#VIEW', 'isAction(\'PROJECT_LOG_VIEW\')'),
(0xf0876c7f0ab34c858e5c8dc7009b8b4e, '2019-06-19 10:58:17', '2019-06-19 10:58:17', 'isAdmin() and isProjectUnlocked()', 'Gives access to a specific project for a user.', 'Project.Member#ADD', 'isAction(\'PROJECT_MEMBER_ADD\')'),
(0x71b7872e1772464e81f7b072d8d851cd, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isSurveyor() and isProjectUnlocked()', 'Remove the user from the project. Revoking access', 'Project.Member#REMOVE', 'isAction(\'PROJECT_MEMBER_REMOVE\')'),
(0x202e6e7e30b442e48860a9fb20286326, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isSurveyor() and isProjectUnlocked()', 'Add member to list of people required to sign every stage.', 'Project.DefaultSigner#ADD', 'isAction(\'PROJECT_DEFAULT_SIGNER_ADD\')'),
(0xb5730400befa4d5e9a009467d0fb2a06, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isSurveyor() and isProjectUnlocked()', 'Remove member to list of people required to sign every stage.', 'Project.DefaultSigner#REMOVE', 'isAction(\'PROJECT_DEFAULT_SIGNER_REMOVE\')'),
(0x6b90ffe212224331a12a19ed5e5d6f3c, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isUser() and isProjectMember() and isProjectUnlocked()', 'Allows uploading of documents/assets to a project page.', 'Project.Document#UPLOAD', 'isAction(\'PROJECT_DOCUMENT_UPLOAD\')'),
(0xe42013e6ac424d288ed1953f288f15dd, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isAdmin() and isProjectUnlocked()', 'Allows uploading of documents/assets to a project page.', 'Project.Document#REMOVE', 'isAction(\'PROJECT_DOCUMENT_REMOVE\')'),
(0x835721cbd9c14e8fa151da7f4b1f14fa, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isUser() and isProjectMember() and isProjectArea(environment.area)', 'View project paint areas and stages', 'Project.Area#VIEW', 'isAction(\'PROJECT_AREA_VIEW\')'),
(0x73efb4366e5e4db19f649a1b66285eda, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isSurveyor() and isProjectMember() and isProjectUnlocked() and isProjectArea(environment.area)', 'Removes a project paint area', 'Project.Area#REMOVE', 'isAction(\'PROJECT_AREA_REMOVE\')'),
(0x2c347769284341a7b24cf9783f3bc085, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isSurveyor()  and isProjectMember() and isProjectUnlocked()', 'Adds a project paint area', 'Project.Area#ADD', 'isAction(\'PROJECT_AREA_ADD\')'),
(0x3ef38754651844f98205b068915e6bdc, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isSurveyor() and isProjectMember() and isProjectUnlocked()', 'Adds a required signer to a paint stage', 'Project.Area.Stage.Signer#ADD', 'isAction(\'PROJECT_AREA_STAGE_SIGNER_ADD\')'),
(0x5d026bfd5c754ed695630b3c0d45e373, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isSurveyor() and isProjectMember() and isProjectUnlocked()', 'Removes a required signer from a paint stage.', 'Project.Area.Stage.Signer#REMOVE', 'isAction(\'PROJECT_AREA_STAGE_SIGNER_REMOVE\')'),
(0x91b19eeb2d7f4a4abb9fadd55a98ab51, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isUser() and isProjectMember() and isProjectUnlocked()', 'Allows uploading documents for paint stage comments.', 'Project.Area.Stage.Comment.Document#ADD', 'isAction(\'PROJECT_AREA_STAGE_COMMENT_DOCUMENT_UPLOAD\')'),
(0x330637197bba4313acb2851215355997, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isSurveyor() and isProjectMember() and isProjectUnlocked()', 'Create a reminder job', 'Project.Area.Stage#ENABLE_REMINDER', 'isAction(\'PROJECT_AREA_STAGE_ENABLE_REMINDER\')'),
(0x6b23dc1de9d14e3e9f49f61580fdaa89, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isUser() and environment.stage.canBeginStage() and isProjectMember() and isProjectUnlocked()', 'Sign a paint stage', 'Project.Area.Stage#SIGN', 'isAction(\'PROJECT_AREA_STAGE_SIGN\')'),
(0xfe4772252b334001a423bc565368df77, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isUser() and isProjectMember() and isProjectUnlocked()', 'Add a punchlist item to a paint stage.', 'Project.Area.Stage.PunchList#ADD', 'isAction(\'PROJECT_AREA_STAGE_PUNCH_LIST_ADD\')'),
(0x5f12d938e3c6445db6d65211cb92476b, '2019-06-19 10:58:18', '2019-06-19 10:58:18', '(hasRole(\'ROLE_SURVEYOR\') or (environment.punchItem.author.id == subject.id and isProjectMember())) and isProjectUnlocked()', 'Marks a punchlist item as completed', 'Project.Area.Stage.PunchList#COMPLETE', 'isAction(\'PROJECT_AREA_STAGE_PUNCH_LIST_COMPLETE\')'),
(0x1a6a3701bb594b578f0a5fa83b13c72e, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isUser() and isProjectMember() and isProjectUnlocked()', 'Sign a punchlist item', 'Project.Area.Stage.PunchList#SIGN', 'isAction(\'PROJECT_AREA_STAGE_PUNCH_LIST_SIGN\')'),
(0x74d6220190b4407ea633cb559f081972, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isUser() and isProjectMember() and isProjectUnlocked()', 'Generate releaseform', 'Project.Area.Stage#RF_GENERATE', 'isAction(\'PROJECT_AREA_STAGE_RF_GENERATE\')'),
(0xab692b1d69e84eb9a6bcb9aee3e198c4, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isUser()', 'View a list of available roles.', 'Role#FIND_ALL', 'isAction(\'ROLE_FIND_ALL\')'),
(0xc441d0fd61d14f28a0398fb3c6178ea8, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isAdmin()', 'Submit a new role.', 'Role#SUBMIT', 'isAction(\'ROLE_SUBMIT\')'),
(0x76e063299a2940c99443f119ac9071dd, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isAdmin()', 'Remove a role.', 'Role#DELETE', 'isAction(\'ROLE_DELETE\')'),
(0x991d6e6c791b4e9d977c9bdb2fa5a7b5, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isAdmin()', 'Add role to user.', 'User.Role#ADD', 'isAction(\'USER_ROLE_ADD\')'),
(0x6fb50bf9a0214661a328a73796ba0242, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isAdmin()', 'Remove a role from a user.', 'User.Role.#Remove', 'isAction(\'USER_ROLE_REMOVE\')'),
(0x76375dd4be824001b4697b0311c3bbb8, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isAdmin()', 'Register a new user.', 'User#Register', 'isAction(\'USER_REGISTER\')'),
(0xbe9190721b314ec0ba7b17803eb03d69, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isAdmin()', 'View a page of all users.', 'User#FindAll', 'isAction(\'USER_FIND_ALL\')'),
(0xbba88997332b49f186b1530e423bf665, '2019-06-19 10:58:18', '2019-06-19 10:58:18', 'isAdmin() or subject.id.equals(resource.id)', 'User change password.', 'User#ChangePassword', 'isAction(\'USER_CHANGE_PASSWORD\')');

-- --------------------------------------------------------

--
-- Table structure for table `trampoline_privilege`
--

CREATE TABLE `trampoline_privilege` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `name` varchar(64) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `trampoline_role`
--

CREATE TABLE `trampoline_role` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `name` varchar(64) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trampoline_role`
--

INSERT INTO `trampoline_role` (`id`, `auditing_created_at`, `auditing_updated_at`, `name`) VALUES
(0x511eba9176f44276bf8f18914acc8327, '2019-06-19 10:58:20', '2019-06-19 10:58:20', 'ROLE_USER'),
(0xd10cdfc6fb07447cbbfdedb5975d5440, '2019-06-19 10:58:20', '2019-06-19 10:58:20', 'ROLE_ADMIN'),
(0x41a1dd14867846d395d4985e8d1d9c3c, '2019-06-19 10:58:20', '2019-06-19 10:58:20', 'ROLE_OWNERS_REPRESENTATIVE'),
(0xfda3d62d106c4795978a23543a1c86df, '2019-06-19 10:58:20', '2019-06-19 10:58:20', 'ROLE_SURVEYOR'),
(0xfd5e6006dc324dca9364eac5e2a11044, '2019-06-19 10:58:20', '2019-06-19 10:58:20', 'ROLE_YARD'),
(0x22a8928e0a494dc2b4d0615ccd32e136, '2019-06-19 10:58:20', '2019-06-19 10:58:20', 'ROLE_PAINTER');

-- --------------------------------------------------------

--
-- Table structure for table `trampoline_role_privilege`
--

CREATE TABLE `trampoline_role_privilege` (
  `role_id` binary(16) NOT NULL,
  `privilege_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `trampoline_user`
--

CREATE TABLE `trampoline_user` (
  `id` binary(16) NOT NULL,
  `auditing_created_at` datetime NOT NULL,
  `auditing_updated_at` datetime NOT NULL,
  `is_credentials_expired` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `is_expired` bit(1) NOT NULL,
  `last_password_reset_at` datetime NOT NULL,
  `last_seen` datetime NOT NULL,
  `is_locked` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(32) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trampoline_user`
--

INSERT INTO `trampoline_user` (`id`, `auditing_created_at`, `auditing_updated_at`, `is_credentials_expired`, `email`, `is_enabled`, `is_expired`, `last_password_reset_at`, `last_seen`, `is_locked`, `password`, `username`) VALUES
(0x69b9c0f9db9c4d719f89cc6d9aecc5ab, '2019-06-19 10:58:22', '2019-06-19 10:58:22', b'0', 'admin@ccsyacht.com', b'1', b'0', '2019-06-19 10:58:21', '2019-06-19 10:58:21', b'0', '$2a$10$vuQsJY/nG.oeq3LiWz.Kr.PVH7wwlk/Kki4k8J85JMJthd1KyK7e6', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `trampoline_user_role`
--

CREATE TABLE `trampoline_user_role` (
  `user_id` binary(16) NOT NULL,
  `role_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trampoline_user_role`
--

INSERT INTO `trampoline_user_role` (`user_id`, `role_id`) VALUES
(0x69b9c0f9db9c4d719f89cc6d9aecc5ab, 0x511eba9176f44276bf8f18914acc8327),
(0x69b9c0f9db9c4d719f89cc6d9aecc5ab, 0xd10cdfc6fb07447cbbfdedb5975d5440);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ptrs_comment`
--
ALTER TABLE `ptrs_comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4oo9qjg9c3fwtqshwnb307hmj` (`comment_author`);

--
-- Indexes for table `ptrs_paint_area`
--
ALTER TABLE `ptrs_paint_area`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ptrs_paint_area_stages`
--
ALTER TABLE `ptrs_paint_area_stages`
  ADD UNIQUE KEY `UK_1idpq2s5gsoxuxq62kaovh3lv` (`stages_id`),
  ADD KEY `FKh0mxae51cjeocrinhwfp5soce` (`paint_area_id`);

--
-- Indexes for table `ptrs_paint_stage`
--
ALTER TABLE `ptrs_paint_stage`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7e1vb3famm3urkq6yegabpbvs` (`previous_paint_stage`);

--
-- Indexes for table `ptrs_paint_stage_comments`
--
ALTER TABLE `ptrs_paint_stage_comments`
  ADD UNIQUE KEY `UK_l4mc2bj8001th111j9t1a4170` (`comments_id`),
  ADD KEY `FKtdjjeqi084g7q8dtrppsit9i8` (`paint_stage_id`);

--
-- Indexes for table `ptrs_paint_stage_punch_list`
--
ALTER TABLE `ptrs_paint_stage_punch_list`
  ADD UNIQUE KEY `UK_7iuxr204r1vhgxr3p7na0mgot` (`punch_list_id`),
  ADD KEY `FK8bbp0smro9s3nuqk1ep586lc1` (`paint_stage_id`);

--
-- Indexes for table `ptrs_paint_stage_required_signer`
--
ALTER TABLE `ptrs_paint_stage_required_signer`
  ADD KEY `FKbtyqt247q2jn673yfvbiikphp` (`user_id`),
  ADD KEY `FKn07cpsm4qcty8fhw020xj00qm` (`paint_stage_id`);

--
-- Indexes for table `ptrs_project`
--
ALTER TABLE `ptrs_project`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgk3kqga14kyj2rg6fu2f1q6nu` (`project_author`);

--
-- Indexes for table `ptrs_project_activity`
--
ALTER TABLE `ptrs_project_activity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKhvuh0478f8i65l4htmve72het` (`principal`),
  ADD KEY `FKmfpj8a01kwp61s7ito1j1urq2` (`project`);

--
-- Indexes for table `ptrs_project_area_stage_signer`
--
ALTER TABLE `ptrs_project_area_stage_signer`
  ADD KEY `FKgfc6h8dwcoi6u2fsehu86rl1r` (`user_id`),
  ADD KEY `FKfe9eni615tvsjif8i8jfryqql` (`paint_stage_id`);

--
-- Indexes for table `ptrs_project_default_signer`
--
ALTER TABLE `ptrs_project_default_signer`
  ADD KEY `FKo9q2063k0yqwowavwaxihnvar` (`user_id`),
  ADD KEY `FKgety3f3c97dl2qt8qf1ti9t2y` (`project_id`);

--
-- Indexes for table `ptrs_project_doc`
--
ALTER TABLE `ptrs_project_doc`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK76g2j60oahpxwy6nntqr68d63` (`asset_meta`),
  ADD KEY `FKp3u6rqyhyayg9608xkx32ynin` (`document_author`);

--
-- Indexes for table `ptrs_project_document`
--
ALTER TABLE `ptrs_project_document`
  ADD KEY `FKtqfy5awt8sjb0h5ol4wmwkfgq` (`document_id`),
  ADD KEY `FKiowhww9khwkp3hpjrjvpme5ux` (`project_id`);

--
-- Indexes for table `ptrs_project_member`
--
ALTER TABLE `ptrs_project_member`
  ADD KEY `FKpbv0l8c30k73tloti01r0sagn` (`user_id`),
  ADD KEY `FKe0wvl8sj1urj6hjmu64u70p2u` (`project_id`);

--
-- Indexes for table `ptrs_project_paint_areas`
--
ALTER TABLE `ptrs_project_paint_areas`
  ADD UNIQUE KEY `UK_dg7yrhp78vyrnaeuxtrufjhaj` (`paint_areas_id`),
  ADD KEY `FKiubh75xr7kjpflog17yj47ubq` (`project_id`);

--
-- Indexes for table `ptrs_punchlist_item`
--
ALTER TABLE `ptrs_punchlist_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1rj8hqh7na2en7pud21t1km4q` (`punch_item_author`);

--
-- Indexes for table `ptrs_punch_item_signer`
--
ALTER TABLE `ptrs_punch_item_signer`
  ADD KEY `FKhmmwdsmc09ronj3thbw0uc36r` (`user_id`),
  ADD KEY `FKsutajvwgcmkygvfvnoaefpfvy` (`punch_item_id`);

--
-- Indexes for table `ptrs_sign_reminder`
--
ALTER TABLE `ptrs_sign_reminder`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKholnwg4dw4j3rnneflc57mnfk` (`reminder_area`),
  ADD KEY `FK11erdhr2bokiq3ycgsmo8508` (`reminder_project`),
  ADD KEY `FKlj9jx80hibkefs9lf1qrwxcht` (`reminder_stage`);

--
-- Indexes for table `ptrs_user_asset`
--
ALTER TABLE `ptrs_user_asset`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKclnb7jubmv48nui107y5rgy8u` (`asset_meta`),
  ADD KEY `FKfla7vm4i8wjtr13u7qn3pribl` (`asset_author`);

--
-- Indexes for table `trampoline_asset_meta_data`
--
ALTER TABLE `trampoline_asset_meta_data`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `trampoline_policy_rule`
--
ALTER TABLE `trampoline_policy_rule`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_k3iva7si52rs7oam4lb6iqxxw` (`name`);

--
-- Indexes for table `trampoline_privilege`
--
ALTER TABLE `trampoline_privilege`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_jwnedqyldy78n74rl6ayk9tit` (`name`);

--
-- Indexes for table `trampoline_role`
--
ALTER TABLE `trampoline_role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_4oj2nd5miwywlmgbi0ychrn8c` (`name`);

--
-- Indexes for table `trampoline_role_privilege`
--
ALTER TABLE `trampoline_role_privilege`
  ADD KEY `FK963sgljbo2ifmcs73i854tcb5` (`privilege_id`),
  ADD KEY `FKg80p8eee32m5dbx9vcvucq3bt` (`role_id`);

--
-- Indexes for table `trampoline_user`
--
ALTER TABLE `trampoline_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_o5qvkg7am5xbng9ybkxbroeoy` (`email`),
  ADD UNIQUE KEY `UK_2rqw1ol0jhn81xv88vvwj8u9` (`username`);

--
-- Indexes for table `trampoline_user_role`
--
ALTER TABLE `trampoline_user_role`
  ADD KEY `FK5rlp0bjm8pkvlmlnupg1f4sv6` (`role_id`),
  ADD KEY `FKii60lmxsyuxj1gm4gfaygaj5t` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
