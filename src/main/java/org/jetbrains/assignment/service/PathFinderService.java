package org.jetbrains.assignment.service;

import org.jetbrains.assignment.api.dto.Location;
import org.jetbrains.assignment.api.dto.Move;
import org.jetbrains.assignment.storage.Request;
import org.jetbrains.assignment.storage.RequestRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PathFinderService {
    private final RequestRepo requestRepo;

    public PathFinderService(RequestRepo requestRepo) {
        this.requestRepo = requestRepo;
    }

    public List<Move> findMoves(List<Location> locations) {
        List<Move> moves = new ArrayList<>();
        for (int i = 1; i < locations.size(); i++) {
            moves.addAll(findMove(locations.get(i - 1), locations.get(i)));
        }
        requestRepo.save(Request.fromLocations(locations, moves));
        return moves;
    }

    public List<Location> findVisitedLocations(List<Move> moves) {
        List<Location> locations = new ArrayList<>();
        Location lastLocation = new Location(0, 0);
        locations.add(lastLocation);
        for (Move move : moves) {
            lastLocation = makeMove(lastLocation, move);
            locations.add(lastLocation);
        }
        requestRepo.save(Request.fromMoves(moves, locations));

        return locations;
    }

    private Location makeMove(Location start, Move move) {
        return switch (move.getDirection()) {
            case "EAST" -> new Location(start.getX() + move.getSteps(), start.getY());
            case "WEST" -> new Location(start.getX() - move.getSteps(), start.getY());
            case "NORTH" -> new Location(start.getX(), start.getY() - move.getSteps());
            case "SOUTH" -> new Location(start.getX(), start.getY() + move.getSteps());
            default -> throw new IllegalArgumentException("Unknown direction: " + move.getDirection());
        };
    }

    private List<Move> findMove(Location start, Location dest) {
        List<Move> moves = new ArrayList<>();
        if (start.getX() < dest.getX()) {
            moves.add(new Move("EAST", dest.getX() - start.getX()));
        }
        if (start.getY() > dest.getX()) {
            moves.add(new Move("WEST", start.getX() - dest.getX()));
        }
        if (start.getY() < dest.getY()) {
            moves.add(new Move("SOUTH", dest.getY() - start.getY()));
        }
        if (start.getY() > dest.getY()) {
            moves.add(new Move("NORTH", start.getY() - dest.getY()));
        }
        return moves;
    }
}
