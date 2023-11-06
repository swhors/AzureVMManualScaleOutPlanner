package com.simpson.azurevmmanualscaleoutplanner.service

import com.simpson.azurevmmanualscaleoutplanner.model.AppServiceScalingInfo
import com.azure.resourcemanager.AzureResourceManager
import com.azure.resourcemanager.appservice.models.AppServicePlan
import com.azure.resourcemanager.appservice.AppServiceManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*


@Service
class AzureResourceMgrSvc {
    @Autowired
    lateinit var appServicePlan: AppServicePlan
    @Autowired
    lateinit var appServiceManager: AppServiceManager
    @Autowired
    lateinit var appResourceManager: AzureResourceManager
    @Autowired
    lateinit var appServiceScalingInfo: AppServiceScalingInfo
    
    private final val log: Logger = LoggerFactory.getLogger(javaClass)
    
    fun getScalingInfo() : Optional<AppServiceScalingInfo> = Optional.of(AppServiceScalingInfo(
        hostName = appServiceScalingInfo.hostName,
        currentInstance = appServiceScalingInfo.currentInstance,
        maxInstance = appServiceScalingInfo.maxInstance))
    
    fun updateScalingInfo(instanceNum: Int = 0) : Boolean {
        try {
            return if (appServiceScalingInfo.maxInstance >= instanceNum &&
                       appServiceScalingInfo.currentInstance != instanceNum &&
                       0 < instanceNum) {
                appServicePlan.update().apply { withCapacity(instanceNum) }
                appServicePlan.update().apply()
                appServiceScalingInfo.currentInstance = instanceNum
                true
            } else {
                false
            }
        } catch (e: Exception) {
            log.error(e.message)
            throw(e)
        }
    }
}