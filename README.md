# Feature switcher

Feature access switch web service.

<!-- Installation -->
## Build

```
gradlew clean build
```

## Testing

```
gradlew clean test
```

## Running
```
gradlew bootRun

```
## Manual testing with curl
```
curl -H "Content-Type: application/json" -X GET "http://localhost:8080/api/v1/feature?email=e&featureName=f"
curl -H "Content-Type: application/json" -X POST -d @data.json http://localhost:8080/api/v1/feature
(data.json is the file with request json)