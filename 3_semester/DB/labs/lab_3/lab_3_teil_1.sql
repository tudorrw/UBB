CREATE PROCEDURE CreateNewTable(@tableName VARCHAR(50), @columnName VARCHAR(50), @columnDataType VARCHAR(50))
AS
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'CREATE TABLE ' + @tableName + '('+ @columnName + ' ' + @columnDataType + ' NOT NULL PRIMARY KEY)'
	print @sqlQuery
	exec(@sqlQuery)
	end
GO

CREATE PROCEDURE DeleteTable(@tableName VARCHAR(50))
AS
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'DROP TABLE IF EXISTS ' + @tableName
	print @sqlQuery
	exec(@sqlQuery)
	end
GO



CREATE PROCEDURE AddNewColumn(@tableName VARCHAR(50), @columnName VARCHAR(50), @columnDataType VARCHAR(50))
AS
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'ALTER TABLE ' + @tableName + ' ADD ' + @columnName + ' ' + @columnDataType
	print @sqlQuery
	exec(@sqlQuery)
	end
GO

CREATE PROCEDURE DeleteColumn(@tableName VARCHAR(50), @columnName VARCHAR(50))
AS
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'ALTER TABLE ' + @tableName + ' DROP COLUMN ' + @columnName
	print @sqlQuery
	exec(@sqlQuery)
	end
GO

CREATE PROCEDURE ModifyTypeOfColumn(@tableName VARCHAR(50), @columnName VARCHAR(50), @newColumnDataType VARCHAR(50))
AS
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'ALTER TABLE ' + @tableName + ' ALTER COLUMN ' + @columnName + ' ' + @newColumnDataType
	print @sqlQuery
	exec(@sqlQuery)
	end
GO

CREATE PROCEDURE AddDefaultConstraint(@tableName VARCHAR(50), @columnName VARCHAR(50), @Constraint VARCHAR(50))
AS
	begin
		declare @sqlQuery as varchar(MAX)
		declare @textConstraint as varchar(50)

		IF ISNUMERIC(@Constraint) <> 1
			begin
				set @textConstraint = '''' + @Constraint + ''''
			end
		else
			begin
				set @textConstraint = @Constraint
			end
		set @sqlQuery = 'ALTER TABLE ' + @tableName + ' ADD CONSTRAINT df_' + @columnName + ' DEFAULT ' + @textConstraint + ' FOR ' + @columnName
		print @sqlQuery
		exec(@sqlQuery)
	end
GO

CREATE PROCEDURE DeleteDefaultConstraint(@tableName VARCHAR(50), @columnName VARCHAR(50))
AS
	begin
		declare @sqlQuery as varchar(MAX)
		set @sqlQuery = 'ALTER TABLE ' + @tableName + ' DROP CONSTRAINT df_' + @columnName
		print @sqlQuery
		exec(@sqlQuery)
	end
GO

CREATE PROCEDURE AddForeignKeyConstraint(@tableName VARCHAR(50), @referencedTable VARCHAR(50), @columnName VARCHAR(50), @referencedColumn VARCHAR(50))
AS
	begin
		declare @sqlQuery as varchar(MAX)
		set @sqlQuery = 'ALTER TABLE ' + @tableName + ' ADD CONSTRAINT fk_' + @columnName + ' FOREIGN KEY (' + @columnName + ') REFERENCES ' + @referencedTable + '(' + @referencedColumn + ')'
		print @sqlQuery
		exec(@sqlQuery)
	end
GO

CREATE PROCEDURE DeleteForeignKeyConstraint(@tableName VARCHAR(50), @columnName VARCHAR(50))
AS
	begin
		declare @sqlQuery as varchar(MAX)
		set @sqlQuery = 'ALTER TABLE ' + @tableName + ' DROP CONSTRAINT fk_' + @columnName
		print @sqlQuery
		exec(@sqlQuery)
	end
GO

EXEC CreateNewTable 'Workers', 'Id', 'INT';
EXEC DeleteTable 'Workers'
EXEC AddNewColumn 'Workers', 'Name', 'VARCHAR(50)'
EXEC ModifyTypeOfColumn 'Workers', 'Name', 'NVARCHAR(50)'
EXEC DeleteColumn 'Workers', 'Name'


EXEC AddNewColumn 'Workers', 'Occupation', 'VARCHAR(50)'
EXEC AddDefaultConstraint 'Workers', 'Name', 'Max'
EXEC DeleteDefaultConstraint 'Workers', 'Occupation'

EXEC AddNewColumn 'Workers', 'WorkedHours', 'INT'
EXEC AddDefaultConstraint 'Workers', 'WorkedHours', '0'
EXEC DeleteDefaultConstraint 'Workers', 'WorkedHours'



EXEC CreateNewTable 'Company', 'Id', 'INT';
EXEC AddNewColumn 'Workers', 'CompanyId', 'INT'
EXEC AddForeignKeyConstraint 'Workers', 'Company', 'CompanyId', 'Id'
EXEC DeleteForeignKeyConstraint 'Workers', 'CompanyId'



alter table Studenten
add constraint Geburtsdatum
check Geburtsdatum between '1990-01-01' and '2002-01-01'

create trigger valid_datum
    on Studenten
    after update
    as
        begin
            delete from Studenten
            where Geburtsdatum < '1990-01-01' or Geburtsdatum > '2002-01-01'
        end
    go