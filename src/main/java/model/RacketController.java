package model;

public interface RacketController {
    enum State { GOING_UP, IDLE, GOING_DOWN, TURN_LEFT, TURN_RIGHT };
    State getState();
}