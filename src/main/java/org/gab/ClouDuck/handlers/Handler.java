package org.gab.ClouDuck.handlers;

import jakarta.annotation.PostConstruct;
import org.gab.ClouDuck.aws.AWSUtils;
import org.gab.ClouDuck.requests.Operation;
import org.gab.ClouDuck.requests.RegistrationRequest;
import org.gab.ClouDuck.requests.Request;
import org.gab.ClouDuck.responses.RegistrationResponse;
import org.gab.ClouDuck.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Handler {
    
    final AWSUtils aws;
    private static final String DEFAULT_HOST = "localhost";
    
    private static final int DEFAULT_PORT = 4200;
    
    private final static TaskExecutor executor;
    
    static {
        executor = new ConcurrentTaskExecutor();
    }
    
    @Autowired
    public Handler(AWSUtils aws) {
    
        this.aws = aws;
    }
    
    @PostConstruct
    public void start() {
        start(DEFAULT_HOST, DEFAULT_PORT);
    }
    
    public void start(String host, int port) {
    
        executor.execute(new HandlerTask(host, port));
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
                
                try {
                    var request = in.readObject();
                    if(request instanceof Request r) {
                        Response response = switch (r.getOperation()) {
        
                            case GET -> aws.get(r.getKey(), r.getFolder(), r.getFilename());
                            case SAVE -> aws.save(r.getKey(), r.getFolder(), r.getFilename(), r.getFile());
                            case DELETE -> aws.delete(r.getKey(), r.getFolder(), r.getFilename());
                            case UPDATE -> aws.update(r.getKey(), r.getFolder(), r.getFilename(), r.getFile());
                        };
                        out.writeObject(response);
                    }
                    else if(request instanceof RegistrationRequest r) {
                        var response = aws.registration(r.getKey());
                        out.writeObject(response);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    out.writeObject(Response.error(e.getMessage()));
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
