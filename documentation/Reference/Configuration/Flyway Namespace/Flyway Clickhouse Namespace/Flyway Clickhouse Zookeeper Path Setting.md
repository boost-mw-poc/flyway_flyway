---
subtitle: Flyway Clickhouse Zookeeper Path
---

## Description

The path to the Zookeeper node that contains the Clickhouse cluster configuration.

## Type

String

## Default

<i>none</i>

## Usage

### Flyway Desktop

This can't be configured via Flyway Desktop, although it will be honoured.

### Command-line

```powershell
./flyway -clickhouse.zookeeperPath="/clickhouse/tables/{shard}/{database}/{table}" info
```

### TOML Configuration File

```toml
[flyway.clickhouse]
zookeeperPath = "/clickhouse/tables/{shard}/{database}/{table}"
```

### Configuration File

```properties
flyway.clickhouse.zookeeperPath=/clickhouse/tables/{shard}/{database}/{table}
```

### Environment Variable

```properties
FLYWAY_CLICKHOUSE_ZOOKEEPER_PATH=/clickhouse/tables/{shard}/{database}/{table}
```

### API

```java
ClickHouseConfigurationExtension clickHouseConfigurationExtension = configuration.getConfigurationExtension(ClickHouseConfigurationExtension.class);
clickHouseConfigurationExtension.setZookeeperPath("/clickhouse/tables/{shard}/{database}/{table}");
```

### Gradle

```groovy
flyway {
    pluginConfiguration = [
        clickhouseZookeeperPath: '/clickhouse/tables/{shard}/{database}/{table}'
    ]
}
```

### Maven

```xml
<configuration>
    <pluginConfiguration>
        <clickhouseZookeeperPath>/clickhouse/tables/{shard}/{database}/{table}</clickhouseZookeeperPath>
    </pluginConfiguration>
</configuration>
```
