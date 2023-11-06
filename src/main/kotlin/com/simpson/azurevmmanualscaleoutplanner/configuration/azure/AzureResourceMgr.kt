package com.simpson.azurevmmanualscaleoutplanner.configuration.azure

import com.simpson.azurevmmanualscaleoutplanner.model.AppServiceScalingInfo
import com.azure.core.credential.TokenCredential
import com.azure.core.management.AzureEnvironment
import com.azure.core.management.profile.AzureProfile
import com.azure.identity.DefaultAzureCredentialBuilder
import com.azure.resourcemanager.AzureResourceManager
import com.azure.resourcemanager.appservice.AppServiceManager
import com.azure.resourcemanager.appservice.models.AppServicePlan
import com.azure.resourcemanager.appservice.models.AppServicePlans
import org.springframework.context.annotation.Bean
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration


@Configuration
class AzureResourceMgr {
    
    private final val log: Logger = LoggerFactory.getLogger(javaClass)
    
    @Value("\${user.azure.appservice.plan.rg}")
    lateinit var appServicePlanRG: String
    
    @Value("\${user.azure.appservice.plan.name}")
    lateinit var appServicePlanName: String
    
    @Value("\${user.azure.appservice.service.rg}")
    lateinit var appServiceRg: String
    
    @Value("\${user.azure.appservice.service.name}")
    lateinit var appServiceName: String
    
    @Bean
    fun azureEnv() : AzureEnvironment {
        return AzureEnvironment.AZURE
    }
    
    @Bean
    fun azureProfile(azureEnv: AzureEnvironment) : AzureProfile {
        val profile = AzureProfile(azureEnv)
        log.info("Azure TENANT_ID = {}", profile.tenantId)
        log.info("Azure SUBSCR_ID = {}", profile.subscriptionId)
        log.info("Azure ENDPOINT  = {}", profile.environment.managementEndpoint)
        return profile
    }
    
    @Bean
    fun credential(azureProfile: AzureProfile): TokenCredential {
        return DefaultAzureCredentialBuilder().authorityHost(azureProfile.environment.activeDirectoryEndpoint).build()
    }
    
    @Bean
    fun azureResourceManager(credential: TokenCredential, azureProfile: AzureProfile) : AzureResourceManager {
        return AzureResourceManager.authenticate(credential, azureProfile).withDefaultSubscription()
    }
    
    @Bean
    fun appServicePlan(azureResourceManager: AzureResourceManager) : AppServicePlan{
        val plan: AppServicePlans? = azureResourceManager.appServicePlans()
        val appSvcPlan = plan!!.getByResourceGroup(appServicePlanRG, appServicePlanName) as AppServicePlan
        if (!appSvcPlan.perSiteScaling()) {
            appSvcPlan.update().apply { withPerSiteScaling(true) }
            appSvcPlan.update().apply()
        }
        return appSvcPlan
    }
    
    @Bean
    fun appServiceManager(appServicePlan: AppServicePlan) : AppServiceManager{
	    return appServicePlan.manager() as AppServiceManager
    }
    
    @Bean
    fun appServiceScalingInfo(appServiceManager: AppServiceManager, appServicePlan: AppServicePlan) : AppServiceScalingInfo {
        val app = appServiceManager.webApps()
        val resource = app?.getByResourceGroup(appServiceRg, appServiceName)
        return AppServiceScalingInfo(hostName = resource!!.defaultHostname(), maxInstance = appServicePlan.maxInstances(), currentInstance = appServicePlan.capacity())
    }
}
