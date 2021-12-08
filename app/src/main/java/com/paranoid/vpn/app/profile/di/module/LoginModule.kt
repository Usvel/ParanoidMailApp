package com.paranoid.vpn.app.profile.di.module

import androidx.lifecycle.ViewModel
import com.paranoid.vpn.app.common.di.keys.ViewModelKey
import com.paranoid.vpn.app.profile.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {
    @ViewModelKey(LoginViewModel::class)
    @IntoMap
    @Binds
    abstract fun bindsLoginViewModule(loginViewModel: LoginViewModel): ViewModel
}
