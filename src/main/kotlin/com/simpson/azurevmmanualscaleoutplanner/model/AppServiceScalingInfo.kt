package com.simpson.azurevmmanualscaleoutplanner.model


data class AppServiceScalingInfo(val maxInstance: Int, var currentInstance: Int, val hostName: String)