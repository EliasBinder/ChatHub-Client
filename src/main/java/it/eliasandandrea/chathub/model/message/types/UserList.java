package it.eliasandandrea.chathub.model.message.types;

import it.eliasandandrea.chathub.model.message.Message;

import java.security.PublicKey;
import java.util.HashMap;

public class UserList implements Message {

    public HashMap<String, PublicKey> users;

}
