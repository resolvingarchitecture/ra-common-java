package ra.common.messaging;

import ra.common.JSONSerializable;

import java.util.List;

/**
 *
 */
public interface Message extends JSONSerializable {
    void addErrorMessage(String errorMessage);
    List<String> getErrorMessages();
    void clearErrorMessages();
}
