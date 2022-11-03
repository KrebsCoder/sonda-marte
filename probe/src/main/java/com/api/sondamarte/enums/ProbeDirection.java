package com.api.sondamarte.enums;

import com.api.sondamarte.models.Move;

public enum ProbeDirection {
    NORTH{
        @Override
        public ProbeDirection right() {
            return EAST;
        }
        @Override
        public  ProbeDirection left(){
            return WEST;
        }

        @Override
        public Move getNextX(int X, int Y) {
            return new Move(X, Y);
        }

        @Override
        public Move getNextY(int X, int Y) {
            return new Move(X, (Y + 1));
        }
    },
    SOUTH{
        @Override
        public ProbeDirection right() {
            return WEST;
        }

        @Override
        public ProbeDirection left() {
            return EAST;
        }

        @Override
        public Move getNextX(int X, int Y) {
            return new Move(X, Y);
        }


        @Override
        public Move getNextY(int X, int Y) {
            return new Move(X, (Y - 1));
        }
    },
    WEST{
        @Override
        public ProbeDirection right() {
            return NORTH;
        }

        @Override
        public ProbeDirection left() {
            return SOUTH;
        }

        @Override
        public Move getNextX(int X, int Y) {
            return new Move((X - 1), Y);
        }

        @Override
        public Move getNextY(int X, int Y) {
            return new Move(X, Y);
        }
    },
    EAST{
        @Override
        public ProbeDirection right() {
            return SOUTH;
        }

        @Override
        public ProbeDirection left() {
            return NORTH;
        }

        @Override
        public Move getNextX(int X, int Y) {
            return new Move((X + 1), Y);
        }

        @Override
        public Move getNextY(int X, int Y) {
            return new Move(X, Y);
        }
    };

    public abstract ProbeDirection right();

    public abstract ProbeDirection left();

    public abstract Move getNextX(int X, int Y);

    public abstract Move getNextY(int X, int Y);
}
