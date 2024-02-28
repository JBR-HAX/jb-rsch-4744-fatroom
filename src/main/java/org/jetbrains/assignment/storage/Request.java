package org.jetbrains.assignment.storage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.jetbrains.assignment.api.dto.Location;
import org.jetbrains.assignment.api.dto.Move;

import java.util.List;
import java.util.UUID;

@Entity
public class Request {
    @Id
    private UUID id;
    private String input;
    private String output;

    public Request() {
    }

    public Request(String input, String output) {
        this.id = UUID.randomUUID();
        this.input = input;
        this.output = output;
    }

    public static Request fromMoves(List<Move> input, List<Location> output) {
        return new Request(input.toString(), output.toString());
    }

    public static Request fromLocations(List<Location> input, List<Move> output) {
        return new Request(input.toString(), output.toString());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
