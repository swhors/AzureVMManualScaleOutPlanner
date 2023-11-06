package com.simpson.azurevmmanualscaleoutplanner.controller

import com.simpson.azurevmmanualscaleoutplanner.model.AppServiceScalingInfo
import com.simpson.azurevmmanualscaleoutplanner.service.AzureResourceMgrSvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/azure")
@RestController
class AzureController {
    @Autowired
    lateinit var azureResourceMgrSvc: AzureResourceMgrSvc
    
    @GetMapping("/scaling")
    fun getResourceInfo() : ResponseEntity<AppServiceScalingInfo> {
        val infoOpt = azureResourceMgrSvc.getScalingInfo()
        return if (infoOpt.isPresent) ResponseEntity(infoOpt.get(), HttpStatus.OK) else ResponseEntity(HttpStatus.NOT_FOUND)
    }
    
    @GetMapping("/scaling/{instance_num}")
    fun updateResourceInfo(@PathVariable("instance_num") instanceNum: Int) : ResponseEntity<String> {
        val instanceNumber = if (instanceNum <= 0) 1 else instanceNum
        val infoOpt = azureResourceMgrSvc.getScalingInfo()
        if (infoOpt.isPresent) {
            val info = infoOpt.get()
            val msg1 = String.format(
                "Before : HostName = %s, capacity = %d, maxInstance = %d\n",
                info.hostName,
                info.currentInstance,
                info.maxInstance
            )
            
            var msg2 = "No change\n"
            
            if (instanceNumber != info.currentInstance && info.currentInstance < info.maxInstance) {
                msg2 = if (azureResourceMgrSvc.updateScalingInfo(instanceNumber))
                    String.format(
                        "Before : HostName = %s, capacity = %d, maxInstance = %d\n",
                        info.hostName,
                        instanceNumber,
                        info.maxInstance
                    ) else {
                    "Fail to change\n"
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(msg1 + msg2)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}