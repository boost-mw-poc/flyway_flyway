---
subtitle: YugabyteDB
---

- **Verified Versions:** 2.20, 2024.1, 2.21
- **Maintainer:** {% include community-db.html %}

## Supported Versions and Support Levels

{% include database-boilerplate.html %}

## Driver
The preferred driver for this plugin is [YugabyteDB JDBC driver](https://github.com/yugabyte/pgjdbc).

| Item                                | Details                                                                                                                   |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------- |
| **URL format**                      | <code>jdbc:yugabytedb://<i>host</i>:<i>port</i>/<i>database</i></code>                                                    |
| **SSL support**                     | Yes - add `?ssl=true`                                                                                                     |
| **Ships with Flyway Command-line**  | No                                                                                                                        |
| **Maven Central coordinates**       | `com.yugabyte:jdbc-yugabytedb`                                                                                            |
| **Supported versions**              | `42.3.5-yb-1` and later                                                                                                   |
| **Default Java class**              | `com.yugabyte.Driver`                                                                                                     |
| **Flyway Community implementation** | [flyway-community-db-support](https://github.com/flyway/flyway-community-db-support/tree/main/flyway-database-yugabytedb) |


### PostgreSQL Driver
Alternatively, one can also use the [PostgreSQL](<Database Driver Reference/PostgreSQL Database>) JDBC Driver with this plugin.

| Item                               | Details                                                                |
| ---------------------------------- | ---------------------------------------------------------------------- |
| **URL format**                     | <code>jdbc:postgresql://<i>host</i>:<i>port</i>/<i>database</i></code> |
| **SSL support**                    | Yes - add `?ssl=true`                                                  |
| **Ships with Flyway Command-line** | Yes                                                                    |
| **Maven Central coordinates**      | `org.postgresql:postgresql`                                            |
| **Supported versions**             | `9.3-1104-jdbc4` and later                                             |
| **Default Java class**             | `org.postgresql.Driver`                                                |

## Java Usage

YugabyteDB support is a separate dependency for Flyway and will need to be added to your Java project to access these features.

### Maven
#### Open Source

```xml

<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-database-yugabytedb</artifactId>
</dependency>
```

### Gradle
#### Open Source

```groovy
buildscript {
    dependencies {
        implmentation "org.flywaydb:flyway-database-yugabytedb"
    }
}
```

## Notes

YugabyteDB is a variant of PostgreSQL and Flyway usage is the same for the two databases. For more details, 
please refer to the [PostgreSQL](<Database Driver Reference/postgresql-database>) page.

## Limitations

- AWS SecretsManager is not supported with YugabyteDB.
