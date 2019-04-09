package com.sumitanantwar.postsbrowser.mobile.application.di

import com.sumitanantwar.postsbrowser.mobile.application.di.scope.ControllerScope
import com.sumitanantwar.postsbrowser.mobile.ui.postdetails.PostDetailsController
import com.sumitanantwar.postsbrowser.mobile.ui.postlist.PostsListController
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ControllerModule {

    @ControllerScope
    @ContributesAndroidInjector
    abstract fun contributesPostListController(): PostsListController

    @ControllerScope
    @ContributesAndroidInjector
    abstract fun contributesPostDetailsController(): PostDetailsController
}