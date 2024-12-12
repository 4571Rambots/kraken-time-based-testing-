package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
    private TalonFX krakenMotor;
    private XboxController xboxController;

    private static final int KRACKEN_MOTOR_CAN_ID = 1;
    private static final int XBOX_CONTROLLER_PORT = 0;
    private static final double DEADBAND_THRESHOLD = 0.04;
    private static final double AUTO_SPEED = 0.1; 
    private static final double AUTO_DURATION = 3.0; 

    private Timer autoTimer;

    @Override
    public void robotInit() {
        krakenMotor = new TalonFX(KRACKEN_MOTOR_CAN_ID);
        xboxController = new XboxController(XBOX_CONTROLLER_PORT);

        TalonFXConfiguration config = new TalonFXConfiguration();
        krakenMotor.getConfigurator().apply(config);

        autoTimer = new Timer();
    }

    @Override
    public void autonomousInit() {
        autoTimer.reset();
        autoTimer.start();
    }

    @Override
    public void autonomousPeriodic() {
        if (autoTimer.get() < AUTO_DURATION) {
            DutyCycleOut motorOutput = new DutyCycleOut(AUTO_SPEED);
            krakenMotor.setControl(motorOutput);
        } else {
            DutyCycleOut motorOutput = new DutyCycleOut(0);
            krakenMotor.setControl(motorOutput);
        }
    }

    @Override
    public void teleopPeriodic() {
        double speed = -xboxController.getLeftY();

        if (Math.abs(speed) < DEADBAND_THRESHOLD) {
            speed = 0;
        }

        double scaledSpeed = speed * 0.1;
        //double scaledSpeed = speed ;

        DutyCycleOut motorOutput = new DutyCycleOut(scaledSpeed);
        krakenMotor.setControl(motorOutput);
    }

    @Override
    public void disabledInit() {
        DutyCycleOut motorOutput = new DutyCycleOut(0);
        krakenMotor.setControl(motorOutput);
    }
}
