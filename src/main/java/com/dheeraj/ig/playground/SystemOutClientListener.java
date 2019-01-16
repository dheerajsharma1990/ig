package com.dheeraj.ig.playground;

import com.lightstreamer.client.ClientListener;
import com.lightstreamer.client.LightstreamerClient;
import javax.annotation.Nonnull;

public class SystemOutClientListener implements ClientListener {

    @Override
    public void onListenEnd(@Nonnull LightstreamerClient client) {
        System.out.println("Stops listening to client events");
    }

    @Override
    public void onListenStart(@Nonnull LightstreamerClient client) {
        System.out.println("Start listening to client events");

    }

    @Override
    public void onPropertyChange(@Nonnull String property) {
        System.out.println("Client property changed: " + property);
    }

    @Override
    public void onServerError(int code, @Nonnull String message) {
        System.out.println("Server error: " + code + ": " + message);
    }

    @Override
    public void onStatusChange(@Nonnull String newStatus) {
        System.out.println("Connection status changed to " + newStatus);
    }


}