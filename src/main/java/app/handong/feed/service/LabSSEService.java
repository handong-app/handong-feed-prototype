package app.handong.feed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class LabSSEService {

    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;

    // Store all active emitters
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        // Create a new SseEmitter
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        // Add the emitter to the list
        emitters.add(emitter);

        // Remove the emitter on completion, timeout, or error
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((ex) -> emitters.remove(emitter));

        // Send an initial event to confirm the connection
        sendToClient(emitter, "Connection established", "SSE connected");

        return emitter;
    }

    public void broadcast(Object data, String comment) {
        // Iterate through all active emitters and send the event
        emitters.forEach(emitter -> sendToClient(emitter, data, comment));
    }

    private <T> void sendToClient(SseEmitter emitter, T data, String comment) {
        try {
            emitter.send(SseEmitter.event()
                    .data(data)
                    .comment(comment));
        } catch (IOException e) {
            // Remove the emitter if sending fails
            emitters.remove(emitter);
        }
    }
}
