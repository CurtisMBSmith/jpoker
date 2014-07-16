package ca.sariarra.poker.game.action;

import ca.sariarra.poker.table.component.HandOfPlay;

public enum HandAction implements HandActionExecutor {

	BETTING_ROUND_LIMIT() {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.limitBettingRound(false);
		}
	},
	BETTING_ROUND_LIMIT_BIG {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.limitBettingRound(true);
		}
	},
	BETTING_ROUND_NO_LIMIT {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.noLimitBettingRound();
		}
	},
	BETTING_ROUND_POT_LIMIT {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.potLimitBettingRound();
		}
	},
	SHOWDOWN {
		@Override
		public void execute(final HandOfPlay hand) {
			// TODO
		}
	},
	RESOLUTION {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.resolveHand();
		}
	},
	BRING_IN {
		@Override
		public void execute(final HandOfPlay hand) {
			// TODO
		}
	},
	POST_BLIND_BETS {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.postAntes();
			hand.postSmallBlind();
			hand.postBigBlind();
		}
	},
	DEAL_2_HOLE {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.dealHole(2);
		}
	},
	DEAL_4_HOLE {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.dealHole(4);
		}
	},
	DEAL_2_HOLE_1_EXPOSED {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.dealHole(2);
			hand.dealExposed(1);
		}
	},
	DEAL_1_EXPOSED {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.dealExposed(1);
		}
	},
	DEAL_1_HOLE {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.dealHole(1);
		}
	},
	DEAL_3_COMMUNITY {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.dealCommunity(3);
		}
	},
	DEAL_1_COMMUNITY {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.dealCommunity(1);
		}
	},
	DRAW_UPTO_5 {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.drawAction(5);

		}
	},
	DRAW_UPTO_4 {
		@Override
		public void execute(final HandOfPlay hand) {
			hand.drawAction(4);
		}
	};
}
