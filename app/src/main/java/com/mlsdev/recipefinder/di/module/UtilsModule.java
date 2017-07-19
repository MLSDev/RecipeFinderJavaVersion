package com.mlsdev.recipefinder.di.module;

import android.content.Context;

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
    DiagramUtils provideDiagramUtils(Context context, UtilsUI utilsUI) {
        return new DiagramUtils(context, utilsUI);
    }

    @Provides
    @Singleton
    UtilsUI provideUtilsUI(Context context) {
        return new UtilsUI(context);
    }

    @Provides
    @Singleton
    ParamsHelper provideParamsHelper() {
        return new ParamsHelper();
    }

}
