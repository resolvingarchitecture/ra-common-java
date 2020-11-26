package ra.common.network;

public enum ControlCommand {
    InitiateComm,
    Start,
    RegisterService,
    UnregisterService,
    StartService,
    PauseService,
    UnPauseService,
    RestartService,
    StopService,
    GracefullyStopService,
    Pause,
    UnPause,
    Restart,
    Shutdown,
    GracefullyShutdown,
    CloseClient,
    Ack,
    EndComm
}
