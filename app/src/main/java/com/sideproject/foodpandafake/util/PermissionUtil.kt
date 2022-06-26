package com.sideproject.foodpandafake.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat


class PermissionUtil {
    fun getLocalAndSetupPermission(context: Activity): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
        } else {
            return true
        }
        return false
    }

    fun isOpenGPS(context: Activity):Boolean{
        val locationManager =context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        //判断GPS是否开启，没有开启，则开启
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false
        }
        return true
    }


}