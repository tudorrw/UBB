CREATE OR ALTER PROCEDURE CreateTableForVersions(@tableName VARCHAR(50), @columnName VARCHAR(50), @columnDataType VARCHAR(50))
    AS
        begin
		    declare @version as int
		    set @version = (select VersionNumber from CurrentVersion)
            set @version = @version + 1
            update CurrentVersion set VersionNumber = @version
            insert into Versionen (VersionNumber, ProcedureName, Parameter1, Parameter2, Parameter3) values (@version, 'CreateTableForVersions', @tableName, @columnName, @columnDataType)
            exec CreateNewTable @tableName, @columnName, @columnDataType
        end
    GO



CREATE OR ALTER PROCEDURE AddNewColumnVersion(@tableName VARCHAR(50), @columnName VARCHAR(50), @columnDataType VARCHAR(50))
    AS
        begin
            declare @version as int
            set @version = (select VersionNumber from CurrentVersion)
            set @version = @version + 1
            update CurrentVersion set VersionNumber = @version
            insert into Versionen (VersionNumber, ProcedureName, Parameter1, Parameter2, Parameter3) values (@version,'AddNewColumnVersion', @tableName, @columnName, @columnDataType)
            exec AddNewColumn @tableName, @columnName, @columnDataType
        end
    GO


CREATE OR ALTER PROCEDURE ModifyTypeOfColumnVersion(@tableName VARCHAR(50), @columnName VARCHAR(50), @newColumnDataType VARCHAR(50))
    AS
        begin
            declare @oldColumnDataType as varchar(50)
            set @oldColumnDataType = (Select DATA_TYPE from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = @tableName and COLUMN_NAME = @columnName)
            declare @length as varchar(50)
            set @length = (Select CHARACTER_MAXIMUM_LENGTH from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = @tableName and COLUMN_NAME = @columnName)
            --in cazul in care tipul de date este varchar, nvarchar...
            if @length is not NULL
                SET @oldColumnDataType = @oldColumnDataType + '(' + @length + ')'
            declare @version as int
            set @version = (select VersionNumber from CurrentVersion)
            set @version = @version + 1
            update CurrentVersion set VersionNumber = @version
            insert into Versionen (VersionNumber, ProcedureName, Parameter1, Parameter2, Parameter3, Parameter4) values (@version, 'ModifyTypeOfColumnVersion', @tableName, @columnName, @newColumnDataType, @oldColumnDataType)
            exec ModifyTypeOfColumn @tableName, @columnName, @newColumnDataType
            end
    GO



CREATE OR ALTER PROCEDURE AddDefaultConstraintVersion(@tableName VARCHAR(50), @columnName VARCHAR(50), @constraint VARCHAR(50))
    AS
        begin
            declare @version as int
            set @version = (select VersionNumber from CurrentVersion)
            set @version = @version + 1
            update CurrentVersion set VersionNumber = @version
            insert into Versionen (VersionNumber, ProcedureName, Parameter1, Parameter2, Parameter3) values (@version, 'AddDefaultConstraintVersion', @tableName, @columnName, @constraint)
            exec AddDefaultConstraint @tableName, @columnName, @constraint
        end
    GO


CREATE OR ALTER PROCEDURE AddForeignKeyConstraintVersion(@tableName VARCHAR(50), @columnName VARCHAR(50), @referencedTable VARCHAR(50), @referencedColumn VARCHAR(50))
    AS
        begin
            declare @version as int
            set @version = (select VersionNumber from CurrentVersion)
            set @version = @version + 1
            update CurrentVersion set VersionNumber = @version
            insert into Versionen (VersionNumber, ProcedureName, Parameter1, Parameter2, Parameter3, Parameter4) values (@version, 'AddForeignKeyConstraintVersion', @tableName, @columnName, @referencedTable, @referencedColumn)
            exec AddForeignKeyConstraint @tableName, @referencedTable, @columnName, @referencedColumn
        end
    GO




CREATE OR ALTER PROCEDURE gotoVersion(@version int)
    AS
        begin
            declare @currentVersion int, @procedureName VARCHAR(50), @tableName VARCHAR(50), @columnName VARCHAR(50),
            @columnDataType VARCHAR(50), @oldColumnDataType VARCHAR(50), @constraint VARCHAR(50), @foreignTableName VARCHAR(50), @foreignKeyName VARCHAR(50)

            set @currentVersion = (select VersionNumber from CurrentVersion)

            if(@currentVersion <= @version)
                begin
                    set @currentVersion = @currentVersion + 1
                    while(@currentVersion < @version + 1)
                        begin
                            select @procedureName = ProcedureName, @tableName = Parameter1, @columnName = Parameter2, @foreignTableName = Parameter3,
                                   @columnDataType = Parameter3, @constraint = Parameter3, @foreignKeyName = Parameter4 from Versionen where VersionNumber = @currentVersion;

                            if(@procedureName = 'CreateTableForVersions')
                                exec CreateNewTable @tableName, @columnName, @columnDataType
                            if(@procedureName = 'AddNewColumnVersion')
                                exec AddNewColumn @tableName, @columnName, @columnDataType
                            if(@procedureName = 'ModifyTypeOfColumnVersion')
                                exec ModifyTypeOfColumn @tableName, @columnName, @columnDataType
                            if(@procedureName = 'AddDefaultConstraintVersion')
                                exec AddDefaultConstraint @tableName, @columnName, @constraint
                            if(@procedureName = 'AddForeignKeyConstraintVersion')
                                exec AddForeignKeyConstraint @tableName, @foreignTableName, @columnName, @foreignKeyName

                            update CurrentVersion set VersionNumber = @currentVersion + 1
                            SET @currentVersion = @currentVersion + 1
                        end
                        update CurrentVersion set VersionNumber = @currentVersion - 1
                end
            else
                begin
                    while(@currentVersion > @Version)
                        begin
                            select @procedureName = ProcedureName, @tableName = Parameter1, @columnName = Parameter2,
                                   @oldColumnDataType = Parameter4 from Versionen where @currentVersion = VersionNumber
                            if(@procedureName = 'CreateTableForVersions')
                                exec DeleteTable @tableName
                            if(@procedureName = 'AddNewColumnVersion')
                                exec DeleteColumn @tableName, @columnName
                            if(@procedureName = 'ModifyTypeOfColumnVersion')
                                exec ModifyTypeOfColumn @tableName, @columnName,@oldColumnDataType
                            if(@procedureName = 'AddDefaultConstraintVersion')
                                exec DeleteDefaultConstraint @tableName, @columnName
                            if(@procedureName = 'AddForeignKeyConstraintVersion')
                                exec DeleteForeignKeyConstraint @tableName, @columnName
                            set @currentVersion = @currentVersion - 1;
                            update CurrentVersion set VersionNumber = @currentVersion
                        end
                end
        end
    GO

exec CreateTableForVersions 'T1', 'Id', 'INT'
exec AddNewColumnVersion 'T1', 'Nume', 'NVARCHAR(20)'
exec AddNewColumnVersion 'T1', 'Rating', 'INT'
exec ModifyTypeOfColumnVersion 'T1', 'Nume', 'VARCHAR(50)'
exec AddDefaultConstraintVersion 'T1', 'Nume', 'Test'
exec AddDefaultConstraintVersion 'T1', 'Rating', '0'

exec AddNewColumnVersion 'T1', 'FKey', 'INT'
exec CreateTableForVersions 'T2', 'Id', 'INT'
exec AddForeignKeyConstraintVersion 'T1', 'FKey', 'T2', 'Id'

select * from Versionen
select * from CurrentVersion

exec gotoVersion 0
exec gotoVersion 3
exec gotoVersion 5
exec gotoVersion 7
exec gotoVersion 9



drop table CurrentVersion
drop table T1
drop table T2
drop table Versionen

EXEC CreateNewTable 'CurrentVersion', 'VersionNumber', 'INT';
Insert into CurrentVersion(VersionNumber) values (0)
EXEC CreateNewTable 'Versionen', 'VersionNumber', 'INT';
EXEC AddNewColumn 'Versionen', 'ProcedureName', 'VARCHAR(50)'
EXEC AddNewColumn 'Versionen', 'Parameter1', 'VARCHAR(50)'
EXEC AddNewColumn 'Versionen', 'Parameter2', 'VARCHAR(50)'
EXEC AddNewColumn 'Versionen', 'Parameter3', 'VARCHAR(50)'
EXEC AddNewColumn 'Versionen', 'Parameter4', 'VARCHAR(50)'
GO







