// waiter

/* Initial beliefs */

//at(P) :- pos(P,X,Y) & pos(r1,X,Y).
patrolling.
pos(ord,8,2).
pos(corner1,2,5).
pos(corner2,6,5).
pos(corner3,6,11).
pos(corner4,2,11).
dest(6,5).

/* Initial goal */

//!check(slots).

/* Plans */

//+!check(slots) : true <- next(slot); !check(slots).
+pos(waiter,X1,Y1) : patrolling & X1 = 2 & Y1 = 5 & dest(2,5)
	<-	+dest(6,5);
		-dest(2,5);
		+newdestination.
+pos(waiter,X1,Y1) : patrolling & X1 = 6 & Y1 = 5 & dest(6,5)
	<-	+dest(6,11);
		-dest(6,5);
		+newdestination.
+pos(waiter,X1,Y1) : patrolling & X1 = 6 & Y1 = 11 & dest(6,11)
	<-	+dest(2,11);
		-dest(6,11);
		+newdestination.
+pos(waiter,X1,Y1) : patrolling & X1 = 2 & Y1 = 11 & dest(2,11)
	<-	+dest(2,5);
		-dest(2,11);
		+newdestination.

+newdestination : patrolling
	<-	?dest(DESTX,DESTY);
		-newdestination;
		moveTowards(DESTX,DESTY).

+pos(waiter,X1,Y1) : patrolling & not dest(X1,Y1)
	<-	?dest(DESTX,DESTY);
		moveTowards(DESTX,DESTY).

