package ra.common.messaging;

import java.util.Map;

/**
 * Provides a message indicating what command to run.
 */
public final class CommandMessage extends BaseMessage {

    public enum Command {
        Start,
        Shutdown,
        GracefullyShutdown,
        Restart,
        Pause,
        Unpause,
        NetState,
        RegisterStatusListener,
        UnregisterStatusListener,
        RegisterStateChangeListener,
        UnregisterStateChangeListener
    }

    private Command command;

    public CommandMessage() {}

    public CommandMessage(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String,Object> m = super.toMap();
        if(command!=null) m.put("command", command.name());
        return m;
    }

    @Override
    public void fromMap(Map<String, Object> m) {
        super.fromMap(m);
        if(m.get("command")!=null) command = Command.valueOf((String)m.get("command"));
    }
}
