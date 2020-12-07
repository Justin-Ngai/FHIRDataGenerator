package com.mycompany.app;

import com.azure.messaging.eventhubs.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.function.Consumer;
import java.util.concurrent.TimeUnit;
import java.net.http.*;
import com.mashape.unirest.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.HttpResponse.*;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.options.Options;

public class App {
    public static void main(String[] args) {
        final String connectionString = "Endpoint=sb://ehnsjn.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=i+nqwQ+K0xXW7938kSd5rHboyfUY/yxIpBKMH2MB8uA=";
        final String eventHubName = "eh";

        // create a producer using the namespace connection string and event hub name
        EventHubProducerClient producer = new EventHubClientBuilder().connectionString(connectionString, eventHubName)
                .buildProducerClient();

        // prepare a batch of events to send to the event hub
        EventDataBatch batch = producer.createBatch();
        batch.tryAdd(new EventData(health()));

        // send the batch of events to the event hub
        producer.send(batch);

        // close the producer
        producer.close();
        //System.out.println(health());
        //System.out.println(health());
    }

    // public static void health() {
    //     System.out.println("line 1");
    // }
    public static String health() {
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://syntheticmass.mitre.org/v1/fhir/Patient?_count=1&apikey=JLlYJKf3uCFveUgAJD35owD8wxmtENAb").asString();
//            System.out.println(response.getBody());
            return response.getBody();
        } catch(Exception e){
            return null;            // Always must return something
        }
    }
}