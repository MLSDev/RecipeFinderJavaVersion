package com.mlsdev.recipefinder.data.source.local;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    public static void main(String[] args) throws SQLException, IOException {

        // Provide the name of .txt file which you have already created and kept in res/raw directory
        writeConfigFile("ormlite_config.txt");
    }
}
