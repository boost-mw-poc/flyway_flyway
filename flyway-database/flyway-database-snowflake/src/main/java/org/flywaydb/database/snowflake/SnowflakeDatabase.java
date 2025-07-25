/*-
 * ========================LICENSE_START=================================
 * flyway-database-snowflake
 * ========================================================================
 * Copyright (C) 2010 - 2025 Red Gate Software Ltd
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package org.flywaydb.database.snowflake;

import static org.flywaydb.core.internal.database.base.DatabaseConstants.DATABASE_HOSTING_AWS_SNOWFLAKE;
import static org.flywaydb.core.internal.database.base.DatabaseConstants.DATABASE_HOSTING_AZURE_SNOWFLAKE;
import static org.flywaydb.core.internal.database.base.DatabaseConstants.DATABASE_HOSTING_GCP_SNOWFLAKE;
import static org.flywaydb.core.internal.database.base.DatabaseConstants.DATABASE_HOSTING_LOCAL;

import lombok.CustomLog;
import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.extensibility.Tier;
import org.flywaydb.core.internal.database.base.Database;
import org.flywaydb.core.internal.database.base.Table;
import org.flywaydb.core.internal.jdbc.JdbcConnectionFactory;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.jdbc.StatementInterceptor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.flywaydb.core.internal.util.FlywayDbWebsiteLinks;

@CustomLog
public class SnowflakeDatabase extends Database<SnowflakeConnection> {
    /**
     * Whether quoted identifiers are treated in a case-insensitive way. Defaults to false. See
     * https://docs.snowflake.com/en/sql-reference/identifiers-syntax.html#controlling-case-using-the-quoted-identifiers-ignore-case-parameter
     */
    private final boolean quotedIdentifiersIgnoreCase;

    public SnowflakeDatabase(Configuration configuration, JdbcConnectionFactory jdbcConnectionFactory, StatementInterceptor statementInterceptor) {
        super(configuration, jdbcConnectionFactory, statementInterceptor);

        // There will be issues if the Flyway schema history table was created while this option was set false
        // (it is set either at the account level, or the individual session level) and it is subsequently set true.
        quotedIdentifiersIgnoreCase = getQuotedIdentifiersIgnoreCase(jdbcTemplate);
        LOG.info("QUOTED_IDENTIFIERS_IGNORE_CASE option is " + quotedIdentifiersIgnoreCase);
    }

    private static boolean getQuotedIdentifiersIgnoreCase(JdbcTemplate jdbcTemplate) {
        try {
            // Attempt query
            List<Map<String, String>> result = jdbcTemplate.queryForList("SHOW PARAMETERS LIKE 'QUOTED_IDENTIFIERS_IGNORE_CASE'");
            Map<String, String> row = result.get(0);
            return "TRUE".equals(row.get("value").toUpperCase(Locale.ENGLISH));
        } catch (SQLException e) {
            LOG.warn("Could not query for parameter QUOTED_IDENTIFIERS_IGNORE_CASE.");
            return false;
        }
    }

    @Override
    protected SnowflakeConnection doGetConnection(Connection connection) {
        return new SnowflakeConnection(this, connection);
    }

    @Override
    public void ensureSupported(Configuration configuration) {
        ensureDatabaseIsRecentEnough("3.0");

        ensureDatabaseNotOlderThanOtherwiseRecommendUpgradeToFlywayEdition("3", Tier.PREMIUM, configuration);

        checkDatabaseVersionUntested("9.19");
    }

    @Override
    public String getRawCreateScript(Table table, boolean baseline) {
        // CAUTION: Quotes are optional around column names without underscores; but without them, Snowflake will
        // uppercase the column name leading to SELECTs failing.
        return "CREATE TABLE " + table + " (\n" +
                quote("installed_rank") + " NUMBER(38,0) NOT NULL,\n" +
                quote("version") + " VARCHAR(50),\n" +
                quote("description") + " VARCHAR(200),\n" +
                quote("type") + " VARCHAR(20) NOT NULL,\n" +
                quote("script") + " VARCHAR(1000) NOT NULL,\n" +
                quote("checksum") + " NUMBER(38,0),\n" +
                quote("installed_by") + " VARCHAR(100) NOT NULL,\n" +
                quote("installed_on") + " TIMESTAMP_LTZ(9) NOT NULL DEFAULT CURRENT_TIMESTAMP(),\n" +
                quote("execution_time") + " NUMBER(38,0) NOT NULL,\n" +
                quote("success") + " BOOLEAN NOT NULL,\n" +
                "primary key (" + quote("installed_rank") + "));\n" +

                (baseline ? getBaselineStatement(table) + ";\n" : "");
    }

    @Override
    public String getSelectStatement(Table table) {
        // CAUTION: Quotes are optional around column names without underscores; but without them, Snowflake will
        // uppercase the column name. In data readers, the column name is case sensitive.
        return "SELECT " + quote("installed_rank")
                + "," + quote("version")
                + "," + quote("description")
                + "," + quote("type")
                + "," + quote("script")
                + "," + quote("checksum")
                + "," + quote("installed_on")
                + "," + quote("installed_by")
                + "," + quote("execution_time")
                + "," + quote("success")
                + " FROM " + table
                + " WHERE " + quote("installed_rank") + " > ?"
                + " ORDER BY " + quote("installed_rank");
    }

    @Override
    public String getInsertStatement(Table table) {
        // CAUTION: Quotes are optional around column names without underscores; but without them, Snowflake will
        // uppercase the column name.
        return "INSERT INTO " + table
                + " (" + quote("installed_rank")
                + ", " + quote("version")
                + ", " + quote("description")
                + ", " + quote("type")
                + ", " + quote("script")
                + ", " + quote("checksum")
                + ", " + quote("installed_by")
                + ", " + quote("execution_time")
                + ", " + quote("success")
                + ")"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public boolean supportsDdlTransactions() {
        return false;
    }

    @Override
    public String getBooleanTrue() {
        return "true";
    }

    @Override
    public String getBooleanFalse() {
        return "false";
    }

    @Override
    public boolean catalogIsSchema() {
        return false;
    }

    @Override
    public String getDatabaseHosting() {
        String url = configuration.getUrl();

        if (url.contains("azure.snowflakecomputing.com")) {
            return DATABASE_HOSTING_AZURE_SNOWFLAKE;
        } else if (url.contains("gcp.snowflakecomputing.com")) {
            return DATABASE_HOSTING_GCP_SNOWFLAKE;
        } else {
            return DATABASE_HOSTING_AWS_SNOWFLAKE;
        }
    }

    private void checkDatabaseVersionUntested(String newestSupportedVersion) {
        if (getVersion().isNewerThan(newestSupportedVersion)) {
            informVersionUntested(newestSupportedVersion);
        }
    }

    private void informVersionUntested(final String newestSupportedVersion) {
        final String message = databaseType + " " + computeVersionDisplayName(getVersion())
            + " is newer than the latest version tested with this version of Flyway: " + newestSupportedVersion + "."
            + "\nCheck here: " + FlywayDbWebsiteLinks.SNOWFLAKE + " to see if your version is supported in the latest Flyway release";
        LOG.info(message);
    }
}
