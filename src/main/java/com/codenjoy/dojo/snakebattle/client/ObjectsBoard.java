package com.codenjoy.dojo.snakebattle.client;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.snakebattle.model.Elements;

import java.util.*;

import static com.codenjoy.dojo.snakebattle.model.Elements.*;

public class ObjectsBoard extends Board {
    private static Random random = new Random();
    private static final List<Point> excludedPoints = new ArrayList<>();
    private final int SNAKE_LENGTH = 10;

    static {
        excludedPoints.add(new PointImpl(8, 22));
        excludedPoints.add(new PointImpl(9, 22));
        excludedPoints.add(new PointImpl(10, 22));
        excludedPoints.add(new PointImpl(11, 22));
        excludedPoints.add(new PointImpl(10, 21));
        excludedPoints.add(new PointImpl(11, 21));
        excludedPoints.add(new PointImpl(10, 20));
        excludedPoints.add(new PointImpl(11, 20));
        excludedPoints.add(new PointImpl(8, 20));
        excludedPoints.add(new PointImpl(9, 20));
        excludedPoints.add(new PointImpl(19, 20));
        excludedPoints.add(new PointImpl(20, 20));
        excludedPoints.add(new PointImpl(21, 20));
        excludedPoints.add(new PointImpl(10, 12));
        excludedPoints.add(new PointImpl(11, 12));
        excludedPoints.add(new PointImpl(9, 10));
        excludedPoints.add(new PointImpl(10, 10));
        excludedPoints.add(new PointImpl(11, 10));
        excludedPoints.add(new PointImpl(9, 9));
        excludedPoints.add(new PointImpl(10, 9));
        excludedPoints.add(new PointImpl(11, 9));
        excludedPoints.add(new PointImpl(19, 10));
        excludedPoints.add(new PointImpl(21, 10));
        excludedPoints.add(new PointImpl(23, 10));
        excludedPoints.add(new PointImpl(19, 9));
        excludedPoints.add(new PointImpl(20, 9));
        excludedPoints.add(new PointImpl(22, 9));
        excludedPoints.add(new PointImpl(23, 9));
        excludedPoints.add(new PointImpl(19, 8));
        excludedPoints.add(new PointImpl(20, 8));
        excludedPoints.add(new PointImpl(21, 8));
        excludedPoints.add(new PointImpl(22, 8));
        excludedPoints.add(new PointImpl(23, 8));

    }

    public boolean isFree(int x, int y) {
        boolean notEmpty = isAt(x, y, WALL, START_FLOOR, STONE
                , ENEMY_HEAD_DOWN, ENEMY_HEAD_LEFT, ENEMY_HEAD_RIGHT, ENEMY_HEAD_UP
                , ENEMY_TAIL_END_DOWN, ENEMY_TAIL_END_LEFT, ENEMY_TAIL_END_RIGHT, ENEMY_TAIL_END_UP
                , ENEMY_BODY_HORIZONTAL, ENEMY_BODY_VERTICAL
                , ENEMY_BODY_LEFT_DOWN, ENEMY_BODY_LEFT_UP
                , ENEMY_BODY_RIGHT_DOWN, ENEMY_BODY_RIGHT_UP
                , ENEMY_TAIL_INACTIVE, ENEMY_HEAD_SLEEP
                , BODY_HORIZONTAL, BODY_VERTICAL
                , BODY_LEFT_DOWN, BODY_LEFT_UP
                , BODY_RIGHT_DOWN, BODY_RIGHT_UP
                , TAIL_END_DOWN, TAIL_END_LEFT, TAIL_END_RIGHT, TAIL_END_UP);

        return !notEmpty;
    }

    public boolean canMoveTo(Direction direction) {
        Point p = convertDirectionToPoint(direction);
        return canMoveTo(p.getX(), p.getY());
    }

    private Point convertDirectionToPoint(Direction direction) {
        int dx = 0;
        int dy = 0;
        switch (direction) {
            case RIGHT:
                dx = 1;
                break;
            case LEFT:
                dx = -1;
                break;
            case UP:
                dy = 1;
                break;
            case DOWN:
                dy = -1;
                break;
        }
        return new PointImpl(getMe().getX() + dx, getMe().getY() + dy);
    }

    public boolean canMoveTo(int x, int y) {
        return ((isFree(x, y) || (isStoneAt(x, y) && canEatStone())) && !excludedPoints.contains(new PointImpl(x, y)));
    }

    public boolean canMoveToWithBite(Direction direction) {
        Point p = convertDirectionToPoint(direction);
        return canMoveTo(direction) || isAt(p.getX(), p.getY(), getSnake());
    }

    public boolean canEatStone() {
        return isAt(getMe(), HEAD_EVIL) || getSnakeLength() > SNAKE_LENGTH;
    }

    public int getSnakeLength() {
        return get(Elements.getSnake()).size();
    }

    public Direction getDirectionToPoint(Point point) {
        if (point == null) {
            return null;
        }
        Direction d = null;
        int dx = point.getX() - getMe().getX();
        int dy = point.getY() - getMe().getY();
        if (Math.abs(dx) < Math.abs(dy)) {
            d = dy > 0
                    ? viseVersaSelector(Direction.UP, viseVersaSelector(Direction.LEFT, Direction.RIGHT))
                    : viseVersaSelector(Direction.DOWN, viseVersaSelector(Direction.LEFT, Direction.RIGHT));
        }
        if ((d == null || !canMoveTo(d)) && (Math.abs(dx) > Math.abs(dy))) {
            d = dx > 0
                    ? viseVersaSelector(Direction.RIGHT, viseVersaSelector(Direction.UP, Direction.DOWN))
                    : viseVersaSelector(Direction.LEFT, viseVersaSelector(Direction.UP, Direction.DOWN));
        }
        if (d == null || !canMoveTo(d)) {
            d = random.nextInt(100) > 50
                    ? (dx > 0 ? Direction.RIGHT : Direction.LEFT)
                    : (dy > 0 ? Direction.UP : Direction.DOWN);
        }

        return d;
    }

    public Direction viseVersaSelector(Direction first, Direction second) {
        if (canMoveTo(first)) {
            return first;
        } else {
            return second;
        }
    }

    public Point getNearestPointByElement(Elements elem, int radius) {
        if (elem == null) {
            return null;
        }
        int x = getMe().getX();
        int y = getMe().getY();

        for (int dx = -radius; dx <= radius; ++dx) {
            for (int dy = -radius; dy <= radius; ++dy) {
                int sx = x + dx;
                int sy = y + dy;
                if (sx < 0 || sx >= size() || sy < 0 || sy >= size()) {
                    continue;
                }
                if ((elem.ch() == (getAt(sx, sy).ch())) && (!excludedPoints.contains(new PointImpl(sx, sy)))) {
                    return new PointImpl(sx, sy);
                }
            }
        }
        return null;
    }

    public Point getNearestPointByElement(List<Elements> elems, int radius) {
        if (elems == null) {
            return null;
        }
        Map<Double, Point> points = new HashMap<>();
        for (Elements elem : elems) {
            Point p = getNearestPointByElement(elem, radius);
            if (p != null) {
                points.put(p.distance(getMe()), p);
            }
        }
        if (points.isEmpty()) {
            return null;
        }
        Optional<Double> d = points.keySet().stream()
                .sorted()
                .findFirst();


        return d.map(points::get).orElse(null);

    }

    public Point getNearestPointByElement(Elements[] elems, int radius) {
        if (elems == null) {
            return null;
        }
        return getNearestPointByElement(Arrays.asList(elems), radius);
    }

    public boolean isElementInRadius(Elements elem, int radius) {
        List<Elements> elements = getNear(getMe().getX(), getMe().getY(), radius);
        return !elements.isEmpty() && elements.contains(elem);
    }

    @Override
    public List<Elements> getNear(int x, int y, int radius) {
        List<Elements> result = new LinkedList<>();

        for (int layer = 0; layer < this.countLayers(); ++layer) {
            result.addAll(getNear(layer, x, y, radius));
        }

        return result;
    }


    @Override
    protected List<Elements> getNear(int numLayer, int x, int y, int radius) {
        List<Elements> result = new LinkedList<>();

        for (int dx = -radius; dx <= radius; ++dx) {
            for (int dy = -radius; dy <= radius; ++dy) {
                if (!PointImpl.pt(x + dx, y + dy).isOutOf(this.size)
                        && (dx != 0 || dy != 0)
                        && (!this.withoutCorners() || dx == 0 || dy == 0)) {
                    if (!excludedPoints.contains(new PointImpl(x + dx, y + dy))) {
                        result.add(getAt(numLayer, x + dx, y + dy));
                    }
                }
            }
        }

        return result;
    }
}
