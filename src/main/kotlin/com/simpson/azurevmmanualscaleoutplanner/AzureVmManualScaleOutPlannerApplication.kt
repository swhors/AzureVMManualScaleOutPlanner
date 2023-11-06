package com.simpson.azurevmmanualscaleoutplanner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

private val logger = KotlinLogging.logger {}

@SpringBootApplication
class AzureVmManualScaleOutPlannerApplication

fun main(args: Array<String>) {
    runApplication<AzureVmManualScaleOutPlannerApplication>(*args)
}
