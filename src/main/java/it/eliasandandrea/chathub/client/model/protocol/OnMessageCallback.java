package it.eliasandandrea.chathub.client.model.protocol;

import it.eliasandandrea.chathub.shared.protocol.sharedEvents.MessageEvent;

public interface OnMessageCallback {

    public void onMessage(MessageEvent event) throws Exception;

}
