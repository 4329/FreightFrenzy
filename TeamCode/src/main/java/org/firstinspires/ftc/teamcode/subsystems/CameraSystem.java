package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.R;

import java.util.ArrayList;
import java.util.List;

@Config
public class CameraSystem extends SubsystemBase {
    public static boolean isObjectDetectionEnabled = true;
    public static TelemetrySystem.TelemetryLevel telemetryLevel = TelemetrySystem.TelemetryLevel.MATCH;

    enum ObjectDirection {
        LEFT,
        CENTER,
        RIGHT,
        UNKNOWN

    }

    private ObjectDirection objectDirection = ObjectDirection.UNKNOWN;

    private Telemetry telemetry;
    private boolean cameraOn = true;
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };
    private static final String VUFORIA_KEY =
            "AUvkxGf/////AAABmYi7+0rJvkjZimizKYVB+ZMPZ0DWqWjmtcPlNHhp0QDBoGfK08vgmKd65DQF7SZNt6ZFVZXQV213AFt2098FDL1gPEzCV8+p2SXL7QiJZPH39yzGiECVfetSIBKOt/he1klw1rgi6kCY/gMpxQMoKS32+1tiMteOnr+u2YNkb9PP26VZeRWfje+SdyikwE/b1TBPRvOkk7zC/loskgcnqyf3nbTZHHkAs+jNNTT/JSnOdcajje1q3a87B2RyJ5kysWB83ydrJLgH1zz+QGm74GbjDql8u2osOkznv0AWoQcXVZaplITBgAziKdxAvsxqLHycc6Wi5qs3fy0Hdx5lYVM93w/uG8TBGCI7kFZmep8H";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    List<Recognition> updatedRecognitions;

    @Override
    public void periodic() {

        if (isObjectDetectionEnabled) {
            updatedRecognitions = tfod.getRecognitions();
        } else {
            updatedRecognitions = null;
        }


        switch (telemetryLevel) {
            case DEBUG:
            case DIAGNOSTIC:
                if (updatedRecognitions != null){
                    telemetry.addData(this.getName() + ":ObjectsDetected", updatedRecognitions.size() );
                    int i = 0;
                    for(Recognition recognition : removeNonCubes(updatedRecognitions)){
                        telemetry.addData(this.getName() + ":Object Label", recognition.getLabel() );
                        telemetry.addData(this.getName() + ":Object Confidence", recognition.getConfidence() );
                        telemetry.addData(this.getName() + ":Object Left", recognition.getLeft() );
                        telemetry.addData(this.getName() + ":Object Right", recognition.getRight() );
                        telemetry.addData(this.getName() + ":Object Top", recognition.getTop() );
                        telemetry.addData(this.getName() + ":Object Height", recognition.getHeight() );
                        telemetry.addData(this.getName() + ":Object Degrees", recognition.estimateAngleToObject(AngleUnit.DEGREES) );
                    }

                } else {
                    telemetry.addData(this.getName() + ":ObjectsDetected", -1);
                }
            case CONFIG:
                telemetry.addData(this.getName() + ":isObjectDetectionEnabled", isObjectDetectionEnabled);
            case MATCH:
                telemetry.addData(this.getName() + ":getObjectDirection", getObjectDirection(removeNonCubes(this.updatedRecognitions)));


        }

    }

    public CameraSystem(HardwareMap hardwareMap, String cameraConfigName,  Telemetry telemetry) {
        this.telemetry = telemetry;
        initVuforia(hardwareMap,cameraConfigName);
        initTfod(hardwareMap);
        // FtcDashboard.getInstance().startCameraStream(vuforia,0);

        if (isObjectDetectionEnabled) {
            // tfod.setClippingMargins(0,0,0,0);
            tfod.activate();
            tfod.setZoom(2.5, 16.0 / 9.0);
        } else {
            tfod.deactivate();
        }
    }

    private void initVuforia(HardwareMap hardwareMap, String configCameraName) {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        WebcamName webcamName = hardwareMap.get(WebcamName.class, configCameraName);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        // parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.cameraName = webcamName;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        FtcDashboard.getInstance().startCameraStream(vuforia, 0);

    }

    private void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.6f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 640;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

    private List<Recognition> removeNonCubes(List<Recognition> recognitions){
        List<Recognition> onlyCubes = new ArrayList<Recognition>();
        for(Recognition recognition : recognitions){
            if (recognition.getLabel()=="Cube") {
                onlyCubes.add(recognition);
            }
        }
        return onlyCubes;
    }

    public void enableObjectDetection() {
        tfod.activate();
        tfod.setZoom(2.5, 16.0 / 9.0);
    }

    public void disableObjectDetection() {
        tfod.deactivate();
    }

    public ObjectDirection getObjectDirection(List<Recognition> recognitions){
        final double directDegreeTolerance = 2.0;
        if (recognitions != null){
            if (recognitions.size() == 1) {
                Recognition recognition = recognitions.get(0);
                if (recognition.estimateAngleToObject(AngleUnit.DEGREES) < -directDegreeTolerance) {
                    return ObjectDirection.LEFT;
                } else if (recognition.estimateAngleToObject(AngleUnit.DEGREES) > directDegreeTolerance) {
                    return ObjectDirection.RIGHT;
                } else {
                    return ObjectDirection.CENTER;
                }
            }
        }
        return ObjectDirection.UNKNOWN;
    }


}