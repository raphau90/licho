package com.raphau.licho

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LichoPermissionsManager @Inject constructor(private val context: Context) {
    val requiredPermissions = arrayOf(Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,
        Manifest.permission.READ_CONTACTS)

    private val permissionsStateLd = MutableLiveData<PermissionsState>()

    init {
        refreshPermissions()
    }

    private fun refreshPermissions() {
        val missingPermissions = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
        val state: PermissionsState = if (missingPermissions.isNotEmpty()) {
            PermissionsState.MissingPermissions(missingPermissions,
                permissionsStateLd.value is PermissionsState.MissingPermissions)
        } else {
            PermissionsState.HasAllPermissions
        }
        if (state != permissionsStateLd.value) {
            permissionsStateLd.postValue(state)
        }
    }

    fun getPermissionState(): LiveData<PermissionsState> = permissionsStateLd

    fun onPermissionsChanged() = refreshPermissions()

    fun hasPermissionsGranted(permission: String): Boolean {
        val state = permissionsStateLd.value
        return state is PermissionsState.HasAllPermissions ||
                (state is PermissionsState.MissingPermissions && !state.permissions.contains(permission))
    }

}

sealed class PermissionsState {
    object HasAllPermissions: PermissionsState()
    data class MissingPermissions(val permissions: Array<String>,
                                  val hasRequestedAlready: Boolean = false): PermissionsState() {
        override fun equals(other: Any?): Boolean {
            return other is MissingPermissions && other.permissions contentEquals permissions
        }
    }
}