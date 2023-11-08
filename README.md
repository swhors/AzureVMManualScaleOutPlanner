# AzureVMManualScaleOutPlanner
Manual ScaleOut Manager for Azure App Service
<br>
# Compile
```
mvn compile
```
<br>
# Build Package
```
mvn package
```
<br>
# Run
```
java -jar target/AzureVMManualScaleOutPlanner-[version].jar --spring.profiles.active=[Active Mode, dev or prod]
```
<br>
# Dockerizing
```
docker build -t AzureVMManualScaleOutPlanner:[version] .
```
