package com.mlsdev.recipefinder.di.module;

import com.mlsdev.recipefinder.view.utils.DiagramUtils;
import com.mlsdev.recipefinder.view.utils.ParamsHelper;
import com.mlsdev.recipefinder.view.utils.UtilsUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    DiagramUtils provideDiagramUtils(UtilsUI utilsUI) {
        return new DiagramUtils(utilsUI);
    }

    @Provides
    @Singleton
    UtilsUI provideUtilsUI() {
        return new UtilsUI();
    }

    @Provides
    @Singleton
    ParamsHelper provideParamsHelper() {
        return new ParamsHelper();
    }

}
