package com.dheeraj.ig.playground;

import com.lightstreamer.client.ClientListener;
import com.lightstreamer.client.ItemUpdate;
import com.lightstreamer.client.LightstreamerClient;
import com.lightstreamer.client.Subscription;
import com.lightstreamer.client.SubscriptionListener;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;

public class Main {

    private static final String[] items = {"MARKET:CS.D.EURUSD.CFD.IP"};

    private static final String[] fields = {"BID", "OFFER"};

    public static void main(String[] args) {
        final String serverAddress = "https://demo-apd.marketdatasystems.com";

        new Main().start(serverAddress);

        try {
            new CountDownLatch(1).await(); //just wait
        } catch (InterruptedException e) {
        }
    }

    private void start(String serverAddress) {

        LightstreamerClient client = new LightstreamerClient(serverAddress, "DEFAULT");
        client.connectionDetails.setPassword("CST-" + "f5f366b600cfe1f6c303858888551d4cd048659f39303021105d1481f531322001112" + "|XST-"
            + "528ea974bfe52f1af51ed3d23b81c470c709db035490a0d28ae49c3ebbc72d5401112");
        client.connectionDetails.setUser("dheerajsharma1990");
        ClientListener clientListener = new SystemOutClientListener();
        client.addListener(clientListener);

        Subscription sub = new Subscription("MERGE", items, fields);

        SubscriptionListener subListener = new SystemOutSubscriptionListener();
        sub.addListener(subListener);

        client.subscribe(sub);
        client.connect();
    }

    private static class SystemOutSubscriptionListener implements SubscriptionListener {

        @Override
        public void onClearSnapshot(String itemName, int itemPos) {
            System.out.println("Server has cleared the current status of the chat");
        }

        @Override
        public void onCommandSecondLevelItemLostUpdates(int lostUpdates, String key) {
            //not on this subscription
        }

        @Override
        public void onCommandSecondLevelSubscriptionError(int code, String message, String key) {
            //not on this subscription
        }

        @Override
        public void onEndOfSnapshot(String arg0, int arg1) {
            System.out.println("Snapshot is now fully received, from now on only real-time messages will be received");
        }

        @Override
        public void onItemLostUpdates(String itemName, int itemPos, int lostUpdates) {
            System.out.println(lostUpdates + " messages were lost");
        }

        @Override
        public void onItemUpdate(ItemUpdate update) {

            System.out.println("====UPDATE====> " + update.getItemName());

            Iterator<Entry<String, String>> changedValues = update.getChangedFields().entrySet().iterator();
            while (changedValues.hasNext()) {
                Entry<String, String> field = changedValues.next();
                System.out.println("Field " + field.getKey() + " changed: " + field.getValue());
            }

            System.out.println("<====UPDATE====");
        }

        @Override
        public void onListenEnd(Subscription subscription) {
            System.out.println("Stop listeneing to subscription events");
        }

        @Override
        public void onListenStart(Subscription subscription) {
            System.out.println("Start listeneing to subscription events");
        }

        @Override
        public void onSubscription() {
            System.out.println("Now subscribed to the chat item, messages will now start coming in");
        }

        @Override
        public void onSubscriptionError(int code, String message) {
            System.out.println("Cannot subscribe because of error " + code + ": " + message);
        }

        @Override
        public void onUnsubscription() {
            System.out.println("Now unsubscribed from chat item, no more messages will be received");
        }

        @Override
        public void onRealMaxFrequency(String frequency) {
            System.out.println("Frequency is " + frequency);
        }

    }

}
