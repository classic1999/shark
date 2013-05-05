# MySQL-Front 3.2  (Build 6.14)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES 'gbk' */;

# Host: pe-stat1.baidu.com    Database: shark
# ------------------------------------------------------
# Server version 5.0.16-max-log

SET character_set_client ='gb2312';
SET character_set_connection ='gb2312';
SET character_set_database ='gb2312';
SET character_set_results ='gb2312';
SET character_set_server ='gb2312';




DROP DATABASE IF EXISTS `shark`;
CREATE DATABASE `shark` /*!40100 DEFAULT CHARACTER SET gbk COLLATE gbk_bin */;
USE `shark`;

#
# Table structure for table Activities
#

CREATE TABLE `Activities` (
  `Id` varchar(100) NOT NULL,
  `ActivitySetDefinitionId` varchar(90) default NULL,
  `ActivityDefinitionId` varchar(90) NOT NULL,
  `Process` decimal(19,0) NOT NULL,
  `TheResource` decimal(19,0) default NULL,
  `PDefName` varchar(200) NOT NULL,
  `ProcessId` varchar(200) NOT NULL,
  `ResourceId` varchar(100) default NULL,
  `State` decimal(19,0) NOT NULL,
  `BlockActivityId` varchar(100) default NULL,
  `Performer` varchar(100) default NULL,
  `IsPerformerAsynchronous` smallint(6) default NULL,
  `Priority` int(11) default NULL,
  `Name` varchar(254) default NULL,
  `Activated` bigint(20) NOT NULL,
  `Accepted` bigint(20) default NULL,
  `LastStateTime` bigint(20) NOT NULL,
  `LimitTime` bigint(20) NOT NULL,
  `Description` varchar(254) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_Activities` (`Id`),
  KEY `I2_Activities` (`Process`,`ActivitySetDefinitionId`,`ActivityDefinitionId`),
  KEY `I3_Activities` (`Process`,`State`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Activities
#


#
# Table structure for table ActivityData
#

CREATE TABLE `ActivityData` (
  `Activity` decimal(19,0) NOT NULL,
  `VariableDefinitionId` varchar(100) NOT NULL,
  `VariableType` int(11) NOT NULL,
  `VariableValue` mediumblob,
  `VariableValueVCHAR` varchar(255) default NULL,
  `VariableValueDBL` double default NULL,
  `VariableValueLONG` bigint(20) default NULL,
  `VariableValueDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `VariableValueBOOL` smallint(6) default NULL,
  `IsResult` smallint(6) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ActivityData` (`Activity`,`VariableDefinitionId`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ActivityData
#


#
# Table structure for table ActivityDataBLOBs
#

CREATE TABLE `ActivityDataBLOBs` (
  `ActivityDataWOB` decimal(19,0) NOT NULL,
  `VariableValue` mediumblob,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ActivityDataBLOBs` (`ActivityDataWOB`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ActivityDataBLOBs
#


#
# Table structure for table ActivityDataWOB
#

CREATE TABLE `ActivityDataWOB` (
  `Activity` decimal(19,0) NOT NULL,
  `VariableDefinitionId` varchar(100) NOT NULL,
  `VariableType` int(11) NOT NULL,
  `VariableValueVCHAR` varchar(255) default NULL,
  `VariableValueDBL` double default NULL,
  `VariableValueLONG` bigint(20) default NULL,
  `VariableValueDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `VariableValueBOOL` smallint(6) default NULL,
  `IsResult` smallint(6) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ActivityDataWOB` (`Activity`,`VariableDefinitionId`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ActivityDataWOB
#


#
# Table structure for table ActivityStateEventAudits
#

CREATE TABLE `ActivityStateEventAudits` (
  `KeyValue` varchar(30) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ActivityStateEventAudits` (`KeyValue`),
  UNIQUE KEY `I2_ActivityStateEventAudits` (`Name`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ActivityStateEventAudits
#

INSERT INTO `ActivityStateEventAudits` VALUES ('open.running','open.running',1000013,0);
INSERT INTO `ActivityStateEventAudits` VALUES ('open.not_running.not_started','open.not_running.not_started',1000015,0);
INSERT INTO `ActivityStateEventAudits` VALUES ('open.not_running.suspended','open.not_running.suspended',1000017,0);
INSERT INTO `ActivityStateEventAudits` VALUES ('closed.completed','closed.completed',1000019,0);
INSERT INTO `ActivityStateEventAudits` VALUES ('closed.terminated','closed.terminated',1000021,0);
INSERT INTO `ActivityStateEventAudits` VALUES ('closed.aborted','closed.aborted',1000023,0);

#
# Table structure for table ActivityStates
#

CREATE TABLE `ActivityStates` (
  `KeyValue` varchar(30) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ActivityStates` (`KeyValue`),
  UNIQUE KEY `I2_ActivityStates` (`Name`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ActivityStates
#

INSERT INTO `ActivityStates` VALUES ('open.running','open.running',1000001,0);
INSERT INTO `ActivityStates` VALUES ('open.not_running.not_started','open.not_running.not_started',1000003,0);
INSERT INTO `ActivityStates` VALUES ('open.not_running.suspended','open.not_running.suspended',1000005,0);
INSERT INTO `ActivityStates` VALUES ('closed.completed','closed.completed',1000007,0);
INSERT INTO `ActivityStates` VALUES ('closed.terminated','closed.terminated',1000009,0);
INSERT INTO `ActivityStates` VALUES ('closed.aborted','closed.aborted',1000011,0);

#
# Table structure for table AndJoinTable
#

CREATE TABLE `AndJoinTable` (
  `Process` decimal(19,0) NOT NULL,
  `ActivitySetDefinitionId` varchar(90) default NULL,
  `ActivityDefinitionId` varchar(90) NOT NULL,
  `Activity` decimal(19,0) NOT NULL,
  `CNT` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_AndJoinTable` (`CNT`),
  KEY `I2_AndJoinTable` (`Process`,`ActivitySetDefinitionId`,`ActivityDefinitionId`),
  KEY `I3_AndJoinTable` (`Activity`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table AndJoinTable
#


#
# Table structure for table AssignmentEventAudits
#

CREATE TABLE `AssignmentEventAudits` (
  `UTCTime` varchar(254) NOT NULL,
  `TheType` decimal(19,0) NOT NULL,
  `ActivityId` varchar(100) NOT NULL,
  `ActivityName` varchar(254) default NULL,
  `ProcessId` varchar(100) NOT NULL,
  `ProcessName` varchar(254) default NULL,
  `ProcessDefinitionName` varchar(200) NOT NULL,
  `ProcessDefinitionVersion` varchar(20) NOT NULL,
  `ActivityDefinitionId` varchar(90) NOT NULL,
  `ActivitySetDefinitionId` varchar(90) default NULL,
  `ProcessDefinitionId` varchar(90) NOT NULL,
  `PackageId` varchar(90) NOT NULL,
  `OldResourceUsername` varchar(100) default NULL,
  `OldResourceName` varchar(100) default NULL,
  `NewResourceUsername` varchar(100) NOT NULL,
  `NewResourceName` varchar(100) default NULL,
  `IsAccepted` smallint(6) NOT NULL,
  `CNT` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_AssignmentEventAudits` (`CNT`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table AssignmentEventAudits
#


#
# Table structure for table AssignmentsTable
#

CREATE TABLE `AssignmentsTable` (
  `Activity` decimal(19,0) NOT NULL,
  `TheResource` decimal(19,0) NOT NULL,
  `ActivityId` varchar(100) NOT NULL,
  `ActivityProcessId` varchar(100) NOT NULL,
  `ActivityProcessDefName` varchar(200) NOT NULL,
  `ResourceId` varchar(100) NOT NULL,
  `IsAccepted` smallint(6) NOT NULL,
  `IsValid` smallint(6) NOT NULL,
  `CNT` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_AssignmentsTable` (`CNT`),
  UNIQUE KEY `I2_AssignmentsTable` (`Activity`,`TheResource`),
  KEY `I3_AssignmentsTable` (`TheResource`,`IsValid`),
  KEY `I4_AssignmentsTable` (`ActivityId`),
  KEY `I5_AssignmentsTable` (`ResourceId`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table AssignmentsTable
#


#
# Table structure for table Category
#

CREATE TABLE `Category` (
  `CATEGORYID` int(11) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  `PARENTID` int(11) NOT NULL,
  `LINKURL` varchar(250) default NULL,
  `ICONURL` varchar(250) default NULL,
  `TARGET` varchar(50) default NULL,
  `PREV` int(11) default NULL,
  `ORDERS` int(11) default NULL,
  `NEXT` int(11) default NULL,
  `GROUPID` varchar(50) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Category
#


#
# Table structure for table Consign
#

CREATE TABLE `Consign` (
  `OID` int(10) NOT NULL,
  `TYPE` int(10) NOT NULL,
  `FLAG` int(10) default NULL,
  `STATUS` int(10) default NULL,
  `SRC` varchar(100) NOT NULL,
  `SRCPWD` varchar(100) NOT NULL,
  `DEST` varchar(100) NOT NULL,
  `PKGID` varchar(100) default NULL,
  `PKGNAME` varchar(100) default NULL,
  `PDEFID` varchar(100) default NULL,
  `PDEFVER` varchar(100) default NULL,
  `PDEFNAME` varchar(100) default NULL,
  `PARTID` varchar(100) default NULL,
  `PARTNAME` varchar(100) default NULL,
  `PROCID` varchar(100) default NULL,
  `PROCNAME` varchar(100) default NULL,
  `ACTID` varchar(100) default NULL,
  `ACTNAME` varchar(100) default NULL,
  `VERSION` int(10) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Consign
#


#
# Table structure for table ConsignChain
#

CREATE TABLE `ConsignChain` (
  `OID` int(10) NOT NULL,
  `CONSIGNTIME` date default NULL,
  `ACTUSER` varchar(100) NOT NULL,
  `CONSIGNUSER` varchar(100) NOT NULL,
  `CONSIGNID` int(10) NOT NULL,
  `VERSION` int(10) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ConsignChain
#


#
# Table structure for table ConsignEventAudit
#

CREATE TABLE `ConsignEventAudit` (
  `OID` int(10) NOT NULL,
  `TIME` date NOT NULL,
  `ACTION` int(10) default NULL,
  `ACTUSER` int(10) default NULL,
  `TYPE` int(10) NOT NULL,
  `FLAG` int(10) default NULL,
  `STATUS` int(10) default NULL,
  `SRC` varchar(100) default NULL,
  `SRCPWD` varchar(100) NOT NULL,
  `DEST` varchar(100) default NULL,
  `PKGID` varchar(100) default NULL,
  `PKGNAME` varchar(100) default NULL,
  `PDEFID` varchar(100) default NULL,
  `PDEFVER` varchar(100) default NULL,
  `PDEFNAME` varchar(100) default NULL,
  `PARTID` varchar(100) default NULL,
  `PARTNAME` varchar(100) default NULL,
  `PROCID` varchar(100) default NULL,
  `PROCNAME` varchar(100) default NULL,
  `ACTID` varchar(100) default NULL,
  `ACTNAME` varchar(100) default NULL,
  `VERSION` int(10) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ConsignEventAudit
#


#
# Table structure for table Counters
#

CREATE TABLE `Counters` (
  `name` varchar(100) NOT NULL,
  `the_number` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_Counters` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Counters
#


#
# Table structure for table CreateProcessEventAudits
#

CREATE TABLE `CreateProcessEventAudits` (
  `UTCTime` varchar(254) NOT NULL,
  `TheType` decimal(19,0) NOT NULL,
  `ProcessId` varchar(100) NOT NULL,
  `ProcessName` varchar(254) default NULL,
  `ProcessDefinitionName` varchar(200) NOT NULL,
  `ProcessDefinitionVersion` varchar(20) NOT NULL,
  `ProcessDefinitionId` varchar(90) NOT NULL,
  `PackageId` varchar(90) NOT NULL,
  `PActivityId` varchar(100) default NULL,
  `PProcessId` varchar(100) default NULL,
  `PProcessName` varchar(254) default NULL,
  `PProcessDefinitionName` varchar(200) default NULL,
  `PProcessDefinitionVersion` varchar(20) default NULL,
  `PActivityDefinitionId` varchar(90) default NULL,
  `PActivitySetDefinitionId` varchar(90) default NULL,
  `PProcessDefinitionId` varchar(90) default NULL,
  `PPackageId` varchar(90) default NULL,
  `CNT` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_CreateProcessEventAudits` (`CNT`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table CreateProcessEventAudits
#


#
# Table structure for table DataEventAudits
#

CREATE TABLE `DataEventAudits` (
  `UTCTime` varchar(254) NOT NULL,
  `TheType` decimal(19,0) NOT NULL,
  `ActivityId` varchar(100) default NULL,
  `ActivityName` varchar(254) default NULL,
  `ProcessId` varchar(100) NOT NULL,
  `ProcessName` varchar(254) default NULL,
  `ProcessDefinitionName` varchar(200) NOT NULL,
  `ProcessDefinitionVersion` varchar(20) NOT NULL,
  `ActivityDefinitionId` varchar(90) default NULL,
  `ActivitySetDefinitionId` varchar(90) default NULL,
  `ProcessDefinitionId` varchar(90) NOT NULL,
  `PackageId` varchar(90) NOT NULL,
  `CNT` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_DataEventAudits` (`CNT`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table DataEventAudits
#


#
# Table structure for table Deadlines
#

CREATE TABLE `Deadlines` (
  `Process` decimal(19,0) NOT NULL,
  `Activity` decimal(19,0) NOT NULL,
  `CNT` decimal(19,0) NOT NULL,
  `TimeLimit` bigint(20) NOT NULL,
  `ExceptionName` varchar(100) NOT NULL,
  `IsSynchronous` smallint(6) NOT NULL,
  `IsExecuted` smallint(6) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_Deadlines` (`CNT`),
  KEY `I2_Deadlines` (`Process`,`TimeLimit`),
  KEY `I3_Deadlines` (`Activity`,`TimeLimit`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Deadlines
#


#
# Table structure for table EventTypes
#

CREATE TABLE `EventTypes` (
  `KeyValue` varchar(30) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_EventTypes` (`KeyValue`),
  UNIQUE KEY `I2_EventTypes` (`Name`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table EventTypes
#

INSERT INTO `EventTypes` VALUES ('packageLoaded','packageLoaded',1000024,0);
INSERT INTO `EventTypes` VALUES ('packageUnloaded','packageUnloaded',1000025,0);
INSERT INTO `EventTypes` VALUES ('packageUpdated','packageUpdated',1000026,0);
INSERT INTO `EventTypes` VALUES ('processCreated','processCreated',1000027,0);
INSERT INTO `EventTypes` VALUES ('processStateChanged','processStateChanged',1000028,0);
INSERT INTO `EventTypes` VALUES ('processContextChanged','processContextChanged',1000029,0);
INSERT INTO `EventTypes` VALUES ('activityStateChanged','activityStateChanged',1000030,0);
INSERT INTO `EventTypes` VALUES ('activityContextChanged','activityContextChanged',1000031,0);
INSERT INTO `EventTypes` VALUES ('activityResultChanged','activityResultChanged',1000032,0);
INSERT INTO `EventTypes` VALUES ('activityAssignmentChanged','activityAssignmentChanged',1000033,0);

#
# Table structure for table Forms
#

CREATE TABLE `Forms` (
  `OID` int(10) NOT NULL auto_increment,
  `NAME` varchar(100) NOT NULL,
  `CONTENT` longtext,
  `FIELDNAMES` longtext,
  `VERSION` int(10) default NULL,
  PRIMARY KEY  (`OID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Forms
#


#
# Table structure for table GroupGroupTable
#

CREATE TABLE `GroupGroupTable` (
  `sub_gid` decimal(19,0) NOT NULL,
  `groupid` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_GroupGroupTable` (`sub_gid`,`groupid`),
  KEY `I2_GroupGroupTable` (`groupid`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table GroupGroupTable
#


#
# Table structure for table GroupTable
#

CREATE TABLE `GroupTable` (
  `groupid` varchar(100) NOT NULL,
  `description` varchar(254) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_GroupTable` (`groupid`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table GroupTable
#

INSERT INTO `GroupTable` VALUES ('SHARK_DEFAULT','',1000035,0);
INSERT INTO `GroupTable` VALUES ('AdminGroup','Default Admin Group',1000036,0);

#
# Table structure for table GroupUser
#

CREATE TABLE `GroupUser` (
  `USERNAME` varchar(100) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_GroupUser` (`USERNAME`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table GroupUser
#


#
# Table structure for table GroupUserPackLevelParticipant
#

CREATE TABLE `GroupUserPackLevelParticipant` (
  `PARTICIPANTOID` decimal(19,0) NOT NULL,
  `USEROID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_GroupUserPackLevelParticipant` (`PARTICIPANTOID`,`USEROID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table GroupUserPackLevelParticipant
#


#
# Table structure for table GroupUserProcLevelParticipant
#

CREATE TABLE `GroupUserProcLevelParticipant` (
  `PARTICIPANTOID` decimal(19,0) NOT NULL,
  `USEROID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_GroupUserProcLevelParticipant` (`PARTICIPANTOID`,`USEROID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table GroupUserProcLevelParticipant
#


#
# Table structure for table LockTable
#

CREATE TABLE `LockTable` (
  `EngineName` varchar(100) NOT NULL,
  `Id` varchar(100) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_LockTable` (`Id`),
  UNIQUE KEY `I2_LockTable` (`Id`,`EngineName`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table LockTable
#


#
# Table structure for table NewEventAuditData
#

CREATE TABLE `NewEventAuditData` (
  `DataEventAudit` decimal(19,0) NOT NULL,
  `VariableDefinitionId` varchar(100) NOT NULL,
  `VariableType` int(11) NOT NULL,
  `VariableValue` mediumblob,
  `VariableValueVCHAR` varchar(255) default NULL,
  `VariableValueDBL` float default NULL,
  `VariableValueLONG` bigint(20) default NULL,
  `VariableValueDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `VariableValueBOOL` smallint(6) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_NewEventAuditData` (`DataEventAudit`,`VariableDefinitionId`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table NewEventAuditData
#


#
# Table structure for table NewEventAuditDataBlobs
#

CREATE TABLE `NewEventAuditDataBlobs` (
  `NewEventAuditDataWOB` decimal(19,0) NOT NULL,
  `VariableValue` mediumblob,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_NewEventAuditDataBLOBs` (`NewEventAuditDataWOB`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table NewEventAuditDataBlobs
#


#
# Table structure for table NewEventAuditDataWOB
#

CREATE TABLE `NewEventAuditDataWOB` (
  `DataEventAudit` decimal(19,0) NOT NULL,
  `VariableDefinitionId` varchar(100) NOT NULL,
  `VariableType` int(11) NOT NULL,
  `VariableValueVCHAR` varchar(255) default NULL,
  `VariableValueDBL` float default NULL,
  `VariableValueLONG` bigint(20) default NULL,
  `VariableValueDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `VariableValueBOOL` smallint(6) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_NewEventAuditDataWOB` (`DataEventAudit`,`VariableDefinitionId`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table NewEventAuditDataWOB
#


#
# Table structure for table NextXPDLVersions
#

CREATE TABLE `NextXPDLVersions` (
  `XPDLId` varchar(90) NOT NULL,
  `NextVersion` varchar(20) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_NextXPDLVersions` (`XPDLId`,`NextVersion`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table NextXPDLVersions
#


#
# Table structure for table NormalUser
#

CREATE TABLE `NormalUser` (
  `USERNAME` varchar(100) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_NormalUser` (`USERNAME`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table NormalUser
#


#
# Table structure for table ObjectId
#

CREATE TABLE `ObjectId` (
  `next` decimal(19,0) NOT NULL,
  PRIMARY KEY  (`next`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ObjectId
#


#
# Table structure for table OldEventAuditData
#

CREATE TABLE `OldEventAuditData` (
  `DataEventAudit` decimal(19,0) NOT NULL,
  `VariableDefinitionId` varchar(100) NOT NULL,
  `VariableType` int(11) NOT NULL,
  `VariableValue` mediumblob,
  `VariableValueVCHAR` varchar(255) default NULL,
  `VariableValueDBL` float default NULL,
  `VariableValueLONG` bigint(20) default NULL,
  `VariableValueDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `VariableValueBOOL` smallint(6) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_OldEventAuditData` (`DataEventAudit`,`VariableDefinitionId`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table OldEventAuditData
#


#
# Table structure for table OldEventAuditDataBLOBs
#

CREATE TABLE `OldEventAuditDataBLOBs` (
  `OldEventAuditDataWOB` decimal(19,0) NOT NULL,
  `VariableValue` mediumblob,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_OldEventAuditDataBLOBs` (`OldEventAuditDataWOB`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table OldEventAuditDataBLOBs
#


#
# Table structure for table OldEventAuditDataWOB
#

CREATE TABLE `OldEventAuditDataWOB` (
  `DataEventAudit` decimal(19,0) NOT NULL,
  `VariableDefinitionId` varchar(100) NOT NULL,
  `VariableType` int(11) NOT NULL,
  `VariableValueVCHAR` varchar(255) default NULL,
  `VariableValueDBL` float default NULL,
  `VariableValueLONG` bigint(20) default NULL,
  `VariableValueDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `VariableValueBOOL` smallint(6) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_OldEventAuditDataWOB` (`DataEventAudit`,`VariableDefinitionId`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table OldEventAuditDataWOB
#


#
# Table structure for table PackLevelParticipant
#

CREATE TABLE `PackLevelParticipant` (
  `PARTICIPANT_ID` varchar(90) NOT NULL,
  `PACKAGEOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_PackLevelParticipant` (`PARTICIPANT_ID`,`PACKAGEOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table PackLevelParticipant
#


#
# Table structure for table PackLevelXPDLApp
#

CREATE TABLE `PackLevelXPDLApp` (
  `APPLICATION_ID` varchar(90) NOT NULL,
  `PACKAGEOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_PackLevelXPDLApp` (`APPLICATION_ID`,`PACKAGEOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table PackLevelXPDLApp
#


#
# Table structure for table PackLevelXPDLAppTAAppDetail
#

CREATE TABLE `PackLevelXPDLAppTAAppDetail` (
  `XPDL_APPOID` decimal(19,0) NOT NULL,
  `TOOLAGENTOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_PackLevelXPDLAppTAAppDetail` (`XPDL_APPOID`,`TOOLAGENTOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table PackLevelXPDLAppTAAppDetail
#


#
# Table structure for table PackLevelXPDLAppTAAppDetailUsr
#

CREATE TABLE `PackLevelXPDLAppTAAppDetailUsr` (
  `XPDL_APPOID` decimal(19,0) NOT NULL,
  `TOOLAGENTOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_PackLevelXPDLAppTAAppDetailUsr` (`XPDL_APPOID`,`TOOLAGENTOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table PackLevelXPDLAppTAAppDetailUsr
#


#
# Table structure for table PackLevelXPDLAppTAAppUser
#

CREATE TABLE `PackLevelXPDLAppTAAppUser` (
  `XPDL_APPOID` decimal(19,0) NOT NULL,
  `TOOLAGENTOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_PackLevelXPDLAppTAAppUser` (`XPDL_APPOID`,`TOOLAGENTOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table PackLevelXPDLAppTAAppUser
#


#
# Table structure for table PackLevelXPDLAppToolAgentApp
#

CREATE TABLE `PackLevelXPDLAppToolAgentApp` (
  `XPDL_APPOID` decimal(19,0) NOT NULL,
  `TOOLAGENTOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_PackLevelXPDLAppToolAgentApp` (`XPDL_APPOID`,`TOOLAGENTOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table PackLevelXPDLAppToolAgentApp
#


#
# Table structure for table PopeDom2Role
#

CREATE TABLE `PopeDom2Role` (
  `POPEDOM2ROLEID` int(11) NOT NULL,
  `ROLEID` int(11) NOT NULL,
  `POPEDOM` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table PopeDom2Role
#


#
# Table structure for table ProcLevelParticipant
#

CREATE TABLE `ProcLevelParticipant` (
  `PARTICIPANT_ID` varchar(90) NOT NULL,
  `PROCESSOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcLevelParticipant` (`PARTICIPANT_ID`,`PROCESSOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcLevelParticipant
#


#
# Table structure for table ProcLevelXPDLApp
#

CREATE TABLE `ProcLevelXPDLApp` (
  `APPLICATION_ID` varchar(90) NOT NULL,
  `PROCESSOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcLevelXPDLApp` (`APPLICATION_ID`,`PROCESSOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcLevelXPDLApp
#


#
# Table structure for table ProcLevelXPDLAppTAAppDetail
#

CREATE TABLE `ProcLevelXPDLAppTAAppDetail` (
  `XPDL_APPOID` decimal(19,0) NOT NULL,
  `TOOLAGENTOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcLevelXPDLAppTAAppDetail` (`XPDL_APPOID`,`TOOLAGENTOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcLevelXPDLAppTAAppDetail
#


#
# Table structure for table ProcLevelXPDLAppTAAppDetailUsr
#

CREATE TABLE `ProcLevelXPDLAppTAAppDetailUsr` (
  `XPDL_APPOID` decimal(19,0) NOT NULL,
  `TOOLAGENTOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcLevelXPDLAppTAAppDetailUsr` (`XPDL_APPOID`,`TOOLAGENTOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcLevelXPDLAppTAAppDetailUsr
#


#
# Table structure for table ProcLevelXPDLAppTAAppUser
#

CREATE TABLE `ProcLevelXPDLAppTAAppUser` (
  `XPDL_APPOID` decimal(19,0) NOT NULL,
  `TOOLAGENTOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcLevelXPDLAppTAAppUser` (`XPDL_APPOID`,`TOOLAGENTOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcLevelXPDLAppTAAppUser
#


#
# Table structure for table ProcLevelXPDLAppToolAgentApp
#

CREATE TABLE `ProcLevelXPDLAppToolAgentApp` (
  `XPDL_APPOID` decimal(19,0) NOT NULL,
  `TOOLAGENTOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcLevelXPDLAppToolAgentApp` (`XPDL_APPOID`,`TOOLAGENTOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcLevelXPDLAppToolAgentApp
#


#
# Table structure for table ProcessData
#

CREATE TABLE `ProcessData` (
  `Process` decimal(19,0) NOT NULL,
  `VariableDefinitionId` varchar(100) NOT NULL,
  `VariableType` int(11) NOT NULL,
  `VariableValue` mediumblob,
  `VariableValueVCHAR` varchar(255) default NULL,
  `VariableValueDBL` double default NULL,
  `VariableValueLONG` bigint(20) default NULL,
  `VariableValueDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `VariableValueBOOL` smallint(6) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcessData` (`Process`,`VariableDefinitionId`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcessData
#


#
# Table structure for table ProcessDataBLOBs
#

CREATE TABLE `ProcessDataBLOBs` (
  `ProcessDataWOB` decimal(19,0) NOT NULL,
  `VariableValue` mediumblob,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcessDataBLOBs` (`ProcessDataWOB`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcessDataBLOBs
#


#
# Table structure for table ProcessDataWOB
#

CREATE TABLE `ProcessDataWOB` (
  `Process` decimal(19,0) NOT NULL,
  `VariableDefinitionId` varchar(100) NOT NULL,
  `VariableType` int(11) NOT NULL,
  `VariableValueVCHAR` varchar(255) default NULL,
  `VariableValueDBL` double default NULL,
  `VariableValueLONG` bigint(20) default NULL,
  `VariableValueDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `VariableValueBOOL` smallint(6) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcessDataWOB` (`Process`,`VariableDefinitionId`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcessDataWOB
#


#
# Table structure for table ProcessDefinitions
#

CREATE TABLE `ProcessDefinitions` (
  `Name` varchar(200) NOT NULL,
  `PackageId` varchar(90) NOT NULL,
  `ProcessDefinitionId` varchar(90) NOT NULL,
  `ProcessDefinitionCreated` bigint(20) NOT NULL,
  `ProcessDefinitionVersion` varchar(20) NOT NULL,
  `State` int(11) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcessDefinitions` (`Name`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcessDefinitions
#


#
# Table structure for table ProcessRequesters
#

CREATE TABLE `ProcessRequesters` (
  `Id` varchar(100) NOT NULL,
  `ActivityRequester` decimal(19,0) default NULL,
  `ResourceRequester` decimal(19,0) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcessRequesters` (`Id`),
  KEY `I2_ProcessRequesters` (`ActivityRequester`),
  KEY `I3_ProcessRequesters` (`ResourceRequester`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcessRequesters
#


#
# Table structure for table ProcessStateEventAudits
#

CREATE TABLE `ProcessStateEventAudits` (
  `KeyValue` varchar(30) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcessStateEventAudits` (`KeyValue`),
  UNIQUE KEY `I2_ProcessStateEventAudits` (`Name`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcessStateEventAudits
#

INSERT INTO `ProcessStateEventAudits` VALUES ('open.running','open.running',1000012,0);
INSERT INTO `ProcessStateEventAudits` VALUES ('open.not_running.not_started','open.not_running.not_started',1000014,0);
INSERT INTO `ProcessStateEventAudits` VALUES ('open.not_running.suspended','open.not_running.suspended',1000016,0);
INSERT INTO `ProcessStateEventAudits` VALUES ('closed.completed','closed.completed',1000018,0);
INSERT INTO `ProcessStateEventAudits` VALUES ('closed.terminated','closed.terminated',1000020,0);
INSERT INTO `ProcessStateEventAudits` VALUES ('closed.aborted','closed.aborted',1000022,0);

#
# Table structure for table ProcessStates
#

CREATE TABLE `ProcessStates` (
  `KeyValue` varchar(30) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ProcessStates` (`KeyValue`),
  UNIQUE KEY `I2_ProcessStates` (`Name`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ProcessStates
#

INSERT INTO `ProcessStates` VALUES ('open.running','open.running',1000000,0);
INSERT INTO `ProcessStates` VALUES ('open.not_running.not_started','open.not_running.not_started',1000002,0);
INSERT INTO `ProcessStates` VALUES ('open.not_running.suspended','open.not_running.suspended',1000004,0);
INSERT INTO `ProcessStates` VALUES ('closed.completed','closed.completed',1000006,0);
INSERT INTO `ProcessStates` VALUES ('closed.terminated','closed.terminated',1000008,0);
INSERT INTO `ProcessStates` VALUES ('closed.aborted','closed.aborted',1000010,0);

#
# Table structure for table Processes
#

CREATE TABLE `Processes` (
  `Id` varchar(100) NOT NULL,
  `ProcessDefinition` decimal(19,0) NOT NULL,
  `PDefName` varchar(200) NOT NULL,
  `ActivityRequesterId` varchar(100) default NULL,
  `ActivityRequesterProcessId` varchar(100) default NULL,
  `ResourceRequesterId` varchar(100) NOT NULL,
  `ExternalRequesterClassName` varchar(254) default NULL,
  `State` decimal(19,0) NOT NULL,
  `Priority` int(11) default NULL,
  `Name` varchar(254) default NULL,
  `Created` bigint(20) NOT NULL,
  `Started` bigint(20) default NULL,
  `LastStateTime` bigint(20) NOT NULL,
  `LimitTime` bigint(20) NOT NULL,
  `Description` varchar(254) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_Processes` (`Id`),
  KEY `I2_Processes` (`ProcessDefinition`),
  KEY `I3_Processes` (`State`),
  KEY `I4_Processes` (`ActivityRequesterId`),
  KEY `I5_Processes` (`ResourceRequesterId`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Processes
#


#
# Table structure for table ResourcesTable
#

CREATE TABLE `ResourcesTable` (
  `Username` varchar(100) NOT NULL,
  `Name` varchar(100) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ResourcesTable` (`Username`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ResourcesTable
#

INSERT INTO `ResourcesTable` VALUES ('admin',NULL,1000203,0);
INSERT INTO `ResourcesTable` VALUES ('yijun',NULL,1000801,0);

#
# Table structure for table Role2Category
#

CREATE TABLE `Role2Category` (
  `ROLE2CATEGORYID` int(11) NOT NULL,
  `CATEGORYID` int(11) NOT NULL,
  `ROLEID` int(11) NOT NULL,
  `POPEDOM` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Role2Category
#


#
# Table structure for table Role2Group
#

CREATE TABLE `Role2Group` (
  `ROLE2GROUPID` int(11) NOT NULL,
  `ROLEID` int(11) NOT NULL,
  `GROUPTABLEOID` int(11) NOT NULL,
  `CREATEDATE` date default NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Role2Group
#


#
# Table structure for table Role2User
#

CREATE TABLE `Role2User` (
  `ROLE2USERID` int(11) NOT NULL,
  `ROLEID` int(11) NOT NULL,
  `USERTABLEOID` int(11) NOT NULL,
  `CREATEDATE` date default NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Role2User
#


#
# Table structure for table Roles
#

CREATE TABLE `Roles` (
  `ROLEID` int(11) NOT NULL,
  `ROLENAME` varchar(50) NOT NULL,
  `CREATEDATE` date default NULL,
  `DESCRIPTION` varchar(255) default NULL,
  `USERID` varchar(255) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table Roles
#


#
# Table structure for table StateEventAudits
#

CREATE TABLE `StateEventAudits` (
  `UTCTime` varchar(254) NOT NULL,
  `TheType` decimal(19,0) NOT NULL,
  `ActivityId` varchar(100) default NULL,
  `ActivityName` varchar(254) default NULL,
  `ProcessId` varchar(100) NOT NULL,
  `ProcessName` varchar(254) default NULL,
  `ProcessDefinitionName` varchar(200) NOT NULL,
  `ProcessDefinitionVersion` varchar(20) NOT NULL,
  `ActivityDefinitionId` varchar(90) default NULL,
  `ActivitySetDefinitionId` varchar(90) default NULL,
  `ProcessDefinitionId` varchar(90) NOT NULL,
  `PackageId` varchar(90) NOT NULL,
  `OldProcessState` decimal(19,0) default NULL,
  `NewProcessState` decimal(19,0) default NULL,
  `OldActivityState` decimal(19,0) default NULL,
  `NewActivityState` decimal(19,0) default NULL,
  `CNT` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_StateEventAudits` (`CNT`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table StateEventAudits
#


#
# Table structure for table ToolAgentApp
#

CREATE TABLE `ToolAgentApp` (
  `TOOL_AGENT_NAME` varchar(250) NOT NULL,
  `APP_NAME` varchar(90) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ToolAgentApp` (`TOOL_AGENT_NAME`,`APP_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ToolAgentApp
#


#
# Table structure for table ToolAgentAppDetail
#

CREATE TABLE `ToolAgentAppDetail` (
  `APP_MODE` decimal(10,0) NOT NULL,
  `TOOLAGENT_APPOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ToolAgentAppDetail` (`APP_MODE`,`TOOLAGENT_APPOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ToolAgentAppDetail
#


#
# Table structure for table ToolAgentAppDetailUser
#

CREATE TABLE `ToolAgentAppDetailUser` (
  `TOOLAGENT_APPOID` decimal(19,0) NOT NULL,
  `USEROID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ToolAgentAppDetailUser` (`TOOLAGENT_APPOID`,`USEROID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ToolAgentAppDetailUser
#


#
# Table structure for table ToolAgentAppUser
#

CREATE TABLE `ToolAgentAppUser` (
  `TOOLAGENT_APPOID` decimal(19,0) NOT NULL,
  `USEROID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ToolAgentAppUser` (`TOOLAGENT_APPOID`,`USEROID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ToolAgentAppUser
#


#
# Table structure for table ToolAgentUser
#

CREATE TABLE `ToolAgentUser` (
  `USERNAME` varchar(100) NOT NULL,
  `PWD` varchar(100) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_ToolAgentUser` (`USERNAME`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table ToolAgentUser
#


#
# Table structure for table UserGroupTable
#

CREATE TABLE `UserGroupTable` (
  `userid` decimal(19,0) NOT NULL,
  `groupid` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_UserGroupTable` (`userid`,`groupid`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table UserGroupTable
#

INSERT INTO `UserGroupTable` VALUES (1000037,1000036,1000038,0);
INSERT INTO `UserGroupTable` VALUES (1000201,1000036,1000202,0);
INSERT INTO `UserGroupTable` VALUES (1000401,1000035,1000402,0);

#
# Table structure for table UserPackLevelParticipant
#

CREATE TABLE `UserPackLevelParticipant` (
  `PARTICIPANTOID` decimal(19,0) NOT NULL,
  `USEROID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_UserPackLevelParticipant` (`PARTICIPANTOID`,`USEROID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table UserPackLevelParticipant
#


#
# Table structure for table UserProcLevelParticipant
#

CREATE TABLE `UserProcLevelParticipant` (
  `PARTICIPANTOID` decimal(19,0) NOT NULL,
  `USEROID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_UserProcLevelParticipant` (`PARTICIPANTOID`,`USEROID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table UserProcLevelParticipant
#


#
# Table structure for table UserTable
#

CREATE TABLE `UserTable` (
  `userid` varchar(100) NOT NULL,
  `firstname` varchar(50) default NULL,
  `lastname` varchar(50) default NULL,
  `passwd` varchar(50) NOT NULL,
  `email` varchar(254) default NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_UserTable` (`userid`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table UserTable
#

INSERT INTO `UserTable` VALUES ('SHARK_ADMIN','','','112bb791304791ddcf692e29fd5cf149b35fea37','',1000037,0);
INSERT INTO `UserTable` VALUES ('admin','Administrator','Admin','73d7665487f88c55adb98e90a84f579728032f46','admin@together.at',1000201,0);
INSERT INTO `UserTable` VALUES ('yijun','','','112bb791304791ddcf692e29fd5cf149b35fea37','',1000401,0);

#
# Table structure for table WF_ACT_TIME
#

CREATE TABLE `WF_ACT_TIME` (
  `ID` int(11) NOT NULL auto_increment,
  `PID` varchar(64) default NULL COMMENT '����id',
  `TID` varchar(64) default NULL COMMENT '����id',
  `PLAN_START_TIME` bigint(20) default NULL COMMENT 'Ԥ�ƿ�ʼʱ��',
  `START_TIME` bigint(20) default NULL COMMENT 'ʵ�ʿ�ʼʱ��',
  `PERFORMER` varchar(64) default NULL COMMENT '����ִ����',
  `SETTINGUSER` varchar(64) default NULL COMMENT '�趨����������ִ��ʱ����û���',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='������������������ʱ���ʵ�����ʱ���';

#
# Dumping data for table WF_ACT_TIME
#


#
# Table structure for table WF_ADMIN_RIGHT_MAPPING
#

CREATE TABLE `WF_ADMIN_RIGHT_MAPPING` (
  `ID` int(11) NOT NULL auto_increment COMMENT 'ID',
  `USER_ID` int(11) default NULL COMMENT '�û�id',
  `ADMIN_RIGHT_ID` int(11) default NULL COMMENT 'ϵͳȨ��id',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='ϵͳȨ��ӳ���';

#
# Dumping data for table WF_ADMIN_RIGHT_MAPPING
#


#
# Table structure for table WF_AGENT
#

CREATE TABLE `WF_AGENT` (
  `AGENT_ID` int(11) NOT NULL auto_increment COMMENT '����ID',
  `USER_NAME` varchar(50) default NULL COMMENT '�û�ID',
  `MIDDLE_USER_NAME` varchar(500) default NULL COMMENT '�м������ID',
  `TARGET_USER_NAME` varchar(50) default NULL COMMENT '���մ�����ID',
  `PACKAGE_ID` varchar(50) default NULL COMMENT '���������̵İ�ID',
  `PROCESS_ID` varchar(50) default NULL COMMENT '���������̵�����ID',
  `START_TIME` datetime default NULL COMMENT '��ʼʱ��',
  `STOP_TIME` datetime default NULL COMMENT '����ʱ��',
  `AGENT_INFO` varchar(200) default NULL COMMENT '������Ϣ',
  `IS_EXPIRED` int(11) default NULL COMMENT '�Ƿ���ʧЧ',
  PRIMARY KEY  (`AGENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='������Ϣ��';

#
# Dumping data for table WF_AGENT
#


#
# Table structure for table WF_ATTACH
#

CREATE TABLE `WF_ATTACH` (
  `ID` int(11) NOT NULL auto_increment,
  `PROCESSID` varchar(50) default '0',
  `ACTID` varchar(50) default '0',
  `PROCESSNAME` varchar(100) default NULL,
  `ACTNAME` varchar(100) default NULL,
  `REALNAME` varchar(200) default NULL,
  `FILENAME` varchar(200) default NULL,
  `ATTACHVERSION` int(11) default '0',
  `UPLOADTIME` varchar(50) default NULL,
  `UPLOADUSER` varchar(50) default NULL,
  `ATTACHTYPE` varchar(200) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table WF_ATTACH
#


#
# Table structure for table WF_DEPARTMENT
#

CREATE TABLE `WF_DEPARTMENT` (
  `DEPART_ID` int(11) NOT NULL auto_increment COMMENT '����ID',
  `DEPART_FULL_NAME` varchar(50) default NULL COMMENT '��������',
  `DEPART_SHORT_NAME` varchar(20) default NULL COMMENT '���ż��',
  `DEPART_DESC` varchar(100) default NULL COMMENT '��������',
  `DEPART_FULL_ENG_NAME` varchar(100) default NULL COMMENT '����Ӣ��ȫ��',
  `DEPART_SHORT_ENG_NAME` varchar(20) default NULL COMMENT '����Ӣ�ļ��',
  `DEPART_LEVEL` int(11) default NULL COMMENT '���ż���',
  PRIMARY KEY  (`DEPART_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='���ű�';

#
# Dumping data for table WF_DEPARTMENT
#


#
# Table structure for table WF_DEPARTMENT_RELATION
#

CREATE TABLE `WF_DEPARTMENT_RELATION` (
  `ID` int(11) NOT NULL auto_increment COMMENT 'id',
  `PARENT_DEPART` int(11) default NULL COMMENT '������',
  `CHILD_DEPART` int(11) default NULL COMMENT '�Ӳ���',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='���Ź�ϵ��';

#
# Dumping data for table WF_DEPARTMENT_RELATION
#


#
# Table structure for table WF_DEPART_ROLE_RIGHT_MAPPING
#

CREATE TABLE `WF_DEPART_ROLE_RIGHT_MAPPING` (
  `ID` int(11) NOT NULL auto_increment COMMENT 'id',
  `DEPART_ROLE_ID` int(11) default NULL COMMENT '���Ž�ɫid',
  `DEPART_ID` int(11) default NULL COMMENT '����ID',
  `RIGHT_ID` int(11) default NULL COMMENT 'Ȩ��id',
  `IS_ENABLE` int(11) default NULL COMMENT '�Ƿ����',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='���Ź���Ȩ��ӳ���';

#
# Dumping data for table WF_DEPART_ROLE_RIGHT_MAPPING
#


#
# Table structure for table WF_FLOW_INFO
#

CREATE TABLE `WF_FLOW_INFO` (
  `ID` int(11) NOT NULL auto_increment COMMENT 'id',
  `PID` varchar(50) default NULL COMMENT '����ʵ��id',
  `NAME` varchar(50) default NULL COMMENT '����',
  `VALUE` varchar(500) default NULL COMMENT 'ֵ',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='������Ϣ��';

#
# Dumping data for table WF_FLOW_INFO
#


#
# Table structure for table WF_FLOW_PROJECT_INFO
#

CREATE TABLE `WF_FLOW_PROJECT_INFO` (
  `PID` varchar(50) NOT NULL COMMENT '����ʵ��id',
  `PACKAGE_ID` varchar(50) default NULL COMMENT '���������İ�ID',
  `PROCESS_DEF_ID` varchar(50) default NULL COMMENT '���̶���id',
  `PROCESS_UN` varchar(50) default NULL COMMENT '����������',
  `PROCESS_TYPE` varchar(50) default NULL COMMENT '��������',
  `DEPART_ID` int(11) default NULL COMMENT '�����Ĳ���id',
  `PRODUCT` varchar(50) default NULL COMMENT '��Ʒ��',
  `PROJECT_NUM` varchar(50) default NULL COMMENT '��Ŀ���',
  `PROJECT_NAME` varchar(50) default NULL COMMENT '��Ŀ����',
  `MANAGER_UN` varchar(50) default NULL COMMENT '��Ŀ����',
  `PLAN_START_TIME` date default NULL COMMENT 'Ԥ�ƿ�ʼʱ�䣨��һ������',
  `PLAN_COMPLETE_TIME` date default NULL COMMENT 'Ԥ�ƽ���ʱ�䣨���һ������',
  `START_TIME` date default NULL COMMENT 'ʵ�ʿ�ʼʱ��',
  `COMPLETE_TIME` date default NULL COMMENT 'ʵ�ʽ���ʱ��',
  `UP_PID` varchar(50) default NULL COMMENT '��������pid���¼��������ϼ���pid��',
  `DOWN_PID` varchar(50) default NULL COMMENT '��������pid���ϼ��������¼���pid��',
  `FINISHED` int(11) default '0' COMMENT '�Ƿ������',
  PRIMARY KEY  (`PID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='������Ŀ��Ϣ��';

#
# Dumping data for table WF_FLOW_PROJECT_INFO
#


#
# Table structure for table WF_FLOW_PROJECT_INFOMATION
#

CREATE TABLE `WF_FLOW_PROJECT_INFOMATION` (
  `ID` int(11) NOT NULL auto_increment,
  `PID` varchar(64) NOT NULL COMMENT '����ʵ��id',
  `PACKAGE_ID` varchar(64) default NULL COMMENT '��id',
  `PROCESS_DEF_ID` varchar(64) default NULL COMMENT '���̶���id',
  `PROCESS_UN` varchar(64) default NULL COMMENT '���̴�����',
  `PROCESS_TYPE` varchar(64) default NULL COMMENT '��������',
  `DEPART_ID` int(11) default NULL COMMENT '������������id',
  `PRODUCT` varchar(64) default NULL COMMENT '��Ʒ��',
  `PROJECT_NUM` varchar(64) default NULL COMMENT '��Ŀ���',
  `PROJECT_NAME` varchar(64) default NULL COMMENT '��Ŀ��',
  `MANAGER_UN` varchar(64) default NULL COMMENT '��Ŀ����',
  `PLAN_START_TIME` bigint(20) default '0' COMMENT '�ƻ���ʼʱ��',
  `PLAN_COMPLETE_TIME` bigint(20) default '0' COMMENT '�ƻ����ʱ��',
  `START_TIME` bigint(20) default '0' COMMENT 'ʵ�ʿ�ʼʱ��',
  `COMPLETE_TIME` bigint(20) default '0' COMMENT 'ʵ�����ʱ��',
  `UP_PID` varchar(1024) default NULL COMMENT '���Ϲ���id',
  `DOWN_PID` varchar(1024) default NULL COMMENT '���¹���id',
  `FINISHED` int(11) default '0' COMMENT '�Ƿ����',
  `CC_EMAIL` varchar(256) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

#
# Dumping data for table WF_FLOW_PROJECT_INFOMATION
#


#
# Table structure for table WF_FLOW_RIGHT
#

CREATE TABLE `WF_FLOW_RIGHT` (
  `ID` int(11) NOT NULL auto_increment COMMENT 'id',
  `RIGHT_TYPE` int(11) default NULL COMMENT 'Ȩ������',
  `PACKAGE_ID` varchar(50) default NULL COMMENT '����',
  `PROCESS_ID` varchar(50) default NULL COMMENT '������',
  `PROCESS_NAME` varchar(50) default NULL COMMENT '��������',
  `DEPART_ID` int(11) default NULL COMMENT '����ID',
  `DEPART_ROLE_ID` int(11) default NULL COMMENT '���Ž�ɫID',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='������Ȩ��';

#
# Dumping data for table WF_FLOW_RIGHT
#


#
# Table structure for table WF_FORMEDTASK
#

CREATE TABLE `WF_FORMEDTASK` (
  `ID` int(11) NOT NULL auto_increment COMMENT '����',
  `TASKID` int(11) default '0' COMMENT '����ID',
  `ISFORMED` int(1) default '0' COMMENT '�����Ƿ�֪ͨ1��֪ͨ��0 ��δ֪ͨ',
  `USERNAME` varchar(50) default NULL COMMENT '֪ͨ�˵�½��',
  `FORMTIME` varchar(50) default NULL COMMENT '֪ͨʱ��',
  `CONTENT` mediumtext COMMENT '֪ͨ����',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table WF_FORMEDTASK
#


#
# Table structure for table WF_PACKAGE
#

CREATE TABLE `WF_PACKAGE` (
  `ID` int(11) NOT NULL auto_increment,
  `PACKAGE_ID` varchar(128) NOT NULL,
  `PACKAGE_NAME` varchar(128) default NULL,
  `PROCESS_DEF_ID` varchar(128) default NULL,
  `PROCESS_DEF_NAME` varchar(128) default NULL,
  `GROUP_NAME` varchar(128) default NULL,
  `GROUP_ID` varchar(128) default NULL,
  `RELOAD` int(11) NOT NULL default '0',
  `FILE_PATH` varchar(128) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk COMMENT='���ذ������̶�������';

#
# Dumping data for table WF_PACKAGE
#


#
# Table structure for table WF_PARTICIPANT
#

CREATE TABLE `WF_PARTICIPANT` (
   `ID` int(11) NOT NULL auto_increment,
  `PROCESS_ID` varchar(128) NOT NULL,
  `PACKAGE_ID` varchar(128) default NULL,
  `PROCESS_DEF_ID` varchar(128) default NULL,
  `PARTICIPANT_ID` varchar(128) default NULL,
  `USER_ID` varchar(64) default NULL,
  `XOR_TYPE` int(11) NOT NULL default '0' COMMENT '��ʾ���ִ�����Ƿ���ϵ��1,���ϵ,:0,���ϵ',
  PRIMARY KEY  (`ID`),
  KEY `processid` (`PROCESS_ID`),
  KEY `participant` (`PARTICIPANT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;



#
# Dumping data for table WF_PARTICIPANT
#


#
# Table structure for table wf_start_user
#

DROP TABLE IF EXISTS `WF_START_USER`;
CREATE TABLE `WF_START_USER` (
  `ID` int(11) NOT NULL auto_increment,
  `PREPARE_ID` int(11) NOT NULL default '0' COMMENT 'ϵͳ�Զ�������Ψһid',
  `PARTICIPANT_ID` varchar(128) NOT NULL COMMENT '������id',
  `PARTICIPANT_NAME` varchar(64) default NULL COMMENT '������˵��',
  `ROLE_NAME` varchar(64) default NULL COMMENT '��ɫ����',
  `MUTI_SELECT` int(11) NOT NULL default '0' COMMENT '�Ƿ��ѡ',
  `COULD_NULL` int(11) NOT NULL default '0' COMMENT 'ִ�����Ƿ�����Ϊ��',
  `XOR_TYPE` int(11) NOT NULL default '0' COMMENT '���ִ����֮���Ƿ���ϵ',
  `USER_ID` varchar(64) default NULL COMMENT '�û�id',
  `USER_NAME` varchar(64) NOT NULL COMMENT '��ʵ�û���',
  `IS_SELECTED` int(11) NOT NULL default '0' COMMENT '�Ƿ���ѡ��״̬',
  `TINDEX` int(11) default NULL COMMENT '�����ĳ��ִ���',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='��������ʱ���ݴ���Ϣ';



#
# Table structure for table WF_PROCESS_ADVICE
#

CREATE TABLE `WF_PROCESS_ADVICE` (
  `ID` int(11) NOT NULL auto_increment,
  `PID` varchar(128) default NULL COMMENT '����ʵ��id',
  `POST_UN` varchar(64) default NULL COMMENT '���������',
  `POST_TM` bigint(20) default NULL COMMENT '�������ʱ��',
  `ADVICE` varchar(2048) default NULL COMMENT '�û���������',
  `URL` varchar(128) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='�����̵�����';

#
# Dumping data for table WF_PROCESS_ADVICE
#


#
# Table structure for table WF_PRODUCT
#

CREATE TABLE `WF_PRODUCT` (
  `PRODUCT_ID` int(11) NOT NULL auto_increment,
  `PRODUCT_NAME` varchar(50) default NULL COMMENT '��Ʒ������',
  `PRODUCT_DESC` varchar(100) default NULL COMMENT '��Ʒ������',
  `IS_ENABLE` int(11) default NULL,
  PRIMARY KEY  (`PRODUCT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk COMMENT='��Ʒ��';

#
# Dumping data for table WF_PRODUCT
#


#
# Table structure for table WF_PRODUCT_MAPPING
#

CREATE TABLE `WF_PRODUCT_MAPPING` (
  `ID` int(11) NOT NULL auto_increment,
  `PRODUCT_ID` int(11) NOT NULL default '0' COMMENT '��Ʒ��id',
  `DEPART_ID` int(11) NOT NULL default '0' COMMENT '����id',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk COMMENT='��Ʒ���벿�ŵ�ӳ��';

#
# Dumping data for table WF_PRODUCT_MAPPING
#


#
# Table structure for table WF_ROLE
#

CREATE TABLE `WF_ROLE` (
  `ROLE_ID` int(11) NOT NULL auto_increment COMMENT '��ɫID',
  `ROLE_NAME` varchar(100) default NULL COMMENT '��ɫ����',
  `ROLE_DESC` varchar(200) default NULL COMMENT '��ɫ����',
  `IS_ENABLE` int(11) default NULL COMMENT '�Ƿ����',
  PRIMARY KEY  (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='��ɫ�����';

#
# Dumping data for table WF_ROLE
#


#
# Table structure for table WF_ROLE_IN_DEPART
#

CREATE TABLE `WF_ROLE_IN_DEPART` (
  `DEPART_ROLE_ID` int(11) NOT NULL auto_increment COMMENT '���Ž�ɫID',
  `DEPART_ID` int(11) default NULL COMMENT '����ID',
  `ROLE_ID` int(11) default NULL COMMENT '��ɫID',
  `REMARK` varchar(200) default NULL COMMENT '��ע',
  `IS_ENABLE` int(11) default NULL COMMENT '�Ƿ����',
  PRIMARY KEY  (`DEPART_ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='���Ž�ɫ��';

#
# Dumping data for table WF_ROLE_IN_DEPART
#


#
# Table structure for table WF_USER
#

DROP TABLE IF EXISTS `WF_USER`;
CREATE TABLE `WF_USER` (
  `USER_ID` int(11) NOT NULL auto_increment COMMENT '�û�id',
  `USER_NAME` varchar(50) default NULL COMMENT '�û���',
  `PASS` varchar(50) default NULL COMMENT '����',
  `TRUE_NAME` varchar(50) default NULL COMMENT '�û�����',
  `ENG_NAME` varchar(50) default NULL COMMENT '�û�Ӣ����',
  `EMPLOYER_ID` varchar(100) default NULL COMMENT '����',
  `GENDER` int(11) default NULL COMMENT '�Ա�',
  `BIRTHDAY` date default NULL COMMENT '��������',
  `MOBILE` varchar(20) default NULL COMMENT '�ֻ�����',
  `OFFICE_TEL` varchar(20) default NULL COMMENT '�칫�绰',
  `MAIL` varchar(50) default NULL COMMENT '�ʼ���ַ',
  `IS_ENABLE` int(11) default NULL COMMENT '�Ƿ����',
  `APM_USER_NAME` varchar(20) default NULL COMMENT 'APM�е��û���',
  `COWORK_USER_NAME` varchar(20) default NULL COMMENT 'COWORK�е��û���',
  `APM_PASS` varchar(50) default NULL COMMENT 'apm�ʺ�����',
  `COWORK_PASS` varchar(11) default NULL COMMENT 'cowork�ʺ�����',
  `MSN` varchar(50) default NULL COMMENT '�û���msn��ַ',
  `INFORM_BY_MAIL` int(1) NOT NULL default '1' COMMENT 'ͨ���ʼ�֪ͨ',
  `INFORM_BY_MSN` int(1) NOT NULL default '0' COMMENT 'ͨ��MSN֪ͨ',
  `INFORM_BY_PHONE` int(1) NOT NULL default '0' COMMENT 'ͨ���ֻ�����֪ͨ',
  PRIMARY KEY  (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='�û���';

#
# Dumping data for table WF_USER
#

INSERT INTO `WF_USER` VALUES (1,'yijun','123456',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,1,0,0);
INSERT INTO `WF_ADMIN_RIGHT_MAPPING` VALUES (1,1,0);

#
# Table structure for table WF_USER_POSITION
#

CREATE TABLE `WF_USER_POSITION` (
  `ID` int(11) NOT NULL auto_increment COMMENT 'ID',
  `USER_ID` int(11) default NULL COMMENT '�û�ID',
  `DEPART_ID` int(11) default NULL COMMENT '���ڲ���ID',
  `DEPART_ROLE_ID` int(11) default NULL COMMENT '���Ž�ɫID',
  `REMARK` varchar(200) default NULL COMMENT '��ע',
  `IS_ENABLE` int(11) default NULL COMMENT '�Ƿ����',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='�û���ɫ��';

#
# Dumping data for table WF_USER_POSITION
#


#
# Table structure for table WF_PERFORM_USER
#
CREATE TABLE `WF_PERFORM_USER` (
  `ID` int(11) NOT NULL auto_increment COMMENT '�����ӵ�����',
  `DEPART_ROLE_ID` int(11) default NULL COMMENT '���Ž�ɫ',
  `PACKAGE_ID` varchar(128) default NULL COMMENT '��id',
  `PROCESS_DEF_ID` varchar(128) default NULL COMMENT '���̶���id',
  `PROCESS_DEF_NAME` varchar(128) default NULL,
  `DEPART_ID` int(11) default NULL COMMENT '������������id',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='��¼��ɫ������������ͣ��Ա���Ľ�ɫ����';

#
# Dumping data for table WF_PERFORM_USER
#



#
# Table structure for table XPDLApplicationPackage
#

CREATE TABLE `XPDLApplicationPackage` (
  `PACKAGE_ID` varchar(90) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_XPDLApplicationPackage` (`PACKAGE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table XPDLApplicationPackage
#


#
# Table structure for table XPDLApplicationProcess
#

CREATE TABLE `XPDLApplicationProcess` (
  `PROCESS_ID` varchar(90) NOT NULL,
  `PACKAGEOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_XPDLApplicationProcess` (`PROCESS_ID`,`PACKAGEOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table XPDLApplicationProcess
#


#
# Table structure for table XPDLData
#

CREATE TABLE `XPDLData` (
  `XPDLContent` mediumblob NOT NULL,
  `XPDLClassContent` mediumblob NOT NULL,
  `XPDL` decimal(19,0) NOT NULL,
  `CNT` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_XPDLData` (`CNT`),
  UNIQUE KEY `I2_XPDLData` (`XPDL`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table XPDLData
#


#
# Table structure for table XPDLHistory
#

CREATE TABLE `XPDLHistory` (
  `XPDLId` varchar(90) NOT NULL,
  `XPDLVersion` varchar(20) NOT NULL,
  `XPDLClassVersion` bigint(20) NOT NULL,
  `XPDLUploadTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `XPDLHistoryUploadTime` timestamp NOT NULL default '0000-00-00 00:00:00',
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_XPDLHistory` (`XPDLId`,`XPDLVersion`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table XPDLHistory
#


#
# Table structure for table XPDLHistoryData
#

CREATE TABLE `XPDLHistoryData` (
  `XPDLContent` mediumblob NOT NULL,
  `XPDLClassContent` mediumblob NOT NULL,
  `XPDLHistory` decimal(19,0) NOT NULL,
  `CNT` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_XPDLHistoryData` (`CNT`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table XPDLHistoryData
#


#
# Table structure for table XPDLParticipantPackage
#

CREATE TABLE `XPDLParticipantPackage` (
  `PACKAGE_ID` varchar(90) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_XPDLParticipantPackage` (`PACKAGE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table XPDLParticipantPackage
#


#
# Table structure for table XPDLParticipantProcess
#

CREATE TABLE `XPDLParticipantProcess` (
  `PROCESS_ID` varchar(90) NOT NULL,
  `PACKAGEOID` decimal(19,0) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_XPDLParticipantProcess` (`PROCESS_ID`,`PACKAGEOID`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table XPDLParticipantProcess
#


#
# Table structure for table XPDLReferences
#

CREATE TABLE `XPDLReferences` (
  `ReferredXPDLId` varchar(90) NOT NULL,
  `ReferringXPDL` decimal(19,0) NOT NULL,
  `ReferredXPDLNumber` int(11) NOT NULL,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_XPDLReferences` (`ReferredXPDLId`,`ReferringXPDL`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table XPDLReferences
#


#
# Table structure for table XPDLS
#

CREATE TABLE `XPDLS` (
  `XPDLId` varchar(90) NOT NULL,
  `XPDLVersion` varchar(20) NOT NULL,
  `XPDLClassVersion` bigint(20) NOT NULL,
  `XPDLUploadTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `oid` decimal(19,0) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`oid`),
  UNIQUE KEY `I1_XPDLS` (`XPDLId`,`XPDLVersion`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table XPDLS
#


#
# Table structure for table advice
#

CREATE TABLE `advice` (
  `OID` int(10) NOT NULL,
  `ACCEPTTIME` date default NULL,
  `DONETIME` date default NULL,
  `PROCID` varchar(100) default NULL,
  `ACTID` varchar(100) default NULL,
  `ADVICER` varchar(100) NOT NULL,
  `TEXT` varchar(4000) NOT NULL,
  `VERSION` int(10) default NULL,
  `SRC` varchar(100) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table advice
#


#
# Table structure for table objectid
#

CREATE TABLE `objectid` (
  `next` decimal(19,0) NOT NULL,
  PRIMARY KEY  (`next`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

#
# Dumping data for table objectid
#

INSERT INTO `objectid` VALUES (1001200);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
