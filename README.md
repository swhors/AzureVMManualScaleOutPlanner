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
AZURE_CLIENT_ID=[CLIENT_ID];AZURE_TENANT_ID=[TENANT_ID];AZURE_CLIENT_SECRET=[CLIENT_SECRET];AZURE_SUBSCRIPTION_ID=[SUBSCRIPTION_ID} java -jar  --spring.profiles.active=[Active Mode, dev or prod] target/AzureVMManualScaleOutPlanner-[version]-SNAPSHOT.jar
</code>

# Dockerizing
<code>
docker build -t AzureVMManualScaleOutPlanner:[version] .
</code>
