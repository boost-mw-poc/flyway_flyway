---
subtitle: Fabric Data Warehouse
---
- **Status:** Preview
- **Verified Versions:** Latest
- **Maintainer:** {% include redgate-badge.html %}

## Supported Versions and Support Levels

{% include database-boilerplate.html %}

## Driver

| Item                               | Details                                                                                                                              |
|------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| **URL format**                     | <code>jdbc:sqlserver://<i>host</i>:<i>port</i>;databaseName=<i>database</i></code>                                                   |
| **SSL support**                    | [Yes](https://docs.microsoft.com/en-us/sql/connect/jdbc/connecting-with-ssl-encryption?view=sql-server-ver15) \- add `;encrypt=true` |
| **Ships with Flyway Command-line** | Yes                                                                                                                                  |
| **Maven Central coordinates**      | `com.microsoft.sqlserver:mssql-jdbc`                                                                                                 |
| **Supported versions**             | `12.0` and later                                                                                                                      |
| **Default Java class**             | `com.microsoft.sqlserver.jdbc.SQLServerDriver`                                                                                       |

Connectivity is through Flyway's existing SQL Server component so you can refer the [SQL Server page](<Database Driver Reference/SQL Server Database>) for further details on how to connect and communicate with the database.

## Fabric Data Warehouse Specific Issues
None currently

### Foundational capabilities only
Only foundation-level capabilities (migrations) are supported. Advanced capabilities like generation of SQL, state-based workflows or drift detection will not currently work.

### Authentication
Fabric Data Warehouse typically requires Microsoft Entra authentication. Ensure your connection string includes appropriate authentication parameters.

For detailed authentication setup, refer to the [SQL Server page](<Database Driver Reference/SQL Server Database>) authentication section.