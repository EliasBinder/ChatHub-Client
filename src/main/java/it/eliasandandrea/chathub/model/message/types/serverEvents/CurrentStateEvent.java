package it.eliasandandrea.chathub.model.message.types.serverEvents;

import it.eliasandandrea.chathub.model.encryption.KeyCombination;
import it.eliasandandrea.chathub.model.message.ServerEvent;

import java.util.HashMap;

public class CurrentStateEvent implements ServerEvent {

    public String UUID;
    public HashMap<String, KeyCombination> users;

}
