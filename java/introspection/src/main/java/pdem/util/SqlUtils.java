package pdem.util;

/**
 * 
 * Oracle - DB2 - MS SQL Server

ALL_TABLES - SYSIBM.SYSTABLES - sysobjects
ALL_TAB_COLUMNS - SYSIBM.SYSCOLUMNS - syscolumns
ALL_PROCEDURES - SYSIBM.SYSROUTINES, SYSIBM.SYSROUTINESTEXT
ALL_ERRORS - N/A(?)
ALL_SOURCE - SYSIBM.SYSROUTINES, SYSIBM.SYSROUTINESTEXT
pour DB2400 select * from qsys2.SYSCOLUMNS

pour mySql:
select table_name, table_schema from information_schema.tables 
Spécificité SQL server

toutes les colonnes par nom de table: on est obligé de faire une jointure
select c.name from syscolumns c inner join sysobjects o on o.id=c.id where o.name='XXX'

tables: select * from sysobjects o where xtype='U';
SQL Server 2005 or higher, use sys.objects instead of sysobjects:

  SELECT  sys.objects.name, sys.schemas.name AS schema_name
  FROM    sys.objects 
  INNER JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id
2005 introduced schemas. up to 2000, users equaled schemas. The same query for SQL Server 2000:

  SELECT  sysusers.name AS OwnerName, sysobjects.name
  FROM sysobjects
  INNER JOIN sysusers ON sysobjects.uid = sysusers.uid
 */
public class SqlUtils {

}
