package ca.cmbs.poker.game.action;

import ca.cmbs.poker.game.component.hand.HandOfPlay;

public enum HandAction implements HandActionExecutor {

    BETTING_ROUND_LIMIT {
        @Override
        public boolean execute(final HandOfPlay hand) {
            return hand.limitBettingRound(false);
        }
    },
	BETTING_ROUND_LIMIT_BIG {
		@Override
        public boolean execute(final HandOfPlay hand) {
            return hand.limitBettingRound(true);
        }
    },
	BETTING_ROUND_NO_LIMIT {
		@Override
        public boolean execute(final HandOfPlay hand) {
            return hand.noLimitBettingRound();
        }
    },
	BETTING_ROUND_POT_LIMIT {
		@Override
        public boolean execute(final HandOfPlay hand) {
            return hand.potLimitBettingRound();
        }
    },
	SHOWDOWN {
		@Override
        public boolean execute(final HandOfPlay hand) {
            // TODO
            return true;
        }
    },
	RESOLUTION {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.resolveHand();
            return true;
        }
    },
	BRING_IN {
		@Override
        public boolean execute(final HandOfPlay hand) {
            // TODO
            return false;
        }
    },
	POST_BLIND_BETS {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.postAntes();
            hand.postSmallBlind();
			hand.postBigBlind();
            return false;
        }
    },
	DEAL_2_HOLE {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.dealHole(2);
            return false;
        }
    },
	DEAL_4_HOLE {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.dealHole(4);
            return false;
        }
    },
	DEAL_2_HOLE_1_EXPOSED {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.dealHole(2);
            hand.dealExposed(1);
            return false;
        }
    },
	DEAL_1_EXPOSED {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.dealExposed(1);
            return false;
        }
    },
	DEAL_1_HOLE {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.dealHole(1);
            return false;
        }
    },
	DEAL_3_COMMUNITY {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.dealCommunity(3);
            return false;
        }
    },
	DEAL_1_COMMUNITY {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.dealCommunity(1);
            return false;
        }
    },
	DRAW_UPTO_5 {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.drawAction(5);
            return false;
        }
    },
	DRAW_UPTO_4 {
		@Override
        public boolean execute(final HandOfPlay hand) {
            hand.drawAction(4);
            return false;
        }
    }
}
