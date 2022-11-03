package it.eliasandandrea.chathub.client.model.protocol;

import it.eliasandandrea.chathub.shared.protocol.sharedEvents.MessageEvent;

public interface OnMessageCallback {

    void onMessage(MessageEvent event) throws Exception;

}
