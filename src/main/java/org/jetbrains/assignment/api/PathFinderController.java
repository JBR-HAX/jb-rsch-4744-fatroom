package org.jetbrains.assignment.api;

import org.jetbrains.assignment.api.dto.Location;
import org.jetbrains.assignment.api.dto.Move;
import org.jetbrains.assignment.service.PathFinderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PathFinderController {
    private final PathFinderService finderService;

    public PathFinderController(PathFinderService finderService) {
        this.finderService = finderService;
    }

    @PostMapping("/moves")
    public List<Move> getMoves(@RequestBody List<Location> locations) {
        return finderService.findMoves(locations);
    }

    @PostMapping("/locations")
    public List<Location> getLocations(@RequestBody List<Move> moves) {
        return finderService.findVisitedLocations(moves);
    }
}
