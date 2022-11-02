package com.api.sondamarte.enums;

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
    };

    public abstract ProbeDirection right();
    public abstract ProbeDirection left();
}
