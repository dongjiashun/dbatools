use dms;
CREATE TABLE `rds_slowsql_detailed` (
   `id`  int(20) NOT NULL AUTO_INCREMENT COMMENT '����������',
   `sqlid` bigint(20) NOT NULL,
   `dbname` varchar(20) NOT NULL COMMENT '���ݿ�����',
   `sqltext` mediumtext NOT NULL COMMENT '��sqlcontext',
   `mysqltotalexecutioncounts` int(11) NOT NULL COMMENT 'ִ���ܴ���',
   `mysqltotalexecutiontimes` bigint(20) NOT NULL COMMENT 'ִ����ʱ��',
   `maxexecutiontime` bigint(20) NOT NULL COMMENT 'ִ�����ʱ��',
   `totallocktimes` bigint(20) NOT NULL COMMENT '������ʱ��',
   `maxlocktime` bigint(20) NOT NULL COMMENT '�������ʱ��',
   `parsetotalrowcounts` bigint(20) NOT NULL COMMENT '����������',
   `parsemaxrowcount` bigint(20) NOT NULL COMMENT '�����������',
   `returntotalrowcounts` bigint(20) NOT NULL COMMENT '����������',
   `returnmaxrowcount` bigint(20) NOT NULL COMMENT '�����������',
   `createtime` varchar(20) NOT NULL COMMENT 'sql����ʱ��',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='RDS���sql��ϸ��';
 