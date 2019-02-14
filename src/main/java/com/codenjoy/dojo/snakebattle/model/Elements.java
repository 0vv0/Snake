package com.codenjoy.dojo.snakebattle.model;

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


import com.codenjoy.dojo.services.printer.CharElements;

import java.util.Arrays;
import java.util.Objects;

/**
 * Тут указана легенда всех возможных объектов на поле и их состояний.
 * Важно помнить, что для каждой енумной константы надо создать спрайт в папке \src\main\webapp\resources\sprite.
 */
public enum Elements implements CharElements {

    NONE(' '),         // пустое место
    WALL('☼'),         // а это стенка
    START_FLOOR('#'),  // место старта змей
    OTHER('?'),        // этого ты никогда не увидишь :)

    APPLE('○'),        // яблоки надо кушать от них становишься длинее
    STONE('●'),        // а это кушать не стоит - от этого укорачиваешься
    FLYING_PILL('©'),  // таблетка полета - дает суперсилы
    FURY_PILL('®'),    // таблетка ярости - дает суперсилы
    GOLD('$'),         // золото - просто очки

    // голова твоей змеи в разных состояниях и напрвлениях
    HEAD_DOWN('▼'),
    HEAD_LEFT('◄'),
    HEAD_RIGHT('►'),
    HEAD_UP('▲'),
    HEAD_DEAD('☻'),    // этот раунд ты проиграл
    HEAD_EVIL('♥'),    // скушали таблетку ярости
    HEAD_FLY('♠'),     // скушали таблетку полета
    HEAD_SLEEP('&'),   // змейка ожидает начала раунда

    // хвост твоей змейки
    TAIL_END_DOWN('╙'),
    TAIL_END_LEFT('╘'),
    TAIL_END_UP('╓'),
    TAIL_END_RIGHT('╕'),
    TAIL_INACTIVE('~'),

    // туловище твоей змейки
    BODY_HORIZONTAL('═'),
    BODY_VERTICAL('║'),
    BODY_LEFT_DOWN('╗'),
    BODY_LEFT_UP('╝'),
    BODY_RIGHT_DOWN('╔'),
    BODY_RIGHT_UP('╚'),

    // змейки противников
    ENEMY_HEAD_DOWN('˅'),
    ENEMY_HEAD_LEFT('<'),
    ENEMY_HEAD_RIGHT('>'),
    ENEMY_HEAD_UP('˄'),
    ENEMY_HEAD_DEAD('☺'),   // этот раунд противник проиграл
    ENEMY_HEAD_EVIL('♣'),   // противник скушал таблетку ярости
    ENEMY_HEAD_FLY('♦'),    // противник скушал таблетку полета
    ENEMY_HEAD_SLEEP('ø'),  // змейка ожидает начала раунда

    // хвосты змеек противников
    ENEMY_TAIL_END_DOWN('¤'),
    ENEMY_TAIL_END_LEFT('×'),
    ENEMY_TAIL_END_UP('æ'),
    ENEMY_TAIL_END_RIGHT('ö'),
    ENEMY_TAIL_INACTIVE('*'),

    // туловище змеек противников
    ENEMY_BODY_HORIZONTAL('─'),
    ENEMY_BODY_VERTICAL('│'),
    ENEMY_BODY_LEFT_DOWN('┐'),
    ENEMY_BODY_LEFT_UP('┘'),
    ENEMY_BODY_RIGHT_DOWN('┌'),
    ENEMY_BODY_RIGHT_UP('└');

    final char ch;

    Elements(char ch) {
        this.ch = ch;
    }

    @Override
    public char ch() {
        return ch;
    }

    @Override
    public String toString() {
        return String.valueOf(ch);
    }

    public static Elements valueOf(char ch) {
        for (Elements el : Elements.values()) {
            if (el.ch == ch) {
                return el;
            }
        }
        throw new IllegalArgumentException("No such element for " + ch);
    }

    public static Elements[] getSnakeHead() {
        return new Elements[]{
                HEAD_DOWN,
                HEAD_LEFT,
                HEAD_RIGHT,
                HEAD_UP,
                HEAD_DEAD,
                HEAD_EVIL,
                HEAD_FLY,
                HEAD_SLEEP
        };
    }

    public static Elements[] getSnakeBody() {
        return new Elements[]{
                BODY_HORIZONTAL,
                BODY_VERTICAL,
                BODY_LEFT_DOWN,
                BODY_LEFT_UP,
                BODY_RIGHT_DOWN,
                BODY_RIGHT_UP
        };
    }

    public static Elements[] getSnakeTail() {
        return new Elements[]{
                TAIL_END_DOWN,
                TAIL_END_LEFT,
                TAIL_END_UP,
                TAIL_END_RIGHT,
                TAIL_INACTIVE
        };
    }

    public static Elements[] getSnake() {
        Elements[] snake = new Elements[getSnakeHead().length + getSnakeBody().length + getSnakeTail().length];
        System.arraycopy(getSnakeHead(), 0, snake, 0, getSnakeHead().length);// Put snake head into begin of array
        System.arraycopy(getSnakeBody(), 0, snake, getSnakeHead().length, getSnakeBody().length);// Put snake body into middle of array
        System.arraycopy(getSnakeTail(), 0, snake, getSnakeHead().length + getSnakeBody().length, getSnakeTail().length);// Put snake tail into end of array
        return snake;
    }

    public static Elements[] getEnemyHead() {
        return new Elements[]{
                ENEMY_HEAD_DOWN,
                ENEMY_HEAD_LEFT,
                ENEMY_HEAD_RIGHT,
                ENEMY_HEAD_UP,
                ENEMY_HEAD_DEAD,
                ENEMY_HEAD_EVIL,
                ENEMY_HEAD_FLY,
                ENEMY_HEAD_SLEEP
        };
    }

    public static Elements[] getEnemyBody() {
        return new Elements[]{
                ENEMY_BODY_HORIZONTAL,
                ENEMY_BODY_VERTICAL,
                ENEMY_BODY_LEFT_DOWN,
                ENEMY_BODY_LEFT_UP,
                ENEMY_BODY_RIGHT_DOWN,
                ENEMY_BODY_RIGHT_UP
        };
    }

    public static Elements[] getEnemyTail() {
        return new Elements[]{
                ENEMY_TAIL_END_DOWN,
                ENEMY_TAIL_END_LEFT,
                ENEMY_TAIL_END_UP,
                ENEMY_TAIL_END_RIGHT,
                ENEMY_TAIL_INACTIVE
        };
    }

    public static Elements[] getEnemy() {
        Elements[] enemy = new Elements[getEnemyHead().length + getEnemyBody().length + getEnemyTail().length];
        System.arraycopy(getEnemyHead(), 0, enemy, 0, getEnemyHead().length);
        System.arraycopy(getEnemyBody(), 0, enemy, getEnemyHead().length, getEnemyBody().length);
        System.arraycopy(getEnemyTail(), 0, enemy, getEnemyHead().length + getEnemyBody().length, getEnemyTail().length);
        return enemy;
    }

    public static int getPrice(Elements element) {
        int price = Integer.MAX_VALUE;
        switch (element) {
            case NONE:
                price = 10;
                break;
            case WALL:
            case START_FLOOR:
            case OTHER:
                price = Integer.MAX_VALUE;
                break;
            case APPLE:
                price = 5;
                break;
            case STONE:
                price = Integer.MAX_VALUE;
                break;
            case FLYING_PILL:
                price = 10;
                break;
            case FURY_PILL:
                price = 7;
                break;
            case GOLD:
                price = 2;
                break;
            case HEAD_DOWN:
            case HEAD_LEFT:
            case HEAD_RIGHT:
            case HEAD_UP:
            case HEAD_DEAD:
            case HEAD_EVIL:
            case HEAD_FLY:
            case HEAD_SLEEP:
                price = 10;
                break;
            case TAIL_END_DOWN:
            case TAIL_END_LEFT:
            case TAIL_END_UP:
            case TAIL_END_RIGHT:
            case TAIL_INACTIVE:
            case BODY_HORIZONTAL:
            case BODY_VERTICAL:
            case BODY_LEFT_DOWN:
            case BODY_LEFT_UP:
            case BODY_RIGHT_DOWN:
            case BODY_RIGHT_UP:
                price = 15;
                break;
            case ENEMY_HEAD_DOWN:
            case ENEMY_HEAD_LEFT:
            case ENEMY_HEAD_RIGHT:
            case ENEMY_HEAD_UP:
                price = 100;
                break;
            case ENEMY_HEAD_DEAD:
                price = Integer.MAX_VALUE;
                break;
            case ENEMY_HEAD_EVIL:
                price = Integer.MAX_VALUE;
                break;
            case ENEMY_HEAD_FLY:
                price = 10;
                break;
            case ENEMY_HEAD_SLEEP:
            case ENEMY_TAIL_END_DOWN:
            case ENEMY_TAIL_END_LEFT:
            case ENEMY_TAIL_END_UP:
            case ENEMY_TAIL_END_RIGHT:
            case ENEMY_TAIL_INACTIVE:
            case ENEMY_BODY_HORIZONTAL:
            case ENEMY_BODY_VERTICAL:
            case ENEMY_BODY_LEFT_DOWN:
            case ENEMY_BODY_LEFT_UP:
            case ENEMY_BODY_RIGHT_DOWN:
            case ENEMY_BODY_RIGHT_UP:
                price = Integer.MAX_VALUE;
                break;
        }
        return price;
    }
}
