package org.gab.ClouDuck.handlers;

import org.gab.ClouDuck.aws.AWSUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Handler {
    
    private static final String DEFAULT_HOST = "localhost";
    
    private static final int DEFAULT_PORT = 4200;
    
    private final static TaskExecutor executor;
    
    static {
        executor = new ConcurrentTaskExecutor();
    }
    
    public static void start() {
        start(DEFAULT_HOST, DEFAULT_PORT);
    }
    
    public static void start(String host, int port) {
    
        executor.execute(new HandlerTask(host, port));
    }
}

class HandlerTask implements Runnable {
    private String host;
    private int port;
    public HandlerTask(String host, int port) {
        
        this.host = host;
        this.port = port;
    }
    
    public void run() {
    
        try(ServerSocket server = new ServerSocket(port);
            Socket clientSocket = server.accept();
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            
            var request = (Request)(in.readObject());
            AWSUtils aws;
            if(request.getRegion() != null)
                aws = new AWSUtils(request.getAccessKey(), request.getSecretKey(), request.getBucketName(), request.getRegion());
            else
                aws = new AWSUtils(request.getAccessKey(), request.getSecretKey(), request.getBucketName());
            
            Response response = switch (request.getOperation()) {
              
                case GET -> aws.get(request.getFolder(), request.getFilename());
                case SAVE -> aws.save(request.getFolder(), request.getFilename(), request.getFile());
                case DELETE -> aws.delete(request.getFolder(), request.getFilename());
                case UPDATE -> aws.update(request.getFolder(), request.getFilename(), request.getFile());
            };
            
            out.writeObject(response);
        
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
