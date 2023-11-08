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
mvn -B package --file pom.xml -DskipTests
```
  ** azure-liburary를 사용할 경우에 Test를 건너 뛰는 것이 좋다.
<br>
# Run
```
java -jar target/AzureVMManualScaleOutPlanner-[version]-SNAPSHOT.jar --spring.profiles.active=[Active Mode, dev or prod]
```
<br>
# Dockerizing
```
docker build -t AzureVMManualScaleOutPlanner:[version] .
```
