package ra.common.network;

public enum ControlCommand {
    InitiateComm,
    RegisterService,
    UnregisterService,
    StartService,
    PauseService,
    UnPauseService,
    RestartService,
    StopService,
    GracefullyStopService,
    Start,
    Pause,
    UnPause,
    Restart,
    Shutdown,
    GracefullyShutdown,
    CloseClient,
    Send,
    Ack,
    EndComm
}
