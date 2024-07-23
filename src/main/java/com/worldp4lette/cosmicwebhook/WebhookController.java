package com.worldp4lette.cosmicwebhook;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class WebhookController {

    @PostMapping("/payload")
    public String handleWebhook(@RequestBody String payload,  @RequestHeader("X-GitHub-Event") String event) {
        if ("push".equals(event)) {
            try {
                ProcessBuilder builder = new ProcessBuilder();
                builder.command("sh", "-c", "cd ~/Cosmic/Cosmic && git pull");
                Process process = builder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                return output.toString();
            } catch (IOException e) {
                return "Error: " + e.getMessage();
            }
        }
        return "No action has been taken";
    }
}
