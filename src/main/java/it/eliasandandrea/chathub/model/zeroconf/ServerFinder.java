package it.eliasandandrea.chathub.model.zeroconf;

import it.eliasandandrea.chathub.model.Server;
import javafx.application.Platform;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.Executors;

public class ServerFinder {

    public ServerFinder(ServerAddedListener serverAddedListener, ServerRemovedListener serverRemovedListener) {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            jmdns.addServiceListener("_chathub._tcp.local.", new ZeroconfServiceListener());
            Executors.newSingleThreadExecutor().submit(() -> {
                LinkedList<ServiceInfo> serviceInfos = new LinkedList<>();
                while(true){
                    Thread.sleep(5000);
                    try {
                        ServiceInfo[] serviceInfoArray = jmdns.list("_chathub._tcp.local.");
                        for (ServiceInfo info : serviceInfoArray) {
                            boolean exists = serviceInfos.stream().anyMatch(localInfo ->
                                    localInfo.getHostAddresses()[0].equals(info.getHostAddresses()[0])
                                    && localInfo.getPort() == info.getPort()
                            );
                            if (!exists) {
                                serviceInfos.add(info);
                                Server server = new Server(info.getName(), info.getHostAddresses()[0], info.getPort());
                                Platform.runLater(() -> serverAddedListener.onServerAdded(server));
                            }
                        }
                        for (ServiceInfo info : serviceInfos) {
                            boolean exists = Arrays.stream(serviceInfoArray).anyMatch(localInfo ->
                                    localInfo.getHostAddresses()[0].equals(info.getHostAddresses()[0])
                                    && localInfo.getPort() == info.getPort()
                            );
                            if (!exists){
                                serviceInfos.remove(info);
                                Server server = new Server(info.getName(), info.getHostAddresses()[0], info.getPort());
                                Platform.runLater(() -> serverRemovedListener.onServerRemoved(server));
                            }
                        }
                    }catch (Exception ex){
                    }
                }
            });
            //Shutdown jmdns when the program is closed
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    jmdns.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
