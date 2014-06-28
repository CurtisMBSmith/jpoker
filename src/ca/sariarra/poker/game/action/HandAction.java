package ca.sariarra.poker.game.action;

import ca.sariarra.poker.table.Table;

public enum HandAction implements HandActionExecutor {

	BETTING_ROUND_LIMIT() {
		@Override
		public void execute(final Table table) {
			table.limitBettingRound(false);
		}
	},
	BETTING_ROUND_LIMIT_BIG {
		@Override
		public void execute(final Table table) {
			table.limitBettingRound(true);
		}
	},
	BETTING_ROUND_NO_LIMIT {
		@Override
		public void execute(final Table table) {
			table.noLimitBettingRound();
		}
	},
	BETTING_ROUND_POT_LIMIT {
		@Override
		public void execute(final Table table) {
			table.potLimitBettingRound();
		}
	},
	SHOWDOWN {
		@Override
		public void execute(final Table table) {
			// TODO
		}
	},
	BRING_IN {
		@Override
		public void execute(final Table table) {
			// TODO
		}
	},
	POST_BLIND_BETS {
		@Override
		public void execute(final Table table) {
			table.postAntes();
			table.postSmallBlind();
			table.postBigBlind();
		}
	},
	DEAL_2_HOLE {
		@Override
		public void execute(final Table table) {
			table.dealHole(2);
		}
	},
	DEAL_4_HOLE {
		@Override
		public void execute(final Table table) {
			table.dealHole(4);
		}
	},
	DEAL_2_HOLE_1_EXPOSED {
		@Override
		public void execute(final Table table) {
			table.dealHole(2);
			table.dealExposed(1);
		}
	},
	DEAL_1_EXPOSED {
		@Override
		public void execute(final Table table) {
			table.dealExposed(1);
		}
	},
	DEAL_1_HOLE {
		@Override
		public void execute(final Table table) {
			table.dealHole(1);
		}
	},
	DEAL_3_COMMUNITY {
		@Override
		public void execute(final Table table) {
			table.dealCommunity(3);
		}
	},
	DEAL_1_COMMUNITY {
		@Override
		public void execute(final Table table) {
			table.dealCommunity(1);
		}
	},
	DRAW_UPTO_5 {
		@Override
		public void execute(final Table table) {
			table.drawAction(5);

		}
	},
	DRAW_UPTO_4 {
		@Override
		public void execute(final Table table) {
			table.drawAction(4);
		}
	};
}
