package com.codenjoy.dojo.snakebattle.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.services.*;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.algs.DeikstraFindWay;
import com.codenjoy.dojo.snakebattle.model.Elements;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * User: your name
 * Это твой алгоритм AI для игры. Реализуй его на свое усмотрение.
 * Обрати внимание на {@see YourSolverTest} - там приготовлен тестовый
 * фреймворк для тебя.
 */
public class YourSolver implements Solver<ObjectsBoard> {
//    private Random random = new Random();

    private Dice dice;
    private ObjectsBoard board;
    private Direction direction = Direction.RIGHT;
    private static float SEARCH_RADIUS = 0.75f;
    private int eatenStones = 0;
    private final int SNAKE_LENGTH = 5;

    YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(ObjectsBoard board) {
        this.board = board;
        if (board.isGameOver()) {
            direction = Direction.RIGHT;
            eatenStones = 0;
            return "";
        }
        String act = "";
        if (eatenStones > 0
                && board.getSnakeLength() < SNAKE_LENGTH
                && board.isAt(board.getMe(), Elements.HEAD_EVIL)
        ) {
            act = ",act";
            eatenStones--;
        }

        return this.getDirection(board).toString() + act;
    }

    public static void main(String[] args) {
        WebSocketRunner.runClient(
                "https://game3.epam-bot-challenge.com.ua/codenjoy-contest/board/player/d8xr344qq5q9e76rnm7n?code=5139897655169534637",
                new YourSolver(new RandomDice()),
                new ObjectsBoard());
    }

    private Direction getDirection(ObjectsBoard board) {
//        System.out.println(board.getMe());
        if (board.getAt(board.getMe()).equals(Elements.HEAD_SLEEP)) {
            eatenStones = 0;
            return Direction.RIGHT;
        }

//        Point selectedPoint = selectTarget();

        direction = targetSelector(selectTarget());

        if (board.canMoveTo(direction)) {
            Point p = board.getMe();
            p = direction.change(p);
            if (board.getAllAt(p).contains(Elements.STONE)) {
                eatenStones++;
                System.out.println("Eaten stones: " + eatenStones);
            }
            return direction;
        }

        List<Direction> directions = allowedMoves();
        if (directions.isEmpty()) {
            directions = allowedMovesWithBite();
        }

        return directions.isEmpty() ? Direction.random() : directions.get(0);
    }

//    public List<Point> getPointsWithElementsAtDistanceRadius(Elements element, int distance) {
//        if (distance < 0 || distance >= board.size()) {
//            throw new IllegalArgumentException("Distance should be more than 0 and less than board size");
//        }
//        if (element == null) {
//            throw new IllegalArgumentException("Nothing to search");
//        }
//        List<Point> points = new ArrayList<>();
//        int x, y;
//        for (int i = 1; i <= distance; i++) {
//            y = board.getMe().getY() + distance;
//
//            x = board.getMe().getX() + i;
//            if (element.ch() == board.getAt(x, y).ch()) {
//                points.add(new PointImpl(x, y));
//            }
//            x = board.getMe().getX() + i;
//            if (element.ch() == board.getAt(x, y).ch()) {
//                points.add(new PointImpl(x, y));
//            }
//
//
//        }
//
//        return points;
//    }

//    public boolean eatableStone(Point point) {
//        return board.isStoneAt(point.getX(), point.getY()) && board.canEatStone();
//    }

    public Direction targetSelector(Point point) {
        Direction direction = point == null
                ? Direction.random()
                : board.getDirectionToPoint(point);
//        for (int i = 1; i < board.size() * SEARCH_RADIUS; ++i) {
//            if (board.canEatStone() && board.isElementInRadius(Elements.STONE, i)) {
//                direction = board.getDirectionToPoint(board.getNearestPointByElement(Elements.STONE, i));
//            } else if (board.isElementInRadius(Elements.GOLD, i)) {
//                direction = board.getDirectionToPoint(board.getNearestPointByElement(Elements.GOLD, i));
//            } else if (board.isElementInRadius(Elements.APPLE, i)) {
//                direction = board.getDirectionToPoint(board.getNearestPointByElement(Elements.APPLE, i));
//            } else if (board.isElementInRadius(Elements.FURY_PILL, i)) {
//                direction = board.getDirectionToPoint(board.getNearestPointByElement(Elements.FURY_PILL, i));
////            } else if (isElementInRadius(Elements.FLYING_PILL, i)) {
////                direction = board.getDirectionToPoint(board.getClosetPointByElement(Elements.FLYING_PILL, i));
//            } else {
//                continue;
//            }
//            break;
//        }
        return direction;
    }

    public Point selectTarget() {
        Point point = null;
        for (int i = 0; i < board.size() * SEARCH_RADIUS; i++) {
            if (board.getAt(board.getMe()).equals(Elements.HEAD_EVIL)) {
                point = board.getNearestPointByElement(Elements.getEnemy(), 3);
            }
            if (board.canEatStone()) {
                point = board.getNearestPointByElement(Elements.STONE, i);
            }
            if (point == null) {
                point = board.getNearestPointByElement(Elements.GOLD, i);
            }
            if (point == null) {
                point = board.getNearestPointByElement(Elements.APPLE, i);
            }
            if (point == null) {
                point = board.getNearestPointByElement(Elements.FURY_PILL, i);
//            } else if (isElementInRadius(Elements.FLYING_PILL, i)) {
//                direction = board.getDirectionToPoint(board.getClosetPointByElement(Elements.FLYING_PILL, i));
            }
            if (point != null) {
                break;
            }
        }

        System.out.println("Target = " + point);
        return point;
    }

    public List<Direction> allowedMoves() {
        List<Direction> directions = new ArrayList<>();
        for (Direction value : Direction.values()) {
            if (board.canMoveTo(value)) {
                directions.add(value);
            }
        }
        return directions;
    }

    public List<Direction> allowedMovesWithBite() {
        List<Direction> directions = new ArrayList<>();
        for (Direction value : Direction.values()) {
            if (board.canMoveToWithBite(value)) {
                directions.add(value);
            }
        }
        return directions;
    }

}
