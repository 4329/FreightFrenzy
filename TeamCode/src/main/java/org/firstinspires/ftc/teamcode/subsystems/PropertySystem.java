package org.firstinspires.ftc.teamcode.subsystems;

import android.os.Environment;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.commands.ReportTelemetry;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertySystem extends SubsystemBase {
    private String propertyFileName;
    private File configFile;
    private Exception lastException;
    private Properties properties;

    public PropertySystem(String propertyFileName) {
        this.propertyFileName = propertyFileName;
        File configFile = new File(Environment.getExternalStorageDirectory(), propertyFileName);
    }

    public String getProperty(String propertyName) {
        if (properties == null) {
            // If properties haven't been read, read them
            if (! readProperties()) {
                // Something failed reading properties. Should be lastExeception
                return null;
            }
        }
        // Should have properties so get it
        return properties.getProperty(propertyName, null);
    }

    public boolean setProperty(String propertyName, String propertyValue){
        if(properties == null){
            // properties haven't been used yet, so read existing ones so that we re-write all properties
            // Don't worry about read errors and just write a new set of properties
            readProperties();
        }
        // properties should now exist
        properties.setProperty(propertyName,propertyValue);
        return storeProperties();
    }
    private boolean readProperties(){
        lastException = null;
        try{
            FileReader reader = new FileReader(configFile);
            properties = new Properties();
            properties.load(reader);
            reader.close();
            return true;
        }
        catch (IOException ex){
            lastException=ex;
            return false;
        }
    }

    private boolean storeProperties(){
        lastException = null;
        try {
            FileWriter writer = new FileWriter(configFile);
            properties.store(writer,"Storing Properties");
            writer.close();
            return true;
        }
        catch (IOException ex){
            lastException=ex;
            return false;
        }
    }
}
