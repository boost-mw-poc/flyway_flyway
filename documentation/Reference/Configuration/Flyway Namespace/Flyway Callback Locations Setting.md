---
subtitle: flyway.callbackLocations
---

## Description

Array of locations to scan recursively for callbacks. The location type is determined by its prefix.

If the `callbackLocations` setting is not set, then callbacks will be scanned
from [migration locations](<Configuration/Flyway Namespace/Flyway Locations Setting>).
Note that [deploy command](<Commands/Deploy>) callbacks can only be read from `callbackLocations`, and never from
`locations`.

### Default value

There is no default value if not set, but projects created through the [init command](<Commands/Init>) will set the
following:

```toml
[flyway]
callbackLocations = ["filesystem:callbacks"]
```

### Classpath

Locations without a prefix or locations starting with <code>classpath:</code> point to a package on the classpath and
may contain both SQL and Java-based callbacks. You must ensure the package is available on the classpath (
see [Adding to the classpath](<Usage/Adding to the classpath>)).

### Filesystem

Locations starting with <code>filesystem:</code> point to a directory on the filesystem, may only contain SQL callbacks
and are only scanned recursively down non-hidden directories.
Relative paths will be resolved against your [working directory](<Command-line Parameters/Working Directory Parameter>).

### Amazon S3

Locations starting with <code>s3:</code> point to a bucket in AWS S3, may only contain SQL callbacks, and are scanned
recursively. They are in the format <code>s3:&lt;bucket&gt;(/optionalfolder/subfolder)</code>. To use AWS S3,
the [AWS SDK v2](https://mvnrepository.com/artifact/software.amazon.awssdk/services) and dependencies must be included,
and [configured](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html) for your S3 account.<br/>

### Google Cloud Storage

{% include teams.html %}

Locations starting with <code>gcs:</code> point to a bucket in Google Cloud Storage, may only contain SQL callbacks, and
are scanned recursively. They are in the format <code>gcs:&lt;bucket&gt;(/optionalfolder/subfolder)</code>. To use GCS,
the GCS library must be included, and the GCS environment variable <code>GOOGLE_APPLICATION_CREDENTIALS</code> must be
set to the credentials file for the service account that has access to the bucket.<br/>

### Wildcards

Locations can contain wildcards. This allows matching against a path pattern instead of a single path. Supported
wildcards:<br/>
<ul>
    <li>
        <code>**</code> : Matches any 0 or more directories. (e.g. <code>db/**/test</code> will match <code>db/version1.0/test</code>, <code>db/version2.0/test</code>, <code>db/development/version/1.0/test</code> but not <code>db/version1.0/release</code>)
    </li>
    <li>
        <code>*</code> : Matches any 0 or more non-separator characters. (e.g. <code>db/release1.*</code> will match <code>db/release1.0</code>, <code>db/release1.1</code>, <code>db/release1.123</code> but not <code>db/release2.0</code>)
    </li>
    <li>
        <code>?</code> : Matches any 1 non-separator character. (e.g. <code>db/release1.?</code> will match <code>db/release1.0</code>, <code>db/release1.1</code> but not <code>db/release1.11</code>)
    </li>
</ul>

## Type

String array

## Default

<i>None</i>

## Usage

### Flyway Desktop

This can't be set in a config file via Flyway Desktop, although it will be honoured, and it can be configured as an
advanced parameter in operations on the Migrations page.

### Command-line

```powershell
./flyway -callbackLocations="filesystem:callbacks" info
```

### TOML Configuration File

```toml
[flyway]
callbackLocations = ["filesystem:callbacks"]
```

### Configuration File

```properties
flyway.callbackLocations=filesystem:callbacks
```

### Environment Variable

```properties
FLYWAY_CALLBACK_LOCATIONS=filesystem:callbacks
```

### API

```java
Flyway.configure()
    .callbackLocations("filesystem:callbacks")
    .load()
```

### Gradle

```groovy
flyway {
    callbackLocations = ['filesystem:callbacks']
}
```

### Maven

```xml
<configuration>
  <callbackLocations>
    <callbackLocation>filesystem:callbacks</callbackLocation>
  </callbackLocations>
</configuration>
```
