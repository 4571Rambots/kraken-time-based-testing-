package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
    private TalonFX krakenMotor; 
    private XboxController xboxController;

    // Motor CAN ID
    private static final int KRACKEN_MOTOR_CAN_ID = 4; 
    private static final int XBOX_CONTROLLER_PORT = 0; 

    @Override
    public void robotInit() {
        krakenMotor = new TalonFX(KRACKEN_MOTOR_CAN_ID);
        xboxController = new XboxController(XBOX_CONTROLLER_PORT);

        
        TalonFXConfiguration config = new TalonFXConfiguration();
        krakenMotor.getConfigurator().apply(config);
    }

    @Override
    public void teleopPeriodic() {
        double speed = -xboxController.getLeftY(); 

        DutyCycleOut motorOutput = new DutyCycleOut(speed);
        krakenMotor.setControl(motorOutput);
    }

    @Override
    public void disabledInit() {
        DutyCycleOut motorOutput = new DutyCycleOut(0);
        krakenMotor.setControl(motorOutput);
    }
}
