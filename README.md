# AzureVMManualScaleOutPlanner
Manual ScaleOut Manager for Azure App Service
# Compile
<code>
mvn compile
</code>
# Build Package
<code>
mvn -B package --file pom.xml -DskipTests
</code>
  ** ***주의*** : azure-liburary를 사용할 경우에 Test를 건너 뛰는 것이 좋다.
# Run
<code>
java -jar target/AzureVMManualScaleOutPlanner-[version]-SNAPSHOT.jar --spring.profiles.active=[Active Mode, dev or prod]
</code>
# Dockerizing
<code>
docker build -t AzureVMManualScaleOutPlanner:[version] .
</code>
