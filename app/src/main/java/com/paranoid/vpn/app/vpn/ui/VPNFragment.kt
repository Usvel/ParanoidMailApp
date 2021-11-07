package com.paranoid.vpn.app.vpn.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.paranoid.vpn.app.R
import com.paranoid.vpn.app.common.ui.base.BaseFragment
import com.paranoid.vpn.app.common.utils.Utils
import com.paranoid.vpn.app.common.utils.VPNState
import com.paranoid.vpn.app.databinding.NavigationVpnFragmentBinding

class VPNFragment :
    BaseFragment<NavigationVpnFragmentBinding, VPNViewModel>(NavigationVpnFragmentBinding::inflate) {
    private var vpnStateOn: VPNState = VPNState.NOT_CONNECTED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Just for test
        loadMainConfiguration()

        binding.vpnButtonBackground.setOnClickListener {
            when (vpnStateOn) {
                VPNState.CONNECTED -> vpnButtonDisable()
                VPNState.NOT_CONNECTED -> vpnButtonConnected()
                VPNState.ERROR -> vpnButtonDisable()
            }
        }

        binding.helpButton.setOnClickListener {
            context?.let { context_ ->
                Utils.makeToast(
                    context_,
                    Utils.getString(R.string.help_info)
                )
            }
        }

        binding.shareIcon.setOnClickListener {
            context?.let { context_ ->
                Utils.makeToast(
                    context_,
                    Utils.getString(R.string.share_configuration)
                )
            }
        }

        binding.qrIcon.setOnClickListener {
            context?.let { context_ ->
                Utils.makeToast(
                    context_,
                    Utils.getString(R.string.scan_qr_code)
                )
            }
        }
    }

    private fun loadMainConfiguration() {
        binding.mainConfigurationText.text = Utils.getString(R.string.test_first_configuration)
        binding.mainConfigurationCard.setOnClickListener {
            context?.let { context_ ->
                Utils.makeToast(
                    context_,
                    Utils.getString(R.string.test_first_configuration)
                )
            }
        }
    }

    private fun getVpnButtonColor(vpnButtonStateAttr: Int): Int {
        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(vpnButtonStateAttr, typedValue, true)
        return typedValue.data
    }

    private fun vpnButtonDisable() {
        vpnStateOn = VPNState.NOT_CONNECTED
        binding.turnOnVPN.setImageResource(R.drawable.ic_power)
        binding.connectionStatus.visibility = View.GONE
        binding.isConnected.text = Utils.getString(R.string.not_connected)
        binding.vpnButtonBackground.background.setTint(getVpnButtonColor(R.attr.vpnButtonDisabled))
    }

    private fun vpnButtonConnected() {
        vpnStateOn = VPNState.CONNECTED
        binding.turnOnVPN.setImageResource(R.drawable.ic_power)
        binding.connectionStatus.visibility = View.VISIBLE
        binding.isConnected.text = getString(R.string.connected)
        binding.vpnButtonBackground.background.setTint(getVpnButtonColor(R.attr.vpnButtonConnected))
    }

    private fun vpnButtonError() {
        vpnStateOn = VPNState.ERROR
        binding.turnOnVPN.setImageResource(R.drawable.ic_dino)
        binding.connectionStatus.visibility = View.GONE
        binding.isConnected.text = Utils.getString(R.string.error)
        binding.vpnButtonBackground.background.setTint(getVpnButtonColor(R.attr.vpnButtonError))
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[VPNViewModel::class.java]
    }
}
