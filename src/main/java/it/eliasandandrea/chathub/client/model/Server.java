package it.eliasandandrea.chathub.client.model;

import java.util.Objects;

public class Server {

    private String name;
    private String address;
    private int port;
    private int type;

    public Server(String name, String address, int port, int type) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getType() {
        return type;
    }
    //equals method


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Server other = (Server) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (this.port != other.port) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }
}
