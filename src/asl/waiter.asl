// waiter

/* Initial beliefs */

//at(P) :- pos(P,X,Y) & pos(r1,X,Y).

/* Initial goal */

//!check(slots).

/* Plans */

//+!check(slots) : true <- next(slot); !check(slots).

